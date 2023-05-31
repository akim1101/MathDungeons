package com.akimrobot.mathdungeons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

public class GameOverScreen implements Screen {
    BitmapFont font;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Texture bgTexture;

    Game agame;
    TextButton btnReturn;
    int score;


    Stage stage;

    Skin skin;
    Preferences prefs;

    public GameOverScreen(Game game, int score, Preferences preferences){
        prefs = preferences;
        this.score = score;
        agame = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        bgTexture = new Texture("bg0.png");
        font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
        stage = new Stage(new StretchViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("normal_uiskin.json"));
        btnReturn = new TextButton("GO TO MENU", skin);
        btnReturn.setSize(320, 100);
        btnReturn.setPosition(480, 120);
        stage.addActor(btnReturn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        btnReturn.addListener(new ClickListener() {
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
        spriteBatch.end();
        spriteBatch.begin();
        font.draw(spriteBatch, "SCORE: " + score, 500, 620);
        font.draw(spriteBatch, "HIGH SCORE: " + Integer.toString(prefs.getInteger("highScore")) , 500, 520);
        if(score >= 6000 ){
            font.draw(spriteBatch, "MONEY EARNED: " + score / 10 + "$" , 500, 420);
            font.draw(spriteBatch, "CONGRATULATIONS! YOU BEAT THE GAME!", 360, 100);
        } else {
            font.draw(spriteBatch, "MONEY EARNED: " + score / 50 + "$" , 500, 420);
        }
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
