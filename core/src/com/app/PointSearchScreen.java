package com.app;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import javax.xml.soap.Text;

/**
 * Created by UltraBook Samsung on 27.03.2018.
 */

public class PointSearchScreen extends Stage implements Screen,GestureListener {
    private SpriteBatch batch;
    private Sprite sprite;
    private  int widthMapPict;//ширина картинки карты
    private int heightMapPict;//высота картинки карты
    private  float positionMapW, positionMapH;//позиция по У картинки карты
    private  int stateWidthScreen,stateHeightScreen;//позиция по Х картинки карты
    //все переменные,описанные выше, изменяются при масштабировании
    private  float statePositionW,statePositionH,stateWMap,stateHMap;// неизменная позиция и размеры картинки
    InputMultiplexer inputMultiplexer;
    public String currImage = new String("GZ_1.png");//название картинки

    private Stage stage;
    public MyGame game;
    public Texture map;

    private boolean backButtonPressed;

    private int point;
    ShapeRenderer pointShape;

    PointSearchScreen(final MyGame game) {

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //Settings colours for toolbox
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        BitmapFont myFont = new BitmapFont();
        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont.getData().setScale(2,2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", myFont);//new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("green", new Color((float)0.42, (float)0.68, (float)0.27, 1));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        //Toolbar up ans down
        int toolBar_height = 100;

        TextButton toolbarUp = new TextButton("", skin, "default");
        toolbarUp.setSize(game.getWidthScreen(), toolBar_height);
        toolbarUp.setPosition(0, game.getHeightScreen() - 100);
        stage.addActor(toolbarUp);

        TextButton toolbarDown = new TextButton("", skin, "default");
        toolbarDown.setSize(game.getWidthScreen(), 50);
        toolbarDown.setPosition(0, 0);
        stage.addActor(toolbarDown);

        //Change colour for button
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        myFont = new BitmapFont();
        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont.getData().setScale(2,2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", myFont);//new BitmapFont());

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("green", new Color((float)0.42, (float)0.71, (float)0.27, 1));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        //Text Button "BACK"
        TextButton backButton = new TextButton("< BACK", skin, "default");
        backButton.setSize(250, 100);
        backButton.setPosition(0, game.getHeightScreen()-100);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backButtonPressed) {
                    dispose();
                    game.existError = false;
                    game.setScreen(game.mapScreen);
                    deleteWaiter();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getWaiter();
                backButtonPressed = true;
                return true;
            }
        });
        stage.addActor(backButton);

        pointShape = new ShapeRenderer();
    }

    public void getWaiter(){
        //Change colour for waiter
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        BitmapFont myFont = new BitmapFont();
        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont.getData().setScale(2,2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", myFont);//new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("black", new Color((float)0, (float)0, (float)0, 0.5f));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        int waiter_height = 100;

        TextButton waiter = new TextButton("Wait, please...",
                skin, "default");
        waiter.setSize(game.getWidthScreen(), waiter_height);
        waiter.setPosition(0, game.getHeightScreen() / 2);
        waiter.setName("waiter");
        stage.addActor(waiter);
    }

    public void deleteWaiter(){
        for(Actor actor : stage.getActors()) {
            if (actor.getName() == "waiter")
                actor.addAction(Actions.removeActor());
        }
    }

    public void show() {
        //инициализация полей, каждый раз происходит заново, когда открывается заново окно
        stateWidthScreen = widthMapPict = Gdx.app.getGraphics().getWidth();
        stateHeightScreen = heightMapPict = Gdx.app.getGraphics().getHeight();
        heightMapPict = heightMapPict / 2;
        statePositionW = positionMapW = 0;
        statePositionH = positionMapH = heightMapPict/3;

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

        batch.begin();
        //отрисовка карты через структуру
        sprite.draw(batch);
        batch.end();
        drawPoint();

        stage.act(delta);
        stage.draw();
    }

    public void setPoint (int point) { this.point = point; }

    public void drawPoint() {
        float scaleX = widthMapPict/stateWMap;
        float scaleY = heightMapPict/stateHMap;

        if(! game.getGraph().hasVertex(point) ) {
            throw new UndirGraph.NoSuchVertexException("no vertex\n");
        }
        pointShape.begin(ShapeRenderer.ShapeType.Filled);
        pointShape.setColor(Color.RED);

        pointShape.rectLine(game.getGraph().getVertex(point).getX()*scaleX+positionMapW,
                game.getGraph().getVertex(point).getY()*scaleY + positionMapH,
                game.getGraph().getVertex(point).getX()*scaleX + 8 + positionMapW,
                game.getGraph().getVertex(point).getY()*scaleY + positionMapH,
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

