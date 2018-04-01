


package com.app;

import java.util.*;
import static java.lang.Math.min;

public class UndirGraph implements Graph{
    /**
     * Number of vertices in the graph.
     */
    private int n;

    /**
     * Number of edges in the graph.
     */
    private int e;

    /**
     * The graph is a mapping from the 'vertex number ' to a instance
     * of the class 'Vertex'.
     * The Vertex is a collection of 'vertex numbers ' of vertices
     * adjacent to itself.
     */
    private Map<Integer, Vertex> graphMap;

    /**
     * Construct a new undirected graph with 'n' vertices.
     * @param n The number of vertices.
     */
    public UndirGraph(int n){
        this.n = n;
        this.graphMap = new HashMap<Integer, Vertex>();
    }

    /**
     * Construct a graph as a copy of another graph.
     * @param rhs The graph to copy.
     */
    public UndirGraph(UndirGraph rhs){
        this.n = rhs.n;
        this.e = rhs.e;
        this.graphMap = new HashMap<Integer, Vertex>();
        for(Map.Entry<Integer, Vertex> entry: rhs.graphMap.entrySet()){
            this.graphMap.put(entry.getKey(), new Vertex(entry.getValue()));
        }
    }

    /**
     * Construct a graph from a Scanner.
     * @param string The scanner.
     */
    //public UndirGraph(java.util.Scanner sc){
    public UndirGraph(String string){
        String typeOfRoom = null;
        this.graphMap = new HashMap<Integer, Vertex>();
        StringTokenizer strToken = new StringTokenizer(string,"\n");

        int tokens = strToken.countTokens();
        for(int k = 0; k < tokens; k++) {

            StringTokenizer strSubToken = new StringTokenizer(strToken.nextToken(),";");
            int count = strSubToken.countTokens();
            int[] arr = new int[count - 1];
            for(int i = 0; i < count; i++){
                if (i == count - 2) {
                    typeOfRoom = strSubToken.nextToken();
                    break;
                }
                arr[i] = Integer.parseInt(strSubToken.nextToken());
            }

            //arr[0] is the number of the vertex, arr[1] and arr[2] is coordinates
            graphMap.put(arr[0], new Vertex());
            graphMap.get(arr[0]).setCoordinate(arr[1] * 768 / 6531, arr[2] * 592 / 4082);
            graphMap.get(arr[0]).setNumber(arr[0]);
            graphMap.get(arr[0]).setTypeOfRoom(typeOfRoom);
            for(int i = 3; i < count - 1; ++i) {
                graphMap.get(arr[0]).addNeighbour(arr[i]);
            }
        }
        e = edges();
    }

    /**
     * Returns vertex by the number
     * @param v - number of the vertex
     * @return - vertex
     */
    public Vertex getVertex(int v) {
        return this.graphMap.get(v);
    }


    /**
     * Custom Exception type which flags exceptions due to
     * improper use of 'vertex numbers '.
     */
    static class NoSuchVertexException extends RuntimeException{
        public NoSuchVertexException(String no_such_vertex) {
            super(no_such_vertex);
        }
    }

    /**
     * Custom exception type which flags exception due to
     * bad edge specifications.
     */
    class BadEdgeException extends RuntimeException{
        public BadEdgeException(String bad_edge){
            super(bad_edge);
        }
    }

    /**
     * Add a edge between Vertex v1 and Vertex v2.
     * @param v1
     * @param v2
     */
    @Override
    public void addEdge(int v1, int v2){
        if(!hasVertex(v1) || !hasVertex(v2))
            throw new NoSuchVertexException("No such vertex!");
        graphMap.get(v1).addNeighbour(v2);
        graphMap.get(v2).addNeighbour(v1);
    }

    /**
     * Remove a edge between Vertex v1 and Vertex v2.
     * @param v1
     * @param v2
     */
    @Override
    public void removeEdge(int v1, int v2){
        if(!edgeExists(v1, v2))
            throw new BadEdgeException("Such an edge does not exist!");
        else{
            graphMap.get(v1).removeNeighbour(v2);
            graphMap.get(v2).removeNeighbour(v1);
        }
    }

    /**
     * Count the total number of edges.
     * @return Integer specifying the total number of edges in the graph.
     */
    private int edges(){
        int total = 0;
        for(Vertex v : graphMap.values()){
            total += v.noOfNeighbours();
        }
        return total / 2;
    }

    /**
     * The number of edges in the graph.
     * @return 'e' which denotes |E| of the graph.
     */
    @Override
    public int numberOfEdges(){
        return e;
    }

    /**
     * The number of vertices in the graph.
     * @return 'v' which denotes |V| of the graph.
     */
    @Override
    public int size(){
        return n;
    }

    /**
     * Determines if a edge exists between Vertex v1 and Vertex v2.
     * @param v1    First Vertex.
     * @param v2    Second Vertex.
     * @return true/false.
     */
    @Override
    public boolean edgeExists(int v1, int v2){
        return graphMap.containsKey(v1) && graphMap.containsKey(v2) &&
                graphMap.get(v1).isNeighbour(v2) &&
                graphMap.get(v2).isNeighbour(v1);
    }

    /**
     * Determines if 'v' is a valid vertex number.
     * @param v
     * @return true/false.
     */
    @Override
    public boolean hasVertex(int v){
        return graphMap.containsKey(v);
    }

    /**
     * Print the graph.
     */
    @Override
    public void print(){
        System.out.println("The Graph:- ");
        for(Map.Entry<Integer, Vertex> entry : graphMap.entrySet()){
            System.out.print(entry.getKey() + " -> ");
            for(Integer i : entry.getValue())
                System.out.print(i + " ");
            System.out.println("\n");
        }
    }

    private final static int INF = Integer.MAX_VALUE / 2;

    /**
     * Searching path from vertex A to vertex B
     * @param a - start vertex
     * @param b - end vertex
     * @return - path
     */
    public ArrayList<Vertex> searchPath(int a, int b) {

        if(getVertex(a) == null || getVertex(b) == null)
            throw new NoSuchVertexException("no vertex\n");

        Map<Integer, Integer> prev = new HashMap<Integer, Integer>();
        Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
        ArrayList<Vertex> path = new ArrayList<Vertex>();

        Set<Integer> keys = graphMap.keySet();
        for(Integer key : keys) {
            dist.put(key, INF);
            graphMap.get(key).setVisited(false);
        }
        dist.put(a, 0);

        while(true) {
            int v = -1;

            for(Integer key : keys) {
                if (!graphMap.get(key).isVisited() && dist.get(key) < INF && (v == -1 || dist.get(v) > dist.get(key)))
                    v = key;
            }

            if(v == -1)
                break;
            graphMap.get(v).setVisited(true);

            for(Integer key : keys) {
                if(!graphMap.get(key).isVisited() && edgeExists(key, v)) {
                    dist.put(key, min(dist.get(key), dist.get(v) + 1));
                    if(dist.get(v) + 1 <= dist.get(key)) {
                        prev.put(key, v);
                    }
                }
            }
        }

        Integer buf = prev.get(b);
        ArrayList<Integer> bufList = new ArrayList<Integer>();
        while(buf != null) {
            bufList.add(buf);
            buf = prev.get(buf);
        }

        for(int i = bufList.size() - 1; i >= 0; i--) {
            path.add(graphMap.get(bufList.get(i)));
        }
        path.add(graphMap.get(b));
        return path;
    }

}

















