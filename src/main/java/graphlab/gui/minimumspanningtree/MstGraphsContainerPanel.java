package graphlab.gui.minimumspanningtree;

import graphlab.algorithms.Algorithm;
import graphlab.datastructures.AdjacencyListGraph;
import graphlab.gui.GenericControlPanel;
import graphlab.gui.GenericGraphsContainerPanel;
import graphlab.gui.GenericTab;
import graphlab.utils.Constants;
import graphlab.utils.GraphUtils;

import javax.swing.*;


public class MstGraphsContainerPanel extends GenericGraphsContainerPanel {

    private final MstGraphPanel prim;
    private final MstGraphPanel kruskal;
    private final MstGraphPanel boruvka;

    public MstGraphsContainerPanel(GenericTab mtsTab, GenericControlPanel genericControlPanel) {

        super(mtsTab, genericControlPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

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

        sl.putConstraint(SpringLayout.WEST, boruvka, 5, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, boruvka, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, prim, 5, SpringLayout.EAST, boruvka);
        sl.putConstraint(SpringLayout.NORTH, prim, 5, SpringLayout.NORTH, this);

        sl.putConstraint(SpringLayout.WEST, kruskal, 5, SpringLayout.EAST, prim);
        sl.putConstraint(SpringLayout.NORTH, kruskal, 5, SpringLayout.NORTH, this);
    }
}

