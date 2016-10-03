package graphlab.gui.traversal;

import graphlab.gui.GenericTab;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the traversal tab in the main window.
 */
public class TraversalTab extends GenericTab {

    public TraversalTab(Main main) {

        super(main);
        controlPanel = new TraversalControlPanel(main, this);
        graphsContainerPanel = new TraversalGraphsContainerPanel(this, controlPanel);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphsContainerPanel, controlPanel);
        divider.setDividerLocation(450);
        add(divider, BorderLayout.CENTER);

        addComponentListener(this);
    }
}

