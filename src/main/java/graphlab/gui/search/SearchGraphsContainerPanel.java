package graphlab.gui.search;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GraphContainerPanel;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;


public class SearchGraphsContainerPanel extends GraphContainerPanel {

    private final GraphSearchPanel bfsGraph;
    private final GraphSearchPanel dfsGraph;
    private final GraphSearchPanel ucsGraph;
    private final GraphSearchPanel aStarGraph;
    private SearchPanel searchPanel;

    public SearchGraphsContainerPanel(Main main, SearchPanel searchPanel) {
        super(main);
        this.searchPanel = searchPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        dfsGraph = new GraphSearchPanel(Algorithm.DFS, this, new AdjacencyListGraph(graph));
        row1.add(dfsGraph);
        graphPanels.add(dfsGraph);

        bfsGraph = new GraphSearchPanel(Algorithm.BFS, this, new AdjacencyListGraph(graph));
        graphPanels.add(bfsGraph);
        row1.add(bfsGraph);

        ucsGraph = new GraphSearchPanel(Algorithm.UCS, this, new AdjacencyListGraph(graph));
        graphPanels.add(ucsGraph);
        row2.add(ucsGraph);

        aStarGraph = new GraphSearchPanel(Algorithm.ASTAR, this, new AdjacencyListGraph(graph));
        graphPanels.add(aStarGraph);
        row2.add(aStarGraph);

        add(row1);
        add(row2);
    }

    @Override
    public void setOperationAsFinished() {
        searchPanel.setSearchAsFinished();
    }

}

