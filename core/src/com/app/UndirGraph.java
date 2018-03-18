package com.app;

import java.util.*;

import static java.lang.Math.min;

/**
 *
 * @author aneesh
 */
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
     * @param sc The scanner.
     */
    public UndirGraph(Scanner sc){
        //Scan the number of vertices in the graph
        this.n = sc.nextInt();
        this.graphMap = new HashMap<Integer, Vertex>();
        System.out.print(sc.nextLine());  //Set the scanner to the next line.

        while(sc.hasNext()){
            String input = sc.nextLine();
            StringTokenizer strToken = new StringTokenizer(input);
            int count = strToken.countTokens();

            //DEBUG
            //System.out.println(input + " " + count);

            int[] arr = new int[count];
            for(int i = 0; i < count; ++i){
                arr[i] = Integer.parseInt(strToken.nextToken());
            }

            //arr[0] is the number of the vertex
            graphMap.put(arr[0], new Vertex());
            for(int i = 1; i < count; ++i){
                graphMap.get(arr[0]).addNeighbour(arr[i]);
                //System.out.println(arr[i]);
            }
        }
        e = edges();
    }

    /**
     * Custom Exception type which flags exceptions due to
     * improper use of 'vertex numbers '.
     */
    class NoSuchVertexException extends RuntimeException{
        NoSuchVertexException(String no_such_vertex) {
            super(no_such_vertex);
        }
    }

    /**
     * Custom exception type which flags exception due to
     * bad edge specifications.
     */
    class BadEdgeException extends RuntimeException{
        BadEdgeException(String bad_edge){
            super(bad_edge);
        }
    }

    /**
     * Add a edge between Vertex v1 and Vertex v2.
     * @param v1 - vertex 1
     * @param v2- vertex 2
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
     * @param v1 - vertex 1
     * @param v2 - vertex 2
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

    public ArrayList<Integer> searchPath(int a, int b) {

        Map<Integer, Integer> prev = new HashMap<Integer, Integer>();
        Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

        Set<Integer> keys = graphMap.keySet();
        for(Integer key : keys) {
            dist.put(key, INF);
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
                    prev.put(key, v);
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
            path.add(bufList.get(i));
        }
        path.add(b);

        return path;
    }

}

















