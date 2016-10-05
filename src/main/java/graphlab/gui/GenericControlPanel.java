package graphlab.gui;

import graphlab.utils.Constants;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is a generic control panel, extended by single algorithms
 */
public abstract class GenericControlPanel extends JPanel implements ChangeListener {

    private Main main;
    protected GenericTab genericTab;
    private String operationName;
    private int edgesNumber;
    private int nodesNumber;
    private int speed;
    private final JButton resetButton;
    private final JButton newGraphButton;
    private final JButton startButton;


    public GenericControlPanel(Main main, GenericTab genericTab, String operationName, int edgesNumber, int nodesNumber, int speed) {
        this.main = main;

        this.genericTab = genericTab;
        this.operationName = operationName;
        this.edgesNumber = edgesNumber;
        this.nodesNumber = nodesNumber;
        this.speed = speed;

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, Constants.MAX_EDGES, this.edgesNumber);
        edgeSlider.setName("edges");
        edgeSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, edgeLabel, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.SOUTH, edgeLabel, -13, SpringLayout.SOUTH, this);

        sl.putConstraint(SpringLayout.WEST, edgeSlider, 5, SpringLayout.EAST, edgeLabel);
        sl.putConstraint(SpringLayout.EAST, edgeSlider, -5, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.SOUTH, edgeSlider, -5, SpringLayout.SOUTH, this);


        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, Constants.MAX_NODES, this.nodesNumber);
        nodesSlider.setName("nodes");
        nodesSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, nodesLabel, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.SOUTH, nodesLabel, -8, SpringLayout.NORTH, edgeSlider);

        sl.putConstraint(SpringLayout.WEST, nodesSlider, 5, SpringLayout.EAST, nodesLabel);
        sl.putConstraint(SpringLayout.EAST, nodesSlider, -5, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.SOUTH, nodesSlider, 0, SpringLayout.NORTH, edgeSlider);


        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, Constants.MAX_SPEED, this.speed);
        speedSlider.setName("speed");
        speedSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, speedLabel, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.SOUTH, speedLabel, -23, SpringLayout.NORTH, nodesSlider);

        sl.putConstraint(SpringLayout.WEST, speedSlider, 5, SpringLayout.EAST, speedLabel);
        sl.putConstraint(SpringLayout.EAST, speedSlider, -5, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.SOUTH, speedSlider, -15, SpringLayout.NORTH, nodesSlider);


        // buttons
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            genericTab.getGraphsContainer().reset();
            main.setProgressBar(0);
            repaint();
        });

        newGraphButton = new JButton("New Graph");
        newGraphButton.addActionListener(e -> {
            genericTab.getGraphsContainer().newGraph();
            main.setProgressBar(0);
            repaint();
        });

        startButton = new JButton(operationName);
        startButton.addActionListener(e -> {
            if (startButton.getText().equals("Stop")) {
                genericTab.getGraphsContainer().stop();
                startButton.setText(operationName);
                main.setStatusBarMessage("Ready");
                newGraphButton.setEnabled(true);
                resetButton.setEnabled(true);
                main.setProgressBar(0);
                repaint();
            }
            else {
                main.setStatusBarMessage(operationName + Constants.IN_PROGRESS_MESSAGE);
                startButton.setText("Stop");
                resetButton.setEnabled(false);
                newGraphButton.setEnabled(false);
                genericTab.getGraphsContainer().updateSpeed();
                genericTab.getGraphsContainer().start();
            }
        });

        sl.putConstraint(SpringLayout.WEST, startButton, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.EAST, startButton, -5, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.NORTH, startButton, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, resetButton, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.EAST, resetButton, -5, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.NORTH, resetButton, 25, SpringLayout.SOUTH, startButton);

        sl.putConstraint(SpringLayout.WEST, newGraphButton, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.EAST, newGraphButton, -5, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.NORTH, newGraphButton, 5, SpringLayout.SOUTH, resetButton);


        add(speedLabel);
        add(speedSlider);
        add(startButton);
        add(resetButton);
        add(newGraphButton);
        add(nodesLabel);
        add(nodesSlider);
        add(edgeLabel);
        add(edgeSlider);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("speed")) {
            speed = slider.getValue();
            genericTab.getGraphsContainer().updateSpeed();
        }
        else if (slider.getName().equals("nodes")) {
            nodesNumber = slider.getValue();
        }
        else if (slider.getName().equals("edges")) {
            edgesNumber = slider.getValue();
        }
    }

    public int getNodesNumber() {
        return nodesNumber;
    }

    public int getEdgesNumber() {
        return edgesNumber;
    }

    public int getSpeed() {
        return speed;
    }

    public void setOperationAsFinished() {
        startButton.setText(operationName);
        main.setStatusBarMessage("Ready");
        newGraphButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    public String getOperationName() {
        return operationName;
    }
}
