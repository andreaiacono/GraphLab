package graphlab.gui.traversal;

import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericTab;
import graphlab.gui.Main;

/**
 * The controls of the Traverse algorithm.
 */
public class TraversalControlPanel extends GenericControlPanel{

    private static int DEFAULT_SPEED = 50;
    private static int DEFAULT_NODES = 100;
    private static int DEFAULT_EDGES = 10;

    public TraversalControlPanel(Main main, GenericTab genericTab) {
        super(main, genericTab, "Traverse", DEFAULT_EDGES, DEFAULT_NODES, DEFAULT_SPEED);
    }
}
