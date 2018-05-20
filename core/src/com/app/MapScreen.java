package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
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

import java.util.ArrayList;


public class MapScreen extends Stage implements Screen, GestureListener{
    private SpriteBatch batch;
    private Sprite sprite;
    private float gX,gY;
    private int widthMapPict;//ширина картинки карты
    private int heightMapPict;//высота картинки карты
    private float positionMapW, positionMapH;//позиция по У картинки карты
    private int stateWidthScreen,stateHeightScreen;//позиция по Х картинки карты
    //все переменные,описанные выше, изменяются при масштабировании
    //private float statePositionW,statePositionH,stateWMap,stateHMap;// неизменная позиция и размеры картинки

    private int firstPoint, secondPoint;
    ShapeRenderer pointShape;
    private boolean onePointMode = false, twoPointMode = false;

    private Stage stage;
    private MyGame game;
    private TextField firstPointTextField, secondPointTextField;
    private String currImage = "GZ_1.png";
    private Texture map;
    private boolean searchButtonPressed, switchFlButtonPressed, twoPointModeButtonPressed, onePointModeButtonPressed;
    private Dialog dialog;
    private OrthographicCamera camera = null;
    //private BitmapFont fontForMenu = getFont(Color.BLACK, 42);


    MapScreen(final MyGame game) {
        gX = gY = 0;
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Color colButUp = new Color(0.7f, 0.5f, 1, 0.6f);
        Color colButDown = new Color(0.7f,0.3f, 0.7f, 0.3f);
        int sizeFontBut = 25;

        // Error dialog
        dialog = new Dialog("", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        dialog.setColor(Color.CLEAR);
        dialog.text("   Аудитория не найдена!   ");
        dialog.button("   OK   ", true); //sends "true" as the result

        //Обозначение для ниже стоящих кнопок.

        float sizeHeight_bFloor = game.getHeightScreen() * 0.07f,
                sizeWidth_bFloor = game.getWidthScreen() * 0.07f;

        float positionHeight_bFloor = game.getHeightScreen() * 0.8f,
                positionWidth_bFloor = game.getWidthScreen() - sizeWidth_bFloor - game.getWidthScreen() * 0.01f;

        //TextButton nameOfSwitchFlButton = new TextButton("Floors", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        //nameOfSwitchFlButton.setSize(sizeWidth_bFloor, sizeHeight_bFloor);
        //nameOfSwitchFlButton.setPosition(positionWidth_bFloor, positionHeight_bFloor);
        //nameOfSwitchFlButton.setName("nameOfSwitchFlButton");
        //stage.addActor(nameOfSwitchFlButton);

        pointShape = new ShapeRenderer();

        //Массив кнопок отвечающих за выбор этажа. Создано в виде массива с возможностью дальнейшнего
        //расширения.
        TextButton []switchFlButton = new TextButton[2];
        switchFlButton[0] = new TextButton("0", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        switchFlButton[0].setSize(sizeWidth_bFloor, sizeHeight_bFloor);
        switchFlButton[0].setPosition(positionWidth_bFloor, positionHeight_bFloor - sizeHeight_bFloor);
        switchFlButton[0].addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (switchFlButtonPressed) {
                    if (!currImage.equals("GZ_0.png")) {
                        dispose();
                        currImage = "GZ_0.png";
                        setStartPositionMap();
                    }
                    switchFlButtonPressed = false;
                    deleteWaiter();
                    onePointMode = false;
                    twoPointMode = false;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switchFlButtonPressed = true;
                getWaiter();
                return true;
            }
        });
        switchFlButton[0].setName("switchFlButton[0]");
        stage.addActor(switchFlButton[0]);

        switchFlButton[1] = new TextButton("1", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        switchFlButton[1].setSize(sizeWidth_bFloor, sizeHeight_bFloor);
        switchFlButton[1].setPosition(positionWidth_bFloor, positionHeight_bFloor - 2*sizeHeight_bFloor);
        switchFlButton[1].addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (switchFlButtonPressed) {
                    if (!currImage.equals("GZ_1.png")) {
                        dispose();
                        currImage = "GZ_1.png";
                        setStartPositionMap();
                    }
                    switchFlButtonPressed = false;
                    deleteWaiter();
                    onePointMode = false;
                    twoPointMode = false;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getWaiter();
                switchFlButtonPressed = true;
                return true;
            }
        });
        switchFlButton[1].setName("switchFlButton[1]");
        stage.addActor(switchFlButton[1]);

        //Text Field for point
        float sizeHeight_fSearch = game.getHeightScreen() * 0.07f,
                sizeWidth_fSearch = game.getWidthScreen() * 0.98f;

