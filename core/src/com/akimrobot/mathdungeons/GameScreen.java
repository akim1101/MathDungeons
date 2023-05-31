package com.akimrobot.mathdungeons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.akimrobot.mathdungeons.MenuScreen.preferences;



import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;




public class GameScreen implements Screen {
    BitmapFont font;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;

    Texture backgorundTexture;
    Texture playerTexture;
    Texture slime1Texture;
    Texture slime2Texture;
    Texture slime3Texture;
    Texture slimeBossTexture;
    Texture skeleton1Texture;
    Texture skeleton2Texture;
    Texture skeleton3Texture;
    Texture skeletonBossTexture;

    Texture book1Texture;
    Texture book2Texture;
    Texture book3Texture;
    Texture bookBossTexture;
    Texture healthTexture;
    Texture healthDiedTexture;

    Texture btnTexture;
    String primerString;
    String answerString1;
    String answerString2;
    String answerString3;
    String[] answerPositions;
    boolean isGenerationCompleted = false;
    MathGenerator mathGenerator = new MathGenerator();
    Random random = new Random();

    TextButton btn1;
    TextButton btn2;
    TextButton btn3;

    Stage stage;

    Texture [] bookTextures;
    Texture[] slimeTextures;
    Texture[] skeletonTextures;

    boolean isRightAnswered;

    int scoreGlobal;
    int enemyID;
    int totalHealth;
    boolean isWrongAnswered;

    Skin skin;

    TextureAtlas textureAtlas;

    Texture[] healthBar1;
    Texture[] healthBar2;
    Texture[] healthBar3;
    Texture[] healthBar4;


    int rightAnswersCount;
    boolean isBossStage;
    int bossStageCount;
    int gameTier;
    Game game;
    Music music;
    TextureRegion[] animationFrames;
    Animation<TextureRegion> animationAnimation;
    float elapsedTime;

    int missileX;
    Texture missileTexture;


