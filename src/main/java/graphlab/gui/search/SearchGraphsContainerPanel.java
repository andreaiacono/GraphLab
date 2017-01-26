package graphlab.gui.search;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import javax.swing.*;
import java.awt.*;


public class SearchGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final SearchGraphPanel bfsGraph;
    private final SearchGraphPanel dfsGraph;
    private final SearchGraphPanel ucsGraph;
    private final SearchGraphPanel aStarGraph;

    public SearchGraphsContainerPanel(GenericTab searchTab, GenericControlPanel genericControlPanel) {

        super(searchTab, genericControlPanel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        dfsGraph = new SearchGraphPanel(Algorithm.DFS, searchTab, new AdjacencyListGraph(graph));
        row1.add(dfsGraph);
        addGraphPanel(dfsGraph);

        bfsGraph = new SearchGraphPanel(Algorithm.BFS, searchTab, new AdjacencyListGraph(graph));
        addGraphPanel(bfsGraph);
        row1.add(bfsGraph);

        ucsGraph = new SearchGraphPanel(Algorithm.UCS, searchTab, new AdjacencyListGraph(graph));
        addGraphPanel(ucsGraph);
        row2.add(ucsGraph);

        aStarGraph = new SearchGraphPanel(Algorithm.ASTAR, searchTab, new AdjacencyListGraph(graph));
        addGraphPanel(aStarGraph);
        row2.add(aStarGraph);

        add(row1);
        add(row2);
    }
}

