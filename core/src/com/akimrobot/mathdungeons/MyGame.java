package com.akimrobot.mathdungeons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Objects;
import java.util.Random;
import java.util.logging.FileHandler;

public class MyGame extends ApplicationAdapter {
	BitmapFont font;
	SpriteBatch spriteBatch;
	OrthographicCamera camera;

	Texture backgorundTexture;
	Texture playerTexture;
	Texture slime1Texture;
	Texture slime2Texture;
	Texture slime3Texture;
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

	ImageButton btn1;
	ImageButton btn2;
	ImageButton btn3;

	Stage stage;

	Texture[] slimeTextures;

	boolean isRightAnswered;

	int scoreGlobal;
	int enemyID;
	int totalHealth;
	boolean isWrongAnswered;

	Texture[] healthBar1;
	Texture[] healthBar2;
	Texture[] healthBar3;

	@Override
	public void create () {
		font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		backgorundTexture = new Texture("bg0.jpg");
		btnTexture = new Texture("btn.png");
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);


		btn1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnTexture)));
		btn2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnTexture)));
		btn3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnTexture)));

		btn1.setSize(320, 100);
		btn1.setPosition(240, 150);
		stage.addActor(btn1);

		btn2.setSize(320, 100);
		btn2.setPosition(640, 150);
		stage.addActor(btn2);

		btn3.setSize(320, 100);
		btn3.setPosition(1040, 150);
		stage.addActor(btn3);

		playerTexture = new Texture("player.png");
		slime1Texture = new Texture("slime1.png");
		slime2Texture = new Texture("slime2.png");
		slime3Texture = new Texture("slime3.png");
		slimeTextures = new Texture[3];
		slimeTextures[0] = slime1Texture;
		slimeTextures[1] = slime2Texture;
		slimeTextures[2] = slime3Texture;

		healthTexture = new Texture("health.png");
		healthDiedTexture = new Texture("health_died.png");

		healthBar1 = new Texture[]{healthTexture, healthTexture, healthTexture};
		healthBar2 = new Texture[]{healthDiedTexture, healthTexture, healthTexture};
		healthBar3 = new Texture[]{healthDiedTexture, healthDiedTexture, healthTexture};

		totalHealth = 3;




	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		while (!isGenerationCompleted){
			primerString = mathGenerator.createPrimer(random.nextInt(101), random.nextInt(101)); //TODO: background for slime cave
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


		spriteBatch.draw(playerTexture, 180, 360);
		spriteBatch.draw(slimeTextures[enemyID], 1040, 360);

		spriteBatch.draw(healthBar1[totalHealth - 1], 590, 650 );
		spriteBatch.draw(healthBar2[totalHealth - 1], 640, 650 );
		spriteBatch.draw(healthBar3[totalHealth - 1], 690, 650 );


		font.draw(spriteBatch, primerString, 640, 500);
		font.draw(spriteBatch, Integer.toString(scoreGlobal), 90, 700);

		spriteBatch.end();
		stage.act();
		stage.draw();
		spriteBatch.begin();
		font.draw(spriteBatch, answerPositions[0], 320, 215);
		font.draw(spriteBatch, answerPositions[1], 670, 215);
		font.draw(spriteBatch, answerPositions[2], 1000, 215);
		spriteBatch.end();







		btn1.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(Objects.equals(answerString1, answerPositions[0])){
					isGenerationCompleted = false;
					isRightAnswered = true;
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
			scoreGlobal += 100;
		} else if (isWrongAnswered) {
			totalHealth = totalHealth - 1;
			if(totalHealth == 0){
				scoreGlobal = 0;
				totalHealth = 3;
			}
		}
		isWrongAnswered = false;
		isRightAnswered = false;

		}



	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		backgorundTexture.dispose();
		font.dispose();
		playerTexture.dispose();
		slime1Texture.dispose();
		stage.dispose();
	}
}
