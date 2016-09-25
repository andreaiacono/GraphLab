package graphlab.gui;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.SearchType;


public class SearchGraphsContainerPanel extends GraphContainerPanel {

    private final GraphSearchPanel bfsGraph;
    private final GraphSearchPanel dfsGraph;
    private final GraphSearchPanel ucsGraph;
    private final GraphSearchPanel aStarGraph;

    public SearchGraphsContainerPanel(Main main) {
        super(main);
        dfsGraph = new GraphSearchPanel(SearchType.DFS, this, new AdjacencyListGraph(graph));
        add(dfsGraph);
        graphPanels.add(dfsGraph);
        bfsGraph = new GraphSearchPanel(SearchType.BFS, this, new AdjacencyListGraph(graph));
        add(bfsGraph);
        graphPanels.add(bfsGraph);
        ucsGraph = new GraphSearchPanel(SearchType.UCS, this, new AdjacencyListGraph(graph));
        add(ucsGraph);
        graphPanels.add(ucsGraph);
        aStarGraph = new GraphSearchPanel(SearchType.ASTAR, this, new AdjacencyListGraph(graph));
        add(aStarGraph);
        graphPanels.add(aStarGraph);
    }

    @Override
    public void setOperationAsFinished() {

        main.setSearchAsFinished();
    }
}

