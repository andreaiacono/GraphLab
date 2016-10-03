package graphlab.gui;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.utils.GraphUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GenericGraphsContainerPanel extends JPanel {

    protected AdjacencyListGraph graph;
    protected int edgesNumber;
    protected int nodesNumber;
    protected int speed;
    public List<GenericGraphPanel> genericGraphPanels;
    private GenericControlPanel genericControlPanel;


    public GenericGraphsContainerPanel(GenericTab genericTab, GenericControlPanel genericControlPanel) {
        this.genericControlPanel = genericControlPanel;
        genericGraphPanels = new ArrayList<>();
        edgesNumber = genericControlPanel.getEdgesNumber();
        nodesNumber = genericControlPanel.getNodesNumber();
        speed = genericControlPanel.getSpeed();
        graph = GraphUtils.createRandomGraph(genericControlPanel.getNodesNumber(), genericControlPanel.getEdgesNumber(), 500, true);
    }

    public void newGraph() {
        edgesNumber = genericControlPanel.getEdgesNumber();
        nodesNumber = genericControlPanel.getNodesNumber();
        AdjacencyListGraph graph = GraphUtils.createRandomGraph(nodesNumber, edgesNumber, 500, true);
        genericGraphPanels.forEach(panel -> panel.setGraph(new AdjacencyListGraph(graph)));
    }

    public void addGraphPanel(GenericGraphPanel genericGraphPanel) {
        genericGraphPanels.add(genericGraphPanel);
    }

    public void reset() {
        genericGraphPanels.forEach(panel -> panel.reset());
    }

    public void start() {
        genericGraphPanels.forEach(panel -> panel.start());
    }

    public void stop() {
        genericGraphPanels.forEach(panel -> panel.stop());
    }

    public void updateSpeed(int speed) { genericGraphPanels.forEach(panel -> panel.setSpeed(speed)); }
}
