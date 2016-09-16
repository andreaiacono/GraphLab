package graphlab.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Main extends JFrame implements ChangeListener {

    private final JLabel statusBar;
    private final JSplitPane spDivider;
    private final SearchPanel searchPanel;
    private final JButton searchButton;

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

        // control and drawing panels
        searchPanel = new SearchPanel(this);
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        spDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchPanel, controlPanel);
        spDivider.setDividerLocation(450);
        add(spDivider, BorderLayout.CENTER);

        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("speed");
        searchPanel.setSpeed(50);
        speedSlider.addChangeListener(this);

        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        nodesSlider.setName("nodes");
        searchPanel.setNodesNumber(100);
        nodesSlider.addChangeListener(this);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);
        edgeSlider.setName("edges");
        searchPanel.setEdgesNumber(10);
        edgeSlider.addChangeListener(this);

        // sets the status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusBar, BorderLayout.SOUTH);

        // buttons
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            if (searchButton.getText().equals("Stop")) {
                searchPanel.stopSearch();
                searchButton.setText("Search");
                statusBar.setText("Ready");
            }
            else {
                statusBar.setText("Searching in progress..");
                searchButton.setText("Stop");
                searchPanel.search();
            }
        });

        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> searchPanel.reset());

        JButton newGraphButton = new JButton("New Graph");
        newGraphButton.addActionListener(e -> searchPanel.newGraph());

        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(searchButton);
        controlPanel.add(reset);
        controlPanel.add(newGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);

        // starts
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("speed")) {
            searchPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("nodes")) {
            searchPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("edges")) {
            searchPanel.setEdgesNumber(slider.getValue());
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        spDivider.setDividerLocation((getSize().getHeight() * 0.9) / getSize().getHeight());
    }


    public void setSearchAsFinished() {
        searchButton.setText("Search");
    }
}
