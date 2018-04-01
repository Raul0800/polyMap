package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

/**
 * Created by UltraBook Samsung on 26.03.2018.
 */

public class PathScreen extends Stage implements Screen{

    private Stage stage;
    public MyGame game;
    private boolean isPressed;
    private Texture map = new Texture("1_plan_main.png");
    private ShapeRenderer line;

    private int firstPoint, secondPoint;

    PathScreen(final MyGame game) {

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // Text Button "BACK"
        Button button = new TextButton("< BACK", mySkin, "default");
        button.setSize(250, 100);
        button.setPosition(50, game.getHeightScreen() - 150);
        button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isPressed) {
                    game.setScreen(game.inputABScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true;
            }
        });
        stage.addActor(button);

        line = new ShapeRenderer();
    }

    @Override
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

        try {
            drawPath();
        }
        catch (UndirGraph.NoSuchVertexException ex) {
            System.out.println("no vertex");
        }

        stage.act(delta);
        stage.draw();
    }


    public void setFirstPoint (int point) { firstPoint = point; }
    public void setSecondPoint (int point) { secondPoint = point; }


    void drawPath () {

        if (!(game.getGraph().hasVertex(firstPoint) && game.getGraph().hasVertex(secondPoint))) {
            throw new UndirGraph.NoSuchVertexException("no vertex");
        }

        line.begin(ShapeRenderer.ShapeType.Filled);
        line.setColor(Color.RED);

        ArrayList<Vertex> path = game.getGraph().searchPath(firstPoint, secondPoint);

        for(int i = 0; i < path.size() - 1; i++) {
            line.rectLine(path.get(i).getX(),     path.get(i).getY() + game.getHeightScreen() / 6,
                          path.get(i + 1).getX(), path.get(i + 1).getY() + game.getHeightScreen() / 6,
                    5);
        }
        line.rectLine(path.get(0).getX(),     path.get(0).getY() + game.getHeightScreen() / 6,
                  path.get(0).getX() + 8, path.get(0).getY() + game.getHeightScreen() / 6 + 8,
                10);
        line.rectLine(path.get(path.size() - 1).getX(),     path.get(path.size() - 1).getY() + game.getHeightScreen() / 6,
                  path.get(path.size() - 1).getX() + 8, path.get(path.size() - 1).getY() + game.getHeightScreen() / 6 + 8,
                10);
        line.end();
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
