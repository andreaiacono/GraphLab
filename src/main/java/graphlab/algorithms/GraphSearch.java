package graphlab.algorithms;

import graphlab.datastructures.Edge;
import graphlab.datastructures.Graph;
import graphlab.datastructures.Node;
import graphlab.datastructures.NodeStatus;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraphSearch {

    public static void bfs(Graph graph, Consumer<Node> onVisitedNode, Consumer<Edge> onVisitedEdge, Consumer<Node> onProcessedNode) {
        genericFirstSearch(graph,
                (queue, node) -> queue.add(node),
                (queue) -> (Node) queue.poll(),
                onVisitedNode,
                onVisitedEdge,
                onProcessedNode);
    }

    public static void dfs(Graph graph, Consumer<Node> onVisitedNode, Consumer<Edge> onVisitedEdge, Consumer<Node> onProcessedNode) {
        genericFirstSearch(graph,
                (stack, node) -> stack.push(node),
                (stack) -> (Node) stack.pop(),
                onVisitedNode,
                onVisitedEdge,
                onProcessedNode);
    }

    public static void genericFirstSearch(Graph graph, BiConsumer<Deque, Node> nodePutter, Function<Deque, Node> nodeGetter, Consumer<Node> onVisitedNode, Consumer<Edge> onVisitedEdge, Consumer<Node> onProcessedNode) {

        graph.getNodes().forEach(node -> node.setStatus(NodeStatus.UNKNOWN));
        Deque<Node> queue = new ArrayDeque<>();
        nodePutter.accept(queue, graph.getNodes().get(0));

        while (!queue.isEmpty()) {
            Node node = nodeGetter.apply(queue);
            node.setStatus(NodeStatus.DISCOVERED);
            onVisitedNode.accept(node);
            for (Edge edge: node.getEdges()) {
                if ((edge.getDestination()).getStatus() == NodeStatus.UNKNOWN) {
                    nodePutter.accept(queue, edge.getDestination());
                    (edge.getDestination()).setStatus(NodeStatus.DISCOVERED);
                    onVisitedEdge.accept(edge);
                }
            }
            node.setStatus(NodeStatus.PROCESSED);
            onProcessedNode.accept(node);
        }
    }
}
