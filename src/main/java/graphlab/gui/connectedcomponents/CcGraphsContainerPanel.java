package graphlab.gui.connectedcomponents;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import java.awt.*;

/**
 * The panel that contains all the GenericGraphPanel for the Connected Components.
 */
public class CcGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel bfsConnectedComponentsGraph;

    public CcGraphsContainerPanel(GenericTab ccTab, GenericControlPanel genericControlPanel) {

        super(ccTab, genericControlPanel);

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        bfsConnectedComponentsGraph = new CcGraphPanel(Algorithm.CONNECTED_COMPONENTS_BFS, ccTab, new AdjacencyListGraph(graph));
        add(bfsConnectedComponentsGraph);

        addGraphPanel(bfsConnectedComponentsGraph);
    }
}

