# GraphLab
is a Swing application that shows visually how works several graph algorithms:
![Graphlab screenshot](https://raw.githubusercontent.com/andreaiacono/andreaiacono.github.io/master/img/graphlab.png)
It now has two tabs: 
* Traversal
* Search

### Traversal tab
In the Traversal tab there two panels, one for DFS and the other for BFS. Pressing the button "Traverse" will start the searches and will shoe the different approaches.

### Search tab
In the Search tab there are four panels:
* DFS (Depth First Search)
* BFS (Breadth First Search)
* UCS (Uniform Cost Search)
* A*
Pressing the button "Search" will start the searches and will shoe the different approaches.
The UCS algorithm uses the geometric distance between a node and another as the cost; the A* algorithm uses the distance from the current node to the searched node as the heuristic function. 

In the panel representing the graphs, is possible to move nodes by pressing the mouse button and also to popup a menu for setting starting and searched nodes.

## Requirements

* Java 8 (with the java executable available in path)

## Usage
The project is built with Maven, so to create the JAR you just need to launch:

    mvn clean install
    
and then launch it with:
 
    java -jar target/GraphLab.jar