package graphlab.algorithms;

import graphlab.datastructures.*;
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

    public static void kruskal(Graph graph, Consumer<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        DisjointSets sets = new DisjointSets(graph.getNodes());

        // sort all edges
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        java.util.Collections.sort(sortedEdges, (e1, e2) -> Integer.compare(e1.getCost(), e2.getCost()));

        // greedly takes the minimum cost edge
        for (Edge edge : sortedEdges){
            if (sets.nodesAreInDifferentSets(edge.getSource(), edge.getDestination())) {
                onVisitedEdge.accept(edge);
                sets.merge(sets.find(edge.getDestination()), sets.find(edge.getSource()));
                edge.getDestination().setPathParent(edge.getSource());
            }
        }
    }
}
