package graphlab.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Main extends JFrame implements ChangeListener {

    private final JLabel statusBar;
    private final JSplitPane spDivider;
    private final DrawingPanel drawingPanel;

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
        drawingPanel = new DrawingPanel();
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        spDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, drawingPanel, controlPanel);
        spDivider.setDividerLocation(450);
        add(spDivider, BorderLayout.CENTER);

        JLabel speedLabel = new JLabel("Speed: ");
        controlPanel.add(speedLabel);
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 50);
        speedSlider.setName("speed");
        drawingPanel.setSpeed(50);
        controlPanel.add(speedSlider);
        speedSlider.addChangeListener(this);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> drawingPanel.search());
        controlPanel.add(searchButton);

        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> drawingPanel.reset());
        controlPanel.add(reset);

        JButton newGraphButton = new JButton("New Graph");
        newGraphButton.addActionListener(e -> drawingPanel.newGraph());
        controlPanel.add(newGraphButton);

        JLabel nodesLabel = new JLabel("Nodes: ");
        controlPanel.add(nodesLabel);
        JSlider nodesSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        nodesSlider.setName("nodes");
        drawingPanel.setNodesNumber(100);
        controlPanel.add(nodesSlider);
        nodesSlider.addChangeListener(this);

        JLabel edgeLabel = new JLabel("Edges: ");
        controlPanel.add(edgeLabel);
        JSlider edgeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);
        edgeSlider.setName("edges");
        drawingPanel.setEdgesNumber(10);
        controlPanel.add(edgeSlider);
        edgeSlider.addChangeListener(this);

        // sets the status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusBar, BorderLayout.SOUTH);

        // starts
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider.getName().equals("speed")) {
            drawingPanel.setSpeed(slider.getValue());
        }
        else if (slider.getName().equals("nodes")) {
            drawingPanel.setNodesNumber(slider.getValue());
        }
        else if (slider.getName().equals("edges")) {
            drawingPanel.setEdgesNumber(slider.getValue());
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        spDivider.setDividerLocation((getSize().getHeight() * 0.9) / getSize().getHeight());
    }


}
