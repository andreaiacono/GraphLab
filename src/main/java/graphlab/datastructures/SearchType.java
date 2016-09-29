package graphlab.datastructures;

public enum SearchType {
    DFS,
    BFS,
    UCS,
    ASTAR {
        @Override
        public String toString() {
            return "A*";
        }
    },
    GREEDY {
        @Override
        public String toString() {
            return "Greedy";
        }
    }
}

