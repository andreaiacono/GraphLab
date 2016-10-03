package graphlab.gui;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Edge;
import graphlab.datastructures.Node;
import graphlab.datastructures.NodeStatus;
import graphlab.utils.Constants;
import graphlab.utils.GraphUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static graphlab.utils.Constants.X_SHIFT;
import static graphlab.utils.Constants.Y_SHIFT;

/**
 * This is the generic square panel where the graph is drawn and animated.
 */
public abstract class GenericGraphPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    /** graphics rendering constants **/
    private static final Font ALGORITHM_NAME_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font[] KEY_FONT = new Font[30];
    private static final Color[] GRAYS = new Color[256];
    private static final Color[] COLOR_GRADIENT = new Color[256];
    private static final Color DEFAULT_EDGE_COLOR = new Color(100,100,100);
    private static final Color KEY_COLOR = new Color(180, 180, 180);
    private static final Border WORKING_BORDER = BorderFactory.createEtchedBorder();
    private static final Border FINISHED_BORDER = BorderFactory.createEtchedBorder(Color.BLUE, Color.LIGHT_GRAY);
    static {
        for (int j = 0; j < KEY_FONT.length; j++) {
            KEY_FONT[j] = new Font("Arial", Font.BOLD, 4 + j);
        }
        for (int j=0; j<255; j++) {
            GRAYS[j] = new Color(j, j, j);
            COLOR_GRADIENT[j] = new Color(j, j, 255);
        }
    }

    // algorithm specific values
    protected int bellmanFordStep = 0;
    private boolean hasSearchedNode;
    public boolean isFinished;
    protected double mf;

    // user interaction variables
    protected int clickedNodeX;
    protected int clickedNodeY;
    protected boolean isMousePressed;
    protected Node clickedNode;
    protected int panelSide;
    protected int speed;
    private int nodeSize;
    protected final JPopupMenu popupMenu;



    protected List<Node> visitedNodes = new ArrayList<>();
    protected List<Edge> visitedEdges = new ArrayList<>();
    protected List<Edge> edgesOnPath = new ArrayList<>();
    protected List<Node> processedNodes = new ArrayList<>();

    protected GenericTab genericTab;
    protected AdjacencyListGraph graph;
    protected Algorithm algorithm;

    public GenericGraphPanel(Algorithm algorithm, GenericTab genericTab, AdjacencyListGraph graph, boolean hasSearchedNode) {
        this.algorithm = algorithm;
        this.genericTab = genericTab;
        this.graph = graph;
        this.hasSearchedNode = hasSearchedNode;
        setBackground(new Color(200, 200, 200));
        setBorder(WORKING_BORDER);

        popupMenu = new JPopupMenu();

        JMenuItem menuItem = new JMenuItem(Constants.SET_STARTING_NODE_LABEL);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

        MouseListener popupListener = new PopupListener();
        this.addMouseListener(popupListener);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setGraph(AdjacencyListGraph graph) {
        this.graph = graph;
        visitedEdges = new ArrayList<>();
        visitedNodes = new ArrayList<>();
        processedNodes = new ArrayList<>();
        edgesOnPath = new ArrayList<>();
        bellmanFordStep = 0;
        setBorder(WORKING_BORDER);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // makes a local copy of data to avoid concurrent modifications
        List<Edge> edges = new ArrayList<>(visitedEdges);

        // FIXME: when changing window size, a part of the graph is lost over the border!
        mf = getPreferredSize().getHeight() / 600;
        Graphics2D g2 = (Graphics2D) g;
        float edgeColorIncrement = 255 / (edges.size() != 0 ? (float) edges.size() : 255f);
        float bellmanFordIncrement = 80 / (float)graph.getNodes().size();
        float colorShade = 0f;
        int grayShade = (int) (180 - (bellmanFordIncrement * bellmanFordStep));
        if (algorithm != Algorithm.BELLMANFORD || bellmanFordStep == 0) {
            graph.getNodes().forEach(node -> node.getEdges().forEach(edge -> drawColoredEdge(g2, edge, mf, Color.BLACK)));
        }
        else {
            g2.setStroke(new BasicStroke(3));
            Color color = GRAYS[180];
            graph.getNodes().forEach(node -> node.getEdges().forEach(edge -> drawColoredEdge(g2, edge, mf, color)));
        }

        g2.setStroke(new BasicStroke(3));
        for (Edge edge : edges) {
            Color color = DEFAULT_EDGE_COLOR;
            if (algorithm == Algorithm.BELLMANFORD) {
                color = GRAYS[grayShade];
            }
            else if (algorithm != Algorithm.DIJKSTRA) {
                colorShade += edgeColorIncrement;
                color = COLOR_GRADIENT[(int) colorShade];
            }
            drawColoredEdge(g2, edge, mf, color);
        }

        if (algorithm == Algorithm.DIJKSTRA || algorithm == Algorithm.BELLMANFORD) {
            drawShortestPath(g2);
        }

        g2.setStroke(new BasicStroke(1));
        graph.getNodes().forEach(node -> drawColoredNode(g2, mf, (node)));

        g.setColor(Color.BLACK);
        g.setFont(ALGORITHM_NAME_FONT);
        g.drawString(algorithm.toString(), 5, 15);
    }

    private void drawShortestPath(Graphics2D g2) {
        try {
            int counter = 1;

            // first time, just counts the nodes
            Node currentNode = GraphUtils.getTargetNode(graph);
            while (currentNode.getParentForShortestPath() != null && counter < 500) {
                counter++;
                currentNode = currentNode.getParentForShortestPath();
            }

            if (counter > 0) {
                // now draws the path
                float increment = 255 / counter;
                int colorShade = 255;

                currentNode = GraphUtils.getTargetNode(graph);
                while (currentNode.getParentForShortestPath() != null && counter > 0) {
                    colorShade -= increment;
                    drawColoredEdge(g2, currentNode, currentNode.getParentForShortestPath(), mf, new Color(0, 255, colorShade));
                    currentNode = currentNode.getParentForShortestPath();
                    counter--;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawColoredNode(Graphics g, double mf, Node node) {
        drawColoredNode(g, mf, node, null);
    }

    private void drawColoredNode(Graphics g, double mf, Node node, Color nodeColor) {
        nodeSize = (int) (25 * mf);
        Color color;
        if (node.isStartNode()) {
            color = Color.GREEN;
        }
        else if (node.isTargetNode() && hasSearchedNode) {
            color = Color.CYAN;
        }
        else {
            color = nodeColor == null ? node.getStatus().color : nodeColor;
        }

        if (node == clickedNode) {
            color = new Color(0, 100, 200);
        }
        g.setColor(color);
        g.fillOval(((int) (node.getX() * mf)) - nodeSize / 2 + X_SHIFT, ((int) (node.getY() * mf)) - nodeSize / 2 + Y_SHIFT, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.drawOval(((int) (node.getX() * mf)) - nodeSize / 2 + X_SHIFT, ((int) (node.getY() * mf)) - nodeSize / 2 + Y_SHIFT, nodeSize, nodeSize);
        g.setColor(KEY_COLOR);
        g.setFont(KEY_FONT[(int) (8 * mf)]);
        FontMetrics fontMetrics = g.getFontMetrics();
        int width = fontMetrics.stringWidth("" + node.getKey());
        g.drawString("" + node.getKey(), (int) (node.getX() * mf) + (nodeSize / 8 - width) / 2 + X_SHIFT, (int) (node.getY() * mf) + nodeSize / 4 + Y_SHIFT);
    }

    private void drawColoredEdge(Graphics g, Edge edge, double mf, Color edgeColor) {
        drawColoredEdge(g, edge.getSource(), edge.getDestination(), mf, edgeColor);
    }

    private void drawColoredEdge(Graphics g, Node sourceNode, Node destinationNode, double mf, Color edgeColor) {
        g.setColor(edgeColor);
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
        setBorder(WORKING_BORDER);
        graph.getNodes().forEach(node -> {
            node.setStatus(NodeStatus.UNKNOWN);
            node.setParentForShortestPath(null);
        });
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
        setBorder(FINISHED_BORDER);
        genericTab.setOperationAsFinished();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setProgressBar(int value) {
        genericTab.setProgressBar(value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Node clickedNode = graph.getNodes().stream().filter(node -> node.getX() == clickedNodeX && node.getY() == clickedNodeY).findFirst().get();
        if (e.getActionCommand().equals(Constants.SET_STARTING_NODE_LABEL)) {
            graph.getNodes().forEach(node -> node.setStartNode(false));
            clickedNode.setStartNode(true);
        }
        else if (e.getActionCommand().equals(Constants.SET_TARGET_NODE_LABEL)) {
            graph.getNodes().forEach(node -> node.setTargetNode(false));
            clickedNode.setTargetNode(true);
        }
        repaint();
    }

    private Node getClickedNodeFromCoords(int x, int y) {
        Node node = null;
        for (Node n : graph.getNodes()) {
            if (Math.abs(n.getX() - x / mf + nodeSize + X_SHIFT) < nodeSize &&
                    Math.abs(n.getY() - y / mf + nodeSize + Y_SHIFT) < nodeSize) {
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
            clickedNode.setX((int) (e.getX() / mf) - X_SHIFT);
            clickedNode.setY((int) (e.getY() / mf) - Y_SHIFT);
            repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        clickedNode = getClickedNodeFromCoords(e.getX(), e.getY());
        if (clickedNode != null) {
            Node startingNode = graph.getNodes().stream().filter(node -> node.isStartNode()).findFirst().get();
            genericTab.main.updateStatusBar(clickedNode.toString(GraphUtils.getDistance(startingNode, clickedNode)));
        }
        else {
            if (genericTab.graphsContainerPanel.genericGraphPanels.stream().allMatch(graph -> graph.isFinished)) {
                genericTab.main.updateStatusBar("Ready");
            }
            else {
                genericTab.main.updateStatusBar(genericTab.getControlPanel().getOperationName() + Constants.IN_PROGRESS_MESSAGE);
            }
        }
        repaint();
    }

    public void start() {
        setBorder(WORKING_BORDER);
        visitedEdges = new ArrayList<>();
        visitedNodes = new ArrayList<>();
        processedNodes = new ArrayList<>();
        isFinished = false;

        executeStart();
    }

    public void stop() {
        setBorder(WORKING_BORDER);
        executeStop();
    }

    protected abstract void executeStart();

    protected abstract void executeStop();

    public class PopupListener extends MouseAdapter {

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
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}