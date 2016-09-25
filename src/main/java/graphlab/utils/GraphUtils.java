package graphlab.utils;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Node;

import java.util.Random;
import java.util.stream.IntStream;

public class GraphUtils {

    /**
     * creates a random graph with the specified number of nodes; each node can have at max
     * the specified number of edges, and the key of the node can have value between 0 and
     * the specified value. The edges of a node are are chosen among the closest.
     *
     * @param nodesNumber the number of nodes to create
     * @param edgesNumber the number of edges for each node
     * @param maxValue    the max value a node's key can have
     * @return
     */
    public static AdjacencyListGraph createRandomGraph(int nodesNumber, int edgesNumber, int maxValue) {

        Random random = new Random();
        AdjacencyListGraph graph = new AdjacencyListGraph();

        // creates the nodes
        for (int j = 0; j < nodesNumber; j++) {
            Node node = new Node(j, random.nextInt(maxValue), random.nextInt(maxValue));
            if (j == 0) {
                node.setStartNode(true);
            }
            graph.addNode(node);
        }

        // creates the edges
        java.util.List<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            int[] selectedEdges = new int[edgesNumber];
            Node currentNode = nodes.get(i);

            // for every node chooses the closest nodes to create the edge
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

        int index = 1 + new Random().nextInt(nodesNumber - 1);
        graph.getNodes().get(index).setSearchedNode(true);

        return graph;
    }

}
