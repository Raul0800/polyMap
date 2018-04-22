package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainScreen extends Stage implements Screen {

    private Stage stage;
    private MyGame game;
    private boolean isPressed;

    private Texture logo = new Texture("logo.png");


    private static void addListeners(EventListener listener, Actor... actors) {
        for (Actor actor : actors)
            actor.addListener(listener);
    }

    protected static Drawable getTintedDrawable(TextureAtlas.AtlasRegion region, Color color) {
        Sprite sprite = new Sprite(region);
        sprite.setColor(color);
        return new SpriteDrawable(sprite);
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
                    //isPressed = false;
                    game.setScreen(screen);
                    isPressed = false;
                    for (Actor actor : stage.getActors()) {
                        if (actor.getName() == "waiter")
                            actor.addAction(Actions.removeActor());
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                //Change colour for waiter
                Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

                Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                pixmap.setColor(Color.GREEN);
                pixmap.fill();
                BitmapFont myFont = new BitmapFont();
                myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                myFont.getData().setScale(2, 2);
                skin.add("green", new Texture(pixmap));
                skin.add("default", myFont);//new BitmapFont());

                TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
                textButtonStyle.up = skin.newDrawable("black", new Color((float) 0, (float) 0, (float) 0, 0.5f));
                textButtonStyle.font = skin.getFont("default");
                skin.add("default", textButtonStyle);

                int waiter_height = 100;

                TextButton waiter = new TextButton("Wait, please...",
                        skin, "default");
                waiter.setSize(game.getWidthScreen(), waiter_height);
                waiter.setPosition(0, game.getHeightScreen() / 2);
                waiter.setName("waiter");
                stage.addActor(waiter);
                return true;
            }
        });
        return button;
    }


    MainScreen(final MyGame game) {
        int col_width = game.getWidthScreen() / 3;
        int row_height = col_width;
        int toolBar_height = 100;

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game.existError = false;

        //Map button
        stage.addActor(createScreenChangingButton("map\\up\\Map_200.png",
                "map\\down\\Map_200.png",
                col_width, row_height,
                0, this.game.getHeightScreen() - row_height - toolBar_height, this.game.mapScreen));
        //A-B map button
        stage.addActor(createScreenChangingButton("search_on_map\\up\\Search_Map_200.png",
                "search_on_map\\down\\Search_Map_200.png",
                col_width, row_height,
                2 * col_width, this.game.getHeightScreen() - row_height - toolBar_height, this.game.inputABScreen));
        //Gallery button
        stage.addActor(createScreenChangingButton("gallery\\up\\Gallery_200.png",
                "gallery\\down\\Gallery_200.png",
                col_width, row_height,
                0, this.game.getHeightScreen() - 2 * row_height - toolBar_height, this.game.secondScreen));
        //List of departments button
        stage.addActor(createScreenChangingButton(
                "department_list\\up\\ListOfDepartments_200.png",
                "department_list\\down\\ListOfDepartments_200.png",
                col_width, row_height,
                2 * col_width, this.game.getHeightScreen() - 2 * row_height - toolBar_height, this.game.secondScreen));
        //Favorites button
        stage.addActor(createScreenChangingButton("favorites\\up\\Fav_200.png",
                "favorites\\down\\Fav_200.png",
                col_width, row_height,
                0, this.game.getHeightScreen() - 3 * row_height - toolBar_height, this.game.secondScreen));
        //Settings button
        stage.addActor(createScreenChangingButton("settings\\up\\Settings_200.png",
                "settings\\down\\Settings_200.png",
                col_width, row_height,
                2 * col_width, this.game.getHeightScreen() - 3 * row_height - toolBar_height, this.game.secondScreen));
        //Toolbar up ans down
        //Change colour
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        BitmapFont myFont = new BitmapFont();
        myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont.getData().setScale(2, 2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", myFont);//new BitmapFont());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("green", new Color((float) 0.42, (float) 0.68, (float) 0.27, 1));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        //int toolBar_height = 100;

        TextButton toolbarUp = new TextButton("  SPBSTU",
                skin, "default");
        toolbarUp.setSize(game.getWidthScreen(), toolBar_height);
        toolbarUp.setPosition(0, game.getHeightScreen() - 100);
        stage.addActor(toolbarUp);

        TextButton toolbarDown = new TextButton("                                                      " +
                "PolyMap 2018", skin, "default");
        toolbarDown.setSize(game.getWidthScreen(), 50);
        toolbarDown.setPosition(0, 0);
        stage.addActor(toolbarDown);

        final float sidePanelWidth = col_width;
        final float sidePanelHeight = game.getHeightScreen();

        AssetManager buttonAssets = new AssetManager();
        TextureAtlas buttonAtlas;

        buttonAssets.load("data/side_panel_buttons/choose_building.pack", TextureAtlas.class);
        buttonAssets.finishLoading();

        buttonAtlas = buttonAssets.get("data/side_panel_buttons/choose_building.pack");
        Image chooseBuilding = new Image(buttonAtlas.findRegion("choose_building"));
        Image mainBuilding = new Image(buttonAtlas.findRegion("down/main_building200"));

        TextureAtlas atlas = new TextureAtlas("data/menu_ui.atlas");

        final Image image_background = new Image(getTintedDrawable(atlas.findRegion("image_background"), Color.WHITE));
        //final Image button_menu = new Image(atlas.findRegion("button_menu"));

        // initialize NavigationDrawer
        final SidePanel drawer = new SidePanel(sidePanelWidth, sidePanelHeight);

        // add items into drawer panel.
        drawer.add(chooseBuilding).pad(0, 0, game.getHeightScreen()/10, 0).expandX().row();
        drawer.add(mainBuilding).pad(0, 0, 0, 0).expandX().row();
        drawer.add().height(game.getHeightScreen() * 2 / 3).row(); // empty
        // setup attributes for menu navigation drawer.
        drawer.setBackground(image_background.getDrawable());
        drawer.bottom().left();
        drawer.setWidthStartDrag(40f);
        drawer.setWidthBackDrag(0F);
        drawer.setTouchable(Touchable.enabled);

        stage.addActor(drawer);

        /*
        button_menu.setOrigin(Align.center);
        stage.addActor(button_menu);
        drawer.setRotateMenuButton(button_menu, 90f);
        */
        // show the panel
        drawer.showManually(true);

        //button_menu.setName("BUTTON_MENU");
        image_background.setName("IMAGE_BACKGROUND");

        ClickListener listener = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                boolean closed = drawer.isCompletelyClosed();
                Actor actor = event.getTarget();
                if (actor.getName().equals("BUTTON_MENU") || actor.getName().equals("IMAGE_BACKGROUND")) {

                    image_background.setTouchable(closed ? Touchable.enabled : Touchable.disabled);
                    drawer.showManually(closed);
                }
            }
        };

        addListeners(listener, /*button_menu,*/ image_background);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        int col_width = game.getWidthScreen() / 3;
        int row_height = col_width;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(logo, col_width, this.game.getHeightScreen() - 2 * row_height, col_width, row_height);
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
