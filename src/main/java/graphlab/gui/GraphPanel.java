package graphlab.gui;

import graphlab.algorithms.GraphSearch;
import graphlab.datastructures.*;
import graphlab.utils.ConsumerWithException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GraphPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    private final JPopupMenu popupMenu;
    private TraversalPanel traversalPanel;
    private AdjacencyListGraph graph;
    private List<Node> visitedNodes = new ArrayList<>();
    private List<Edge> visitedEdges = new ArrayList<>();
    private List<Node> processedNodes = new ArrayList<>();
    private SearchType searchType;
    private int panelSide;
    private int speed;
    private GraphSearchWorker searchWorker;
    int nodeSize;
    private double mf;

    // when drawing on panel, x and y coords are a bit shifted for not drawing nodes on borders
    private int X_SHIFT = 10;
    private int Y_SHIFT = 20;
    private int clickedNodeX;
    private int clickedNodeY;

    public GraphPanel(SearchType searchType, TraversalPanel traversalPanel, AdjacencyListGraph graph) {
        this.searchType = searchType;
        this.traversalPanel = traversalPanel;
        this.graph = graph;
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(200, 200, 200));

        popupMenu = new JPopupMenu();

        JMenuItem menuItem = new JMenuItem("Set as starting node");
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

        g.drawString(searchType.toString(), 5, 15);

    }

    private void drawColoredNode(Graphics g, double mf, Node node) {
        drawColoredNode(g, mf, node, null);
    }

    private void drawColoredNode(Graphics g, double mf, Node node, Color nodeColor) {
        nodeSize = (int) (10 * mf);
        Color color = node.isStartNode() ? Color.GREEN : nodeColor == null ? node.getStatus().color : nodeColor;
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

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = traversalPanel.getSize();
        panelSide = dimension.width < dimension.height * 2 ? dimension.width / 2 - X_SHIFT : dimension.height - Y_SHIFT;
        return new Dimension(panelSide, panelSide);
    }


    public void reset() {
        visitedNodes = new ArrayList<>();
        visitedEdges = new ArrayList<>();
        processedNodes = new ArrayList<>();
        graph.getNodes().forEach(node -> node.setStatus(NodeStatus.UNKNOWN));
        repaint();
    }

    private void updateGraph() throws Exception {
        repaint();
        if (speed > 0) {
            Thread.sleep(speed);
        }
    }

    public void search() {
        visitedEdges = new ArrayList<>();
        visitedNodes = new ArrayList<>();
        processedNodes = new ArrayList<>();
        repaint();
        searchWorker = new GraphSearchWorker(visitedNodes, visitedEdges, processedNodes);
        searchWorker.execute();
    }

    public void stopSearch() {
        searchWorker.cancel(true);
    }

    public void setSearchAsFinished() {
        traversalPanel.setSearchAsFinished();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setProgressBar(int value) {
        traversalPanel.setProgressBar(value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Node clickedNode = graph.getNodes().stream().filter(node -> node.getX() == clickedNodeX && node.getY() == clickedNodeY).findFirst().get();
        graph.getNodes().forEach(node -> node.setStartNode(false));
        clickedNode.setStartNode(true);
        repaint();
    }

    class GraphSearchWorker extends SwingWorker<Void, Void> {

        List<Node> visitedNodes;
        List<Edge> visitedEdges;
        List<Node> processedNodes;

        public GraphSearchWorker(List<Node> visitedNodes, List<Edge> visitedEdges, List<Node> processedNodes) {
            this.visitedNodes = visitedNodes;
            this.visitedEdges = visitedEdges;
            this.processedNodes = processedNodes;
        }

        @Override
        protected Void doInBackground() throws Exception {

            Boolean isCanceled = new Boolean(false);

            Consumer<Node> visitNode = node -> {
                visitedNodes.add(node);
                setProgressBar((int) ((visitedNodes.size() / (float) graph.getNodes().size()) * 100));
            };
            Consumer<Node> processNode = node -> processedNodes.add(node);
            ConsumerWithException<Edge> visitEdge = edge -> {
                visitedEdges.add(edge);
                updateGraph();
            };

            switch (searchType) {
                case BFS:
                    GraphSearch.bfs(graph, visitNode, visitEdge, processNode, isCanceled);
                    break;
                case DFS:
                    GraphSearch.dfs(graph, visitNode, visitEdge, processNode, isCanceled);
                    break;
            }

            setProgressBar(100);
            setSearchAsFinished();
            return null;
        }

    }

    class PopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            Node clickedNode = getClickedNodeFromCoords(e.getX(), e.getY());
            if (clickedNode != null) {
                clickedNodeX = clickedNode.getX();
                clickedNodeY = clickedNode.getY();
                maybeShowPopup(e);
            }
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

        public void mouseReleased(MouseEvent e) {
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
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}