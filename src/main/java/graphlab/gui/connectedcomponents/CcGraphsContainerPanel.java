package graphlab.gui.connectedcomponents;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import javax.swing.*;

/**
 * The panel that contains all the GenericGraphPanel for the Connected Components.
 */
public class CcGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel bfsConnectedComponentsGraph;

    public CcGraphsContainerPanel(GenericTab ccTab, GenericControlPanel genericControlPanel) {

        super(ccTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        bfsConnectedComponentsGraph = new CcGraphPanel(Algorithm.CONNECTED_COMPONENTS_BFS, ccTab, new AdjacencyListGraph(graph));
        add(bfsConnectedComponentsGraph);

        sl.putConstraint(SpringLayout.WEST, bfsConnectedComponentsGraph, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, bfsConnectedComponentsGraph, 5, SpringLayout.NORTH, this);

        addGraphPanel(bfsConnectedComponentsGraph);
    }
}

