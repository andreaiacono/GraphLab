package graphlab.gui.traversal;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import javax.swing.*;

/**
 * The panel that contains all the GenericGraphPanel for the traversal.
 */
public class TraversalGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel bfsGraph;
    private final GenericGraphPanel dfsGraph;

    public TraversalGraphsContainerPanel(GenericTab traversalTab, GenericControlPanel genericControlPanel) {

        super(traversalTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        dfsGraph = new TraversalGraphPanel(Algorithm.DFS, traversalTab, new AdjacencyListGraph(graph));
        bfsGraph = new TraversalGraphPanel(Algorithm.BFS, traversalTab, new AdjacencyListGraph(graph));
        add(dfsGraph);
        add(bfsGraph);

        addGraphPanel(dfsGraph);
        addGraphPanel(bfsGraph);

        sl.putConstraint(SpringLayout.WEST, dfsGraph, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, dfsGraph, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, bfsGraph, 5, SpringLayout.EAST, dfsGraph);
        sl.putConstraint(SpringLayout.NORTH, bfsGraph, 5, SpringLayout.NORTH, this);
    }
}

