package graphlab.gui.shortestpath;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.algorithms.Algorithm;
import graphlab.gui.GraphContainerPanel;
import graphlab.gui.Main;

import java.awt.*;


public class ShortestPathContainerPanel extends GraphContainerPanel {

    private final GraphShortestPathPanel dijkstra;
    private final GraphShortestPathPanel bellmanFord;
    private final ShortestPathPanel shortestPathPanel;

    public ShortestPathContainerPanel(Main main, ShortestPathPanel shortestPathPanel, int nodesNumber, int edgesNumber) {
        super(main, nodesNumber, edgesNumber);
        this.shortestPathPanel = shortestPathPanel;
        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);
        dijkstra = new GraphShortestPathPanel(Algorithm.DIJKSTRA, this, new AdjacencyListGraph(graph));
        add(dijkstra);
        graphPanels.add(dijkstra);
        bellmanFord = new GraphShortestPathPanel(Algorithm.BELLMANFORD, this, new AdjacencyListGraph(graph));
        add(bellmanFord);
        graphPanels.add(bellmanFord);
    }

    @Override
    public void setOperationAsFinished() {
        shortestPathPanel.setSearchAsFinished();
    }

}

