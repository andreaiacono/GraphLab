package graphlab.datastructures;

public enum SearchType {
    DFS("DFS"), BFS("BFS"), UCS("UCS"), ASTAR("A*"), GREEDY("Greedy");

    private final String name;

    SearchType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}