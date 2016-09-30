package graphlab.algorithms;

public enum Algorithm {
    DFS,
    BFS,
    UCS,
    ASTAR {
        @Override
        public String toString() {
            return "A*";
        }
    },
    GREEDY,
    DIJKSTRA
}

