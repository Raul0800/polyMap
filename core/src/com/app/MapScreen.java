package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.xml.soap.Text;

/**
 * Created by UltraBook Samsung on 27.03.2018.
 */

public class MapScreen extends Stage implements Screen {
    private Stage stage;
    public MyGame game;
    private TextField textFieldSearch;
    public Texture map = new Texture("1_plan_main.png");
    private boolean backButtonPressed, searchButtonPressed;

    MapScreen(final MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));


        //Text Field for point
        textFieldSearch = new TextField("", skin);
        textFieldSearch.setPosition(50, game.getHeightScreen() - 300);
        textFieldSearch.setSize(game.getWidthScreen() - 300, 100);
        stage.addActor(textFieldSearch);

        //Text Button "BACK"
        TextButton backButton = new TextButton("< BACK", skin, "default");
        backButton.setSize(250, 100);
        backButton.setPosition(50, game.getHeightScreen() - 150);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backButtonPressed) {
                    dispose();
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    stage.unfocusAll();
                    textFieldSearch.setText("");
                    game.setScreen(game.mainScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButtonPressed = true;
                return true;
            }
        });
        stage.addActor(backButton);

        //Text Button "SEARCH"
        TextButton searchButton = new TextButton("GO >", skin, "default");
        searchButton.setSize(200, 100);
        searchButton.setPosition(game.getWidthScreen() - 220, game.getHeightScreen() - 300);
        searchButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (searchButtonPressed) {
                    dispose();
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    game.pointSearchScreen.setPoint(Integer.parseInt(textFieldSearch.getText()));
                    stage.unfocusAll();
                    textFieldSearch.setText("");
                    game.setScreen(game.pointSearchScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                searchButtonPressed = true;
                return true;
            }
        });
        stage.addActor(searchButton);

    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(map, 0, game.getHeightScreen()/6, game.getWidthScreen(), game.getHeightScreen()/2/*500*/);
        stage.getBatch().end();

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

    @Override
    public void dispose() {
    }

}

