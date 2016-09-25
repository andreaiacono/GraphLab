package graphlab.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Main extends JFrame implements ChangeListener {

    private JLabel statusBar;
    
    private JSplitPane traversalDivider;
    private JSplitPane searchDivider;
    
    private TraversalGraphsContainerPanel traversalGraphsContainerPanel;
    private SearchGraphsContainerPanel searchGraphsContainerPanel;
    
    private JProgressBar progressBar;
    
    private JButton traverseButton;
    private JButton traverseNewGraphButton;
    private JButton traverseResetButton;
    private JButton searchButton;
    private JButton searchNewGraphButton;
    private JButton searchResetButton;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() {

        super("GraphLab");

        setSize(1400, 500);
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

        // control and drawing panels
        this.searchGraphsContainerPanel = new SearchGraphsContainerPanel(this);
        JPanel containerPanel = new JPanel(new GridLayout(0, 1));
        JPanel controlPanel = new JPanel();

        searchDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.searchGraphsContainerPanel, controlPanel);
        searchDivider.setDividerLocation(450);
        containerPanel.add(searchDivider, BorderLayout.CENTER);

        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("search_speed");
        this.searchGraphsContainerPanel.setSpeed(50);
        speedSlider.addChangeListener(this);

        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        nodesSlider.setName("search_nodes");
        this.searchGraphsContainerPanel.setNodesNumber(100);
        nodesSlider.addChangeListener(this);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 40, 10);
        edgeSlider.setName("search_edges");
        this.searchGraphsContainerPanel.setEdgesNumber(10);
        edgeSlider.addChangeListener(this);

        // buttons
        searchResetButton = new JButton("Reset");
        searchResetButton.addActionListener(e -> {
            this.searchGraphsContainerPanel.reset();
            progressBar.setValue(0);
            repaint();
        });

        searchNewGraphButton = new JButton("New Graph");
        searchNewGraphButton.addActionListener(e -> {
            this.searchGraphsContainerPanel.newGraph();
            progressBar.setValue(0);
            repaint();
        });

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            if (searchButton.getText().equals("Stop")) {
                this.searchGraphsContainerPanel.stopSearch();
                searchButton.setText("Search");
                searchNewGraphButton.setEnabled(true);
                searchResetButton.setEnabled(true);
                progressBar.setValue(0);
                statusBar.setText("Ready");
                repaint();
            }
            else {
                statusBar.setText("Searching in progress..");
                searchButton.setText("Stop");
                searchResetButton.setEnabled(false);
                searchNewGraphButton.setEnabled(false);
                repaint();
                this.searchGraphsContainerPanel.search();
            }
        });

        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(searchButton);
        controlPanel.add(searchResetButton);
        controlPanel.add(searchNewGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);

        return containerPanel;
    }


    private JPanel createTraversalPanel() {

        // control and drawing panels
        this.traversalGraphsContainerPanel = new TraversalGraphsContainerPanel(this);
        JPanel containerPanel = new JPanel(new GridLayout(0, 1));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        traversalDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.traversalGraphsContainerPanel, controlPanel);
        traversalDivider.setDividerLocation(450);
        containerPanel.add(traversalDivider, BorderLayout.CENTER);

        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("traverse_speed");
        this.traversalGraphsContainerPanel.setSpeed(50);
        speedSlider.addChangeListener(this);

        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        nodesSlider.setName("traverse_nodes");
        this.traversalGraphsContainerPanel.setNodesNumber(100);
        nodesSlider.addChangeListener(this);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 40, 10);
        edgeSlider.setName("traverse_edges");
        this.traversalGraphsContainerPanel.setEdgesNumber(10);
        edgeSlider.addChangeListener(this);

        // buttons
        traverseResetButton = new JButton("Reset");
        traverseResetButton.addActionListener(e -> {
            this.traversalGraphsContainerPanel.reset();
            progressBar.setValue(0);
            repaint();
        });

        traverseNewGraphButton = new JButton("New Graph");
        traverseNewGraphButton.addActionListener(e -> {
            this.traversalGraphsContainerPanel.newGraph();
            progressBar.setValue(0);
            repaint();
        });

        traverseButton = new JButton("Traverse");
        traverseButton.addActionListener(e -> {
            if (traverseButton.getText().equals("Stop")) {
                this.traversalGraphsContainerPanel.stopSearch();
                traverseButton.setText("Traverse");
                statusBar.setText("Ready");
                traverseNewGraphButton.setEnabled(true);
                traverseResetButton.setEnabled(true);
                progressBar.setValue(0);
                repaint();
            }
            else {
                statusBar.setText("Traversing in progress..");
                traverseButton.setText("Stop");
                this.traversalGraphsContainerPanel.search();
                traverseResetButton.setEnabled(false);
                traverseNewGraphButton.setEnabled(false);
            }
        });

        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(traverseButton);
        controlPanel.add(traverseResetButton);
        controlPanel.add(traverseNewGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);

        return containerPanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("search_speed")) {
            searchGraphsContainerPanel.setSpeed(slider.getValue());
            searchGraphsContainerPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("traverse_speed")) {
            traversalGraphsContainerPanel.setSpeed(slider.getValue());
            traversalGraphsContainerPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("search_nodes")) {
            searchGraphsContainerPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("traverse_nodes")) {
            traversalGraphsContainerPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("search_edges")) {
            searchGraphsContainerPanel.setEdgesNumber(slider.getValue());
        }
        else if (slider.getName().equals("traverse_edges")) {
            traversalGraphsContainerPanel.setEdgesNumber(slider.getValue());
        }
    }

    public void setProgressBar(int value) {
        // show the progress for the faster of the methods
        if (progressBar.getValue() < value || value == 0) {
            progressBar.setValue(value);
            repaint();
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        traversalDivider.setDividerLocation((getSize().getHeight() * 0.9) / getSize().getHeight());
        searchDivider.setDividerLocation((getSize().getHeight() * 0.9) / getSize().getHeight());
    }


    public void setTraversalAsFinished() {
        traverseButton.setText("Traverse");
        statusBar.setText("Ready");
        traverseNewGraphButton.setEnabled(true);
        traverseResetButton.setEnabled(true);
    }

    public void setSearchAsFinished() {
        searchButton.setText("Search");
        statusBar.setText("Ready");
        searchNewGraphButton.setEnabled(true);
        searchResetButton.setEnabled(true);
    }
}
