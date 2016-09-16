package graphlab.gui;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Node;
import graphlab.datastructures.SearchType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;

public class DrawingPanel extends JPanel {

    private final GraphPanel bfsGraph;
    private final GraphPanel dfsGraph;
    private int edgesNumber = 8;
    private int nodesNumber = 100;

    public DrawingPanel() {
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        AdjacencyListGraph graph = createRandomGraph();
        dfsGraph = new GraphPanel(SearchType.DFS, this, new AdjacencyListGraph(graph));
        add(dfsGraph);
        bfsGraph = new GraphPanel(SearchType.BFS, this, new AdjacencyListGraph(graph));
        add(bfsGraph);
    }

    private AdjacencyListGraph createRandomGraph() {
        int canvasSize = 500;
        Random random = new Random();
        AdjacencyListGraph graph = new AdjacencyListGraph();
        for (int j = 0; j < nodesNumber; j++) {
            Node node = new Node(j, random.nextInt(canvasSize), random.nextInt(canvasSize));
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

    public void reset() {
        dfsGraph.reset();
        bfsGraph.reset();
    }

    public void newGraph() {
        AdjacencyListGraph graph = createRandomGraph();
        bfsGraph.setGraph(new AdjacencyListGraph(graph));
        dfsGraph.setGraph(new AdjacencyListGraph(graph));
    }

    public void search() {
        dfsGraph.search();
        bfsGraph.search();
    }

    public void setSpeed(int speed) {
        bfsGraph.setSpeed(speed);
        dfsGraph.setSpeed(speed);
    }

    public void setEdgesNumber(int edgesNumber) {
        this.edgesNumber = edgesNumber;
    }

    public void setNodesNumber(int nodesNumber) {
        this.nodesNumber = nodesNumber;
    }
}

