package graphlab.utils;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Node;

import java.util.Random;
import java.util.stream.IntStream;

public class GraphUtils {

    public static AdjacencyListGraph createRandomGraph(int nodesNumber, int edgesNumber, int canvasSize) {

        Random random = new Random();
        AdjacencyListGraph graph = new AdjacencyListGraph();
        for (int j = 0; j < nodesNumber; j++) {
            Node node = new Node(j, random.nextInt(canvasSize), random.nextInt(canvasSize));
            if (j == 0) {
                node.setStartNode(true);
            }
            graph.addNode(node);
        }

        java.util.List<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            int[] selectedEdges = new int[edgesNumber];
            Node currentNode = nodes.get(i);
            for (int k = 0; k < edgesNumber; k++) {
                if (random.nextBoolean()) {
                    int min = Integer.MAX_VALUE;
                    int index = 0;
                    for (int j = 0; j < nodes.size(); j++) {
                        final int jj = j;
                        if (i == j || IntStream.of(selectedEdges).anyMatch(x -> x == jj)) continue;
                        Node node = nodes.get(j);
                        int distance = Math.abs(node.getX() - currentNode.getX()) + Math.abs(node.getY() - currentNode.getY());
                        if (distance < min) {
                            min = distance;
                            index = j;
                        }
                    }
                    selectedEdges[k] = index;
                    currentNode.addEdge(nodes.get(index));
                }
            }
        }
        return graph;
    }

}
