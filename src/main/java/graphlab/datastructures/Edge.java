package graphlab.datastructures;

import graphlab.utils.GraphUtils;

import java.awt.*;

public class Edge implements Comparable{
    private Node source;
    private Node destination;
    private int cost;
    private Color color;

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

    public void recomputeCost() {
        this.cost = GraphUtils.getDistance(source, destination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (getSource() != null ? !getSource().equals(edge.getSource()) : edge.getSource() != null) return false;
        return getDestination() != null ? getDestination().equals(edge.getDestination()) : edge.getDestination() == null;

    }

    @Override
    public int hashCode() {
        int result = getSource() != null ? getSource().hashCode() : 0;
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + source.getKey() + "] -> [" + destination.getKey() + "]";
    }

    @Override
    public int compareTo(Object o) {
        Edge other = (Edge) o;
        return Integer.compare(cost, other.getCost());
    }

    public boolean hasColor() {
        return color != null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
