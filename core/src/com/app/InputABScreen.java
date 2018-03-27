package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.naming.Context;

import sun.awt.im.InputMethodManager;

/**
 * Created by UltraBook Samsung on 25.03.2018.
 */

public class InputABScreen extends Stage implements Screen {

    private Stage stage;
    public MyGame game;
    private boolean backButtonPressed;
    private boolean searchButtonPressed;

    private TextField tfFirstPoint;
    private TextField tfSecondPoint;

    InputABScreen(final MyGame game) {

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        int row_height = 200;
        int col_width = 200;

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        tfFirstPoint = new TextField("", mySkin);
        tfSecondPoint = new TextField("", mySkin);

        tfFirstPoint.setPosition(50, 800);
        tfSecondPoint.setPosition(50, 650);

        tfFirstPoint.setSize(500, 100);
        tfSecondPoint.setSize(500, 100);

        stage.addActor(tfFirstPoint);
        stage.addActor(tfSecondPoint);


        // Text Button
        Button searchButton = new TextButton("SEARCH >", mySkin, "default");
        searchButton.setSize(760, 200);
        searchButton.setPosition(0, 0);
        searchButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (searchButtonPressed) {
                    //dispose();
                    game.setScreen(game.pathScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                searchButtonPressed = true;
                return true;
            }
        });
        stage.addActor(searchButton);

        Button button = new TextButton("< BACK", mySkin, "default");
        button.setSize(250, 100);
        button.setPosition(50, 980);
        button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backButtonPressed) {
                    //dispose();
                    game.setScreen(game.firstScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButtonPressed = true;
                return true;
            }
        });
        stage.addActor(button);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
}
