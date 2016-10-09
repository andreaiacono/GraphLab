# GraphLab
is a Swing application that shows visually how works several graph algorithms:
![Graphlab screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab.png)


In the panel representing the graphs, is possible to move nodes by pressing the mouse button and also to popup a menu for setting starting and searched nodes.


It now has four tabs: 
* Traversal
* Search
* Shortest Path
* Minimum Spanning Tree

### Traversal tab
In the Traversal tab there are two panels, one for DFS and the other for BFS. Pressing the button "Traverse" will start the searches and will show the different approaches.

### Search tab
In the Search tab there are four panels:
* DFS (Depth First Search)
* BFS (Breadth First Search)
* UCS (Uniform Cost Search)
* A* (A Star)

Pressing the button "Search" will start the searches and will show the different approaches.

The UCS algorithm uses the geometric distance between a node and another as the cost; the A* algorithm uses the distance from the current node to the searched node as the heuristic function. 

### Shortest Path tab
In the Shortest Path tab there are two panels, one for Dijkstra's Algorithm and the other for Bellman-Ford. The Dijkstra's algorithm works by visiting all the nodes and finding the shorter distance to that node, incrementing the total cost with the cost of the last edge; Bellman-Ford, doesn't care about nodes, but only edges: it loops over the edges <code>V-1</code> times to obtain the shortest path: at the end of the search, the nodes will be colored in red because they have not been visited.  

### Minimum Spanning Tree
In the Minimum Spanning Tree tab there are three panels, for Boruvka's Algorithm, Prim's and Kruskal's.

## Requirements

* Java 8 (with the java executable available in path)

## Usage
The project is built with Maven, so to create the JAR you just need to launch:

    mvn clean install
    
and then launch it with:
 
    java -jar target/GraphLab.jar