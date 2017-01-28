package graphlab.gui.search;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import javax.swing.*;


public class SearchGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final SearchGraphPanel bfsGraph;
    private final SearchGraphPanel dfsGraph;
    private final SearchGraphPanel ucsGraph;
    private final SearchGraphPanel aStarGraph;

    public SearchGraphsContainerPanel(GenericTab searchTab, GenericControlPanel genericControlPanel) {

        super(searchTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        dfsGraph = new SearchGraphPanel(Algorithm.DFS, searchTab, new AdjacencyListGraph(graph));
        bfsGraph = new SearchGraphPanel(Algorithm.BFS, searchTab, new AdjacencyListGraph(graph));
        ucsGraph = new SearchGraphPanel(Algorithm.UCS, searchTab, new AdjacencyListGraph(graph));
        aStarGraph = new SearchGraphPanel(Algorithm.ASTAR, searchTab, new AdjacencyListGraph(graph));
        
        add(dfsGraph);
        add(bfsGraph);
        add(ucsGraph);
        add(aStarGraph);

        addGraphPanel(dfsGraph);
        addGraphPanel(bfsGraph);
        addGraphPanel(ucsGraph);
        addGraphPanel(aStarGraph);

        sl.putConstraint(SpringLayout.WEST, dfsGraph, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, dfsGraph, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, bfsGraph, 5, SpringLayout.EAST, dfsGraph);
        sl.putConstraint(SpringLayout.NORTH, bfsGraph, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, ucsGraph, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, ucsGraph, 5, SpringLayout.SOUTH, dfsGraph);

        sl.putConstraint(SpringLayout.WEST, aStarGraph, 5, SpringLayout.EAST, dfsGraph);
        sl.putConstraint(SpringLayout.NORTH, aStarGraph, 5, SpringLayout.SOUTH, bfsGraph);
    }
}

