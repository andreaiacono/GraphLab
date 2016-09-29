package graphlab.gui.traversal;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.SearchType;
import graphlab.gui.GraphContainerPanel;
import graphlab.gui.GraphPanel;
import graphlab.gui.Main;
import graphlab.gui.traversal.GraphTraversalPanel;

import java.awt.*;

public class TraversalGraphsContainerPanel extends GraphContainerPanel {

    private final GraphPanel bfsGraph;
    private final GraphPanel dfsGraph;
    private TraversalPanel traversalPanel;

    public TraversalGraphsContainerPanel(Main main, TraversalPanel traversalPanel) {
        super(main);
        this.traversalPanel = traversalPanel;
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);
        dfsGraph = new GraphTraversalPanel(SearchType.DFS, this, new AdjacencyListGraph(graph));
        add(dfsGraph);
        graphPanels.add(dfsGraph);
        bfsGraph = new GraphTraversalPanel(SearchType.BFS, this, new AdjacencyListGraph(graph));
        add(bfsGraph);
        graphPanels.add(bfsGraph);
    }

    @Override
    public void setOperationAsFinished() {
        traversalPanel.setTraversalAsFinished();
    }
}

