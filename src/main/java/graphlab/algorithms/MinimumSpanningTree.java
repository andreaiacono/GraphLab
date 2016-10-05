package graphlab.algorithms;

import graphlab.datastructures.Edge;
import graphlab.datastructures.Graph;
import graphlab.datastructures.Node;
import graphlab.datastructures.NodeStatus;
import graphlab.utils.ConsumerWithException;
import graphlab.utils.GraphUtils;

import java.util.*;
import java.util.function.Consumer;

public class MinimumSpanningTree {

    public static void prim(Graph graph, Consumer<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        Set<Node> unvisitedNodes = new HashSet<>();
        graph.getNodes().stream().filter(node -> !node.isStartNode()).forEach(unvisitedNodes::add);
        Queue<Edge> availableEdges = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.getCost(), e2.getCost()));

        Node currentNode = GraphUtils.getStartingNode(graph);
        while (!unvisitedNodes.isEmpty()) {

            onVisitedNode.accept(currentNode);
            unvisitedNodes.remove(currentNode);

            // adds to the PQ all the edges of the new node
            currentNode.getEdges().stream()
                       .filter(edge -> unvisitedNodes.contains(edge.getDestination()))
                       .forEach(availableEdges::add);

            // currentNode is now processed
            currentNode.setStatus(NodeStatus.PROCESSED);
            onProcessedNode.accept(currentNode);

            // gets the cheapest edge (from any source node)
            Edge closestEdge = availableEdges.poll();
            if (closestEdge != null) {

                onVisitedEdge.accept(closestEdge);

                // sets the destination as part of the MST
                closestEdge.getDestination().setPathParent(closestEdge.getSource());

                // removes other edges pointing to the destination of the closest edge
                availableEdges.removeIf(edge -> edge.getDestination().equals(closestEdge.getDestination()));
                currentNode = closestEdge.getDestination();
            }

            if (isCanceled) {
                return;
            }
        }
    }
}
