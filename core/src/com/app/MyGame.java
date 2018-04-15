package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MyGame extends Game {

    public MainScreen mainScreen;
    public SecondScreen secondScreen;
    public InputABScreen inputABScreen;
    public PathScreen pathScreen;
    public MapScreen mapScreen;
    public PointSearchScreen pointSearchScreen;

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

    public int getWidthScreen() { return  widthScreen; }
    public int getHeightScreen() { return heightScreen; }

    public UndirGraph getGraph() {return graph; }

    @Override
    public void render() {
        screen.render(1);
    }
}
