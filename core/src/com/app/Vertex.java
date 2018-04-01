package com.app;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author aneesh
 */
class Vertex implements Iterable<Integer>{

    /**
     * Coordinate of vertex
     */
    private int x,y;

    private int number;

    /**
     * Used for search algorithms.
     */
    private boolean visited;

    /**
     * List of vertices adjacent to this vertex.
     */
    private ArrayList<Integer> neighbours;

    /**
     * identifier of room
     */
    String typeOfRoom;

    /**
     * No parameter constructor.
     */
    public Vertex(){
        this.visited = false;
        this.neighbours = new ArrayList<Integer>();
    }

    public void setNumber(int number) { this.number = number; }
    public void setTypeOfRoom (String typeOfRoom) { this.typeOfRoom = typeOfRoom; }
    /**
     * Copy a vertex.(Deep copy)
     * @param rhs The vertex to copy from.
     */
    public Vertex(Vertex rhs){
        this.visited = rhs.visited;
        this.neighbours = new ArrayList<Integer>(rhs.neighbours);
    }

    /**
     * Check to see if the vertex is marked as visited.
     * @return true/false
     */
    public boolean isVisited(){
        return visited;
    }

    /**
     * Set coordinate of vertex
     * @param x - coordinate x
     * @param y - coordinate y
     */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns coordinate of vertex
     * @return x or y coordinate of vertex
     */
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public String getTypeOfRoom() { return this.typeOfRoom; }

    /**
     * Remove a vertex from the list of neighbor.
     * @param v The vertex to remove.
     */
    public void removeNeighbour(int v){
        this.neighbours.remove(v);
    }

    /**
     *
     * @return The number of neighbors to this vertex.
     */
    public int noOfNeighbours(){
        return this.neighbours.size();
    }

    /**
     * Checks if 'v' is a neighbor of this vertex.
     * @param v The vertex in question.
     * @return true/false
     */
    public boolean isNeighbour(int v){
        return this.neighbours.contains(v);
    }

    /**
     * Adds a new neighbor.
     * @param v The vertex to be added.
     */
    public void addNeighbour(int v){
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
    public void setVisited(boolean b){
        this.visited = b;
    }

    @Override
    public Iterator<Integer> iterator(){
        return this.neighbours.iterator();
    }

    public void printInfo() {
        System.out.println("Vertex " + number + ": (" + x + "; " + y + "). Neighbours : " + neighbours);
    }

    int getNumber() { return number; }
}