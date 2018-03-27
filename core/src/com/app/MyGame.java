package com.app;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
    public FirstScreen firstScreen;
    public SecondScreen secondScreen;
    public InputABScreen inputABScreen;
    public PathScreen pathScreen;
    public MapScreen mapScreen;

    @Override
    public void create() {
        //Gdx.graphics.setWindowedMode(720, 210);

        this.firstScreen = new FirstScreen(this);
        this.secondScreen = new SecondScreen(this);
        this.inputABScreen = new InputABScreen(this);
        this.pathScreen = new PathScreen(this);
        this.mapScreen = new MapScreen(this);
        setScreen(firstScreen);

    }

    @Override
    public void render() {
        screen.render(1);
    }
}
