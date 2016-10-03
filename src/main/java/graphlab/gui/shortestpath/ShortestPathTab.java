package graphlab.gui.shortestpath;

import graphlab.gui.GenericTab;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the shortest path tab in the main window.
 */
public class ShortestPathTab extends GenericTab {

    public ShortestPathTab(Main main) {

        super(main);
        controlPanel = new ShortestPathControlPanel(main, this);
        graphsContainerPanel = new ShortestPathGraphsContainerPanel(this, controlPanel);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphsContainerPanel, controlPanel);
        divider.setDividerLocation(450);
        add(divider, BorderLayout.CENTER);

        addComponentListener(this);
    }
}
