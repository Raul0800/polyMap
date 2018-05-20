package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MyGame extends Game {

    SecondScreen secondScreen;
    MapScreen mapScreen;

    private int widthScreen, heightScreen;

    private UndirGraph graph;

    @Override
    public void create() {

        widthScreen = Gdx.app.getGraphics().getWidth();
        heightScreen = Gdx.app.getGraphics().getHeight();

        this.secondScreen = new SecondScreen(this);
        this.mapScreen = new MapScreen(this);
        setScreen(mapScreen);

        FileHandle handle = Gdx.files.internal("graph.csv");
        graph = new UndirGraph(handle.readString(), widthScreen, heightScreen);
    }

    int getWidthScreen() { return  widthScreen; }
    int getHeightScreen() { return heightScreen; }

    UndirGraph getGraph() {return graph; }

    @Override
    public void render() {
        screen.render(1);
    }
}
