package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class FirstScreen extends Stage implements Screen {

    private Stage stage;
    private MyGame game;
    private boolean isPressed;

    FirstScreen(final MyGame game) {

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        int row_height = 200;
        int col_width = 200;

        stage.addActor(createImageButton("Settings_200up.png", "Settings_200down.png", col_width, row_height, 2 * col_width, 0));
        stage.addActor(createImageButton("Map_200up.png", "Map_200down.png", col_width, row_height, 0, 0));
    }

    private Button createImageButton(String textureNameUp, String textureNameDown, float width, float height, float x, float y) {
        Texture tDown = new Texture(textureNameDown);
        Texture tUp = new Texture(textureNameUp);

        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(tUp));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(tDown));

        ImageButton button = new ImageButton(drawableUp, drawableDown);
        button.setSize(width, height);
        button.setPosition(x, y);
        button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isPressed) {
                    dispose();
                    game.setScreen(game.secondScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true;
            }
        });
        return button;
    }

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

    @Override
    public void dispose() {
    }
}
