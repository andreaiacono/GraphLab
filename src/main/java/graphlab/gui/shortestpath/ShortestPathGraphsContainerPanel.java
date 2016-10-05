package graphlab.gui.shortestpath;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.algorithms.Algorithm;
import graphlab.gui.*;

import java.awt.*;

/**
 * The panel that contains all the GenericGraphPanel for the shortest path tab.
 */
public class ShortestPathGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel dijkstra;
    private final GenericGraphPanel bellmanFord;
    private GenericTab traversalTab;

    public ShortestPathGraphsContainerPanel(GenericTab traversalTab, GenericControlPanel genericControlPanel) {

        super(traversalTab, genericControlPanel);
        this.traversalTab = traversalTab;

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        dijkstra = new ShortestPathGraphPanel(Algorithm.DIJKSTRA, traversalTab, new AdjacencyListGraph(graph));
        dijkstra.setDrawEdgesWithColorGradient(false);

        bellmanFord = new ShortestPathGraphPanel(Algorithm.BELLMANFORD, traversalTab, new AdjacencyListGraph(graph));
        bellmanFord.setDrawEdgesWithColorGradient(false);
        bellmanFord.setDrawEdgesWithGrayShade(true);

        add(dijkstra);
        add(bellmanFord);

        addGraphPanel(dijkstra);
        addGraphPanel(bellmanFord);
    }

}