        float positionHeight_fSearch = game.getHeightScreen() * 0.91f,
                positionWidth_fSearch = game.getWidthScreen() * 0.01f;

        firstPointTextField = new TextField("", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        firstPointTextField.setSize(sizeWidth_fSearch, sizeHeight_fSearch);
        firstPointTextField.setMessageText("Search in GZ");
        firstPointTextField.setPosition(positionWidth_fSearch, positionHeight_fSearch);
        firstPointTextField.setName("firstPointTextField");
        stage.addActor(firstPointTextField);

        secondPointTextField = new TextField("", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        secondPointTextField.setSize(sizeWidth_fSearch, sizeHeight_fSearch);
        secondPointTextField.setMessageText("To");
        secondPointTextField.setPosition(positionWidth_fSearch, positionHeight_fSearch - 100);
        secondPointTextField.setName("secondPointTextField");


        //Skin testSkin = getSkin(new Color(1,0,0,1), );
        //Text Button "SEARCH"
        float sizeHeight_bSearch = game.getHeightScreen() * 0.07f,
                sizeWidth_bSearch = game.getWidthScreen() * 0.17f;

        float positionHeight_bSearch = game.getHeightScreen() * 0.91f,
                positionWidth_bSearch = game.getWidthScreen() * 0.81f;

        final TextButton onePointModeButton = new TextButton("1pMode", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        final TextButton twoPointModeButton = new TextButton("2pMode", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");

        onePointModeButton.setSize(sizeWidth_bSearch, sizeHeight_bSearch);
        onePointModeButton.setPosition(100, 100);
        onePointModeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (onePointModeButtonPressed) {
                    deleteWaiter();
                    stage.addActor(twoPointModeButton);
                    onePointModeButton.remove();
                    secondPointTextField.remove();
                    firstPointTextField.setMessageText("Search in GZ");
                    secondPointTextField.setText("");
                    onePointMode = false;
                    twoPointMode = false;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getWaiter();
                onePointModeButtonPressed = true;
                return true;
            }
        });

        twoPointModeButton.setSize(sizeWidth_bSearch, sizeHeight_bSearch);
        twoPointModeButton.setPosition(100, 100);
        twoPointModeButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (twoPointModeButtonPressed) {
                    deleteWaiter();
                    stage.addActor(secondPointTextField);
                    stage.addActor(onePointModeButton);
                    firstPointTextField.setMessageText("From");
                    onePointMode = false;
                    twoPointMode = false;

                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getWaiter();
                twoPointModeButtonPressed = true;
                return true;
            }
        });
        stage.addActor(twoPointModeButton);
    }
    //Новый метод для шрифтов!
    public BitmapFont getFont (Color color, int size){
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 42;
        parameter.color = color;

        parameter.characters = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ\"\n" +
                "                                              + \"абвгдеёжзийклмнопрстуфхцчшщъыьэюя\"\n" +
                "                                              + \"1234567890.,:;_¡!¿?\"\n" +
                "                                              + \"abcdefghijklmnopqrstuvwxyz\"n" +
                "                                              +  \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\"n";

        {
            final float sizeHeight = 1280, sizeWidth = 720;
            parameter.size = (int)(parameter.size * (Gdx.graphics.getWidth() / sizeWidth));
        }
        font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font;
    }

    public Skin getSkin (Color colorUp, Color colorDown, int sizeFont, Color colorFont){
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        BitmapFont myFont = getFont(colorFont, sizeFont);
        //BitmapFont myFont = new BitmapFont();


        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skin.add("default", myFont);//new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("green", colorUp);
        textButtonStyle.down = skin.newDrawable("green", colorDown);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        return skin;
    }

    public void setStartPositionMap (){
        stateWidthScreen = widthMapPict = Gdx.app.getGraphics().getWidth();
        stateHeightScreen = heightMapPict = Gdx.app.getGraphics().getHeight();
        heightMapPict = heightMapPict / 2;
        positionMapW = 0;
        positionMapH = heightMapPict/3;

        map = new Texture(currImage);
        batch = new SpriteBatch();
        sprite = new Sprite(map);
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW, positionMapH);
        camera = new OrthographicCamera(stateWidthScreen, stateHeightScreen);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        camera.update();

    }

    public void drawPoint() {

        pointShape.begin(ShapeRenderer.ShapeType.Filled);
        pointShape.setColor(Color.RED);
        pointShape.setProjectionMatrix(camera.combined);

        pointShape.rectLine(game.getGraph().getVertex(firstPoint).getX(),
                game.getGraph().getVertex(firstPoint).getY() + game.getHeightScreen() / 6,
                game.getGraph().getVertex(firstPoint).getX() + 8,
                game.getGraph().getVertex(firstPoint).getY() + game.getHeightScreen() / 6 + 8,
                10);

        pointShape.end();
    }

    private void drawPath() {
        pointShape.begin(ShapeRenderer.ShapeType.Filled);
        pointShape.setColor(Color.RED);
        ArrayList<Vertex> path = game.getGraph().searchPath(firstPoint, secondPoint);

        for(int i = 0; i < path.size() - 1; i++) {
            pointShape.rectLine((path.get(i).getX() + positionMapW),
                    (path.get(i).getY() + positionMapH),
                    (path.get(i + 1).getX() + positionMapW),
                    (path.get(i + 1).getY() + positionMapH),
                    5);
        }
        pointShape.rectLine((path.get(0).getX() + positionMapW),
                (path.get(0).getY() + positionMapH),
                (path.get(0).getX() + positionMapW + 8),
                (path.get(0).getY() + positionMapH + 8),
                10);
        pointShape.rectLine((path.get(path.size() - 1).getX() + positionMapW),
                (path.get(path.size() - 1).getY() + positionMapH),
                ( path.get(path.size() - 1).getX() + positionMapW + 8),
                (path.get(path.size() - 1).getY() + positionMapH + 8),
                10);
        pointShape.end();
    }


    public void getWaiter(){
        float positionHeightWaiter = game.getHeightScreen() * 0.4f;
        float positionWidthWaiter = 0;

        float sizeHeightWaiter = game.getHeightScreen() * 0.15f;
        float sizeWidthWaiter = game.getWidthScreen();
        Color colorWaiter = new Color(0,0,0,0.5f);
        Color colorFont = new Color (1,1,1,1);
        int sizeFont = 35;

        TextButton waiter = new TextButton("Wait, please...",
                getSkin(colorWaiter, colorWaiter, sizeFont, colorFont), "default");
        waiter.setSize(sizeWidthWaiter, sizeHeightWaiter);
        waiter.setPosition(positionWidthWaiter, positionHeightWaiter);
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
        setStartPositionMap ();

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

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Gdx.input.setOnscreenKeyboardVisible(false);
            deleteWaiter();

            try {
                firstPoint = Integer.parseInt(firstPointTextField.getText());
                onePointMode = true;
                System.out.println(firstPoint);

                if(firstPoint < 0)
                    throw new NumberFormatException("");
                if (!game.getGraph().hasVertex(firstPoint)) {
                    throw new UndirGraph.NoSuchVertexException("no vertex");
                }
            } catch (NumberFormatException | UndirGraph.NoSuchVertexException ex) {
                onePointMode = false;
                game.existError = true;
                return;
            }

            try {
                twoPointMode = true;
                secondPoint = Integer.parseInt(secondPointTextField.getText());
                System.out.println(secondPoint);

                if(secondPoint < 0)
                    throw new NumberFormatException("");
                if (!game.getGraph().hasVertex(secondPoint)) {
                    throw new UndirGraph.NoSuchVertexException("no vertex");
                }
            } catch (NumberFormatException | UndirGraph.NoSuchVertexException ex) {
                twoPointMode = false;
                game.existError = true;
                return;
            }

        }

        batch.begin();
        sprite.draw(batch);
        batch.end();

        if(onePointMode)
            drawPoint();
        if(twoPointMode)
            drawPath();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
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
      if (camera.position.x - deltaX*camera.zoom >= 0 &&
              camera.position.x - deltaX*camera.zoom<= stateWidthScreen
              && camera.position.y+deltaY*camera.zoom >= positionMapH &&
              camera.position.y + deltaY*camera.zoom <= positionMapH+heightMapPict
              ){
                    camera.translate(-deltaX*camera.zoom ,deltaY*camera.zoom );
             camera.update();

      //  System.out.println("camera.position.x: " + camera.position.x);
      }
      return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        float initialDistance = initialPointer1.dst(initialPointer2);
        float distance = pointer1.dst(pointer2);
        if (initialDistance < distance && camera.zoom * 0.99f <= 1)
            camera.zoom = camera.zoom * 0.99f;
        if (initialDistance >  distance && camera.zoom / 0.99f <=1)
            camera.zoom = camera.zoom / 0.99f;
        if (camera.zoom <=1 && initialDistance != distance){
            camera.translate((-(pointer1.x+pointer2.x)/2+ (initialPointer1.x+initialPointer2.x)/2) * 0.015f,
                    ((pointer1.y+pointer2.y)/2-(initialPointer1.y+initialPointer2.y)/2) * 0.015f);
            camera.update();
            //System.out.println("KEKEKKEKE" + (-(pointer1.y+pointer2.y)/2+(initialPointer1.y+initialPointer2.y)/2));
        }
        return true;
    }

    @Override
    public void pinchStop() {

    }
}

