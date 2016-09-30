package graphlab.gui.shortestpath;

import graphlab.gui.Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ShortestPathPanel extends JPanel implements ChangeListener, ComponentListener {

    private final JSplitPane divider;
    private final JButton resetButton;
    private final JButton newGraphButton;
    private final JButton shortestPath;
    private Main main;

    // contains the graph panels
    private final ShortestPathContainerPanel shortestPathContainerPanel;

    public ShortestPathPanel(Main main) {
        this.main = main;
        addComponentListener(this);

        shortestPathContainerPanel = new ShortestPathContainerPanel(main, this);

        // control and drawing panels
        setLayout(new GridLayout(0, 1));
        JPanel controlPanel = new JPanel();
        SpringLayout sl = new SpringLayout();
        controlPanel.setLayout(sl);

        divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, shortestPathContainerPanel, controlPanel);
        divider.setDividerLocation(400);
        add(divider, BorderLayout.CENTER);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 40, 40);
        edgeSlider.setName("edges");
        shortestPathContainerPanel.setEdgesNumber(40);
        edgeSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, edgeLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, edgeLabel, -13, SpringLayout.SOUTH, controlPanel);

        sl.putConstraint(SpringLayout.WEST, edgeSlider, 5, SpringLayout.EAST, edgeLabel);
        sl.putConstraint(SpringLayout.EAST, edgeSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, edgeSlider, -5, SpringLayout.SOUTH, controlPanel);


        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 150);
        nodesSlider.setName("nodes");
        shortestPathContainerPanel.setNodesNumber(150);
        nodesSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, nodesLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, nodesLabel, -8, SpringLayout.NORTH, edgeSlider);

        sl.putConstraint(SpringLayout.WEST, nodesSlider, 5, SpringLayout.EAST, nodesLabel);
        sl.putConstraint(SpringLayout.EAST, nodesSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, nodesSlider, 0, SpringLayout.NORTH, edgeSlider);

        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 5);
        speedSlider.setName("speed");
        shortestPathContainerPanel.setSpeed(5);
        speedSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, speedLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, speedLabel, -23, SpringLayout.NORTH, nodesSlider);

        sl.putConstraint(SpringLayout.WEST, speedSlider, 5, SpringLayout.EAST, speedLabel);
        sl.putConstraint(SpringLayout.EAST, speedSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, speedSlider, -15, SpringLayout.NORTH, nodesSlider);


        // buttons
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            shortestPathContainerPanel.reset();
            main.setProgressBar(0);
            repaint();
        });


        newGraphButton = new JButton("New Graph");
        newGraphButton.addActionListener(e -> {
            shortestPathContainerPanel.newGraph();
            main.setProgressBar(0);
            repaint();
        });

        shortestPath = new JButton("Shortest Path");
        shortestPath.addActionListener(e -> {
            if (shortestPath.getText().equals("Stop")) {
                shortestPathContainerPanel.stopSearch();
                shortestPath.setText("Shortest Path");
                newGraphButton.setEnabled(true);
                resetButton.setEnabled(true);
                main.setProgressBar(0);
                main.setStatusBarMessage("Ready");
                repaint();
            }
            else {
                main.setStatusBarMessage("Shortest Path search in progress..");
                shortestPath.setText("Stop");
                resetButton.setEnabled(false);
                newGraphButton.setEnabled(false);
                repaint();
                shortestPathContainerPanel.search();
            }
        });

        sl.putConstraint(SpringLayout.WEST, shortestPath, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, shortestPath, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, shortestPath, 5, SpringLayout.NORTH, controlPanel);

        sl.putConstraint(SpringLayout.WEST, resetButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, resetButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, resetButton, 25, SpringLayout.SOUTH, shortestPath);

        sl.putConstraint(SpringLayout.WEST, newGraphButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, newGraphButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, newGraphButton, 5, SpringLayout.SOUTH, resetButton);


        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(shortestPath);
        controlPanel.add(resetButton);
        controlPanel.add(newGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);
    }


    public void setSearchAsFinished() {
        shortestPath.setText("Shortest Path");
        main.setStatusBarMessage("Ready");
        newGraphButton.setEnabled(true);
        resetButton.setEnabled(true);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("speed")) {
            shortestPathContainerPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("nodes")) {
            shortestPathContainerPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("edges")) {
            shortestPathContainerPanel.setEdgesNumber(slider.getValue());
        }
    }


    @Override
    public void componentResized(ComponentEvent e) {
        divider.setDividerLocation((int)(getSize().getWidth()-180));
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
