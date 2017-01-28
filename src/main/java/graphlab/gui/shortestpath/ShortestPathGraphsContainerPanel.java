package graphlab.gui.shortestpath;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import javax.swing.*;

/**
 * The panel that contains all the GenericGraphPanel for the shortest path tab.
 */
public class ShortestPathGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel dijkstra;
    private final GenericGraphPanel bellmanFord;

    public ShortestPathGraphsContainerPanel(GenericTab shortestPathTab, GenericControlPanel genericControlPanel) {

        super(shortestPathTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        dijkstra = new ShortestPathGraphPanel(Algorithm.DIJKSTRA, shortestPathTab, new AdjacencyListGraph(graph));
        dijkstra.setDrawEdgesWithColorGradient(false);
        dijkstra.setWorkingEdgesWidth(2);

        bellmanFord = new ShortestPathGraphPanel(Algorithm.BELLMANFORD, shortestPathTab, new AdjacencyListGraph(graph));
        bellmanFord.setDrawEdgesWithColorGradient(false);
        bellmanFord.setDrawEdgesWithGrayShade(true);
        bellmanFord.setWorkingEdgesWidth(2);

        add(dijkstra);
        add(bellmanFord);

        addGraphPanel(dijkstra);
        addGraphPanel(bellmanFord);

        sl.putConstraint(SpringLayout.WEST, dijkstra, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, dijkstra, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, bellmanFord, 5, SpringLayout.EAST, dijkstra);
        sl.putConstraint(SpringLayout.NORTH, bellmanFord, 5, SpringLayout.NORTH, this);
    }

}

