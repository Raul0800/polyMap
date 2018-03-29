package com.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class MyGame extends Game {

    public FirstScreen firstScreen;
    public SecondScreen secondScreen;
    public InputABScreen inputABScreen;
    public PathScreen pathScreen;
    public MapScreen mapScreen;

    private int widthScreen, heightScreen;
    private int firstPoint, secondPoint;




    @Override
    public void create() {

        widthScreen = Gdx.app.getGraphics().getWidth();
        heightScreen = Gdx.app.getGraphics().getHeight();

        this.firstScreen = new FirstScreen(this);
        this.secondScreen = new SecondScreen(this);
        this.inputABScreen = new InputABScreen(this);
        this.pathScreen = new PathScreen(this);
        this.mapScreen = new MapScreen(this);
        setScreen(firstScreen);


    }

    public int getWidthScreen() { return  widthScreen; }
    public int getHeightScreen() { return heightScreen; }

    public void setFirstPoint(int point) { firstPoint = point; }
    public void setSecondPoint(int point) { secondPoint = point; }
    public int getFirstPoint() { return firstPoint; }
    public int getSecondPoint() { return secondPoint; }



    @Override
    public void render() {
        screen.render(1);
    }
}
