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
    private final MstGraphPanel boruvka;

    public MstGraphsContainerPanel(GenericTab mtsTab, GenericControlPanel genericControlPanel) {

        super(mtsTab, genericControlPanel);

        FlowLayout flowLayout = new FlowLayout();
        setLayout(flowLayout);

        // creates an undirected graph
        graph = GraphUtils.createRandomGraph(genericControlPanel.getNodesNumber(), genericControlPanel.getEdgesNumber(), Constants.MAX_NODE_VALUE, graph.isDirected());

        boruvka = new MstGraphPanel(Algorithm.BORUVKA, mtsTab, new AdjacencyListGraph(graph));
        boruvka.setDrawEdgesWithColorGradient(false);
        boruvka.setWorkingEdgesWidth(2);
        addGraphPanel(boruvka);
        add(boruvka);
        
        prim = new MstGraphPanel(Algorithm.PRIM, mtsTab, new AdjacencyListGraph(graph));
        prim.setDrawEdgesWithColorGradient(false);
        prim.setWorkingEdgesWidth(2);
        addGraphPanel(prim);
        add(prim);

        kruskal = new MstGraphPanel(Algorithm.KRUSKAL, mtsTab, new AdjacencyListGraph(graph));
        kruskal.setDrawEdgesWithColorGradient(false);
        kruskal.setWorkingEdgesWidth(2);
        addGraphPanel(kruskal);
        add(kruskal);
    }
}

