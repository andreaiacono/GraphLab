package graphlab.gui.search;

import graphlab.gui.GenericTab;
import graphlab.gui.Main;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is the search tab in the main window.
 */
public class SearchTab extends GenericTab {

    public SearchTab(Main main) {

        super(main);
        controlPanel = new SearchControlPanel(main, this);
        graphsContainerPanel = new SearchGraphsContainerPanel(this, controlPanel);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphsContainerPanel, controlPanel);
        divider.setDividerLocation(450);
        add(divider, BorderLayout.CENTER);

        addComponentListener(this);
    }
}
