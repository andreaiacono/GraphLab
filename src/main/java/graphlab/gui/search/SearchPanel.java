package graphlab.gui.search;

import graphlab.gui.Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SearchPanel extends JPanel implements ChangeListener {

    private final JSplitPane searchDivider;
    private final JButton searchResetButton;
    private final JButton searchNewGraphButton;
    private final JButton searchButton;
    private Main main;

    // contains the graph panels
    private final SearchGraphsContainerPanel searchGraphsContainerPanel;

    public SearchPanel(Main main) {
        this.main = main;

        searchGraphsContainerPanel = new SearchGraphsContainerPanel(main, this);

        // control and drawing panels
        setLayout(new GridLayout(0, 1));
        JPanel controlPanel = new JPanel();
        SpringLayout sl = new SpringLayout();
        controlPanel.setLayout(sl);

        searchDivider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, searchGraphsContainerPanel, controlPanel);
        searchDivider.setDividerLocation(400);
        add(searchDivider, BorderLayout.CENTER);

        JLabel edgeLabel = new JLabel("Edges: ");
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 40, 10);
        edgeSlider.setName("search_edges");
        searchGraphsContainerPanel.setEdgesNumber(10);
        edgeSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, edgeLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, edgeLabel, -13, SpringLayout.SOUTH, controlPanel);

        sl.putConstraint(SpringLayout.WEST, edgeSlider, 5, SpringLayout.EAST, edgeLabel);
        sl.putConstraint(SpringLayout.EAST, edgeSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, edgeSlider, -5, SpringLayout.SOUTH, controlPanel);


        JLabel nodesLabel = new JLabel("Nodes: ");
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        nodesSlider.setName("search_nodes");
        searchGraphsContainerPanel.setNodesNumber(100);
        nodesSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, nodesLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, nodesLabel, -8, SpringLayout.NORTH, edgeSlider);

        sl.putConstraint(SpringLayout.WEST, nodesSlider, 5, SpringLayout.EAST, nodesLabel);
        sl.putConstraint(SpringLayout.EAST, nodesSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, nodesSlider, 0, SpringLayout.NORTH, edgeSlider);

        JLabel speedLabel = new JLabel("Speed: ");
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("search_speed");
        searchGraphsContainerPanel.setSpeed(50);
        speedSlider.addChangeListener(this);

        sl.putConstraint(SpringLayout.WEST, speedLabel, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, speedLabel, -23, SpringLayout.NORTH, nodesSlider);

        sl.putConstraint(SpringLayout.WEST, speedSlider, 5, SpringLayout.EAST, speedLabel);
        sl.putConstraint(SpringLayout.EAST, speedSlider, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.SOUTH, speedSlider, -15, SpringLayout.NORTH, nodesSlider);


        // buttons
        searchResetButton = new JButton("Reset");
        searchResetButton.addActionListener(e -> {
            searchGraphsContainerPanel.reset();
            main.setProgressBar(0);
            repaint();
        });


        searchNewGraphButton = new JButton("New Graph");
        searchNewGraphButton.addActionListener(e -> {
            searchGraphsContainerPanel.newGraph();
            main.setProgressBar(0);
            repaint();
        });

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            if (searchButton.getText().equals("Stop")) {
                searchGraphsContainerPanel.stopSearch();
                searchButton.setText("Search");
                searchNewGraphButton.setEnabled(true);
                searchResetButton.setEnabled(true);
                main.setProgressBar(0);
                main.setStatusBarMessage("Ready");
                repaint();
            }
            else {
                main.setStatusBarMessage("Searching in progress..");
                searchButton.setText("Stop");
                searchResetButton.setEnabled(false);
                searchNewGraphButton.setEnabled(false);
                repaint();
                searchGraphsContainerPanel.search();
            }
        });

        sl.putConstraint(SpringLayout.WEST, searchButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, searchButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, searchButton, 5, SpringLayout.NORTH, controlPanel);

        sl.putConstraint(SpringLayout.WEST, searchResetButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, searchResetButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, searchResetButton, 25, SpringLayout.SOUTH, searchButton);

        sl.putConstraint(SpringLayout.WEST, searchNewGraphButton, 5, SpringLayout.WEST, controlPanel);
        sl.putConstraint(SpringLayout.EAST, searchNewGraphButton, -5, SpringLayout.EAST, controlPanel);
        sl.putConstraint(SpringLayout.NORTH, searchNewGraphButton, 5, SpringLayout.SOUTH, searchResetButton);


        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(searchButton);
        controlPanel.add(searchResetButton);
        controlPanel.add(searchNewGraphButton);
        controlPanel.add(nodesLabel);
        controlPanel.add(nodesSlider);
        controlPanel.add(edgeLabel);
        controlPanel.add(edgeSlider);
    }


    public void setSearchAsFinished() {
        searchButton.setText("Search");
        main.setStatusBarMessage("Ready");
        searchNewGraphButton.setEnabled(true);
        searchResetButton.setEnabled(true);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        searchDivider.setDividerLocation((int)(getSize().getWidth()-180));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("search_speed")) {
            searchGraphsContainerPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("search_nodes")) {
            searchGraphsContainerPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("search_edges")) {
            searchGraphsContainerPanel.setEdgesNumber(slider.getValue());
        }
    }


}