    public GameScreen(Game game){
        /*missileX = 350;*/
        missileTexture = new Texture("missile.png");
        animationFrames = new TextureRegion[16];
        animationFrames[0] = new TextureRegion(new Texture("player_attack1.png"));
        animationFrames[1] = new TextureRegion(new Texture("player_attack1.png"));
        animationFrames[2] = new TextureRegion(new Texture("player_attack1.png"));
        animationFrames[3] = new TextureRegion(new Texture("player_attack1.png"));
        animationFrames[4] = new TextureRegion(new Texture("player_attack2.png"));
        animationFrames[5] = new TextureRegion(new Texture("player_attack2.png"));
        animationFrames[6] = new TextureRegion(new Texture("player_attack2.png"));
        animationFrames[7] = new TextureRegion(new Texture("player_attack2.png"));
        animationFrames[8] = new TextureRegion(new Texture("player_attack3.png"));
        animationFrames[9] = new TextureRegion(new Texture("player_attack3.png"));
        animationFrames[10] = new TextureRegion(new Texture("player_attack3.png"));
        animationFrames[11] = new TextureRegion(new Texture("player_attack3.png"));
        animationFrames[12] = new TextureRegion(new Texture("player_attack4.png"));
        animationFrames[13] = new TextureRegion(new Texture("player_attack4.png"));
        animationFrames[14] = new TextureRegion(new Texture("player_attack4.png"));
        animationFrames[15] = new TextureRegion(new Texture("player_attack4.png"));
        animationAnimation = new Animation<TextureRegion>(1/1000000f, animationFrames);
        music = Gdx.audio.newMusic(Gdx.files.internal("GameTheme.mp3"));
        music.setLooping(true);
        music.play();
        preferences = Gdx.app.getPreferences("preferences");
        preferences.putInteger("highScore", scoreGlobal);
        this.game = game;
        gameTier = 1;
        bossStageCount = 0;
        rightAnswersCount = 1;
        font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        backgorundTexture = new Texture("bg0.png");


        stage = new Stage(new StretchViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);


        skin = new Skin(Gdx.files.internal("normal_uiskin.json"));

        btn1 = new TextButton(answerString1, skin, "default");
        btn2 = new TextButton(answerString2, skin, "default");
        btn3 = new TextButton(answerString3, skin, "default");

        playerTexture = new Texture("player_stock.png");

        book1Texture = new Texture("book1.png");
        book2Texture = new Texture("book2.png");
        book3Texture = new Texture("book3.png");
        bookBossTexture = new Texture("book_boss.png");

        slime1Texture = new Texture("slime1.png");
        slime2Texture = new Texture("slime2.png");
        slime3Texture = new Texture("slime3.png");
        slimeBossTexture = new Texture("slime_boss.png");

        skeleton1Texture = new Texture("skeleton1.png");
        skeleton2Texture = new Texture("skeleton2.png");
        skeleton3Texture = new Texture("skeleton3.png");
        skeletonBossTexture = new Texture("skeleton_boss.png");

        slimeTextures = new Texture[4];
        slimeTextures[0] = slime1Texture;
        slimeTextures[1] = slime2Texture;
        slimeTextures[2] = slime3Texture;
        slimeTextures[3] = slimeBossTexture;

        skeletonTextures = new Texture[4];
        skeletonTextures[0] = skeleton1Texture;
        skeletonTextures[1] = skeleton2Texture;
        skeletonTextures[2] = skeleton3Texture;
        skeletonTextures[3] = skeletonBossTexture;

        bookTextures = new Texture[4];
        bookTextures[0] = book1Texture;
        bookTextures[1] = book2Texture;
        bookTextures[2] = book3Texture;
        bookTextures[3] = bookBossTexture;

        healthTexture = new Texture("health.png");
        healthDiedTexture = new Texture("health_died.png");


        if(preferences.getBoolean("isLifeBought")){
            healthBar1 = new Texture[]{healthTexture, healthTexture, healthTexture, healthTexture};
            healthBar2 = new Texture[]{healthDiedTexture, healthTexture, healthTexture, healthTexture};
            healthBar3 = new Texture[]{healthDiedTexture, healthDiedTexture, healthTexture, healthTexture};
            healthBar4 = new Texture[]{healthDiedTexture, healthDiedTexture, healthDiedTexture, healthTexture};
            totalHealth = 4;
        }
        else {
            healthBar1 = new Texture[]{healthTexture, healthTexture, healthTexture};
            healthBar2 = new Texture[]{healthDiedTexture, healthTexture, healthTexture};
            healthBar3 = new Texture[]{healthDiedTexture, healthDiedTexture, healthTexture};
            totalHealth = 3;
        }
        btn1 = new TextButton("", skin, "default");
        btn2 = new TextButton("", skin, "default");
        btn3 = new TextButton("", skin, "default");

        btn1.setSize(320, 100);
        btn1.setPosition(180, 150);
        stage.addActor(btn1);

        btn2.setSize(320, 100);
        btn2.setPosition(540, 150);
        stage.addActor(btn2);

        btn3.setSize(320, 100);
        btn3.setPosition(900, 150);
        stage.addActor(btn3);
        isBossStage = false;
        preferences = Gdx.app.getPreferences("preferences");



    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        if(!preferences.getBoolean("isMusicPlaying")){
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
        } else {
            music.setLooping(true);
            music.setVolume(0.0f);
            music.pause();
        }
        elapsedTime = TimeUtils.millis() * 100000f;


        while (!isGenerationCompleted){
            if(gameTier == 1 || gameTier == 2){
                primerString = mathGenerator.createPrimer(random.nextInt(101), random.nextInt(101), gameTier);
            }
            else {
                primerString = mathGenerator.createPrimer(random.nextInt(1001), random.nextInt(1001), gameTier);
            }
            answerString1 = Integer.toString(mathGenerator.getAnswer());
            answerString2 = Integer.toString(mathGenerator.getAnswer() - random.nextInt(101));
            answerString3 = Integer.toString(mathGenerator.getAnswer() + random.nextInt(101));
            if(random.nextInt(3) == 0){
                answerPositions = new String[]{answerString2, answerString1, answerString3};
            } else if (random.nextInt(3) == 1) {
                answerPositions = new String[]{answerString1, answerString3, answerString2};
            }
            else {
                answerPositions = new String[]{answerString3, answerString2, answerString1};
            }
            if(primerString != null){
                enemyID = random.nextInt(3);
                isGenerationCompleted = true;
            }
        }
        spriteBatch.draw(backgorundTexture, 0, 0);
        if(missileX > 0){
            spriteBatch.draw(missileTexture, missileX, 400);
            missileX = missileX + 50;
            if(missileX >= 840){
                missileX = 0;
            }
        }

        spriteBatch.draw(playerTexture, 180, 360);


        if(rightAnswersCount % 11 == 0) {
            isBossStage = true;
            if(bossStageCount < 5){
                if(gameTier == 1){
                    spriteBatch.draw(bookTextures[3], 1040, 360);
                } else if (gameTier == 2) {
                    spriteBatch.draw(slimeTextures[3], 1040, 360);
                }
                else if (gameTier == 3) {
                    spriteBatch.draw(skeletonTextures[3], 1040, 360);
                }


            } else if (bossStageCount == 5) {
                gameTier++;
                scoreGlobal += 1000;
                rightAnswersCount++;
                bossStageCount = 0;
                isBossStage = false;
            }

        } else {
            if(gameTier == 1){
                spriteBatch.draw(bookTextures[enemyID], 1040, 360);
            } else if (gameTier == 2) {
                spriteBatch.draw(slimeTextures[enemyID], 1040, 360);
            }
            else if (gameTier == 3) {
                spriteBatch.draw(skeletonTextures[enemyID], 1040, 360);
            }
        }
        if(preferences.getBoolean("isLifeBought")){
            spriteBatch.draw(healthBar1[totalHealth - 1], 590, 650 );
            spriteBatch.draw(healthBar2[totalHealth - 1], 640, 650 );
            spriteBatch.draw(healthBar3[totalHealth - 1], 690, 650 );
            spriteBatch.draw(healthBar4[totalHealth - 1], 740, 650 );
        } else {
            spriteBatch.draw(healthBar1[totalHealth - 1], 590, 650 );
            spriteBatch.draw(healthBar2[totalHealth - 1], 640, 650 );
            spriteBatch.draw(healthBar3[totalHealth - 1], 690, 650 );
        }


        font.draw(spriteBatch, primerString, 640, 500);
        font.draw(spriteBatch, Integer.toString(scoreGlobal), 90, 700);

        spriteBatch.end();
        stage.act();
        stage.draw();
        spriteBatch.begin();
        spriteBatch.end();


        btn1.setText(answerPositions[0]);
        btn2.setText(answerPositions[1]);
        btn3.setText(answerPositions[2]);

        btn1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Objects.equals(answerString1, answerPositions[0])){
                    isGenerationCompleted = false;
                    isRightAnswered = true;
                    System.out.println(1);
                    return super.touchDown(event, x, y, pointer, button);
                }
                else {
                    isWrongAnswered = true;
                    isRightAnswered = false;
                    isGenerationCompleted = false;
                    return super.touchDown(event, x, y, pointer, button);
                }
            }
        });
        btn2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Objects.equals(answerString1, answerPositions[1])){
                    isRightAnswered = true;
                    isGenerationCompleted = false;
                    System.out.println(2);
                    return super.touchDown(event, x, y, pointer, button);
                }
                else {
                    isWrongAnswered = true;
                    isRightAnswered = false;
                    isGenerationCompleted = false;
                    return super.touchDown(event, x, y, pointer, button);
                }
            }
        });
        btn3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Objects.equals(answerString1, answerPositions[2])){
                    isRightAnswered = true;
                    isGenerationCompleted = false;
                    System.out.println(3);
                    return super.touchDown(event, x, y, pointer, button);
                }
                else {
                    isWrongAnswered = true;
                    isRightAnswered = false;
                    isGenerationCompleted = false;
                    return super.touchDown(event, x, y, pointer, button);
                }
            }
        });
        if(isRightAnswered == true){
            missileX = 360;
            /*spriteBatch.begin();


            spriteBatch.draw(animationAnimation.getKeyFrame(elapsedTime, false),180, 360);
            *//*spriteBatch.draw(animationFrames[0], 180, 360);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spriteBatch.draw(animationFrames[1], 180, 360);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spriteBatch.draw(animationFrames[2], 180, 360);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spriteBatch.draw(animationFrames[3], 180, 360);*//*

            spriteBatch.end();*/
            if(isBossStage){
                bossStageCount += 1;
            }else {
                scoreGlobal += 100;
                rightAnswersCount++;
            }
        } else if (isWrongAnswered) {
            totalHealth = totalHealth - 1;
            if(totalHealth == 0){
                /*gameTier = 1;
                rightAnswersCount = 1;
                isBossStage = false;
                scoreGlobal = 0;
                totalHealth = 3;*/
                if(preferences.getInteger("highScore") < scoreGlobal) {
                    preferences.putInteger("highScore", scoreGlobal);
                    preferences.putInteger("moneyCount", preferences.getInteger("moneyCount") + scoreGlobal / 50);
                    preferences.flush();
                    music.pause();
                    game.setScreen(new GameOverScreen(game, scoreGlobal, preferences));

                } else {
                    preferences.putInteger("moneyCount", preferences.getInteger("moneyCount") + scoreGlobal / 50);
                    preferences.flush();
                    music.pause();
                    game.setScreen(new GameOverScreen(game, scoreGlobal, preferences));

                }
            }
        }
        if(scoreGlobal >= 6000){
            if(preferences.getInteger("highScore") < scoreGlobal) {
                preferences.putInteger("highScore", scoreGlobal);
                preferences.putInteger("moneyCount", preferences.getInteger("moneyCount") + scoreGlobal / 10);
                preferences.flush();
                music.pause();
                game.setScreen(new GameOverScreen(game, scoreGlobal, preferences));

            } else {
                preferences.putInteger("moneyCount", preferences.getInteger("moneyCount") + scoreGlobal / 10);
                preferences.flush();
                music.pause();
                game.setScreen(new GameOverScreen(game, scoreGlobal, preferences));

            }
        }
        isWrongAnswered = false;
        isRightAnswered = false;

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
