package graphlab.datastructures;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdjacencyListGraphTest {

    AdjacencyListGraph graph;
    Node node5;

    @Before
    public void createGraph() {
        graph = new AdjacencyListGraph();
        node5 = new Node(5, 0, 0);
        Node node6 = new Node(6, 0, 0);
        Node node2 = new Node(2, 0, 0);
        node5.addEdge(node6);
        node5.addEdge(node2);
        node6.addEdge(node2);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node2);
    }

    @Test
    public void getNodes() throws Exception {
        assertEquals(3, graph.getNodes().size());
    }

    @Test
    public void addNode() throws Exception {
        Node node9 = new Node(9, 0, 0);
        node9.addEdge(node5);
        graph.addNode(node9);
        assertEquals(4, graph.getNodes().size());
        assertEquals(1, graph.getNodes().get(3).getEdges().size());
        assertEquals(9, graph.getNodes().get(3).getKey());
    }

    @Test
    public void removeNode() throws Exception {
        graph.removeNode(node5);
        assertEquals(2, graph.getNodes().size());
    }
}