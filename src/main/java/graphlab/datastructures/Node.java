package graphlab.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int key;
    private int x;
    private int y;
    private int pathCost;
    private NodeStatus status;
    private List<Edge> edges = new ArrayList<>();
    private boolean isStartNode = false;
    private boolean isSearchedNode = false;

    public Node(Node node) {
        this.key = node.getKey();
        this.x= node.getX();
        this.y= node.getY();
        this.status = node.getStatus();
        this.isStartNode = node.isStartNode();
        this.isSearchedNode = node.isSearchedNode();
    }

    public Node(int index, int x, int y) {
        this.key = index;
        this.x = x;
        this.y = y;
        this.status = NodeStatus.UNKNOWN;
    }

    public void addEdge(Node destination) {
        Edge edge = new Edge(this, destination);
        edges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (getKey() != node.getKey()) return false;
        if (getX() != node.getX()) return false;
        return getY() == node.getY();

    }

    @Override
    public int hashCode() {
        int result = getKey();
        result = 31 * result + getX();
        result = 31 * result + getY();
        return result;
    }

    public int getKey() {
        return key;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public boolean isStartNode() {
        return isStartNode;
    }

    public void setStartNode(boolean startNode) {
        isStartNode = startNode;
    }
    public void setSearchedNode(boolean endNode) {
        isSearchedNode = endNode;
    }

    public boolean isSearchedNode() {
        return isSearchedNode;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    @Override
    public String toString() {
        return "Node [" + key + "] (" + x + "," + y + ")";
    }

    public String toString(int distanceFromNode) {
        return "Node [" + key + "] - Coords: (" + x + "," + y + ") - Path cost: " + getPathCost() + " - Distance from searched: " + distanceFromNode + ")";
    }
}
