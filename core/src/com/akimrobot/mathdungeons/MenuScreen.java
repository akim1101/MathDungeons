package com.akimrobot.mathdungeons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Objects;

public class MenuScreen implements Screen {
    public static Preferences preferences;
    BitmapFont font;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    Texture bgTexture;
    Texture logoTexture;

    Game agame;
    TextButton btnPlay;
    TextButton btnSettings;
    TextButton btnShop;
    TextButton btnMusicOnOff;
    Preferences prefs;

    Stage stage;

    Skin skin;
    Music music;
    public MenuScreen(Game game){
        preferences = Gdx.app.getPreferences("preferences");
        music = Gdx.audio.newMusic(Gdx.files.internal("MenuTheme.mp3"));
        prefs = preferences;
        if(!preferences.getBoolean("isMusicPlaying")) {
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
        }
        agame = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        bgTexture = new Texture("bg0.png");
        logoTexture = new Texture("logo.png");
        font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
        stage = new Stage(new StretchViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("normal_uiskin.json"));
        btnPlay = new TextButton("PLAY", skin);
        btnSettings = new TextButton("SETTINGS", skin);
        btnShop = new TextButton("SHOP", skin);
        btnMusicOnOff = new TextButton("MUSIC ON/OFF", skin);

        btnPlay.setSize(320, 100);
        btnMusicOnOff.setSize(320, 100);
        btnSettings.setSize(320, 100);
        btnShop.setSize(320, 100);

        btnPlay.setPosition(480, 320);
        btnMusicOnOff.setPosition(0, 0);
        btnShop.setPosition(480, 210);
        btnSettings.setPosition(480, 100);
        stage.addActor(btnPlay);
        stage.addActor(btnSettings);
        stage.addActor(btnShop);
        stage.addActor(btnMusicOnOff);

        Gdx.input.setInputProcessor(stage);


    }
    @Override
    public void show() {
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                agame.setScreen(new GameScreen(agame));
                music.pause();
            }
        });
        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                agame.setScreen(new SettingsScreen(agame));
                music.pause();
            }
        });
        btnShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                agame.setScreen(new ShopScreen(agame));
                music.pause();
            }
        });
        btnMusicOnOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!preferences.getBoolean("isMusicPlaying")){
                    preferences.putBoolean("isMusicPlaying", true);
                    preferences.flush();
                   music.pause();
                }
                else {
                    preferences.putBoolean("isMusicPlaying", false);
                    preferences.flush();
                    music.play();
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bgTexture, 0,0);
        if(!preferences.getBoolean("isMusicPlaying")){
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
        } else {
            music.setLooping(true);
            music.setVolume(0.0f);
            music.pause();
        }
        spriteBatch.draw(logoTexture, 336, 495);
        spriteBatch.end();

        stage.draw();



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
