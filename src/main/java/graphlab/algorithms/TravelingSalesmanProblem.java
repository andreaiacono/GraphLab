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
        else if (visitedNodes.size() == graph.getNodes().size()) {
            return false;
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

    public static void twoOpt(AdjacencyListGraph graph, ConsumerWithException<Edge> onUnvisitedEdge, ConsumerWithException<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Boolean isCanceled) throws Exception {

        List<Node> path = new ArrayList<>();
        Node startingNode = GraphUtils.getStartingNode(graph);
        path.add(startingNode);
        firstValidRoute(graph, startingNode, startingNode, path);
        setPath(path, onVisitedEdge);
        int n = path.size();
        int minDistance = getTotalDistance(path);
        while (true) {
            int startingDistance = minDistance;
            for (int i = 1; i < n; i++) {
                boolean foundBetterRoute = false;
                for (int j = i + 2; j < n; j++) {
                    Edge edge1 = getEdgeFromTo(path.get(i-1), path.get(i));
                    Edge edge2 = getEdgeFromTo(path.get(j-1), path.get(j));
                    Edge newEdge1 = getEdgeFromTo(edge1.getSource(), edge2.getSource());
                    Edge newEdge2 = getEdgeFromTo(edge1.getDestination(), edge2.getDestination());

                    if (newEdge1 != null && newEdge2 != null) {
                        List<Node> newPath = swapEdges(path, i, j);
                        int newDistance = getTotalDistance(newPath);
                        if (minDistance > newDistance) {
                            unsetPath(edge1, edge2, onUnvisitedEdge);
                            setPath(newEdge1, newEdge2, onVisitedEdge);
                            minDistance = newDistance;
                            path = newPath;
                            foundBetterRoute = true;
                            break;
                        }
                    }
                }
                if (foundBetterRoute) {
                    break;
                }
            }

            if (startingDistance == minDistance && minDistance < Integer.MAX_VALUE) {
                break;
            }
        }
    }

    private static List<Node> swapEdges(List<Node> path, int i, int j) {
        List<Node> result = new ArrayList<>();
        for (int k = 0; k < i; k++) {
            result.add(path.get(k));
        }

        List<Node> swapped = new ArrayList<>();
        for (int k = i; k < j; k++) {
            swapped.add(path.get(k));
        }
        Collections.reverse(swapped);
        result.addAll(swapped);

        for (int k = j; k < path.size(); k++) {
            result.add(path.get(k));
        }
        return result;
    }

    private static void setPath(Edge edge1, Edge edge2, ConsumerWithException<Edge> onVisitedEdge) throws Exception {
        onVisitedEdge.accept(edge1);
        onVisitedEdge.accept(edge2);
    }

    private static void unsetPath(Edge edge1, Edge edge2, ConsumerWithException<Edge> onUnvisitedEdge) throws Exception {
        Edge swappedEdge1 = getEdgeFromTo(edge1.getDestination(), edge1.getSource());
        Edge swappedEdge2 = getEdgeFromTo(edge2.getDestination(), edge2.getSource());
        onUnvisitedEdge.accept(edge1);
        onUnvisitedEdge.accept(edge2);
        onUnvisitedEdge.accept(swappedEdge1);
        onUnvisitedEdge.accept(swappedEdge2);
    }

    private static void setPath(List<Node> path, ConsumerWithException<Edge> onVisitedEdge) throws Exception {
        for (int k = 1; k < path.size(); k++) {
            onVisitedEdge.accept(getEdgeFromTo(path.get(k), path.get(k - 1)));
        }
    }

    private static Edge getEdgeFromTo(Node from, Node to) {

        for (Edge edge : from.getEdges()) {
            if (edge.getDestination().equals(to)) {
                return edge;
            }
        }
        return null;
    }

    private static int getTotalDistance(List<Node> path) {
        int total = 0;
        for (int i = 1; i < path.size(); i++) {
            total += getDistance(path.get(i - 1), path.get(i));
        }
        return total;
    }

    private static int getDistance(Node i, Node j) {
        for (Edge edge : i.getEdges()) {
            if (edge.getDestination().equals(j)) {
                return edge.getCost();
            }
        }
        return Integer.MAX_VALUE;
    }

    private static boolean firstValidRoute(AdjacencyListGraph graph, Node current, Node startingNode, List<Node> path) throws Exception {

        if (current.equals(startingNode) && path.size() == graph.getNodes().size()+1) {
            return true;
        }
        List<Edge> edges = new ArrayList<>(current.getEdges());
        Collections.sort(edges, Comparator.comparingInt(Edge::getCost));
        for (Edge edge : edges) {
            Node destination = edge.getDestination();
            if (!path.contains(destination) || (path.size() == graph.getNodes().size() && destination.equals(startingNode))) {
                path.add(destination);
                destination.setPathParent(current);

                if (firstValidRoute(graph, destination, startingNode, path)) {
                    return true;
                }
                destination.setPathParent(null);
                path.remove(destination);
            }
        }
        return false;
    }

}