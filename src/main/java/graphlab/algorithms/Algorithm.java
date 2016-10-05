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
    DIJKSTRA {
        @Override
        public String toString() {
            return "Dijkstra";
        }
    },
    BELLMANFORD {
        @Override
        public String toString() {
            return "Bellman-Ford";
        }
    },
    PRIM {
        @Override
        public String toString() { return "Prim"; }
    }
}

