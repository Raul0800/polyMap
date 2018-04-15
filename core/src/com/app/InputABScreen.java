package com.app;

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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InputABScreen extends Stage implements Screen {

    private Stage stage;
    private MyGame game;
    private boolean backButtonPressed;
    private boolean searchButtonPressed;

    private TextField tfFirstPoint;
    private TextField tfSecondPoint;
    private Dialog dialog;

    private BitmapFont pointFont;

    InputABScreen(final MyGame game) {

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //Settings colours for toolbox
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        BitmapFont buttonFont = new BitmapFont();
        buttonFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        buttonFont.getData().setScale(2,2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", buttonFont);//new BitmapFont());

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

        //Settings colour for buttons
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        buttonFont = new BitmapFont();
        buttonFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        buttonFont.getData().setScale(2,2);
        skin.add("green", new Texture(pixmap));
        skin.add("default", buttonFont);//new BitmapFont());

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("green", new Color((float)0.42, (float)0.71, (float)0.27, 1));
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        // Error dialog
        dialog = new Dialog("", skin, "default");
        dialog.setColor(Color.CLEAR);
        dialog.text("     Error!     ");
        dialog.button("   OK   ", true); //sends "true" as the result

        // Labels FROM and TO
        pointFont = new BitmapFont();
        pointFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pointFont.getData().setScale(3,3);

        //Text Fields for two points
        tfFirstPoint = new TextField("", skin);
        tfSecondPoint = new TextField("", skin);
        tfFirstPoint.setPosition(150, game.getHeightScreen() - 300);
        tfSecondPoint.setPosition(150, game.getHeightScreen() - 450);
        tfFirstPoint.setSize(game.getWidthScreen() - 200, 100);
        tfSecondPoint.setSize(game.getWidthScreen() - 200, 100);

        stage.addActor(tfFirstPoint);
        stage.addActor(tfSecondPoint);

        // Text Button "SEARCH"
        Button searchButton = new TextButton("SEARCH >", skin, "default");
        searchButton.setSize(game.getWidthScreen(), 150);
        searchButton.setPosition(0, game.getHeightScreen() - 700);
        searchButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (searchButtonPressed) {
                    Gdx.input.setOnscreenKeyboardVisible(false);

                    try {
                        int firstPoint = Integer.parseInt(tfFirstPoint.getText());
                        int secondPoint = Integer.parseInt(tfSecondPoint.getText());

                        if(firstPoint < 0 || secondPoint < 0)
                            throw new NumberFormatException("");

                        if (!(game.getGraph().hasVertex(firstPoint) && game.getGraph().hasVertex(secondPoint)))
                            throw new UndirGraph.NoSuchVertexException("no vertex");

                        game.pathScreen.setFirstPoint(firstPoint);
                        game.pathScreen.setSecondPoint(secondPoint);
                    }
                    catch (NumberFormatException | UndirGraph.NoSuchVertexException ex) {
                        game.existError = true;
                        game.setScreen(game.inputABScreen);
                        return;
                    }

                    stage.unfocusAll();
                    game.setScreen(game.pathScreen);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                searchButtonPressed = true;
                return true;
            }
        });
        stage.addActor(searchButton);

        //Text Button "BACK"
        Button backButton = new TextButton("< BACK", skin, "default");
        backButton.setSize(250, 100);
        backButton.setPosition(0, game.getHeightScreen()-100);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (backButtonPressed) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    stage.unfocusAll();
                    tfFirstPoint.setText("");
                    tfSecondPoint.setText("");
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

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        if(game.existError) {
            dialog.show(stage);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getBatch().begin();
        pointFont.draw(getBatch(), "FROM:", 5, game.getHeightScreen() - 240);
        pointFont.draw(getBatch(), "TO:", 35, game.getHeightScreen() - 380);
        getBatch().end();

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
}
