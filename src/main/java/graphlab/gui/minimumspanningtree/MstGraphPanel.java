package graphlab.gui.minimumspanningtree;

import graphlab.algorithms.Algorithm;
import graphlab.algorithms.MinimumSpanningTree;
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

import static graphlab.utils.Constants.X_SHIFT;
import static graphlab.utils.Constants.Y_SHIFT;

/**
 * The square panel where the MST graph is drawn and animated.
 */
public class MstGraphPanel extends GenericGraphPanel {

    private GraphSearchWorker searchWorker;

    public MstGraphPanel(Algorithm algorithm, GenericTab genericTab, AdjacencyListGraph graph) {
        super(algorithm, genericTab, graph, true);

        this.drawTree = true;
        this.hasSearchedNode = false;

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
        Dimension dimension = genericTab.getGraphsContainer().getSize();
        panelSide = dimension.width < dimension.height * 3 ? dimension.width / 3 - X_SHIFT : dimension.height - Y_SHIFT;
        return new Dimension(panelSide, panelSide);
    }

    class GraphSearchWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {

            Boolean isCanceled = new Boolean(false);

            Consumer<Node> visitNode = node -> visitedNodes.add(node);
            Consumer<Node> processNode = node -> processedNodes.add(node);
            ConsumerWithException<Edge> visitEdge = edge -> {
                visitedEdges.add(edge);
            };
            ConsumerWithException<Edge> processEdge = edge -> {
                edgesOnPath.add(edge);
                setProgressBar((int) ((edgesOnPath.size() / (float) graph.getNodes().size() - 1) * 100));
                updateGraph();
            };

            switch (algorithm) {
                case BORUVKA:
                    MinimumSpanningTree.boruvka(graph, visitNode, visitEdge, processEdge, isCanceled);
                    break;
                case PRIM:
                    MinimumSpanningTree.prim(graph, visitNode, processNode, visitEdge, processEdge, isCanceled);
                    break;
                case KRUSKAL:
                    MinimumSpanningTree.kruskal(graph, visitNode, visitEdge, processEdge, isCanceled);
                    break;
            }

            setProgressBar(0);
            setOperationAsFinished();
            return null;
        }
    }
}