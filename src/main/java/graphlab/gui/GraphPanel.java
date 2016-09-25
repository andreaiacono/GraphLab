package graphlab.gui;

import graphlab.datastructures.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GraphPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    private static final String SET_STARTING_NODE_LABEL = "Set as starting node";
    protected static final String SET_SEARCHED_NODE_LABEL = "Set as searched node";
    protected final JPopupMenu popupMenu;
    protected GraphContainerPanel parentPanel;
    protected AdjacencyListGraph graph;
    private boolean hasSearchedNode;
    protected SearchType searchType;
    protected List<Node> visitedNodes = new ArrayList<>();
    protected List<Edge> visitedEdges = new ArrayList<>();
    protected List<Node> processedNodes = new ArrayList<>();
    protected int panelSide;
    private int speed;
    protected boolean isFinished;
    int nodeSize;
    protected double mf;

    // when drawing on panel, x and y coords are a bit shifted for not drawing nodes on borders
    protected int X_SHIFT = 10;
    protected int Y_SHIFT = 20;
    protected int clickedNodeX;
    protected int clickedNodeY;
    protected boolean isMousePressed;
    protected Node clickedNode;

    public GraphPanel(SearchType searchType, GraphContainerPanel parentPanel, AdjacencyListGraph graph, boolean hasSearchedNode) {
        this.searchType = searchType;
        this.parentPanel = parentPanel;
        this.graph = graph;
        this.hasSearchedNode = hasSearchedNode;
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(200, 200, 200));

        popupMenu = new JPopupMenu();

        JMenuItem menuItem = new JMenuItem(SET_STARTING_NODE_LABEL);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);
    }

    public void setGraph(AdjacencyListGraph graph) {
        this.graph = graph;
        visitedEdges = new ArrayList<>();
        visitedNodes = new ArrayList<>();
        processedNodes = new ArrayList<>();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // makes a local copy of data to avoid concurrent modifications
        List<Edge> edges = new ArrayList<>(visitedEdges);

        mf = getPreferredSize().getHeight() / 550;
        Graphics2D g2 = (Graphics2D) g;
        graph.getNodes().forEach(node -> node.getEdges().forEach(edge -> drawColoredEdge(g2, edge, mf, Color.BLACK)));

        float increment = 255 / (edges.size() != 0 ? new Float(edges.size()) : 255f);
        float color = 0;
        g2.setStroke(new BasicStroke(3));
        for (Edge edge : edges) {
            color += increment;
            drawColoredEdge(g2, edge, mf, new Color((int) color, (int) color, 255));
        }
        g2.setStroke(new BasicStroke(1));
        graph.getNodes().forEach(node -> drawColoredNode(g2, mf, (node)));

        g.drawString(searchType.getName(), 5, 15);

    }

    private void drawColoredNode(Graphics g, double mf, Node node) {
        drawColoredNode(g, mf, node, null);
    }

    private void drawColoredNode(Graphics g, double mf, Node node, Color nodeColor) {
        nodeSize = (int) (10 * mf);
        Color color;
        if (node.isStartNode()) {
            color = Color.GREEN;
        }
        else if (node.isSearchedNode() && hasSearchedNode) {
            color = Color.CYAN;
        }
        else {
            color = nodeColor == null ? node.getStatus().color : nodeColor;
        }


        if (node == clickedNode) {
            color = new Color(0,100,200);
        }
        g.setColor(color);
        g.fillOval(((int) (node.getX() * mf)) - nodeSize / 2 + X_SHIFT, ((int) (node.getY() * mf)) - nodeSize / 2 + Y_SHIFT, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.drawOval(((int) (node.getX() * mf)) - nodeSize / 2 + X_SHIFT, ((int) (node.getY() * mf)) - nodeSize / 2 + Y_SHIFT, nodeSize, nodeSize);
    }

    private void drawColoredEdge(Graphics g, Edge edge, double mf, Color edgeColor) {
        g.setColor(edgeColor);
        Node sourceNode = edge.getSource();
        Node destinationNode = edge.getDestination();
        g.drawLine(
                (int) (sourceNode.getX() * mf) + X_SHIFT,
                (int) (sourceNode.getY() * mf) + Y_SHIFT,
                (int) (destinationNode.getX() * mf) + X_SHIFT,
                (int) (destinationNode.getY() * mf) + Y_SHIFT);
    }


    public void reset() {
        visitedNodes = new ArrayList<>();
        visitedEdges = new ArrayList<>();
        processedNodes = new ArrayList<>();
        this.isFinished = false;
        graph.getNodes().forEach(node -> node.setStatus(NodeStatus.UNKNOWN));
        repaint();
    }

    protected void updateGraph() throws Exception {
        repaint();
        if (speed > 0) {
            Thread.sleep(speed);
        }
    }

    public void setOperationAsFinished() {
        this.isFinished = true;
        parentPanel.setOperationAsFinished();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setProgressBar(int value) {
        parentPanel.setProgressBar(value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SET_STARTING_NODE_LABEL)) {
            Node clickedNode = graph.getNodes().stream().filter(node -> node.getX() == clickedNodeX && node.getY() == clickedNodeY).findFirst().get();
            graph.getNodes().forEach(node -> node.setStartNode(false));
            clickedNode.setStartNode(true);
        }
        else if (e.getActionCommand().equals(SET_SEARCHED_NODE_LABEL)) {
            Node clickedNode = graph.getNodes().stream().filter(node -> node.getX() == clickedNodeX && node.getY() == clickedNodeY).findFirst().get();
            graph.getNodes().forEach(node -> node.setSearchedNode(false));
            clickedNode.setSearchedNode(true);
        }


        repaint();
    }

    private Node getClickedNodeFromCoords(int x, int y) {
        Node node = null;
        for (Node n : graph.getNodes()) {
            if (Math.abs(n.getX() - x / mf + X_SHIFT) < nodeSize &&
                    Math.abs(n.getY() - y / mf + Y_SHIFT) < nodeSize) {
                node = n;
            }
        }
        return node;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        clickedNode = getClickedNodeFromCoords(e.getX(), e.getY());
        if (clickedNode != null) {
            isMousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.isMousePressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMousePressed) {
            clickedNode.setX((int) (e.getX() / mf));
            clickedNode.setY((int) (e.getY() / mf));
            repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        clickedNode = getClickedNodeFromCoords(e.getX(), e.getY());
        repaint();
    }

    public abstract void startOperation();

    public abstract void stopOperation();

    class PopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            clickedNode = getClickedNodeFromCoords(e.getX(), e.getY());
            if (clickedNode != null) {
                clickedNodeX = clickedNode.getX();
                clickedNodeY = clickedNode.getY();
                maybeShowPopup(e);
            }
        }

        public void mouseReleased(MouseEvent e) {
            clickedNode = null;
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean isFinished() {
        return isFinished;
    }
}