package graphlab.datastructures;

import java.util.List;
import java.util.Set;

public abstract class Graph {

    protected boolean directed;

    public abstract List<Node> getNodes();

    public abstract void addNode(Node node);

    public abstract void removeNode(Node node);

    public abstract void clear();

    public abstract Set<Edge> getEdges();

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

}
