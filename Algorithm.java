/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstrasalgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Algorithm 
{
    private final Map<String, Vertex> graph; //mapping of vertex names to vertex objects, built from a set of edges
    
    public static class Edge 
    {
        public final String startVertex, endVertex;
        public final int distance;
        public Edge(String startVertex, String endVertex, int distance)
        {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.distance = distance;
        }
    }

    public static class Vertex implements Comparable<Vertex>
    {
        public final String name;
        public int distance = Integer.MAX_VALUE; //pseudo infinity
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        //Konstruktor
        public Vertex(String name) 
        {
            this.name = name;
        }

        private void printPath()
        {
            if(this == this.previous)
            {
                System.out.printf("%s", this.name);
            }
            else if(this.previous == null)
            {
                System.out.printf("%s(unreached)", this.name);
            }
            else
            {
                this.previous.printPath();
                System.out.printf("-> %s(%d)", this.name, this.distance);
            }
        }
        
        //Test
        private String printPathVisually()
        {
            String result = "";
            if(this == this.previous)
            {
                result = this.name;
            }
            else if(this.previous == null)
            {
                result = this.name + "(unreached)";
            }
            else
            {
                result += this.previous.printPathVisually();
                result += this.name;
            }
            return result;
        }

        @Override
        public int compareTo(Vertex other)
        {
            if(distance == other.distance)
            {
                return name.compareTo(other.name);
            }
            return Integer.compare(distance, other.distance);
        }

        @Override 
        public String toString()
        {
            return "(" + name + ", " + distance + ")";
        }
    }

    //Build a graph from a set of edges
    public Algorithm(Edge[] edges)
    {
        graph = new HashMap<>(edges.length); //edges.length = length of edges array

        //one pass to find all vertices
        for(Edge e : edges)
        {
            if(!graph.containsKey(e.startVertex)) 
                graph.put(e.startVertex, new Vertex(e.startVertex));
            if(!graph.containsKey(e.endVertex))
                graph.put(e.endVertex, new Vertex(e.endVertex));
        }

        //another pass to set neighbouring vertices
        for(Edge e : edges)
        {
            graph.get(e.startVertex).neighbours.put(graph.get(e.endVertex), e.distance);
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); //also do this for an undirected graph
        }
    }
    
    //runs dijkstra using a specified source vertex
    public void dijkstra(String startName)
    {
        if(!graph.containsKey(startName))
        {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();
        
        //set-up vertices
        for(Vertex v : graph.values())
        {
            v.previous = v == source ? source : null;
            v.distance = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }
        dijkstra(q);
    }
    
    //implementation od dijkstras using a binary heap
    private void dijkstra(final NavigableSet<Vertex> q)
    {
        Vertex u, v;
        while(!q.isEmpty())
        {
            u = q.pollFirst(); //vertex with shortest distance will return source
            if(u.distance == Integer.MAX_VALUE) //we can ignore u (and any other remaining vertices) since they are unreachable
                break;
            
            //look at distances to each neighbour
            for(Map.Entry<Vertex, Integer> a : u.neighbours.entrySet())
            {
                v = a.getKey(); //the neighbour in this iteration
                
                final int alternateDist = u.distance + a.getValue();
                if(alternateDist < v.distance) //shorter path to neighbour found
                {
                    q.remove(v);
                    v.distance = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }
    
    //Prints a path from the source to the specified vertex
    public void printPath(String endName)
    {
        if(!graph.containsKey(endName))
        {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return;
        }
        
        graph.get(endName).printPath();
        System.out.println();
    }
    
    //Test
    public String printPathVisually(String endName)
    {
        if(!graph.containsKey(endName))
        {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return "Graph doesn't contain end vertex";
        }
        
        String pathSequence = "";
        pathSequence += graph.get(endName).printPathVisually();
        return pathSequence;
    }
    
    //Prints the path from the source to every vertex (output order is not guaranteed)
    public void printAllPaths()
    {
        for(Vertex v : graph.values())
        {
            v.printPath();
            System.out.println();
        }
    }
}