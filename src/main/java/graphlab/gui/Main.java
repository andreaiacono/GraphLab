package graphlab.gui;

import graphlab.gui.minimumspanningtree.MinimumSpanningTreePanel;
import graphlab.gui.search.SearchPanel;
import graphlab.gui.shortestpath.ShortestPathPanel;
import graphlab.gui.traversal.TraversalPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Main extends JFrame {

    private JLabel statusBar;
    private JProgressBar progressBar;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() {

        super("GraphLab");

        setSize(1000, 510);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (Exception e) {
            // just tried
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Traversal", new TraversalPanel(this));
        tabbedPane.addTab("Search", new SearchPanel(this));
        tabbedPane.addTab("Shortest Path", new ShortestPathPanel(this));
        tabbedPane.addTab("Minimum Spanning Tree", new MinimumSpanningTreePanel());
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

    public void setProgressBar(int value) {
        // avoids values to go back and forth
        if (progressBar.getValue() < value || value == 0) {
            progressBar.setValue(value);
            repaint();
        }
    }

    public void updateStatusBar(String message) {
        statusBar.setText(message);
        repaint();
    }

    public void setStatusBarMessage(String message) {
        statusBar.setText(message);
    }
}
