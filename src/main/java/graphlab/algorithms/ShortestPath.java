package graphlab.algorithms;

import graphlab.datastructures.Edge;
import graphlab.datastructures.Graph;
import graphlab.datastructures.Node;
import graphlab.utils.ConsumerWithException;
import graphlab.utils.GraphUtils;

import java.util.*;
import java.util.function.Consumer;

public class ShortestPath {

    public static void dijkstra(Graph graph, Consumer<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        Node startingNode = GraphUtils.getStartingNode(graph);
        PriorityQueue<Node> toBeVisitedNodes = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.getPathCost(), n2.getPathCost()));
        graph.getNodes().forEach(node -> { node.setPathCost(node == startingNode ? 0 : Integer.MAX_VALUE); toBeVisitedNodes.add(node);});

        while (!toBeVisitedNodes.isEmpty()) {

            Node node = toBeVisitedNodes.poll();
            onVisitedNode.accept(node);

            for (Edge edge : node.getEdges()) {
                onVisitedEdge.accept(edge);

                int cost = node.getPathCost();
                int newCost = cost + edge.getCost();
                Node destinationNode = edge.getDestination();

                if (newCost < destinationNode.getPathCost()) {
                    destinationNode.setPathCost(newCost);
                    edge.getDestination().setParentForShortestPath(edge.getSource());

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
            onProcessedNode.accept(node);
        }
    }
}
