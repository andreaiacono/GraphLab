package graphlab.datastructures;

import java.util.List;

public abstract class Graph {

    public abstract List<Node> getNodes();

    public abstract void addNode(Node node);

    public abstract void removeNode(Node node);

    public abstract void clear();
}
