package graphlab.datastructures;

import graphlab.utils.GraphUtils;

public class Edge {
    private Node source;
    private Node destination;
    private int cost;

    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
        this.cost = GraphUtils.getDistance(source, destination);
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "" + destination.getKey();
    }
}
