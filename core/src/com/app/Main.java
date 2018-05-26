package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


public class Main {

    public static void main(String argv[]) {
        UndirGraph graph;
        FileHandle handle;
        handle = Gdx.files.internal("graph.csv");
        //graph = new UndirGraph(handle.readString(), Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());

        //System.out.println(graph.searchPath(107,138));
    }



}
