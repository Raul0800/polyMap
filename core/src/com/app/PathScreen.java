package com.app;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by UltraBook Samsung on 26.03.2018.
 */

public class PathScreen extends Stage implements Screen,GestureListener {
    private SpriteBatch batch;
    private Sprite sprite;
    private  int widthMapPict;//ширина картинки карты
    private int heightMapPict;//высота картинки карты
    private  float positionMapW, positionMapH;//позиция по У картинки карты
    private  int stateWidthScreen,stateHeightScreen;//позиция по Х картинки карты
    //все переменные,описанные выше, изменяются при масштабировании
    private  float statePositionW,statePositionH,stateWMap,stateHMap;// неизменная позиция и размеры картинки
    InputMultiplexer inputMultiplexer;
    public String currImage = new String("1_plan_main.png");//название картинки
    private Stage stage;
    public MyGame game;
    private boolean isPressed;
    private Texture map ;//= new Texture("1_plan_main.png");
    private ShapeRenderer line;
    private int firstPoint, secondPoint;

    PathScreen(final MyGame game) {
        stateWidthScreen = widthMapPict = Gdx.app.getGraphics().getWidth();
        stateHeightScreen = heightMapPict = Gdx.app.getGraphics().getHeight();
        heightMapPict = heightMapPict / 2;
        statePositionW = positionMapW = 0;
        statePositionH = positionMapH = heightMapPict/3;

        this.game = game;
        stage = new Stage(new ScreenViewport());
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

    }

    @Override
    public void show() {
        //инициализация всех полей происходит в этом методе. При загрузке окна этот метод первый, который вызывается
        line = new ShapeRenderer();
        map = new Texture(currImage);
        batch = new SpriteBatch();
        sprite = new Sprite(map);
        stateHMap = heightMapPict;
        stateWMap = widthMapPict;
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW, positionMapH);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(this));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        try {
            batch.begin();
            //отрисовка карты через структуру
            sprite.draw(batch);
            batch.end();
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
//103 182
        //изменила первоночальный вид вывода линии. Теперь координаты линии зависят не от экрана, а от положения картинки
        float scaleX = widthMapPict/stateWMap;
        float scaleY = heightMapPict/stateHMap;

        System.out.println("START:");
        System.out.println("scaleX: " + scaleX);
        System.out.println("path.get(i).getX(): " + path.get(path.size() - 2).getX());
        System.out.println("positionMapW: " + positionMapW);
        System.out.println("path.get(i).getX()+positionMapW: " + path.get(path.size() - 2).getX()+positionMapW);
        System.out.println("path.get(i).getX()+positionMapW)*scaleX: " +
                (path.get(path.size() - 2).getX()+positionMapW)*scaleX);
        System.out.println("END");

        for(int i = 0; i < path.size() - 1; i++) {
            line.rectLine((path.get(i).getX()*scaleX+positionMapW),
                    (path.get(i).getY()*scaleY + positionMapH),
                    (path.get(i + 1).getX()*scaleX+positionMapW),
                    (path.get(i + 1).getY()*scaleY +positionMapH),
                    5);
        }
        line.rectLine((path.get(0).getX()*scaleX+positionMapW),
                (path.get(0).getY()*scaleY + positionMapH),
                (path.get(0).getX()*scaleX+positionMapW + 8),
                (path.get(0).getY()*scaleY +positionMapH + 8),
                10);
        line.rectLine((path.get(path.size() - 1).getX()*scaleX+positionMapW),
                (path.get(path.size() - 1).getY()*scaleY + positionMapH),
                ( path.get(path.size() - 1).getX()*scaleX+positionMapW + 8),
                (path.get(path.size() - 1).getY()*scaleY + positionMapH + 8),
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //System.out.println("PAAAAAAAAAAAAAAAAAAAAAAAAAAAN:");
        positionMapH = positionMapH - deltaY;
        //System.out.println("abs(widthMapPict - stateWidthScreen): " + abs(widthMapPict - stateWidthScreen) + "\n");
        //System.out.println("abs(positionMapW + deltaX): " + abs(positionMapW + deltaX));
        positionMapW = positionMapW + deltaX;
        sprite.setPosition(positionMapW, positionMapH);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        final int coeffForScale = 10;
        if (initialDistance < distance) {
            //расширять
            widthMapPict += coeffForScale;
            heightMapPict += coeffForScale;

        }
        if (initialDistance >= distance && widthMapPict > stateWidthScreen)
        {
            //сужать
            widthMapPict -= coeffForScale;
            heightMapPict -= coeffForScale;
        }
        sprite.setSize(widthMapPict, heightMapPict);
      //  sprite.setPosition(positionMapW,positionMapH);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}