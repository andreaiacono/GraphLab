package graphlab.gui.travelingsalesmanproblem;

import graphlab.gui.GenericTab;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the TSP tab in the main window.
 */
public class TspTab extends GenericTab {

    public TspTab(Main main) {

        super(main);
        controlPanel = new TspControlPanel(main, this);
        graphsContainerPanel = new TspGraphsContainerPanel(this, controlPanel);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphsContainerPanel, controlPanel);
        divider.setDividerLocation(450);
        add(divider, BorderLayout.CENTER);

        addComponentListener(this);
    }
}

