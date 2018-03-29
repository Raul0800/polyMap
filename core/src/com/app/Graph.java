package com.app;

import java.util.ArrayList;

/**
 *
 * @author aneesh
 */
public interface Graph {
    int numberOfEdges();
    int size();
    boolean edgeExists(int a, int b);
    boolean hasVertex(int v);
    void removeEdge(int a, int b);
    void addEdge(int a, int b);
    void print();

    ArrayList<Vertex> searchPath(int a, int b);
}