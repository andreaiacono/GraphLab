package graphlab.algorithms;

import graphlab.datastructures.Edge;
import graphlab.datastructures.Graph;
import graphlab.datastructures.Node;
import graphlab.datastructures.NodeStatus;
import graphlab.utils.ConsumerWithException;
import graphlab.utils.GraphUtils;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class ShortestPath {

    public static void dijkstra(Graph graph, ConsumerWithException<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        Node startingNode = GraphUtils.getStartingNode(graph);
        PriorityQueue<Node> toBeVisitedNodes = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.getPathCost(), n2.getPathCost()));
        graph.getNodes().forEach(node -> {
            node.setPathCost(node == startingNode ? 0 : Integer.MAX_VALUE);
            toBeVisitedNodes.add(node);
        });

        while (!toBeVisitedNodes.isEmpty()) {

            Node node = toBeVisitedNodes.poll();
            onVisitedNode.accept(node);
            node.setStatus(NodeStatus.DISCOVERED);

            for (Edge edge : node.getEdges()) {
                onVisitedEdge.accept(edge);

                int cost = node.getPathCost();
                int newCost = cost + edge.getCost();
                Node destinationNode = edge.getDestination();

                if (newCost < destinationNode.getPathCost()) {
                    destinationNode.setPathCost(newCost);
                    edge.getDestination().setPathParent(edge.getSource());

                    // no dynamic recomputation of priority in java.util.PriorityQueue,
                    // so remove and re-insert
                    if (toBeVisitedNodes.remove(destinationNode)) {
                        toBeVisitedNodes.add(destinationNode);
                    }
                }
            }
            if (isCanceled) {
                return;
            }
            node.setStatus(NodeStatus.PROCESSED);
            onProcessedNode.accept(node);
        }
    }

    public static void bellmanFord(Graph graph, ConsumerWithException<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Callable bellmanFordStepIncrementer, Boolean isCanceled) throws Exception {

        Node startingNode = GraphUtils.getStartingNode(graph);
        graph.getNodes().forEach(node -> node.setPathCost(node == startingNode ? 0 : Integer.MAX_VALUE));
        Set<Edge> edges = graph.getEdges();

        for (int j=0; j<graph.getNodes().size()-1; j++) {
            onVisitedNode.accept(graph.getNodes().get(j));
            for (Edge edge : edges) {
                if (isCanceled) {
                    return;
                }
                // only the first time draws the edges, otherwise it takes too much to draw the GUI
                if (j==0) {
                    bellmanFordStepIncrementer.call();
                    onVisitedEdge.accept(edge);
                }
                Node sourceNode = edge.getSource();
                Node destinationNode = edge.getDestination();
                int edgeCost = edge.getCost();

                if (sourceNode.getPathCost() != Integer.MAX_VALUE &&
                        sourceNode.getPathCost() + edgeCost < destinationNode.getPathCost()) {
                    destinationNode.setPathCost(sourceNode.getPathCost() + edgeCost);
                    destinationNode.setPathParent(sourceNode);
                }
            }
        }

        for (Edge edge : edges) {
            if (isCanceled) {
                return;
            }
            Node sourceNode = edge.getSource();
            Node destinationNode = edge.getDestination();
            int edgeCost = edge.getCost();

            if (sourceNode.getPathCost() != Integer.MAX_VALUE &&
                    sourceNode.getPathCost() + edgeCost < destinationNode.getPathCost()) {
                throw new Exception("Graph contains negative cycles");
            }
        }
    }
}
