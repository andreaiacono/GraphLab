package graphlab.gui.travelsalesmanproblem;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import java.awt.*;

/**
 * The panel that contains all the GenericGraphPanel for the TSP.
 */
public class tspGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel nearestNeighbor;

    public tspGraphsContainerPanel(GenericTab tspTab, GenericControlPanel genericControlPanel) {

        super(tspTab, genericControlPanel);

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        nearestNeighbor = new tspGraphPanel(Algorithm.NEAREST_NEIGHBOR_TSP, tspTab, new AdjacencyListGraph(graph));
        add(nearestNeighbor);

        addGraphPanel(nearestNeighbor);
    }
}

