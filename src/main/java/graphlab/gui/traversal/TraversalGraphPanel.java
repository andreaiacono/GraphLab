package graphlab.gui.traversal;

import graphlab.algorithms.Algorithm;
import graphlab.algorithms.Search;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Edge;
import graphlab.datastructures.Node;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericTab;
import graphlab.utils.ConsumerWithException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The square panel where the traversal graph is drawn and animated.
 */
public class TraversalGraphPanel extends GenericGraphPanel {

    private GraphTraversalWorker traversalWorker;

    public TraversalGraphPanel(Algorithm algorithm, GenericTab traversalTab, AdjacencyListGraph graph) {
        super(algorithm, traversalTab, graph, false);
        this.algorithm = algorithm;
        this.genericTab = traversalTab;
        this.graph = graph;
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(new Color(200, 200, 200));

        MouseListener popupListener = new PopupListener();
        this.addMouseListener(popupListener);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void executeStart() {
        visitedEdges = new ArrayList<>();
        visitedNodes = new ArrayList<>();
        processedNodes = new ArrayList<>();
        repaint();
        traversalWorker = new GraphTraversalWorker(visitedNodes, visitedEdges, processedNodes);
        traversalWorker.execute();
    }

    public void executeStop() {
        traversalWorker.cancel(true);
    }

    class GraphTraversalWorker extends SwingWorker<Void, Void> {

        List<Node> visitedNodes;
        List<Edge> visitedEdges;
        List<Node> processedNodes;

        public GraphTraversalWorker(List<Node> visitedNodes, List<Edge> visitedEdges, List<Node> processedNodes) {
            this.visitedNodes = visitedNodes;
            this.visitedEdges = visitedEdges;
            this.processedNodes = processedNodes;
        }

        @Override
        protected Void doInBackground() throws Exception {

            Boolean isCanceled = new Boolean(false);

            ConsumerWithException<Node> visitNode = node -> {
                visitedNodes.add(node);
                setProgressBar((int) ((visitedNodes.size() / (float) graph.getNodes().size()) * 100));
                updateGraph();
            };
            Consumer<Node> processNode = node -> processedNodes.add(node);
            ConsumerWithException<Edge> visitEdge = edge -> {
                visitedEdges.add(edge);
                updateGraph();
            };

            switch (algorithm) {
                case BFS:
                    Search.bfs(graph, visitNode, visitEdge, processNode, isCanceled, false);
                    break;
                case DFS:
                    Search.dfs(graph, visitNode, visitEdge, processNode, isCanceled, false);
                    break;
            }

            setProgressBar(0);
            setOperationAsFinished();
            return null;
        }

    }
}