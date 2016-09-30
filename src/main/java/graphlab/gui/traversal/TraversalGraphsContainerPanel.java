package graphlab.gui.traversal;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.algorithms.Algorithm;
import graphlab.gui.GraphContainerPanel;
import graphlab.gui.GraphPanel;
import graphlab.gui.Main;

import java.awt.*;

public class TraversalGraphsContainerPanel extends GraphContainerPanel {

    private final GraphPanel bfsGraph;
    private final GraphPanel dfsGraph;
    private TraversalPanel traversalPanel;

    public TraversalGraphsContainerPanel(Main main, TraversalPanel traversalPanel, int nodesNumber, int edgesNumber) {
        super(main, nodesNumber, edgesNumber);
        this.traversalPanel = traversalPanel;
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);
        dfsGraph = new GraphTraversalPanel(Algorithm.DFS, this, new AdjacencyListGraph(graph));
        add(dfsGraph);
        graphPanels.add(dfsGraph);
        bfsGraph = new GraphTraversalPanel(Algorithm.BFS, this, new AdjacencyListGraph(graph));
        add(bfsGraph);
        graphPanels.add(bfsGraph);
    }

    @Override
    public void setOperationAsFinished() {
        traversalPanel.setTraversalAsFinished();
    }
}

