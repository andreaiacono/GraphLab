package graphlab.gui;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.SearchType;
import graphlab.utils.GraphUtils;

import javax.swing.*;
import java.awt.*;

public class TraversalPanel extends JPanel {

    private final GraphPanel bfsGraph;
    private final GraphPanel dfsGraph;
    private int edgesNumber = 8;
    private int nodesNumber = 100;
    private Main main;

    public TraversalPanel(Main main) {
        this.main = main;
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        AdjacencyListGraph graph = GraphUtils.createRandomGraph(nodesNumber, edgesNumber, 500);
        dfsGraph = new GraphPanel(SearchType.DFS, this, new AdjacencyListGraph(graph));
        add(dfsGraph);
        bfsGraph = new GraphPanel(SearchType.BFS, this, new AdjacencyListGraph(graph));
        add(bfsGraph);
    }


    public void reset() {
        dfsGraph.reset();
        bfsGraph.reset();
    }

    public void newGraph() {
        AdjacencyListGraph graph = GraphUtils.createRandomGraph(nodesNumber, edgesNumber, 500);
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

    public void stopSearch() {
        bfsGraph.stopSearch();
        dfsGraph.stopSearch();
    }

    public void setSearchAsFinished() {
        main.setSearchAsFinished();
    }

    public void setEdgesNumber(int edgesNumber) {
        this.edgesNumber = edgesNumber;
    }

    public void setNodesNumber(int nodesNumber) {
        this.nodesNumber = nodesNumber;
    }

    public void setProgressBar(int value) {
        main.setProgressBar(value);
    }
}

