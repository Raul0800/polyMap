package com.app;

import com.badlogic.gdx.Game;


public class MyGame extends Game {
    public FirstScreen firstScreen;
    public MapScreen mapScreen;

    @Override
    public void create() {
        //Gdx.graphics.setWindowedMode(720, 210);

        this.firstScreen = new FirstScreen(this);
        this.mapScreen = new MapScreen(this);
        setScreen(firstScreen);
    }

    @Override
    public void render() {
        screen.render(1);
    }
}
