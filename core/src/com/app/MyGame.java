package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyGame extends Game {

    public FirstScreen firstScreen;
    public SecondScreen secondScreen;
    public InputABScreen inputABScreen;
    public PathScreen pathScreen;
    public MapScreen mapScreen;
    public PointSearchScreen pointSearchScreen;

    private int widthScreen, heightScreen;

    private UndirGraph graph;

    @Override
    public void create() {

        widthScreen = Gdx.app.getGraphics().getWidth();
        heightScreen = Gdx.app.getGraphics().getHeight();

        this.firstScreen = new FirstScreen(this);
        this.secondScreen = new SecondScreen(this);
        this.inputABScreen = new InputABScreen(this);
        this.pathScreen = new PathScreen(this);
        this.mapScreen = new MapScreen(this);
        this.pointSearchScreen = new PointSearchScreen(this);
        setScreen(firstScreen);

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
