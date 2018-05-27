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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

/**
 * Class for map screen
 */
public class MapScreen extends Stage implements Screen, GestureListener {
    private SpriteBatch batch;
    private Sprite sprite;
    private float gX, gY;
    private int widthMapPict;
    private int heightMapPict;
    private float positionMapW, positionMapH;
    private int stateWidthScreen, stateHeightScreen;


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

    final SidePanel sidePanel;

    /**
     * Constructor, which does main things with the map
     *
     * @param game
     */
    MapScreen(final MyGame game) {
        gX = gY = 0;
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Color colButUp = new Color(0.7f, 0.5f, 1, 0.6f);
        Color colButDown = new Color(0.7f, 0.3f, 0.7f, 0.3f);
        int sizeFontBut = 25;

        pointShape = new ShapeRenderer();
        // Error dialog
        dialog = new Dialog("", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        dialog.setColor(Color.CLEAR);
        dialog.button("\n Аудитория не найдена!\n\n    OK   \n", true); //sends "true" as the result

        float sizeHeight_bFloor = game.getHeightScreen() * 0.07f,
                sizeWidth_bFloor = game.getWidthScreen() * 0.07f;

        float positionHeight_bFloor = game.getHeightScreen() * 0.8f,
                positionWidth_bFloor = game.getWidthScreen() - sizeWidth_bFloor - game.getWidthScreen() * 0.01f;

        //TextButton nameOfSwitchFlButton = new TextButton("Floors", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        //nameOfSwitchFlButton.setSize(sizeWidth_bFloor, sizeHeight_bFloor);
        //nameOfSwitchFlButton.setPosition(positionWidth_bFloor, positionHeight_bFloor);
        //nameOfSwitchFlButton.setName("nameOfSwitchFlButton");
        //stage.addActor(nameOfSwitchFlButton);

        TextButton[] switchFlButton = new TextButton[2];
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
                        onePointMode = twoPointMode = false;
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
        switchFlButton[0].setName("switchFlButton[0]");
        stage.addActor(switchFlButton[0]);

        switchFlButton[1] = new TextButton("1", getSkin(colButUp, colButDown, sizeFontBut, Color.BLACK), "default");
        switchFlButton[1].setSize(sizeWidth_bFloor, sizeHeight_bFloor);
        switchFlButton[1].setPosition(positionWidth_bFloor, positionHeight_bFloor - 2 * sizeHeight_bFloor);
        switchFlButton[1].addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (switchFlButtonPressed) {
                    if (!currImage.equals("GZ_1.png")) {
                        dispose();
                        currImage = "GZ_1.png";
                        setStartPositionMap();
                        onePointMode = twoPointMode = false;
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
        switchFlButton[1].setName("switchFlButton[1]");
        stage.addActor(switchFlButton[1]);

        //Text Field for point
        float sizeHeight_fSearch = game.getHeightScreen() * 0.07f,
                sizeWidth_fSearch = game.getWidthScreen() * 0.82f;//-0.16

        float positionHeight_fSearch = game.getHeightScreen() * 0.91f,
                positionWidth_fSearch = game.getWidthScreen() * 0.15f;

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

        // load atlas file
        TextureAtlas atlas = new TextureAtlas("data/menu_ui.atlas");
        TextureAtlas buttons_new_atlas = new TextureAtlas("data/buttons_new.atlas");
        TextureAtlas logo_atlas = new TextureAtlas("data/logo.atlas");
        // initialize all menu items from atlas region.
        final Image image_map = new Image(buttons_new_atlas.findRegion("baseline_map_black"));
        final Image image_search = new Image(buttons_new_atlas.findRegion("baseline_directions_black"));
       // final Image image_favorite = new Image(buttons_new_atlas.findRegion("baseline_favorite_black"));
        //final Image image_departments = new Image(buttons_new_atlas.findRegion("baseline_school_black"));
        final Image image_help = new Image(new Texture("help.png"));
        final Image image_background = new Image(getTintedDrawable(atlas.findRegion("image_background"), Color.WHITE));
        final Image button_menu = new Image(atlas.findRegion("button_menu"));

        final Image image_logo = new Image(logo_atlas.findRegion("logo"));

        float NAV_HEIGHT = game.getHeightScreen();
        float NAV_WIDTH = game.getWidthScreen() * 3 / 4;
        sidePanel = new SidePanel(NAV_WIDTH, NAV_HEIGHT);

        sidePanel.add(image_logo).size(300, 300).expandX().row();
        sidePanel.add(image_map).size(480, 150).expandX().row();
        sidePanel.add(image_search).size(480, 150).expandX().row();
        sidePanel.add(image_help).size(480, 150).expandX().row();
        //sidePanel.add(image_favorite).size(480, 150).expandX().row();
        //sidePanel.add(image_departments).size(480, 150).expandX().row();
        sidePanel.add().height(getHeight() / 4);

        sidePanel.add(image_background);
        sidePanel.setBackground(image_background.getDrawable());
        sidePanel.bottom().left();
        sidePanel.setWidthStartDrag(40f);
        sidePanel.setWidthBackDrag(0F);


        sidePanel.setRotateMenuButton(button_menu, 90f);

        /* z-index = 1 */
        // add image_background as a separating actor into stage to make smooth shadow with dragging value.
        /*
        image_background.setFillParent(true);
        stage.addActor(image_background);
        sidePanel.setFadeBackground(image_background, 0.5f);
        */
        /* z-index = 2 */
        stage.addActor(sidePanel);

        /* z-index = 3 */
        // add button_menu as a separating actor into stage to rotates with dragging value.
        button_menu.setOrigin(Align.center);
        stage.addActor(button_menu);


        //Clicker
        float positionHeight_bMenu = game.getHeightScreen() * 0.87f,
                positionWidth_bMenu = game.getWidthScreen() * (-0.07f);
        button_menu.setPosition(positionWidth_bMenu, positionHeight_bMenu);
        button_menu.setName("BUTTON_MENU");
        image_search.setName("SEARCH");
        image_background.setName("IMAGE_BACKGROUND");
        image_map.setName("MAP");
        image_help.setName("HELP");

        Image image_shadow = new Image(atlas.findRegion("image_shadow"));
        image_shadow.setHeight(NAV_HEIGHT);
        image_shadow.setX(NAV_WIDTH);
        sidePanel.setAreaWidth(NAV_WIDTH + image_shadow.getWidth());
        sidePanel.addActor(image_shadow);

        // show the panel
        sidePanel.showManually(true);
        ClickListener listener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                boolean closed = sidePanel.isCompletelyClosed();
                Actor actor = event.getTarget();
                if (actor.getName().equals("SEARCH")) {
                    sidePanel.showManually(closed);
                    stage.addActor(secondPointTextField);
                    sidePanel.remove();
                    stage.addActor(sidePanel);
                    firstPointTextField.setMessageText("From");
                    onePointMode = twoPointMode = false;

                } else if (actor.getName().equals("MAP")) {
                    sidePanel.showManually(closed);
                    secondPointTextField.remove();
                    firstPointTextField.setMessageText("Search in GZ");
                    secondPointTextField.setText("");
                    onePointMode = twoPointMode = false;

                } else if (actor.getName().equals("BUTTON_MENU") || actor.getName().equals("IMAGE_BACKGROUND")) {
                    image_background.setTouchable(closed ? Touchable.enabled : Touchable.disabled);
                    sidePanel.showManually(closed);
                } else if (actor.getName().equals("HELP")) {
                    Gdx.net.openURI("https://docs.google.com/document/d/e/2PACX-1vSy42v8tRxigSHiJ4Oi_eDqDct9OBgMkgOPXi-r3lUiQVBF9sTh-bo-gGjvyiB-EfvL_aOHu-XcdNOK/pub");
                }
            }
        };
        addListeners(listener, image_search, button_menu, image_map, image_help);

        stage.addActor(sidePanel);
    }


    /**
     * Method for fonts
     *
     * @param color
     * @param size
     * @return BitMapFont
     */
    public BitmapFont getFont(Color color, int size) {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 42;
        parameter.color = color;

        parameter.characters = "АБВГДЕЁЖЗЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ\"\n" +
                "                                              + \"абвгдеёжзийклмнопрстуфхцчшщъыьэюя\"\n" +
                "                                              + \"1234567890.,:;_¡!¿?\"\n" +
                "                                              + \"abcdefghijklmnopqrstuvwxyz\"n" +
                "                                              +  \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\"n";

        {
            final float sizeHeight = 1280, sizeWidth = 720;
            parameter.size = (int) (parameter.size * (Gdx.graphics.getWidth() / sizeWidth));
        }
        font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font;
    }

    /**
     * Get skin
     *
     * @param colorUp
     * @param colorDown
     * @param sizeFont
     * @param colorFont
     * @return Skin
     */
    public Skin getSkin(Color colorUp, Color colorDown, int sizeFont, Color colorFont) {
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

    /**
     * Set start position map
     */
    public void setStartPositionMap() {
        stateWidthScreen = widthMapPict = Gdx.app.getGraphics().getWidth();
        stateHeightScreen = heightMapPict = Gdx.app.getGraphics().getHeight();
        heightMapPict = heightMapPict / 2;
        positionMapW = 0;
        positionMapH = heightMapPict / 3;

        map = new Texture(currImage);
        batch = new SpriteBatch();
        sprite = new Sprite(map);
        sprite.setSize(widthMapPict, heightMapPict);
        sprite.setPosition(positionMapW, positionMapH);
        camera = new OrthographicCamera(stateWidthScreen, stateHeightScreen);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        camera.update();

    }

    /**
     * Set position map
     */
    public void setPositionMap() {
        camera = new OrthographicCamera(stateWidthScreen, stateHeightScreen);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        camera.update();

    }

    /**
     * Draw point
     */
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

    /**
     * Draw path
     */
    private void drawPath() {
        pointShape.begin(ShapeRenderer.ShapeType.Filled);
        pointShape.setColor(Color.RED);
        ArrayList<Vertex> path = game.getGraph().searchPath(firstPoint, secondPoint);

        for (int i = 0; i < path.size() - 1; i++) {
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
                (path.get(path.size() - 1).getX() + positionMapW + 8),
                (path.get(path.size() - 1).getY() + positionMapH + 8),
                10);
        pointShape.end();
    }

    /**
     * Get waiter
     */
    public void getWaiter() {
        float positionHeightWaiter = game.getHeightScreen() * 0.4f;
        float positionWidthWaiter = 0;

        float sizeHeightWaiter = game.getHeightScreen() * 0.15f;
        float sizeWidthWaiter = game.getWidthScreen();
        Color colorWaiter = new Color(0, 0, 0, 0.5f);
        Color colorFont = new Color(1, 1, 1, 1);
        int sizeFont = 35;

        TextButton waiter = new TextButton("Wait, please...",
                getSkin(colorWaiter, colorWaiter, sizeFont, colorFont), "default");
        waiter.setSize(sizeWidthWaiter, sizeHeightWaiter);
        waiter.setPosition(positionWidthWaiter, positionHeightWaiter);
        waiter.setName("waiter");
        stage.addActor(waiter);
    }

    /**
     * Delete waiter
     */
    public void deleteWaiter() {
        for (Actor actor : stage.getActors()) {
            if (actor.getName() == "waiter")
                actor.addAction(Actions.removeActor());
        }
    }

    /**
     * Add listeners
     *
     * @param listener
     * @param actors
     */
    public static void addListeners(EventListener listener, Actor... actors) {
        for (Actor actor : actors)
            actor.addListener(listener);
    }

    /**
     * Get tinted drawable
     *
     * @param region
     * @param color
     * @return sprite drawable
     */
    public static Drawable getTintedDrawable(TextureAtlas.AtlasRegion region, Color color) {
        Sprite sprite = new Sprite(region);
        sprite.setColor(color);
        return new SpriteDrawable(sprite);
    }

    InputMultiplexer inputMultiplexer;

    /**
     * Show the stage
     */
    public void show() {
        setStartPositionMap();

        inputMultiplexer = new InputMultiplexer();

        //Gdx.input.setInputProcessor(stage);///////////////!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(this));
        //if(sidePanel.isOpen())
        //    Gdx.input.setInputProcessor(sidePanel.gestureDetector);
        //if(sidePanel.isClosed())
        Gdx.input.setInputProcessor(inputMultiplexer);
        //Gdx.input.setInputProcessor(stage);

    }

    @Override
    /**
     * Render the stage
     */
    public void render(float delta) {
        //if(sidePanel.isOpen() || Gdx.input.getX() < 20.0f)
        //    Gdx.input.setInputProcessor(sidePanel.gestureDetector);
        //if(sidePanel.isClosed() && Gdx.input.getX() >= 20.0f)
        //    Gdx.input.setInputProcessor(inputMultiplexer);
        if (sidePanel.isCompletelyClosed()) {
            camera.update();
            batch.setProjectionMatrix(camera.combined);
        }
        Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            setPositionMap();
            Gdx.input.setOnscreenKeyboardVisible(false);
            deleteWaiter();
            try {
                firstPoint = Integer.parseInt(firstPointTextField.getText());
                onePointMode = true;

                if (firstPoint < 0)
                    throw new NumberFormatException("");
                if (!game.getGraph().hasVertex(firstPoint)) {
                    throw new UndirGraph.NoSuchVertexException("no vertex");
                }
            } catch (NumberFormatException | UndirGraph.NoSuchVertexException ex) {
                onePointMode = false;
                dialog.show(stage);
                return;
            }

            try {
                secondPoint = Integer.parseInt(secondPointTextField.getText());
                twoPointMode = true;

                if (secondPoint < 0)
                    throw new NumberFormatException("");
                if (!game.getGraph().hasVertex(secondPoint)) {
                    throw new UndirGraph.NoSuchVertexException("no vertex");
                }
            } catch (NumberFormatException | UndirGraph.NoSuchVertexException ex) {
                if (stage.getActors().contains(secondPointTextField, true)) {
                    dialog.show(stage);
                }
                twoPointMode = false;
                return;
            }
        }

        batch.begin();
        sprite.draw(batch);

        batch.end();

        if ((onePointMode || twoPointMode) && currImage.equals("GZ_0.png")) {
            currImage = "GZ_1.png";
            setStartPositionMap();
        }

        if (onePointMode)
            drawPoint();
        if (twoPointMode)
            drawPath();

        stage.act(delta);
        stage.draw();
    }

    @Override
    /**
     * Resize camera's position
     */
    public void resize(int width, int height) {
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
    /**
     *Dispose the map
     */
    public void dispose() {
        batch.dispose();
        map.dispose();
    }

    /**
     * Processing of interection with the map is below
     */

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
        if (camera.position.x - deltaX * camera.zoom >= 0 &&
                camera.position.x - deltaX * camera.zoom <= stateWidthScreen
                && camera.position.y + deltaY * camera.zoom >= positionMapH &&
                camera.position.y + deltaY * camera.zoom <= positionMapH + heightMapPict
                ) {
            if (sidePanel.isCompletelyClosed()) {
                camera.translate(-deltaX * camera.zoom, deltaY * camera.zoom);
                camera.update();
            }
            // System.out.println("camera.position.x: " + camera.position.x);
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
        if (initialDistance > distance && camera.zoom / 0.99f <= 1)
            camera.zoom = camera.zoom / 0.99f;
        if (camera.zoom <= 1 && initialDistance != distance) {
            camera.translate((-(pointer1.x + pointer2.x) / 2 + (initialPointer1.x + initialPointer2.x) / 2) * 0.015f,
                    ((pointer1.y + pointer2.y) / 2 - (initialPointer1.y + initialPointer2.y) / 2) * 0.015f);
            camera.update();

        }
        return true;
    }

    @Override
    public void pinchStop() {

    }
}
