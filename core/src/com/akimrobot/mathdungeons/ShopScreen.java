package com.akimrobot.mathdungeons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

import static com.akimrobot.mathdungeons.MenuScreen.preferences;

public class ShopScreen implements Screen {
    BitmapFont font;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Texture bgTexture;
    Texture HealthBuyTexture;

    Game agame;
    TextButton btnBuy;
    TextButton btnBackToMenu;
    String score;


    Stage stage;

    Skin skin;
    Preferences prefs;
    Music music;

    public ShopScreen(Game game){
        music = Gdx.audio.newMusic(Gdx.files.internal("ShopTheme.mp3"));
        music.setLooping(true);
        music.play();
        prefs = preferences;
        agame = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        bgTexture = new Texture("bg0.png");
        HealthBuyTexture = new Texture("health_export.png");
        font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
        stage = new Stage(new StretchViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("normal_uiskin.json"));
        btnBuy = new TextButton("BUY", skin);
        btnBackToMenu = new TextButton("GO TO MENU", skin);
        btnBuy.setSize(320, 100);
        btnBackToMenu.setSize(320, 100);
        btnBuy.setPosition(480, 120);
        btnBackToMenu.setPosition(10, 620);
        stage.addActor(btnBuy);
        stage.addActor(btnBackToMenu);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        btnBackToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                agame.setScreen(new MenuScreen(agame));
                music.pause();

            }
        });
        btnBuy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(prefs.getBoolean("isLifeBought") == false && prefs.getInteger("moneyCount") >= 1000){
                    preferences.putBoolean("isLifeBought", true);
                    preferences.putInteger("moneyCount", preferences.getInteger("moneyCount") - 1000);
                    preferences.flush();
                }

            }
        });
    }

    @Override
    public void render(float delta) {
        if(!preferences.getBoolean("isMusicPlaying")){
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
        } else {
            music.setLooping(true);
            music.setVolume(0.0f);
            music.pause();
        }
        camera.update();
        if(preferences.getBoolean("isMusicPlaying")){
            music.play();
        }
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bgTexture, 0,0);
        spriteBatch.draw(HealthBuyTexture, 490,220);
        font.draw(spriteBatch, Integer.toString(prefs.getInteger("moneyCount")) + "$", 1180,620);
        if(!prefs.getBoolean("isLifeBought")) {
            font.draw(spriteBatch, "BUY ADDITIONAL LIFE FOR 1000$", 420, 520);
        }
        else {
            font.draw(spriteBatch, "YOU HAVE ALREADY BOUGHT ADDITIONAL LIFE", 420, 520);
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
