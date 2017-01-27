package graphlab.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdjacencyListGraph extends Graph {

    private List<Node> nodes = new ArrayList<>();

    public AdjacencyListGraph(AdjacencyListGraph graph) {
        graph.getNodes().forEach(node -> nodes.add(new Node(node)));
        graph.getNodes().forEach(node -> node.getEdges().forEach(edge -> nodes.get(node.getKey()).addEdge(nodes.get(edge.getDestination().getKey()))));
        setDirected(graph.isDirected());
    }

    public AdjacencyListGraph() {
    }

    @Override
    public Set<Edge> getEdges() {
        return nodes.stream().flatMap(node -> node.getEdges().stream()).collect(Collectors.toSet());
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public void addNode(Node node) {
        nodes.add(node);
    }

    @Override
    public void removeNode(Node node) {
        nodes.remove(node);
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer("Graph").append("\n");
        nodes.forEach(n -> result.append("Node [").append(n.getKey()).append("] -> ").append(n.getEdges()).append("\n"));
        return result.toString();
    }
}
