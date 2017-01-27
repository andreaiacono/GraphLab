package graphlab.gui.search;

import graphlab.algorithms.Algorithm;
import graphlab.algorithms.Search;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.datastructures.Edge;
import graphlab.datastructures.Node;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericTab;
import graphlab.utils.Constants;
import graphlab.utils.ConsumerWithException;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * The square panel where the search graph is drawn and animated.
 */
public class SearchGraphPanel extends GenericGraphPanel {

    private GraphSearchWorker searchWorker;

    public SearchGraphPanel(Algorithm algorithm, GenericTab genericTab, AdjacencyListGraph graph) {
        super(algorithm, genericTab, graph, true);

        JMenuItem menuItem = new JMenuItem(Constants.SET_TARGET_NODE_LABEL);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);
    }

    public void executeStart() {
        searchWorker = new GraphSearchWorker();
        searchWorker.execute();
    }

    public void executeStop() {
        searchWorker.cancel(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return getPreferredSizeForFourPanels();
    }

    class GraphSearchWorker extends SwingWorker<Void, Void> {

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
                    Search.bfs(graph, visitNode, visitEdge, processNode, isCanceled, true);
                    break;
                case DFS:
                    Search.dfs(graph, visitNode, visitEdge, processNode, isCanceled, true);
                    break;
                case UCS:
                    Search.ucs(graph, visitNode, visitEdge, processNode, isCanceled);
                    break;
                case ASTAR:
                    Search.astar(graph, visitNode, visitEdge, processNode, isCanceled);
                    break;
            }

            setProgressBar(0);
            setOperationAsFinished();
            return null;
        }
    }
}