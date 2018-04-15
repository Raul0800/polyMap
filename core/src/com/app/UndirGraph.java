


package com.app;

import java.util.*;
import static java.lang.Math.min;

public class UndirGraph implements Graph{

    /**
     * The graph is a mapping from the 'vertex number ' to a instance
     * of the class 'Vertex'.
     * The Vertex is a collection of 'vertex numbers ' of vertices
     * adjacent to itself.
     */
    private Map<Integer, Vertex> graphMap;

    /**
     * Construct a graph from a Scanner.
     * @param string The scanner.
     */
    UndirGraph(String string){
        String typeOfRoom = null;
        this.graphMap = new HashMap<>();
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
            graphMap.get(arr[0]).setCoordinate(arr[1] * 722 / 6531, arr[2] * 642 / 4082);
            graphMap.get(arr[0]).setNumber(arr[0]);
            graphMap.get(arr[0]).setTypeOfRoom(typeOfRoom);
            for(int i = 3; i < count - 2; i += 2) {
                graphMap.get(arr[0]).addNeighbour(arr[i], arr[i + 1]);
            }
        }
    }

    /**
     * Returns vertex by the number
     * @param v - number of the vertex
     * @return - vertex
     */
    Vertex getVertex(int v) {
        return this.graphMap.get(v);
    }


    /**
     * Custom Exception type which flags exceptions due to
     * improper use of 'vertex numbers '.
     */
    static class NoSuchVertexException extends RuntimeException{
        NoSuchVertexException(String no_such_vertex) {
            super(no_such_vertex);
        }
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
     * @param v - vertex
     * @return true/false.
     */
    @Override
    public boolean hasVertex(int v){
        return graphMap.containsKey(v);
    }

    private final static int INF = Integer.MAX_VALUE / 2;

    /**
     * Searching path from vertex A to vertex B
     * @param a - start vertex
     * @param b - end vertex
     * @return - path
     */
    public ArrayList<Vertex> searchPath(int a, int b) {

        Map<Integer, Integer> prev = new HashMap<>();
        Map<Integer, Integer> dist = new HashMap<>();
        ArrayList<Vertex> path = new ArrayList<>();

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
                    dist.put(key, min(dist.get(key), dist.get(v) + graphMap.get(v).getDistance(key)));
                    if(dist.get(v) + graphMap.get(v).getDistance(key) <= dist.get(key)) {
                        prev.put(key, v);
                    }
                }
            }
        }

        Integer buf = prev.get(b);

        ArrayList<Integer> bufList = new ArrayList<>();
        while(buf != null) {
            bufList.add(buf);
            buf = prev.get(buf);
        }

        for(int i = bufList.size() - 1; i >= 0; i--) {
            path.add(graphMap.get(bufList.get(i)));
        }
        path.add(graphMap.get(b));
        if(path.size() == 1  &&  a != b)
            return searchPath(b, a);
        return path;
    }

}

















