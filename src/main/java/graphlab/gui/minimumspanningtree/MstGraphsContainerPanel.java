package graphlab.gui.minimumspanningtree;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;
import graphlab.utils.Constants;
import graphlab.utils.GraphUtils;

import java.awt.*;


public class MstGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final MstGraphPanel prim;
    private final MstGraphPanel kruskal;
    private GenericTab genericTab;

    public MstGraphsContainerPanel(GenericTab searchTab, GenericControlPanel genericControlPanel) {

        super(searchTab, genericControlPanel);
        this.genericTab = searchTab;

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        // prim's algorithm works only with undirected graph
        graph = GraphUtils.createRandomGraph(genericControlPanel.getNodesNumber(), genericControlPanel.getEdgesNumber(), Constants.MAX_NODE_VALUE, true);
        prim = new MstGraphPanel(Algorithm.PRIM, searchTab, new AdjacencyListGraph(graph));
        prim.setDrawEdgesWithColorGradient(false);
        addGraphPanel(prim);
        add(prim);

        kruskal = new MstGraphPanel(Algorithm.KRUSKAL, searchTab, new AdjacencyListGraph(graph));
        kruskal.setDrawEdgesWithColorGradient(false);
        addGraphPanel(kruskal);
        add(kruskal);
    }
}

