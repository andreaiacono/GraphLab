package graphlab.gui.traversal;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;

import java.awt.*;

/**
 * The panel that contains all the GenericGraphPanel for the traversal.
 */
public class TraversalGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final GenericGraphPanel bfsGraph;
    private final GenericGraphPanel dfsGraph;
    private GenericTab traversalTab;

    public TraversalGraphsContainerPanel(GenericTab traversalTab, GenericControlPanel genericControlPanel) {

        super(traversalTab, genericControlPanel);
        this.traversalTab = traversalTab;

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        dfsGraph = new TraversalGraphPanel(Algorithm.DFS, traversalTab, new AdjacencyListGraph(graph));
        bfsGraph = new TraversalGraphPanel(Algorithm.BFS, traversalTab, new AdjacencyListGraph(graph));
        add(dfsGraph);
        add(bfsGraph);

        addGraphPanel(dfsGraph);
        addGraphPanel(bfsGraph);
    }

//    @Override
//    public Dimension getPreferredSize() {
//        Dimension dimension = traversalTab.getSize();
//        int panelSide = dimension.width < dimension.height * 2 ? dimension.width / 2 - X_SHIFT : dimension.height - Y_SHIFT;
//        return new Dimension(panelSide, panelSide);
//    }


}

