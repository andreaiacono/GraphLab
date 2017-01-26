package graphlab.algorithms;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Edge;
import graphlab.datastructures.Node;
import graphlab.utils.ConsumerWithException;
import graphlab.utils.GraphUtils;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ConnectedComponents {

    static Color[] colors = new Color[] { new Color(153, 76, 0), new Color(0 ,128, 255), Color.YELLOW, new Color(0, 102, 102), new Color(0, 153, 0), new Color(127, 0, 255), Color.MAGENTA, new Color(153, 0, 0), new Color (255, 0, 255), new Color(204, 204, 255)};

    public static void connectedComponents(AdjacencyListGraph graph, ConsumerWithException<Node> visitNode, ConsumerWithException<Edge> visitEdge, Consumer<Node> processNode, Boolean isCanceled) throws Exception {
        Deque<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        int counter = 0;

        Node startingNode = getNextStartingNode(graph, visited);
        while (startingNode != null) {
            bfs(visitNode, visitEdge, processNode, queue, visited, startingNode, isCanceled, colors[counter % colors.length]);
            startingNode = getNextStartingNode(graph, visited);
            counter ++;
        }
    }

    private static Node getNextStartingNode(AdjacencyListGraph graph, Set<Node> visited) throws Exception {

        if (visited.size() == 0) {
            return GraphUtils.getStartingNode(graph);
        }
        for (Node node: graph.getNodes()) {
            if (! visited.contains(node)) {
                return node;
            }
        }
        return null;
    }


    private static void bfs(ConsumerWithException<Node> onVisitedNode, ConsumerWithException<Edge> onVisitedEdge, Consumer<Node> onProcessedNode, Deque<Node> queue, Set<Node> visited, Node startingNode, boolean isCanceled, Color edgeColor) throws Exception {

        queue.add(startingNode);
        visited.add(startingNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            current.setColor(edgeColor);
            onVisitedNode.accept(current);

            for (Edge edge: current.getEdges()) {
                Node destination = edge.getDestination();
                if (!visited.contains(destination)) {
                    visited.add(destination);
                    queue.add(destination);
                    onVisitedEdge.accept(edge);
                }
            }

            onProcessedNode.accept(current);
            if (isCanceled) return;
        }
    }
}
