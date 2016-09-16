package graphlab.datastructures;

public class Edge {
    private Node source;
    private Node destination;

    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "" + destination.getKey();
    }
}
