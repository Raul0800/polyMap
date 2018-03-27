package com.app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MapScreen extends Stage implements Screen {
    private Stage stage;
    public MyGame game;

    public Texture map = new Texture("I_plan_Draft_Final.png");

    private boolean isPressed;
    MapScreen(final MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

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
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //MAP
        /*get size of device screen*/
        int widthScreen = Gdx.app.getGraphics().getWidth();
        int heightScreen = Gdx.app.getGraphics().getHeight();

        stage.getBatch().begin();
        stage.getBatch().draw(map, 0, heightScreen/4,widthScreen, heightScreen/2/*500*/);
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
