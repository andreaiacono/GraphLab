package graphlab.gui.travelingsalesmanproblem;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import javax.swing.*;

/**
 * The panel that contains all the GenericGraphPanel for the TSP.
 */
public class TspGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel nearestNeighbor;

    public TspGraphsContainerPanel(GenericTab tspTab, GenericControlPanel genericControlPanel) {

        super(tspTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        nearestNeighbor = new TspGraphPanel(Algorithm.NEAREST_NEIGHBOR_TSP, tspTab, new AdjacencyListGraph(graph));
        add(nearestNeighbor);
        addGraphPanel(nearestNeighbor);

        sl.putConstraint(SpringLayout.WEST, nearestNeighbor, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, nearestNeighbor, 5, SpringLayout.NORTH, this);
    }
}

