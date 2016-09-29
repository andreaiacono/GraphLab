package graphlab.algorithms;

import graphlab.datastructures.Edge;
import graphlab.datastructures.Graph;
import graphlab.datastructures.Node;
import graphlab.datastructures.NodeStatus;
import graphlab.utils.ConsumerWithException;
import graphlab.utils.GraphUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Search {

    public static void bfs(Graph graph,
                           Consumer<Node> onVisitedNode,
                           ConsumerWithException<Edge> onVisitedEdge,
                           Consumer<Node> onProcessedNode,
                           Boolean isCanceled,
                           boolean stopAtSearched) throws Exception {

        genericFirstSearch(graph,
                (queue, node) -> queue.add(node),
                (queue) -> (Node) queue.poll(),
                onVisitedNode,
                onVisitedEdge,
                onProcessedNode,
                isCanceled,
                stopAtSearched);
    }

    public static void dfs(Graph graph,
                           Consumer<Node> onVisitedNode,
                           ConsumerWithException<Edge> onVisitedEdge,
                           Consumer<Node> onProcessedNode,
                           Boolean isCanceled,
                           boolean stopAtSearched) throws Exception {

        genericFirstSearch(graph,
                (stack, node) -> stack.push(node),
                (stack) -> (Node) stack.pop(),
                onVisitedNode,
                onVisitedEdge,
                onProcessedNode,
                isCanceled,
                stopAtSearched);
    }

    public static void genericFirstSearch(Graph graph,
                                          BiConsumer<Deque, Node> nodePutter,
                                          Function<Deque, Node> nodeGetter,
                                          Consumer<Node> onVisitedNode,
                                          ConsumerWithException<Edge> onVisitedEdge,
                                          Consumer<Node> onProcessedNode,
                                          Boolean isCanceled,
                                          boolean stopAtSearched) throws Exception {

        graph.getNodes().forEach(node -> node.setStatus(NodeStatus.UNKNOWN));
        Deque<Node> queue = new ArrayDeque<>();
        Node startingNode = GraphUtils.getStartingNode(graph);
        nodePutter.accept(queue, startingNode);

        while (!queue.isEmpty()) {
            Node node = nodeGetter.apply(queue);
            if (stopAtSearched && node.isTargetNode()) {
                return;
            }
            node.setStatus(NodeStatus.DISCOVERED);
            onVisitedNode.accept(node);
            for (Edge edge : node.getEdges()) {
                if ((edge.getDestination()).getStatus() == NodeStatus.UNKNOWN) {
                    nodePutter.accept(queue, edge.getDestination());
                    (edge.getDestination()).setStatus(NodeStatus.DISCOVERED);
                    onVisitedEdge.accept(edge);
                }
            }
            node.setStatus(NodeStatus.PROCESSED);
            onProcessedNode.accept(node);
            if (isCanceled) return;
        }
    }

    public static void astar(Graph graph, Consumer<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {
        genericCostSearch(graph, onVisitedNode, onVisitedEdge, onProcessedNode, isCanceled, true);
    }

    public static void ucs(Graph graph, Consumer<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {
        genericCostSearch(graph, onVisitedNode, onVisitedEdge, onProcessedNode, isCanceled, false);
    }

    public static void genericCostSearch(Graph graph, Consumer<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled, boolean useHeuristic) throws Exception {
        graph.getNodes().forEach(node -> node.setStatus(NodeStatus.UNKNOWN));
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getPathCost(), o2.getPathCost()));
        Node startingNode = GraphUtils.getStartingNode(graph);
        Node targetNode = GraphUtils.getTargetNode(graph);

        graph.getNodes().forEach(node -> node.setPathCost(Integer.MAX_VALUE));
        startingNode.setPathCost(0);
        queue.add(startingNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode.isTargetNode()) {
                return;
            }
            currentNode.setStatus(NodeStatus.DISCOVERED);
            onVisitedNode.accept(currentNode);

            for (Edge edge : currentNode.getEdges()) {
                Node child = edge.getDestination();

                int oldChildCost = child.getPathCost();
                int heuristicCost = useHeuristic ? GraphUtils.getDistance(targetNode, child) : 0;
                child.setPathCost(currentNode.getPathCost() + edge.getCost() + heuristicCost);

                if ((edge.getDestination()).getStatus() == NodeStatus.UNKNOWN) {
                    queue.add(edge.getDestination());
                    (edge.getDestination()).setStatus(NodeStatus.DISCOVERED);
                    onVisitedEdge.accept(edge);
                }
                else if ((queue.contains(child)) && (child.getPathCost() < oldChildCost)) {
                    // Java priority queue does not handle dynamic values,
                    // so this a trick (tough worsening performance)
                    queue.remove(child);
                    queue.add(child);
                }
            }
            currentNode.setStatus(NodeStatus.PROCESSED);
            onProcessedNode.accept(currentNode);
            if (isCanceled) return;
        }
    }

}
