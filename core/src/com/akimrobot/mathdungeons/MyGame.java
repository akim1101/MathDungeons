package com.akimrobot.mathdungeons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Objects;
import java.util.Random;

public class MyGame extends ApplicationAdapter {
	BitmapFont font;
	SpriteBatch spriteBatch;
	OrthographicCamera camera;

	Texture backgorundTexture;
	Texture btnTexture;
	Texture playerTexture;
	Texture enemyTexture;
	String primerString;
	String answerString1;
	String answerString2;
	String answerString3;
	String[] answerPositions;
	boolean isGenerationCompleted = false;
	MathGenerator mathGenerator = new MathGenerator();
	Random random = new Random();
	int touchX;
	int touchY;



	int scoreGlobal;

	@Override
	public void create () {
		font = new BitmapFont(Gdx.files.internal("bitcell.fnt"),Gdx.files.internal("bitcell.png"), false );
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		backgorundTexture = new Texture("bg0.jpg");
		btnTexture = new Texture("btn.png");
		playerTexture = new Texture("player.png");
		enemyTexture = new Texture("slime1.png");

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		while (!isGenerationCompleted){
			primerString = mathGenerator.createPrimer(random.nextInt(101), random.nextInt(101)); //TODO: пофиксить мультинажатие; сделать обнуление счёта при неправильном ответе;
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
				isGenerationCompleted = true;
			}
		}
		spriteBatch.draw(backgorundTexture, 0, 0);
		spriteBatch.draw(btnTexture, 520, 400);
		spriteBatch.draw(btnTexture, 520, 200);
		spriteBatch.draw(btnTexture, 520, 0);
		spriteBatch.draw(playerTexture, 200, 360);
		spriteBatch.draw(enemyTexture, 980, 360);
		font.draw(spriteBatch, primerString, 560, 650);
		font.draw(spriteBatch, Integer.toString(scoreGlobal), 90, 700);
		font.draw(spriteBatch, answerPositions[0], 620, 500);
		font.draw(spriteBatch, answerPositions[1], 620, 300);
		font.draw(spriteBatch, answerPositions[2], 620, 100);
		spriteBatch.end();
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if (screenX >= 611 && screenX <= 948) {
					if (screenY > 100 && screenY < 300) {
					if(Objects.equals(answerString1, answerPositions[0])){
						scoreGlobal += 100;
						isGenerationCompleted = false;
					}
					else {
						scoreGlobal = 0;
						isGenerationCompleted = false;
					}
				}
				else if (screenY > 300 && screenY < 500) {
					if(Objects.equals(answerString1, answerPositions[1])){
						scoreGlobal += 100;
						isGenerationCompleted = false;
					}
					else {
						scoreGlobal = 0;
						isGenerationCompleted = false;
					}
				}
				else if (screenY > 500 && screenY < 700) {
					if(Objects.equals(answerString1, answerPositions[2])){
						scoreGlobal += 100;
						isGenerationCompleted = false;
					}
					else {
						scoreGlobal = 0;
						isGenerationCompleted = false;
					}
					}
				}
				return super.touchDown(screenX, screenY, pointer, button);
			}
		});

		}



	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		backgorundTexture.dispose();
		btnTexture.dispose();
		font.dispose();
		playerTexture.dispose();
		enemyTexture.dispose();
	}
}
