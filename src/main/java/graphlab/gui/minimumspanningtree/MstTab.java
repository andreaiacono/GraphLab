package graphlab.gui.minimumspanningtree;

import graphlab.gui.GenericTab;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the MST tab in the main window.
 */
public class MstTab extends GenericTab {

    public MstTab(Main main) {

        super(main);
        controlPanel = new MstControlPanel(main, this);
        graphsContainerPanel = new MstGraphsContainerPanel(this, controlPanel);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphsContainerPanel, controlPanel);
        divider.setDividerLocation(450);
        add(divider, BorderLayout.CENTER);

        addComponentListener(this);
    }
}
