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

        dijkstra = new ShortestPathPanel(Algorithm.DIJKSTRA, traversalTab, new AdjacencyListGraph(graph));
        bellmanFord = new ShortestPathPanel(Algorithm.BELLMANFORD, traversalTab, new AdjacencyListGraph(graph));

        add(dijkstra);
        add(bellmanFord);

        addGraphPanel(dijkstra);
        addGraphPanel(bellmanFord);
    }

}

