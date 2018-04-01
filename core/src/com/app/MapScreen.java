package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public Texture map = new Texture("I_plan_Draft_Final.png");//Basement_plan__Final.png
    public String currImage = new String("I_plan_Draft_Final.png");
    private boolean backButtonPressed, serchButtonPressed, switchFlButtonPressed;

    MapScreen(final MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        //Change colour
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        BitmapFont myFont = new BitmapFont();
        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont.getData().setScale(2,2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", myFont);//new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("green", new Color((float)0.42, (float)0.71, (float)0.27, 1));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);


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
                    game.setScreen(game.firstScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButtonPressed = true;
                return true;
            }
        });
        stage.addActor(backButton);

        //Text Button "switching floors"
        //TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.font = ;
        //textButtonStyle.fontColor = Color.WHITE;
        //textButtonStyle.downFontColor = Color.BLACK;
        //Optional color to toggle between when pressed
        //textButtonStyle.checkedFontColor = Color.GREEN;
        //final TextButton textButton = new TextButton("Text", textButtonStyle);

        //Skin skin = new Skin();

        //labelToolTip = new TextButton("TEST", skin);

        TextButton switchFlButton = new TextButton("<>", skin, "default");
        switchFlButton.setSize(100, 100);
        switchFlButton.setPosition(game.getWidthScreen() - 220, game.getHeightScreen() - 450);
        switchFlButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (switchFlButtonPressed) {
                    dispose();
                    if (currImage.equals("I_plan_Draft_Final.png")) {
                        map = new Texture("Basement_plan__Final.png");
                        currImage = "Basement_plan__Final.png";
                    }
                    else {
                        map = new Texture("I_plan_Draft_Final.png");
                        currImage = "I_plan_Draft_Final.png";
                    }
                    switchFlButtonPressed = false;
                    //Gdx.input.setOnscreenKeyboardVisible(false);
                    //stage.unfocusAll();
                    //game.setScreen(game.firstScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switchFlButtonPressed = true;
                return true;
            }
        });
        stage.addActor(switchFlButton
        );

        //Text Button "SEARCH"
        TextButton searchButton = new TextButton("GO >", skin, "default");
        searchButton.setSize(200, 100);
        searchButton.setPosition(game.getWidthScreen() - 220, game.getHeightScreen() - 300);
        searchButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (serchButtonPressed) {
                    dispose();
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    stage.unfocusAll();
                    game.setScreen(game.secondScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                serchButtonPressed = true;
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

