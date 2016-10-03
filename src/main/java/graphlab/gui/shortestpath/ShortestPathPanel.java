package graphlab.gui.shortestpath;

import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Edge;
import graphlab.datastructures.Node;
import graphlab.algorithms.Algorithm;
import graphlab.datastructures.NodeStatus;
import graphlab.gui.GenericTab;
import graphlab.gui.GenericGraphPanel;
import graphlab.utils.Constants;
import graphlab.utils.ConsumerWithException;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static graphlab.utils.Constants.X_SHIFT;
import static graphlab.utils.Constants.Y_SHIFT;


/**
 * The square panel where the shortest path graph is drawn and animated.
 */
public class ShortestPathPanel extends GenericGraphPanel {

    private ShortestPathWorker shortestPathWorker;

    public ShortestPathPanel(Algorithm algorithm, GenericTab genericTab, AdjacencyListGraph graph) {
        super(algorithm, genericTab, graph, true);

        // adds a new item to the popup menu
        JMenuItem menuItem = new JMenuItem(Constants.SET_TARGET_NODE_LABEL);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);
    }

    public void executeStart() {
        bellmanFordStep = 0;
        graph.getNodes().forEach(node -> { node.setParentForShortestPath(null); node.setStatus(NodeStatus.UNKNOWN); node.getEdges().forEach(edge -> edge.recomputeCost());});

        shortestPathWorker = new ShortestPathWorker();
        shortestPathWorker.execute();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = genericTab.getSize();
        panelSide = dimension.width < dimension.height * 2 ? dimension.width / 2 - X_SHIFT : dimension.height - Y_SHIFT;
        return new Dimension(panelSide, panelSide);
    }

    public void executeStop() {
        shortestPathWorker.cancel(true);
        bellmanFordStep = 0;
    }

    class ShortestPathWorker extends SwingWorker<Void, Void> {

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
            Callable startingCheck = () -> {
                visitedEdges.clear();
                bellmanFordStep++;
                return null;
            };

            switch (algorithm) {
                case DIJKSTRA:
                    graphlab.algorithms.ShortestPath.dijkstra(graph, visitNode, visitEdge, processNode, isCanceled);
                    break;
                case BELLMANFORD:
                    graphlab.algorithms.ShortestPath.bellmanFord(graph, visitNode, visitEdge, processNode, startingCheck, isCanceled);
                    break;
            }

            setProgressBar(0);
            setOperationAsFinished();
            return null;
        }
    }
}