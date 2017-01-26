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

    public ShortestPathGraphsContainerPanel(GenericTab shortestPathTab, GenericControlPanel genericControlPanel) {

        super(shortestPathTab, genericControlPanel);

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

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
    }

}

