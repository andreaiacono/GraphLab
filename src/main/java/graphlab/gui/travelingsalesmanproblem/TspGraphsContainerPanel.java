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
    private final GenericGraphPanel twoOpt;

    public TspGraphsContainerPanel(GenericTab tspTab, GenericControlPanel genericControlPanel) {

        super(tspTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        nearestNeighbor = new TspGraphPanel(Algorithm.NEAREST_NEIGHBOR_TSP, tspTab, new AdjacencyListGraph(graph));
        twoOpt = new TspGraphPanel(Algorithm.TWO_OPT_TSP, tspTab, new AdjacencyListGraph(graph));
        add(nearestNeighbor);
        addGraphPanel(nearestNeighbor);
        add(twoOpt);
        addGraphPanel(twoOpt);

        sl.putConstraint(SpringLayout.WEST, nearestNeighbor, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, nearestNeighbor, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, twoOpt, 5, SpringLayout.EAST, nearestNeighbor);
        sl.putConstraint(SpringLayout.NORTH, twoOpt, 5, SpringLayout.NORTH, this);
    }
}

