package graphlab.gui.shortestpath;

import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericTab;
import graphlab.gui.Main;

/**
 * The controls of the Shortest Path algorithm.
 */
public class ShortestPathControlPanel extends GenericControlPanel {

    private static int DEFAULT_SPEED = 5;
    private static int DEFAULT_NODES = 25;
    private static int DEFAULT_EDGES = 5;

    public ShortestPathControlPanel(Main main, GenericTab genericTab) {
        super(main, genericTab, "Shortest Path", DEFAULT_EDGES, DEFAULT_NODES, DEFAULT_SPEED);
    }
}
