package graphlab.gui;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

/**
 * The generic tab of the main menu (extended by
 */
public abstract class GenericTab extends JPanel implements ComponentListener {

    protected Main main;
    protected GenericGraphsContainerPanel graphsContainerPanel;
    protected GenericControlPanel controlPanel;
    protected JSplitPane divider;

    public GenericTab(Main main) {
        this.main = main;
        setLayout(new GridLayout(0, 1));
    }

    public void setProgressBar(int value) {
        main.setProgressBar(value);
    }

    public GenericGraphsContainerPanel getGraphsContainer() {
        return graphsContainerPanel;
    }

    public void setOperationAsFinished() {
        if (graphsContainerPanel.genericGraphPanels.stream().allMatch(graph -> graph.isFinished)) {
            controlPanel.setOperationAsFinished();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        divider.setDividerLocation((int)(getSize().getWidth()-180));
    }

    @Override
    public void componentMoved(ComponentEvent e) { }

    @Override
    public void componentShown(ComponentEvent e) { }

    @Override
    public void componentHidden(ComponentEvent e) { }

    public GenericControlPanel getControlPanel() {
        return controlPanel;
    }
}
