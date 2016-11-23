package dijkstrasalgorithm;

import java.util.ArrayList;
import java.util.List;

public class RunAlgorithmGrid 
{
    //Input variables needed for the algorithm, given by the caller
    private static String START = null;
    private static String END = null;
    private static int[][] field;
    private static int[][] obstacle_field; //0 if not, 1 if obstacle
    private static boolean diagonalAllowed = true;
    
    private static final int STRAIGHT_DISTANCE = 10; //final predefined values for the distance
    private static final int DIAGONAL_DISTANCE = 14;
    
    private static String pathSequence = ""; //this will be returned as the resolution

    private static final List<Algorithm.Edge> graph = new ArrayList<>(); //Connections Array used to give to the algorithm
    
    //================================================================================
    // Run Algorithm
    //================================================================================
    
    public static String run(int[][] input, boolean diagonal)
    {
        //Step 1 - turn the matrix into a readable matrix with 0's for normal nodes and 1's for obstacle nodes
        field = new int[input.length][input[0].length];
        obstacle_field = new int[input.length][input[0].length];
        obstacle_field = calculateObstacles(input);
        
        //Step 2 - Find the start and the end node
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[0].length; j++) {
                if(findStartNode(input, i, j)) //find start node
                    START = CoordToString(i, j);
                if(findEndNode(input, i, j))   //find end node
                    END = CoordToString(i, j);
            }
        }
        if(START == null || END == null) {
            System.out.println("No end or no start node defined!");
            return "";
        }
        
        //Step 3 - Set diagonal moves allowed or not allowed
        diagonalAllowed = diagonal;
        
        //Step 4 - Create a new Object Array of the class Algorithm.Edge and calculate all possible connections in the scene
        calcConnections(); //an awful lot
        Algorithm.Edge[] edgeArray = new Algorithm.Edge[graph.size()];
        edgeArray = graph.toArray(edgeArray);
        
        //Step 5 - Run the algorithm and return the solution
        Algorithm algo = new Algorithm(edgeArray);
        algo.dijkstra(START); //calculate all paths from the start node
        //algo.printPath(END); //only calls in console
        pathSequence = algo.printPathVisually(END);
        System.out.println("Path Sequence: " + pathSequence);
        return pathSequence;
    }
    
    //================================================================================
    // Helper methods
    //================================================================================
    
    //given input has 3 as obstacle, output needs to be a 1, start, end and neutral nodes are 0
    private static int[][] calculateObstacles(int[][] input) 
    {
        int[][] output = new int[input.length][input[0].length];
        for(int i = 0; i < output.length; i++) {
            for(int j = 0; j < output[0].length; j++) {
                if(input[i][j] == 3)
                    output[i][j] = 1;
                else
                    output[i][j] = 0;
            }
        }
        return output;
    }
    
    //Coordinates are defined like this: 012012 -> first three chars is the row, last three is the column -> max. of 999 rows and columnss
    //This method converts the raw rows and columns into one String which can be read as a coordinate
    private static String CoordToString(int row, int column)
    {
        String result = "";
        result += String.format("%03d", row);
        result += String.format("%03d", column);
        return result;
    }
    
    private static boolean findStartNode(int[][] input, int row, int column) //returns if this is the start node
    {
        return input[row][column] == 1; //1 = start
    }
    
    private static boolean findEndNode(int[][] input, int row, int column) //returns if this is the end node
    {
        return input[row][column] == 2; //2 = end
    }
    
    //================================================================================
    // Connection calculator
    //================================================================================

    private static void calcConnections()
    {
        for(int row = 0; row < field.length; row++) {
            for(int column = 0; column < field[0].length; column++) {
                //Add row-1, column-1 (left diagonal up)
                if(row != 0 && column != 0 && obstacle_field[row-1][column-1] == 0 && diagonalAllowed) //check if node exists and if there is an obstacle (0 = no obstacle)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row-1, column-1), DIAGONAL_DISTANCE)); //String start, String end, int distance
                //Add row-1, column (up)
                if(row != 0 && obstacle_field[row-1][column] == 0)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row-1, column), STRAIGHT_DISTANCE));
                //Add row-1, column+1 (right diagonal up)
                if(row != 0 && column < field[0].length-1 && obstacle_field[row-1][column+1] == 0 && diagonalAllowed)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row-1, column+1), DIAGONAL_DISTANCE));
                //Add row, column+1 (right)
                if(column < field[0].length-1 && obstacle_field[row][column+1] == 0)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row, column+1), STRAIGHT_DISTANCE));
                //Add row+1, column+1 (right diagonal down)
                if(row < field.length-1 && column < field[0].length-1 && obstacle_field[row+1][column+1] == 0 && diagonalAllowed)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row+1, column+1), DIAGONAL_DISTANCE));
                //Add row+1, column (down)
                if(row < field.length-1 && obstacle_field[row+1][column] == 0)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row+1, column), STRAIGHT_DISTANCE));
                //Add row+1, column-1 (left diagonal down)
                if(row < field.length-1 && column != 0 && obstacle_field[row+1][column-1] == 0 && diagonalAllowed)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row+1, column-1), DIAGONAL_DISTANCE));
                //Add row, column-1 (left)
                if(column != 0 && obstacle_field[row][column-1] == 0)
                    graph.add(new Algorithm.Edge(CoordToString(row, column), CoordToString(row, column-1), STRAIGHT_DISTANCE));
            }
        }
    }
    /*  Bug Report:
     *  When running the algorithm once with diagonal moves allowed and then disabling it again, it calculates diagonal connections even though it shouldn't 
     *  Will fix: probably never
     */
}