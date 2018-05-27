package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Main optional
 */
public class MyGame extends Game {

    MapScreen mapScreen;

    private int widthScreen, heightScreen;

    private UndirGraph graph;

    /**
     * Create screen
     */
    @Override
    public void create() {

        widthScreen = Gdx.app.getGraphics().getWidth();
        heightScreen = Gdx.app.getGraphics().getHeight();

        this.mapScreen = new MapScreen(this);
        setScreen(mapScreen);

        FileHandle handle = Gdx.files.internal("graph.csv");
        graph = new UndirGraph(handle.readString(), widthScreen, heightScreen);
    }

    /**
     * Get width screen
     *
     * @return width of screen
     */
    int getWidthScreen() {
        return widthScreen;
    }

    /**
     * Get height screen
     *
     * @return height of screen
     */
    int getHeightScreen() {
        return heightScreen;
    }

    /**
     * Get graph
     *
     * @return graph
     */
    UndirGraph getGraph() {
        return graph;
    }

    /**
     * Render on screen
     */
    @Override
    public void render() {
        screen.render(1);
    }
}
