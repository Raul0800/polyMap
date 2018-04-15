package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MyGame extends Game {

    MainScreen mainScreen;
    SecondScreen secondScreen;
    InputABScreen inputABScreen;
    PathScreen pathScreen;
    MapScreen mapScreen;
    PointSearchScreen pointSearchScreen;

    private int widthScreen, heightScreen;

    private UndirGraph graph;
    boolean existError;

    @Override
    public void create() {

        widthScreen = Gdx.app.getGraphics().getWidth();
        heightScreen = Gdx.app.getGraphics().getHeight();
        existError = false;

        this.secondScreen = new SecondScreen(this);
        this.inputABScreen = new InputABScreen(this);
        this.pathScreen = new PathScreen(this);
        this.mapScreen = new MapScreen(this);
        this.pointSearchScreen = new PointSearchScreen(this);
        this.mainScreen = new MainScreen(this);
        setScreen(mainScreen);

        FileHandle handle = Gdx.files.internal("graph.csv");
        graph = new UndirGraph(handle.readString());
    }

    int getWidthScreen() { return  widthScreen; }
    int getHeightScreen() { return heightScreen; }

    UndirGraph getGraph() {return graph; }

    @Override
    public void render() {
        screen.render(1);
    }
}
