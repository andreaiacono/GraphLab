package graphlab.gui;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public abstract class GraphContainerPanel extends JPanel {

    protected Main main;
    protected int edgesNumber = 8;
    protected int nodesNumber = 100;
    protected List<GraphPanel> graphPanels;
    protected AdjacencyListGraph graph;

    public GraphContainerPanel(Main main) {
        this.main = main;
        graph = GraphUtils.createRandomGraph(nodesNumber, edgesNumber, 500);
        graphPanels = new ArrayList<>();
    }

    public abstract void setOperationAsFinished();

    public void setEdgesNumber(int edgesNumber) {
        this.edgesNumber = edgesNumber;
    }

    public void setNodesNumber(int nodesNumber) {
        this.nodesNumber = nodesNumber;
    }

    public void setProgressBar(int value) {
        main.setProgressBar(value);
    }

    public void newGraph() {
        AdjacencyListGraph graph = GraphUtils.createRandomGraph(nodesNumber, edgesNumber, 500);
        graphPanels.forEach(panel -> panel.setGraph(new AdjacencyListGraph(graph)));
    }

    public void reset() {
        graphPanels.forEach(panel -> panel.reset());
    }

    public void search() {
        graphPanels.forEach(panel -> panel.startOperation());
    }

    public void setSpeed(int speed) {
        graphPanels.forEach(panel -> panel.setSpeed(speed));
    }

    public void stopSearch() {
        graphPanels.forEach(panel -> panel.stopOperation());
    }
}
