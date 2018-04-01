package com.app;


import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static java.lang.Math.abs;

public class MapScreen extends Stage  implements Screen ,GestureListener{
    private Stage stage;
    public MyGame game;
    public Texture map;
    private boolean isPressed;
    private SpriteBatch batch;
    private Sprite sprite;
    private  int widthMapPict;
    private int heightMapPict;
    private  float positionMapW, positionMapH;
    private  int stateWidthScreen;
    private int stateHeightScreen;
    private  float statePositionW,statePositionH;
    InputMultiplexer inputMultiplexer;
    MapScreen(final MyGame game) {
        //MAP
        /*get size of device screen*/
        stateWidthScreen = widthMapPict = Gdx.app.getGraphics().getWidth();
        stateHeightScreen = heightMapPict = Gdx.app.getGraphics().getHeight();
        heightMapPict = heightMapPict / 2;
        this.game = game;
        stage = new Stage(new ScreenViewport());
        statePositionW = positionMapW = 0;
        statePositionH = positionMapH = heightMapPict/3;
        int row_height = 100;
        int col_width = 100;
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        TextButton button = new TextButton("BACK", skin, "default");
        button.setSize(col_width, row_height);
        button.setPosition(col_width-100, 1185);

        button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isPressed) {
                    dispose();
                    game.setScreen(game.firstScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true;
            }
        });
        stage.addActor(button);
    }

    public void show() {
        map = new Texture("I_plan_Draft_Final.png");
        batch = new SpriteBatch();
        sprite = new Sprite(map);
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW, positionMapH);
        inputMultiplexer = new InputMultiplexer();
        //Gdx.input.setInputProcessor(stage);///////////////!!!!!!!!!!!!!!!!!!!!!!!!
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(this));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("RESIZE!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("touchDown");
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.println("longPress");

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("fling");
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        System.out.println("PAAAAAAAAAAAAAAAAAAAAAAAAAAAN:");
        positionMapH = positionMapH - deltaY;
        System.out.println("abs(widthMapPict - stateWidthScreen): " + abs(widthMapPict - stateWidthScreen) + "\n");
        System.out.println("abs(positionMapW + deltaX): " + abs(positionMapW + deltaX));
        positionMapW = positionMapW + deltaX;
        sprite.setPosition(positionMapW, positionMapH);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        System.out.println("panStop");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        final int coeffForScale = 10;
        if (initialDistance < distance) {
            widthMapPict += coeffForScale;
            heightMapPict += coeffForScale;

        }
        if (initialDistance >= distance && widthMapPict > stateWidthScreen)
        {
            widthMapPict -= coeffForScale;
            heightMapPict -= coeffForScale;
        }
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW,positionMapH);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
       return false;
    }

    @Override
    public void pinchStop() {
        System.out.println("pinchStop");
    }
}