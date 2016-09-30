package graphlab.gui.traversal;

import graphlab.gui.Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class TraversalPanel extends JPanel implements ChangeListener, ComponentListener {

    private final JSplitPane traversalDivider;
    private final JButton traverseResetButton;
    private final JButton traverseNewGraphButton;
    private final JButton traverseButton;
    private Main main;
    private final int DEFAULT_NODES_NUMBER = 150;
    private final int DEFAULT_EDGES_NUMBER = 10;

    // contains the graph panels
    private final TraversalGraphsContainerPanel traversalGraphsContainerPanel;

    public TraversalPanel(Main main) {

        this.main = main;
        addComponentListener(this);

        // control and drawing panels
        traversalGraphsContainerPanel = new TraversalGraphsContainerPanel(main, this, DEFAULT_NODES_NUMBER, DEFAULT_EDGES_NUMBER);
        setLayout(new GridLayout(0, 1));
        JPanel controlPanel = new JPanel();
        SpringLayout sl = new SpringLayout();
        controlPanel.setLayout(sl);

        traversalDivider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, traversalGraphsContainerPanel, controlPanel);
        traversalDivider.setDividerLocation(450);
        add(traversalDivider, BorderLayout.CENTER);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 40, DEFAULT_EDGES_NUMBER);
        edgeSlider.setName("traverse_edges");
        edgeSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, edgeLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, edgeLabel, -13, SpringLayout.SOUTH, controlPanel);

        sl.putConstraint(SpringLayout.WEST, edgeSlider, 5, SpringLayout.EAST, edgeLabel);
        sl.putConstraint(SpringLayout.EAST, edgeSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, edgeSlider, -5, SpringLayout.SOUTH, controlPanel);


        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, DEFAULT_NODES_NUMBER);
        nodesSlider.setName("traverse_nodes");
        nodesSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, nodesLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, nodesLabel, -8, SpringLayout.NORTH, edgeSlider);

        sl.putConstraint(SpringLayout.WEST, nodesSlider, 5, SpringLayout.EAST, nodesLabel);
        sl.putConstraint(SpringLayout.EAST, nodesSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, nodesSlider, 0, SpringLayout.NORTH, edgeSlider);


        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("traverse_speed");
        traversalGraphsContainerPanel.setSpeed(50);
        speedSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, speedLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, speedLabel, -23, SpringLayout.NORTH, nodesSlider);

        sl.putConstraint(SpringLayout.WEST, speedSlider, 5, SpringLayout.EAST, speedLabel);
        sl.putConstraint(SpringLayout.EAST, speedSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, speedSlider, -15, SpringLayout.NORTH, nodesSlider);


        // buttons
        traverseResetButton = new JButton("Reset");
        traverseResetButton.addActionListener(e -> {
            traversalGraphsContainerPanel.reset();
            main.setProgressBar(0);
            repaint();
        });

        traverseNewGraphButton = new JButton("New Graph");
        traverseNewGraphButton.addActionListener(e -> {
            traversalGraphsContainerPanel.newGraph();
            main.setProgressBar(0);
            repaint();
        });

        traverseButton = new JButton("Traverse");
        traverseButton.addActionListener(e -> {
            if (traverseButton.getText().equals("Stop")) {
                traversalGraphsContainerPanel.stopSearch();
                traverseButton.setText("Traverse");
                main.setStatusBarMessage("Ready");
                traverseNewGraphButton.setEnabled(true);
                traverseResetButton.setEnabled(true);
                main.setProgressBar(0);
                repaint();
            }
            else {
                main.setStatusBarMessage("Traversing in progress..");
                traverseButton.setText("Stop");
                traversalGraphsContainerPanel.start();
                traverseResetButton.setEnabled(false);
                traverseNewGraphButton.setEnabled(false);
            }
        });

        sl.putConstraint(SpringLayout.WEST, traverseButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, traverseButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, traverseButton, 5, SpringLayout.NORTH, controlPanel);

        sl.putConstraint(SpringLayout.WEST, traverseResetButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, traverseResetButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, traverseResetButton, 25, SpringLayout.SOUTH, traverseButton);

        sl.putConstraint(SpringLayout.WEST, traverseNewGraphButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, traverseNewGraphButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, traverseNewGraphButton, 5, SpringLayout.SOUTH, traverseResetButton);


        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(traverseButton);
        controlPanel.add(traverseResetButton);
        controlPanel.add(traverseNewGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("traverse_speed")) {
            traversalGraphsContainerPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("traverse_edges")) {
            traversalGraphsContainerPanel.setEdgesNumber(slider.getValue());
        }
        else if (slider.getName().equals("traverse_nodes")) {
            traversalGraphsContainerPanel.setNodesNumber(slider.getValue());
        }
    }

    public void setTraversalAsFinished() {
        traverseButton.setText("Traverse");
        main.setStatusBarMessage("Ready");
        traverseNewGraphButton.setEnabled(true);
        traverseResetButton.setEnabled(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        traversalDivider.setDividerLocation((int)(getSize().getWidth()-180));
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
