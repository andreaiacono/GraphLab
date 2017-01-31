# GraphLab
is a Java Swing application that shows visually how works several graph algorithms. The application has several tabs, each of them showing a particular algorithm; each tab has a control panel on the right that let the user start the execution, stop it and create new graphs using sliders for deciding how many random nodes/edges to create and if directed or not. The user can also set a specific node as the starting node or move the nodes along the canvas just by dragging and dropping them on another position.

The application has now six tabs:
## Traversal Tab

![Graphlab Traverse Tab Screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab/traverse.gif)
The traversal tab shows two panels:
* DFS (Depth First Search)
* BFS (Breadth First Search)

Before starting the traversal every node is red (UNKNOWN) and the edges are thin; while it executes the algorithms, it changes the color of a node to grey (DISCOVERED) and then to to white (PROCESSED) and also the color of the edges, which start blue and then shade to white as long as they get further from the starting node. The difference between DFS and BFS will be highlighted by the visualization since in BFS all the edges around the starting edge will be blue or light blue, and becoming white in circular patterns; in DFS, on the other hand, the color of the edges will follow the neighbors of every node, and the path can be very convoluted.

#### DFS
DFS is a graph traversal algorithm that consists of these steps:
1. selet a starting node as current node
2. for each neighbors of this node
3.     select this neighbor as current node
4. goto step 2
This means that the algorithm will traverse in depth all the edges of the graph, since it will begin with the starting node, then it will choose its first neighbor, then it will choose its first neighbor, ..., and so on.

DFS has lower memory consumption compared to BFS.

#### BFS
BFS is a graph traversal algorithm that consists of these steps:
1. selet a starting node as current node
2. for each neighbors of this node
3. 	   put this neighbor in a queue
4. select first element from queue as current node
5. goto step 2.

This means that the algorithm will traverse in breadth all the edges of the graph, since it will begin with the starting node, then it will put all its neighbors in a queue, then it will get the first neighbor of the strating node and it will put all its neighbors in the queue, then it will get the second neighbor of the starting node and it will put all its neighbors in the queue, ..., and so on.

BFS has higher memory consumption compared to DFS and is suitable when searching for a shortest path from the starting node to any node in the graph.

For more detailed info on these algorithms check Wikipedia for [DFS](https://en.wikipedia.org/wiki/Depth-first_search) and [BFS](https://en.wikipedia.org/wiki/Breadth-first_search).


## Search Tab

![Graphlab Search Tab Screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab/search.gif)
The traversal tab shows four panels:
* DFS (Depth First Search)
* BFS (Breadth First Search)
* UCS (Unform Cost Search)
* A* (A star)

Pressing the button "Search" will start the searches and will show the different approaches.

The UCS algorithm uses the geometric distance between a node and another as the cost; the A* algorithm uses the distance from the current node to the searched node as the heuristic function. 


## Shortest Path

![Graphlab Shortest Path Tab Screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab/shortest_path.gif)
The traversal tab shows two panels:
* Dijkstra
* Bellman-Ford

In the Shortest Path tab there are two panels, one for Dijkstra's Algorithm and the other for Bellman-Ford. The Dijkstra's algorithm works by visiting all the nodes and finding the shorter distance to that node, incrementing the total cost with the cost of the last edge; Bellman-Ford, doesn't care about nodes, but only edges: it loops over the edges <code>V-1</code> times to obtain the shortest path: at the end of the search, the nodes will be colored in red because they have not been visited.  



## Minimum Spanning Tree

![Graphlab Minimum Spanning Tree Tab Screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab/mst.gif)
The traversal tab shows three panels:
* Boruvka
* Prim
* Kruskal

In the Minimum Spanning Tree tab there are three panels, for Boruvka's Algorithm, Prim's and Kruskal's.



## Connected Components

![Graphlab Connected Components Tab Screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab/cc.gif)
The traversal tab shows one panel:
* BFS

A BFS traversal is used to visit all the edges of the graph; then a check is made on the number of visited nodes: if it's lower then the number of nodes of the graph, it means that there are other nodes not touched by the preceding traversal, and so it will loop over all the nodes of the graph looking for the first that has not been visited for starting a BFS on that node too. The operation is repeated until there are no more unvisited nodes.

## Traveling Salesman Problem

![Graphlab Traveling Salesman Problem Tab Screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab/tsp.gif)
The traversal tab shows two panels:
* Nearest Neighbor
* 2-opt



# Requirements

* Java 8 (with the java executable available in path)

# Usage
The project is built with Maven, so to create the JAR you just need to launch:

    mvn clean install
    
and then launch it with:
 
    java -jar target/GraphLab.jar