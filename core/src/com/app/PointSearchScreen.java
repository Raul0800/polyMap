package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import javax.xml.soap.Text;

/**
 * Created by UltraBook Samsung on 27.03.2018.
 */

public class PointSearchScreen extends Stage implements Screen {
    private Stage stage;
    public MyGame game;
    public Texture map = new Texture("I_plan_Draft_Final.png");

    private boolean backButtonPressed;

    private int point;
    ShapeRenderer pointShape;

    PointSearchScreen(final MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //Text Button "BACK"
        TextButton backButton = new TextButton("< BACK", skin, "default");
        backButton.setSize(250, 100);
        backButton.setPosition(50, game.getHeightScreen() - 150);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backButtonPressed) {
                    dispose();
                    game.setScreen(game.mapScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButtonPressed = true;
                return true;
            }
        });
        stage.addActor(backButton);

        pointShape = new ShapeRenderer();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(map, 0, game.getHeightScreen()/6, game.getWidthScreen(), game.getHeightScreen()/2);
        stage.getBatch().end();

        drawPoint();

        stage.act(delta);
        stage.draw();
    }

    public void setPoint (int point) { this.point = point; }

    public void drawPoint() {

        if(! game.getGraph().hasVertex(point) ) {
            throw new UndirGraph.NoSuchVertexException("no vertex\n");
        }
        pointShape.begin(ShapeRenderer.ShapeType.Filled);
        pointShape.setColor(Color.RED);

        pointShape.rectLine(game.getGraph().getVertex(point).getX(),
                game.getGraph().getVertex(point).getY() + game.getHeightScreen() / 6,
                game.getGraph().getVertex(point).getX() + 8,
                game.getGraph().getVertex(point).getY() + game.getHeightScreen() / 6 + 8,
                10);

        pointShape.end();
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

