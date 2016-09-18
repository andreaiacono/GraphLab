package graphlab.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Main extends JFrame implements ChangeListener {

    private JLabel statusBar;
    private JSplitPane spDivider;
    private TraversalPanel traversalPanel;
    private JButton searchButton;
    private JProgressBar progressBar;
    private JButton newGraphButton;
    private JButton resetButton;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() {

        super("GraphLab");

        setSize(900, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (Exception e) {
            // just tried
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Traversal", createTraversalPanel());
        tabbedPane.addTab("Search", createSearchPanel());

        add(tabbedPane);
        add(createStatusPanel(), BorderLayout.SOUTH);

        // starts
        setVisible(true);
    }

    private JPanel createStatusPanel() {

        // sets the status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusBar, BorderLayout.SOUTH);

        JPanel statusPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        progressBar = new JProgressBar(0, 100);
        progressBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        progressBar.setValue(0);
        c.gridx = 0;
        c.weightx = 1;
        statusPanel.add(statusBar, c);
        c.gridx = 10;
        c.weightx = 0;
        statusPanel.add(progressBar, c);
        return statusPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();

        return searchPanel;
    }


    private JPanel createTraversalPanel() {

        JPanel traversalPanel = new JPanel(new GridLayout(0, 1));

        // control and drawing panels
        this.traversalPanel = new TraversalPanel(this);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        spDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.traversalPanel, controlPanel);
        spDivider.setDividerLocation(450);
        traversalPanel.add(spDivider, BorderLayout.CENTER);

        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("speed");
        this.traversalPanel.setSpeed(50);
        speedSlider.addChangeListener(this);

        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        nodesSlider.setName("nodes");
        this.traversalPanel.setNodesNumber(100);
        nodesSlider.addChangeListener(this);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);
        edgeSlider.setName("edges");
        this.traversalPanel.setEdgesNumber(10);
        edgeSlider.addChangeListener(this);

        // buttons
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            this.traversalPanel.reset();
            progressBar.setValue(0);
        });

        newGraphButton = new JButton("New Graph");
        newGraphButton.addActionListener(e -> this.traversalPanel.newGraph());

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            if (searchButton.getText().equals("Stop")) {
                this.traversalPanel.stopSearch();
                searchButton.setText("Search");
                statusBar.setText("Ready");
                newGraphButton.setEnabled(true);
                resetButton.setEnabled(true);
            }
            else {
                statusBar.setText("Searching in progress..");
                searchButton.setText("Stop");
                this.traversalPanel.search();
                resetButton.setEnabled(false);
                newGraphButton.setEnabled(false);
            }
        });

        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(searchButton);
        controlPanel.add(resetButton);
        controlPanel.add(newGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);

        return traversalPanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("speed")) {
            traversalPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("nodes")) {
            traversalPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("edges")) {
            traversalPanel.setEdgesNumber(slider.getValue());
        }
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        spDivider.setDividerLocation((getSize().getHeight() * 0.9) / getSize().getHeight());
    }


    public void setSearchAsFinished() {
        searchButton.setText("Search");
        statusBar.setText("Ready");
        newGraphButton.setEnabled(true);
        resetButton.setEnabled(true);
    }
}
