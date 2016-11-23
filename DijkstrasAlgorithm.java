/**
 * @author Johannes Mols
 * @version 1.0
 */

/*
 * DOCUMENTATION OF GRAPH CLASS
 * ------------------------------------------------------------------------------------------------------------------------
 * 1. In order to create a new Object of the Graph Class, 
 *    you need to give an Edge Array as an argument.
 *    Example: 
 *    private static final Graph.Edge[] GRAPH = { 
 *    new Graph.Edge("a", "b", 10), new Graph.Edge("b", "a", 10) };
 *    
 *    Edges describe ways from and to vertices.
 *    The constructor takes two Strings and one Integer:
 *    The name of the start Vertex and of the end Vertex of the connection
 *    The distance between both vertices
 * 
 * 2. A start and end vertex must be defined by the following statements (exemplary):
 *    private static final String START = "a";
 *    private static final String END = "b";
 *
 * 3. In order to use the algorithm, you first need to create a new object of the Graph class
 *    Graph g = new Graph(GRAPH);
 *    The argument is the Edge array as described in point one
 *    
 *    There are three available functions to use:
 *    1. graph.dijkstra(START);
 *       Run this method to calculate all possible paths from the start vertex
 *    2. graph.printPath(END);
 *       Run this method to print the shortest path to the end vertex in the console output
 *    3. graph.printAllPaths();
 *       Run this method to print all shortest paths to all vertices in the graph in the console output
 * 
 *    Note that method one and two take Strings as arguments, which define the name of the vertex (e.g. "a", "b", "c", ...)
 * ------------------------------------------------------------------------------------------------------------------------
 */

package dijkstrasalgorithm;

import javafx.application.*;
import javafx.stage.Stage;

public class DijkstrasAlgorithm extends Application
{
    public static void main(String[] args) 
    {
        launch(args); //launch GUI in Application class
    }
    
    //launch start method in GUI class
    @Override
    public void start(Stage window) throws Exception
    {
        GridWindow.open();
    }
}