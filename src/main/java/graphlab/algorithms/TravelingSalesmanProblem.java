package graphlab.algorithms;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Edge;
import graphlab.datastructures.Node;
import graphlab.utils.ConsumerWithException;
import graphlab.utils.GraphUtils;

import java.util.*;
import java.util.function.Consumer;

public class TravelingSalesmanProblem {

    public static void nearestNeighbor(AdjacencyListGraph graph, ConsumerWithException<Edge> onUnvisitedEdge, ConsumerWithException<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        Node startingNode = GraphUtils.getStartingNode(graph);
        Node current = startingNode;
        Set<Node> visitedNodes = new HashSet<>();

        nn(graph, current, startingNode, visitedNodes, onUnvisitedEdge, onVisitedNode, onVisitedEdge, onProcessedNode, isCanceled);
    }

    private static boolean nn(AdjacencyListGraph graph, Node current, Node startingNode, Set<Node> visitedNodes, ConsumerWithException<Edge> onUnvisitedEdges, ConsumerWithException<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        if (isCanceled) {
            return false;
        }

        onVisitedNode.accept(current);
        visitedNodes.add(current);
        if (current.equals(startingNode) && visitedNodes.size() == graph.getNodes().size()) {
            return true;
        }
        List<Edge> edges = new ArrayList<>(current.getEdges());
        Collections.sort(edges, Comparator.comparingInt(Edge::getCost));
        for (Edge edge : edges) {
            Node destination = edge.getDestination();
            if (!visitedNodes.contains(destination) || (visitedNodes.size() == graph.getNodes().size() - 2 && destination.equals(startingNode))) {
                onVisitedEdge.accept(edge);
                visitedNodes.add(destination);
                destination.setPathParent(current);
                if (nn(graph, destination, startingNode, visitedNodes, onUnvisitedEdges, onVisitedNode, onVisitedEdge, onProcessedNode, isCanceled)) {
                    return true;
                }
                destination.setPathParent(null);
                visitedNodes.remove(destination);
                onUnvisitedEdges.accept(edge);
            }
        }

        onProcessedNode.accept(current);
        return false;
    }

}