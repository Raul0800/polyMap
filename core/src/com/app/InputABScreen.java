package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


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

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        //Change colour
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        BitmapFont myFont = new BitmapFont();
        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont.getData().setScale(2,2);
        mySkin.add("green", new Texture(pixmap));
        mySkin.add("default", myFont);//new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = mySkin.newDrawable("green", new Color((float)0.42, (float)0.71, (float)0.27, 1));
        textButtonStyle.font = mySkin.getFont("default");
        mySkin.add("default", textButtonStyle);



        //Text Fields for two points
        tfFirstPoint = new TextField("", mySkin);
        tfSecondPoint = new TextField("", mySkin);
        tfFirstPoint.setPosition(50, game.getHeightScreen() - 300);
        tfSecondPoint.setPosition(50, game.getHeightScreen() - 450);
        tfFirstPoint.setSize(game.getWidthScreen() - 100, 100);
        tfSecondPoint.setSize(game.getWidthScreen() - 100, 100);

        stage.addActor(tfFirstPoint);
        stage.addActor(tfSecondPoint);

        // Text Button "SEARCH"
        Button searchButton = new TextButton("SEARCH >", mySkin, "default");
        searchButton.setSize(game.getWidthScreen(), 150);
        searchButton.setPosition(0, game.getHeightScreen() - 700);

        searchButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (searchButtonPressed) {
                    try {
                        game.pathScreen.setFirstPoint(Integer.parseInt(tfFirstPoint.getText()));
                        game.pathScreen.setSecondPoint(Integer.parseInt(tfSecondPoint.getText()));
                    }
                    catch (NumberFormatException ignored) {
                        return;
                    }

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

        //Text Button "BACK"
        Button button = new TextButton("< BACK", mySkin, "default");
        button.setSize(250, 100);
        button.setPosition(50, game.getHeightScreen() - 150);
        button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backButtonPressed) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    stage.unfocusAll();
                    tfFirstPoint.setText("");
                    tfSecondPoint.setText("");
                    game.setScreen(game.mainScreen);

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
