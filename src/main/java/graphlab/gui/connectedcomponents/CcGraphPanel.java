package graphlab.gui.connectedcomponents;

import graphlab.algorithms.Algorithm;
import graphlab.algorithms.ConnectedComponents;
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

import static graphlab.utils.Constants.X_SHIFT;
import static graphlab.utils.Constants.Y_SHIFT;

/**
 * The square panel where the graph is drawn and animated.
 */
public class CcGraphPanel extends GenericGraphPanel {

    private GraphCcWorker ccWorker;

    public CcGraphPanel(Algorithm algorithm, GenericTab ccTab, AdjacencyListGraph graph) {
        super(algorithm, ccTab, graph, false);
        this.algorithm = algorithm;
        this.genericTab = ccTab;
        this.graph = graph;
        this.drawWorkingEdges = false;
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
        ccWorker = new GraphCcWorker(visitedNodes, visitedEdges, processedNodes);
        ccWorker.execute();
    }

    public void executeStop() {
        ccWorker.cancel(true);
    }


    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = genericTab.getGraphsContainer().getSize();
        panelSide = dimension.width < dimension.height ? dimension.width - X_SHIFT : dimension.height - Y_SHIFT;
        return new Dimension(panelSide, panelSide);
    }

    class GraphCcWorker extends SwingWorker<Void, Void> {

        List<Node> visitedNodes;
        List<Edge> visitedEdges;
        List<Node> processedNodes;

        public GraphCcWorker(List<Node> visitedNodes, List<Edge> visitedEdges, List<Node> processedNodes) {
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
                case CONNECTED_COMPONENTS_BFS:
                    ConnectedComponents.connectedComponents(graph, visitNode, visitEdge, processNode, isCanceled);
                    break;
            }

            setProgressBar(0);
            setOperationAsFinished();
            return null;
        }
    }
}