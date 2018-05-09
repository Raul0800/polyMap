package com.app;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MapScreen extends Stage implements Screen, GestureListener{
    private SpriteBatch batch;
    private Sprite sprite;
    private int widthMapPict;//ширина картинки карты
    private int heightMapPict;//высота картинки карты
    private float positionMapW, positionMapH;//позиция по У картинки карты
    private int stateWidthScreen,stateHeightScreen;//позиция по Х картинки карты
    //все переменные,описанные выше, изменяются при масштабировании
    private float statePositionW,statePositionH,stateWMap,stateHMap;// неизменная позиция и размеры картинки

    private int firstPoint, secondPoint;
    ShapeRenderer pointShape;
    private boolean onePointMode = false;

    private Stage stage;
    private MyGame game;
    private TextField textFieldSearch;
    private String currImage = "GZ_1.png";
    private Texture map;
    private boolean searchButtonPressed, switchFlButtonPressed;
    private Dialog dialog;


    MapScreen(final MyGame game) {
//////////////////////////
        this.game = game;
        stage = new Stage(new ScreenViewport());
        int col_width = game.getWidthScreen() / 3;
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


        // Error dialog
        dialog = new Dialog("", skin, "default");
        dialog.setColor(Color.CLEAR);
        dialog.text("   Audience is not found!   ");
        dialog.button("   OK   ", true); //sends "true" as the result

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
                    switchFlButtonPressed = false;
                    deleteWaiter();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switchFlButtonPressed = true;
                getWaiter();
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
                    switchFlButtonPressed = false;
                    deleteWaiter();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getWaiter();
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
        TextButton searchButton = new TextButton("SEARCH >", skin, "default");
        searchButton.setSize(200, 100);
        searchButton.setPosition(game.getWidthScreen() - 200, game.getHeightScreen() - 200);
        searchButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (searchButtonPressed) {

                    Gdx.input.setOnscreenKeyboardVisible(false);
                    deleteWaiter();
                    try {
                        firstPoint = Integer.parseInt(textFieldSearch.getText());
                        if(! game.getGraph().hasVertex(firstPoint) ) {
                            throw new UndirGraph.NoSuchVertexException("no vertex");
                        }
                    }
                    catch (NumberFormatException | UndirGraph.NoSuchVertexException ex) {
                        game.existError = true;
                        return;
                    }
                    stage.unfocusAll();

                    pointShape = new ShapeRenderer();
                    onePointMode = true;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getWaiter();
                searchButtonPressed = true;
                return true;
            }
        });
        stage.addActor(searchButton);
    }

    public void drawPoint() {

        pointShape.begin(ShapeRenderer.ShapeType.Filled);
        pointShape.setColor(Color.RED);

        pointShape.rectLine(game.getGraph().getVertex(firstPoint).getX(),
                game.getGraph().getVertex(firstPoint).getY() + game.getHeightScreen() / 6,
                game.getGraph().getVertex(firstPoint).getX() + 8,
                game.getGraph().getVertex(firstPoint).getY() + game.getHeightScreen() / 6 + 8,
                10);

        pointShape.end();
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

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        //Gdx.input.setInputProcessor(stage);///////////////!!!!!!!!!!!!!!!!!!!!!!!!
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(this));
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(stage);

        if(game.existError) {
            dialog.show(stage);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();
        stage.act(delta);

        if(onePointMode)
            drawPoint();

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

