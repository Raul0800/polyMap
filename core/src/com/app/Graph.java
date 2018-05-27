package com.app;

import java.util.ArrayList;

/**
 * Interface for graph
 */
public interface Graph {

    boolean edgeExists(int a, int b);

    boolean hasVertex(int v);

    ArrayList<Vertex> searchPath(int a, int b);
}