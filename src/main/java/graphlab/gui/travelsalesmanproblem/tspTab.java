package graphlab.gui.travelsalesmanproblem;

import graphlab.gui.GenericTab;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the TSP tab in the main window.
 */
public class tspTab extends GenericTab {

    public tspTab(Main main) {

        super(main);
        controlPanel = new tspControlPanel(main, this);
        graphsContainerPanel = new tspGraphsContainerPanel(this, controlPanel);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphsContainerPanel, controlPanel);
        divider.setDividerLocation(450);
        add(divider, BorderLayout.CENTER);

        addComponentListener(this);
    }
}

