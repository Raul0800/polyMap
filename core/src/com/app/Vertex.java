package com.app;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aneesh
 */
class Vertex {

    /**
     * Coordinate of vertex.
     */
    private int x,y;

    /**
     *  Number of vertex.
     */
    private int number;

    /**
     * Used for search algorithms.
     */
    private boolean visited;

    /**
     * List of vertices adjacent to this vertex.
     */
    private Map<Integer, Integer> neighbours;

    /**
     * identifier of room
     */
    private String typeOfRoom;

    /**
     * No parameter constructor.
     */
    Vertex(){
        this.visited = false;
        this.neighbours = new HashMap<>();
    }

    void setNumber(int number) { this.number = number; }
    void setTypeOfRoom(String typeOfRoom) { this.typeOfRoom = typeOfRoom; }

    /**
     * Check to see if the vertex is marked as visited.
     * @return true/false
     */
    boolean isVisited(){
        return visited;
    }

    /**
     * Return distance from this vertex to neighbour
     * @param v - number of neighbour
     * @return - distance to v
     */
    int getDistance(int v) {
        return neighbours.get(v);
    }

    /**
     * Set coordinate of vertex
     * @param x - coordinate x
     * @param y - coordinate y
     */
    void setCoordinate(int x, int y) {
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
     * Checks if 'v' is a neighbor of this vertex.
     * @param v The vertex in question.
     * @return true/false
     */
    boolean isNeighbour(int v){
        return this.neighbours.containsKey(v);
    }

    /**
     * Adds a new neighbor.
     * @param v The vertex to be added.
     */
    void addNeighbour(int v, int dist){
        this.neighbours.put(v, dist);
    }


    /**
     * Mark the vertex as visited.
     * @param b - value
     */
    void setVisited(boolean b){
        this.visited = b;
    }


    public void printInfo() {
        System.out.println("Vertex " + number + ": (" + x + "; " + y + "). Neighbours : " + neighbours);
    }

    int getNumber() { return number; }
}