package graphlab.utils;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Graph;
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
    public static AdjacencyListGraph createRandomGraph(int nodesNumber, int edgesNumber, int maxValue, boolean directed) {

        Random random = new Random();
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.setDirected(directed);

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
                    int index = -1;
                    for (int j = 0; j < nodes.size(); j++) {
                        final int jj = j; // needed a final variable instead of j
                        if (i == j || IntStream.of(selectedEdges).anyMatch(x -> x == jj)) continue;
                        Node node = nodes.get(j);
                        int distance = Math.abs(node.getX() - currentNode.getX()) + Math.abs(node.getY() - currentNode.getY());
                        if (distance < min) {
                            min = distance;
                            index = j;
                        }
                    }
                    if (index >= 0) {
                        selectedEdges[k] = index;
                        currentNode.addEdge(nodes.get(index));
                        if (!directed) {
                            nodes.get(index).addEdge(currentNode);
                        }
                    }
                }
            }
        }

        int index = 1 + new Random().nextInt(nodesNumber - 1);
        graph.getNodes().get(index).setTargetNode(true);

        return graph;
    }


    public static int getDistance(Node start, Node end) {
        return (int) (Math.sqrt(Math.pow(Math.abs(start.getX() - end.getX()),2) + Math.pow(Math.abs(start.getY() - end.getY()),2)));
    }

    public static Node getStartingNode(Graph graph) throws Exception {
        return graph.getNodes().stream().filter(Node::isStartNode).findFirst().orElseThrow(() -> new Exception("Starting node not present."));
    }

    public static Node getTargetNode(Graph graph) throws Exception {
        return graph.getNodes().stream().filter(Node::isTargetNode).findFirst().orElseThrow(() -> new Exception("Target node not present."));
    }
}
