package graphlab.gui;

import graphlab.algorithms.GraphSearch;
import graphlab.datastructures.*;
import graphlab.utils.ConsumerWithException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GraphPanel extends JPanel {

    private SearchPanel searchPanel;
    private AdjacencyListGraph graph;
    private List<Node> visitedNodes = new ArrayList<>();
    private List<Edge> visitedEdges = new ArrayList<>();
    private List<Node> processedNodes = new ArrayList<>();
    private SearchType searchType;
    private int side;
    private int speed;
    private GraphSearchWorker searchWorker;

    public GraphPanel(SearchType searchType, SearchPanel searchPanel, AdjacencyListGraph graph) {
        this.searchType = searchType;
        this.searchPanel = searchPanel;
        this.graph = graph;
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(230, 230, 230));
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

        double mf = getPreferredSize().getHeight() / 500;
        Graphics2D g2 = (Graphics2D) g;
        float color = 0;
        g2.setStroke(new BasicStroke(1));

        graph.getNodes().forEach(node -> node.getEdges().forEach(edge -> drawColoredEdge(g2, edge, mf, Color.BLACK)));

        float increment = 255 / (edges.size() != 0 ? new Float(edges.size()) : 255f);
        g2.setStroke(new BasicStroke(3));
        for (Edge edge : edges) {
            color += increment;
            drawColoredEdge(g2, edge, mf, new Color((int) color, (int) color, 255));
        }
        g2.setStroke(new BasicStroke(1));

        graph.getNodes().forEach(node -> drawColoredNode(g2, mf, (node), null));

        if (graph.getNodes().size() > 0) {
            drawColoredNode(g2, mf, (graph.getNodes().get(0)), Color.GREEN);
        }
        g.drawString(searchType.toString(), 5, 15);

    }

    private void drawColoredNode(Graphics g, double mf, Node node, Color color) {
        int size = (int) (6 * mf);
        if (color != null) {
            g.setColor(color);
        }
        else {
            if (node.getStatus() == NodeStatus.UNKNOWN) g.setColor(Color.RED);
            if (node.getStatus() == NodeStatus.DISCOVERED) g.setColor(Color.GRAY);
            if (node.getStatus() == NodeStatus.PROCESSED) g.setColor(new Color(245, 245, 245));
        }
        g.fillOval(((int) (node.getX() * mf)) - size, ((int) (node.getY() * mf)) - size, size * 2, size * 2);
        g.setColor(Color.BLACK);
        g.drawOval(((int) (node.getX() * mf)) - size, ((int) (node.getY() * mf)) - size, size * 2, size * 2);
    }

    private void drawColoredEdge(Graphics g, Edge edge, double mf, Color edgeColor) {
        g.setColor(edgeColor);
        Node sourceNode = edge.getSource();
        Node destinationNode = edge.getDestination();
        g.drawLine(
                (int) (sourceNode.getX() * mf),
                (int) (sourceNode.getY() * mf),
                (int) (destinationNode.getX() * mf),
                (int) (destinationNode.getY() * mf));
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = searchPanel.getSize();
        side = dimension.width < dimension.height * 2 ? dimension.width / 2 - 10 : dimension.height - 10;
        return new Dimension(side, side);
    }


    public void reset() {
        visitedNodes = new ArrayList<>();
        visitedEdges = new ArrayList<>();
        processedNodes = new ArrayList<>();
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

    public void setSearchAsFinished () {
        searchPanel.setSearchAsFinished();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

            Consumer<Node> visitNode = node -> visitedNodes.add(node);
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

            setSearchAsFinished();
            return null;
        }

    }

}