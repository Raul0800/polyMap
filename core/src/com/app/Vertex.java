package com.app;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author aneesh
 */
class Vertex implements Iterable<Integer>{
    /**
     * Used for search algorithms.
     */
    private boolean visited;

    /**
     * List of vertices adjacent to this vertex.
     */
    private ArrayList<Integer> neighbours;

    /**
     * No parameter constructor.
     */
    Vertex(){
        this.visited = false;
        this.neighbours = new ArrayList<Integer>();
    }

    /**
     * Copy a vertex.(Deep copy)
     * @param rhs The vertex to copy from.
     */
    Vertex(Vertex rhs){
        this.visited = rhs.visited;
        this.neighbours = new ArrayList<Integer>(rhs.neighbours);
    }

    /**
     * Check to see if the vertex is marked as visited.
     * @return true/false
     */
    boolean isVisited(){
        return visited;
    }

    /**
     * Remove a vertex from the list of neighbor.
     * @param v The vertex to remove.
     */
    void removeNeighbour(int v){
        this.neighbours.remove(v);
    }

    /**
     *
     * @return The number of neighbors to this vertex.
     */
    int noOfNeighbours(){
        return this.neighbours.size();
    }

    /**
     * Checks if 'v' is a neighbor of this vertex.
     * @param v The vertex in question.
     * @return true/false
     */
    boolean isNeighbour(int v){
        return this.neighbours.contains(v);
    }

    /**
     * Adds a new neighbor.
     * @param v The vertex to be added.
     */
    void addNeighbour(int v){
        this.neighbours.add(v);
    }

    /**
     * Become neighbors with all the neighbors of 'other'
     * @param other The vertex in question.
     */
    public void append(Vertex other){
        for(int i : other){
            this.neighbours.add(i);
        }
    }


    /**
     * Mark the vertex as visited.
     * @param b
     */
    void setVisited(boolean b){
        this.visited = b;
    }

    @Override
    public Iterator<Integer> iterator(){
        return this.neighbours.iterator();
    }
}