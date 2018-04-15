package com.app;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

import static java.lang.Math.abs;

/**
 * Created by UltraBook Samsung on 27.03.2018.
 */

public class MapScreen extends Stage implements Screen, GestureListener{
    private SpriteBatch batch;
    private Sprite sprite;
    private  int widthMapPict;
    private int heightMapPict;
    private  float positionMapW, positionMapH;
    private  int stateWidthScreen;
    private int stateHeightScreen;
    private  float statePositionW,statePositionH;
    InputMultiplexer inputMultiplexer;

    private Stage stage;
    public MyGame game;
    private TextField textFieldSearch;
    public String currImage = new String("GZ_1.png");
    public Texture map;
    private boolean backButtonPressed, searchButtonPressed, switchFlButtonPressed;

    void initPicture(String currImage){
        stateWidthScreen = widthMapPict = Gdx.app.getGraphics().getWidth();
        stateHeightScreen = heightMapPict = Gdx.app.getGraphics().getHeight();
        heightMapPict = heightMapPict / 2;
        statePositionW = positionMapW = 0;
        statePositionH = positionMapH = heightMapPict/3;

        map = new Texture(currImage);
        batch = new SpriteBatch();
        sprite = new Sprite(map);
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW, positionMapH);

    }
    MapScreen(final MyGame game) {
//////////////////////////
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

        //Settings colours for buttons
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

        //Обозначение для ниже стоящих кнопок.
        TextButton nameOfSwitchFlButton = new TextButton("Floors", skin, "default");
        nameOfSwitchFlButton.setSize(100, 50);
        nameOfSwitchFlButton.setPosition(game.getWidthScreen() - 150, game.getHeightScreen() - 260);
        stage.addActor(nameOfSwitchFlButton);

        //Массив кнопок отвечающих за выбор этажа. Создано в виде массива с возможностью дальнейшнего
        //расширения.
        TextButton []switchFlButton = new TextButton[2];
        switchFlButton[0] = new TextButton("0", skin, "default");
        switchFlButton[0].setSize(100, 50);
        switchFlButton[0].setPosition(game.getWidthScreen() - 150, game.getHeightScreen() - 311);
        switchFlButton[0].addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (switchFlButtonPressed) {
                    if (!currImage.equals("GZ_0.png")) {
                        dispose();
                        currImage = "GZ_0.png";
                        initPicture(currImage);
                    }
                    switchFlButtonPressed = false;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switchFlButtonPressed = true;
                return true;
            }
        });
        stage.addActor(switchFlButton[0]);

        switchFlButton[1] = new TextButton("1", skin, "default");
        switchFlButton[1].setSize(100, 50);
        switchFlButton[1].setPosition(game.getWidthScreen() - 150, game.getHeightScreen() - 362);
        switchFlButton[1].addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (switchFlButtonPressed) {
                    if (!currImage.equals("GZ_1.png")) {
                        dispose();
                        currImage = "GZ_1.png";
                        initPicture(currImage);
                    }
                    switchFlButtonPressed = false;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switchFlButtonPressed = true;
                return true;
            }
        });
        stage.addActor(switchFlButton[1]);

        //Text Field for point
        textFieldSearch = new TextField("", skin);
        textFieldSearch.setSize(game.getWidthScreen() - 200, 100);
        textFieldSearch.setPosition(0, game.getHeightScreen() - 200);
        stage.addActor(textFieldSearch);

        //Text Button "SEARCH"
        TextButton searchButton = new TextButton("GO >", skin, "default");
        searchButton.setSize(200, 100);
        searchButton.setPosition(game.getWidthScreen() - 200, game.getHeightScreen() - 200);
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
        initPicture(currImage);
        inputMultiplexer = new InputMultiplexer();
        //Gdx.input.setInputProcessor(stage);///////////////!!!!!!!!!!!!!!!!!!!!!!!!
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(this));
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();
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
        batch.dispose();
        map.dispose();
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
        positionMapH = positionMapH - deltaY;
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
            widthMapPict += coeffForScale;
            heightMapPict += coeffForScale;

        }
        if (initialDistance >= distance && widthMapPict > stateWidthScreen)
        {
            widthMapPict -= coeffForScale;
            heightMapPict -= coeffForScale;
        }
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW,positionMapH);
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

