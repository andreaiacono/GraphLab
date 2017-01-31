package graphlab.algorithms;

public enum Algorithm {
    DFS {
        @Override
        public String toString() {
            return "Depth First Search";
        }
    },
    BFS {
        @Override
        public String toString() {
            return "Breadth First Search";
        }
    },
    UCS {
        @Override
        public String toString() {
            return "Uniform Cost Search";
        }
    },
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
    BORUVKA{
        @Override
        public String toString() { return "Borůvka"; }
    },
    PRIM {
        @Override
        public String toString() { return "Prim"; }
    },
    KRUSKAL {
        @Override
        public String toString() { return "Kruskal"; }
    },
    CONNECTED_COMPONENTS_BFS {
        @Override
        public String toString() { return "Bread First Search"; }
    },
    TWO_OPT_TSP {
        @Override
        public String toString() { return "2-opt"; }
    },
    NEAREST_NEIGHBOR_TSP {
        @Override
        public String toString() { return "Nearest Neighbour"; }
    }
}

