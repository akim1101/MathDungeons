package com.akimrobot.mathdungeons;

import static com.akimrobot.mathdungeons.MenuScreen.preferences;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SettingsScreen implements Screen {
    BitmapFont font;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Texture bgTexture;

    TextButton btnReset;
    TextButton btnBackToMenu;

    Stage stage;

    Skin skin;
    Game agame;

    public SettingsScreen(Game game){
        agame = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        bgTexture = new Texture("bg0.png");
        font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
        stage = new Stage(new StretchViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("normal_uiskin.json"));
        btnReset = new TextButton("DELETE HIGH SCORE", skin);
        btnBackToMenu = new TextButton("BACK TO MENU", skin);
        btnReset.setSize(320, 100);
        btnBackToMenu.setSize(320, 100);
        btnReset.setPosition(480, 320);
        btnBackToMenu.setPosition(10, 620);
        stage.addActor(btnReset);
        stage.addActor(btnBackToMenu);


        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void show() {
        btnReset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                preferences.putInteger("highScore", 0);
                preferences.flush();
            }
        });
        btnBackToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                agame.setScreen(new MenuScreen(agame));
            }
        });

    }

    @Override
    public void render(float delta) {
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bgTexture, 0,0);
        font.draw(spriteBatch, "HIGH SCORE:" + Integer.toString(preferences.getInteger("highScore")), 540, 640);
        spriteBatch.end();

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
