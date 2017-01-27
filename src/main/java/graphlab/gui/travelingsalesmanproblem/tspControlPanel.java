package graphlab.gui.travelingsalesmanproblem;

import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericTab;
import graphlab.gui.Main;

/**
 * The controls of the TSP algorithm.
 */
public class tspControlPanel extends GenericControlPanel{

    private static int DEFAULT_SPEED = 2;
    private static int DEFAULT_NODES = 10;
    private static int DEFAULT_EDGES = 8;

    public tspControlPanel(Main main, GenericTab genericTab) {
        super(main, genericTab, "Nearest Neighbor", DEFAULT_EDGES, DEFAULT_NODES, DEFAULT_SPEED);
    }
}
