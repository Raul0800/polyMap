package com.app;

import java.util.ArrayList;

/**
 *
 * @author aneesh
 */
public interface Graph {

    boolean edgeExists(int a, int b);
    boolean hasVertex(int v);

    ArrayList<Vertex> searchPath(int a, int b);
}