package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;


public class MyGame extends Game {
    public MainMenu firstScreen;
    public MapScreen mapScreen;

    @Override
    public void create() {
        //Gdx.graphics.setWindowedMode(720, 210);

        this.firstScreen = new MainMenu(this);
        this.mapScreen = new MapScreen(this);
        setScreen(firstScreen);
    }

    @Override
    public void render() {
        screen.render(1);
    }
}
