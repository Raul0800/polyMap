package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainScreen extends Stage implements Screen {

    private Stage stage;
    public MyGame game;
    private boolean isPressed;

    Texture logo = new Texture("logo.png");

    MainScreen(final MyGame game) {
        int col_width = game.getWidthScreen()/3;
        int row_height = col_width;

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.existError = false;

        //Map button
        stage.addActor(createScreenChangingButton("map\\up\\Map_200up.png",
                "map\\down\\Map_200down.png",
                col_width, row_height,
                0, this.game.getHeightScreen() - row_height, this.game.mapScreen));
        //A-B map button
        stage.addActor(createScreenChangingButton("search_on_map\\up\\Search_Map_200up.png",
                "search_on_map\\down\\Search_Map_200down.png",
                col_width, row_height,
                2*col_width, this.game.getHeightScreen() - row_height, this.game.inputABScreen));
        //Gallery button
        stage.addActor(createScreenChangingButton("gallery\\up\\Gallery_200up.png",
                "gallery\\down\\Gallery_200down.png",
                col_width, row_height,
                0, this.game.getHeightScreen() - 2*row_height, this.game.secondScreen));
        //List of departments button
        stage.addActor(createScreenChangingButton(
                "department_list\\up\\ListOfDepartments_200up.png",
                "department_list\\down\\ListOfDepartments_200down.png",
                col_width, row_height,
                2*col_width, this.game.getHeightScreen() - 2*row_height, this.game.secondScreen));
        //Favorites button
        stage.addActor(createScreenChangingButton("favorites\\up\\Fav_200up.png",
                "favorites\\down\\Fav_200down.png",
                col_width, row_height,
                0, this.game.getHeightScreen() - 3*row_height, this.game.secondScreen));
        //Settings button
        stage.addActor(createScreenChangingButton("settings\\up\\Settings_200up.png",
                "settings\\down\\Settings_200down.png",
                col_width, row_height,
                2*col_width, this.game.getHeightScreen() - 3*row_height, this.game.secondScreen));

    }

    private Button createScreenChangingButton(String textureNameUp,
                                              String textureNameDown,
                                              float width, float height,
                                              float x, float y, final Screen screen) {
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
                    game.existError = false;
                    game.setScreen(screen);
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
        int col_width = game.getWidthScreen()/3;
        int row_height = col_width;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(logo, col_width, this.game.getHeightScreen() - 2*row_height, col_width, row_height);
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
