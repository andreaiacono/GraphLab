package graphlab.datastructures;

import java.util.*;

public class DisjointSets {

    private List<DisjointSet> sets = new ArrayList<>();

    public DisjointSets(List<Node> nodes) {
        nodes.forEach( node -> addNode(node));
    }

    public Node find(Node node) {
        Optional<DisjointSet> optionalSet = sets.stream().filter(set -> set.find(node) != null).findFirst();
        if (optionalSet.isPresent()) {
            return optionalSet.get().representative;
        }
        return null;
    }

    public DisjointSet findSet(Node node) {
        Optional<DisjointSet> optionalSet = sets.stream().filter(set -> set.findSet(node) != null).findFirst();
        if (optionalSet.isPresent()) {
            return optionalSet.get();
        }
        return null;
    }

    public boolean nodesAreInDifferentSets(Node node1 , Node node2) {
        DisjointSet set1 = findSet(node1);
        DisjointSet set2 = findSet(node2);

        return !set1.equals(set2);
    }

    public void merge(Node node1, Node node2, boolean deleteSet) {
        mergeSet(findSet(node1), findSet(node2), deleteSet);
    }

    private void mergeSet(DisjointSet set1, DisjointSet set2, boolean deleteSet) {
        set2.merge(set1);
        if (deleteSet) {
            sets.remove(set1);
        }
    }

    @Override
    public String toString() {
        return "DisjointSets = " + sets;
    }

    /**
     * adds a node as a new set
     * @param node
     */
    public void addNode(Node node) {
        DisjointSet set = new DisjointSet();
        set.addNode(node);
        sets.add(set);
    }

    public int size() {
        return sets.size();
    }

    public List<DisjointSet> get() {
        return sets;
    }

    public class DisjointSet {

        Node representative;
        Deque<Node> nodes = new ArrayDeque<>();

        public void addNode(Node node) {
            if (nodes.isEmpty()) {
                representative = node;
            }
            nodes.add(node);
        }

        public void merge(DisjointSet set) {
            nodes.addAll(set.nodes);
        }

        public Node find(Node node) {
            if (nodes.stream().anyMatch(n -> n.equals(node))) {
                return representative;
            }
            return null;
        }

        public boolean isNodeInSet(Node node) {
            return nodes.stream().anyMatch(n -> n.equals(node));
        }

        public DisjointSet findSet(Node node) {
            if (nodes.stream().anyMatch(n -> n.equals(node))) {
                return this;
            }
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DisjointSet that = (DisjointSet) o;

            if (representative != null ? !representative.equals(that.representative) : that.representative != null)
                return false;
            return nodes != null ? nodes.equals(that.nodes) : that.nodes == null;

        }

        @Override
        public int hashCode() {
            int result = representative != null ? representative.hashCode() : 0;
            result = 31 * result + (nodes != null ? nodes.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "[" + representative.getKey() + ": " + nodes + "]";
        }

        public Deque<Node> getNodes() {
            return nodes;
        }
    }
}
