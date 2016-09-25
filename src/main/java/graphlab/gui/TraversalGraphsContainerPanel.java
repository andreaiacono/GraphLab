package graphlab.gui;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.SearchType;

import java.awt.*;

public class TraversalGraphsContainerPanel extends GraphContainerPanel {

    private final GraphPanel bfsGraph;
    private final GraphPanel dfsGraph;

    public TraversalGraphsContainerPanel(Main main) {
        super(main);
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
        main.setTraversalAsFinished();
    }
}

