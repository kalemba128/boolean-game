package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;
import com.bartoszkalemba.booleangame.GUI.Button;
import com.bartoszkalemba.booleangame.engine.GameManager;

public class GameplayScreen extends AbstractScreen {

	private int currentLevel;
	private GameManager gameManager;
	Sprite topSprite, dialogSprite;

	private float CameraZoomSpeed = 20.f;

	private Label counterLabel, levelLabel, pauseLabel;
	private Button pauseButton, resumeButton, levelsButton, resetButton;

	float targetZoom = 1;

	enum State {
		AnimationStart,
		Play,
		AnimationEnd
	}

	private State state = State.AnimationStart;

	public GameplayScreen(BooleanGame game, int level) {
		super(game);
		currentLevel = level;
		init();
	}


	@Override
	protected void init() {
		game.setPaused(false);

		createInterface();
		loadLevel();

		camera.zoom = 10;
	}

	private void loadLevel() {


		String levelPath = "";

		if (currentLevel > 0){ // normal
			game.unlockLevel(currentLevel);
			levelPath = "levels/normal/level" + currentLevel;
		}

		if (currentLevel < 0){ // creative
			game.unlockLevelCreative(currentLevel *-1);
			levelPath = "levels/creative/level" + (currentLevel * -1);
		}



		if (currentLevel == 0){
			// random
		}


		gameManager = new GameManager();
		gameManager.getLevel().load(levelPath);
		gameManager.setMouse(mouse());
		gameManager.setSpriteBatch(spriteBatch);
		gameManager.setShapeRenderer(shapeRenderer);
        gameManager.getBulletsManager().setGame(game);
	}


	@Override
	protected void update(float delta) {

		updateCounterLabel(gameManager.getLevel().counterSteps);

		if (state == State.AnimationStart) {
			if (camera.zoom > targetZoom)
				camera.zoom -= CameraZoomSpeed * delta;

			if (camera.zoom < targetZoom)
				camera.zoom = targetZoom;

			if (camera.zoom == targetZoom)
				state = State.Play;
		}

		if (state == State.AnimationEnd) {
			if (camera.zoom < 10)
				camera.zoom += CameraZoomSpeed * delta;

			if (camera.zoom > 10)
				camera.zoom = 10;

			if (camera.zoom == 10) {
				if (currentLevel > 0)currentLevel++;
				if (currentLevel < 0)currentLevel--;

				game.setScreen(new GameplayScreen(game, currentLevel));
			}
		}

		if (state == State.Play && !game.isPaused()) {

			gameManager.update(mouse());

			if (gameManager.isWin()) {
				if (game.isSound())
					assets.getSound("win.mp3").play();

				state = State.AnimationEnd;
			}

			pauseButton.update(mouse());

			if (pauseButton.isClick()) {
				game.setPaused(!game.isPaused());
			}
		}

		if (state == State.Play && game.isPaused()) {
			resumeButton.update(mouse());
			levelsButton.update(mouse());
			resetButton.update(mouse());

			if (resumeButton.isClick())
				game.setPaused(false);

			if (levelsButton.isClick())
			{
				if (currentLevel > 0)
				game.setScreen(new LevelsScreen(game));
				if (currentLevel < 0)
					game.setScreen(new CreativeScreen(game));
			}


			if (resetButton.isClick())
				game.setScreen(new GameplayScreen(game, currentLevel));
		}


		/*
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

			if (currentLevel > -40)
			game.setScreen(new GameplayScreen(game, currentLevel - 1));
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {

			if (currentLevel < 40)
				game.setScreen(new GameplayScreen(game, currentLevel + 1));
		}*/

	}


	public void render(float delta) {
		super.render(delta);

		gameManager.render();
		drawInterface();
	}


	/// --- INTERFACE --- ///
	private void createInterface() {
		createTopbar();
		createLevelLabel();
		createCounter();

		pauseButton = new Button(40, calc(getResolution().x - 60, 50), assets.getSprite("pause-button.png"));
		pauseButton.sprite.setColor(Constant.BackgroundColor);

		createDialog();
	}

	private void createDialog() {
		dialogSprite = assets.getSprite("dialog.png");
		Vector2 diaPos = calc(0, 805);
		dialogSprite.setPosition(diaPos.x, diaPos.y);
		dialogSprite.setColor(Constant.MainColor);

		resumeButton = new Button(65, calc(295 + 65, 759 - 65), assets.getSprite("resume-button.png"));
		resumeButton.sprite.setColor(Constant.BackgroundColor);
		levelsButton = new Button(50, calc(170 + 50, 744 - 50), assets.getSprite("levels-button.png"));
		levelsButton.sprite.setColor(Constant.BackgroundColor);
		resetButton = new Button(50, calc(450 + 50, 744 - 50), assets.getSprite("reset-button.png"));
		resetButton.sprite.setColor(Constant.BackgroundColor);

		createPauseLabel();

	}

	private void createPauseLabel() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default72.fnt"), Constant.BackgroundColor);
		pauseLabel = new Label("pause", labelStyle);

		float w = pauseLabel.getWidth();
		float h = pauseLabel.getHeight();

		Vector2 pos = new Vector2(-w/2, 70);
		pauseLabel.setPosition(pos.x, pos.y);
	}

	private void createLevelLabel() {
		String labelText = "";

		if (currentLevel > 0)
			labelText = "#" + currentLevel;

		if (currentLevel < 0)
			labelText = "#" + (currentLevel * -1);

		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Constant.BackgroundColor);
		levelLabel = new Label(labelText, labelStyle);

		float w =levelLabel.getGlyphLayout().width;
		float h = levelLabel.getHeight();

		Vector2 pos = calc(20, 50 + h/2 );
		levelLabel.setPosition(pos.x, pos.y);
	}

	private void createCounter() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default72.fnt"), Constant.BackgroundColor);
		counterLabel = new Label("", labelStyle);


		updateCounterLabel(0);
	}

	private void updateCounterLabel(int counter){
		counterLabel.setText(""+counter);

		float w = counterLabel.getGlyphLayout().width;
		float h = counterLabel.getHeight();

		Vector2 pos = calc(getResolution().x / 2 - w/2, 50 + h/2 );
		counterLabel.setPosition(pos.x, pos.y);
	}

	private void createTopbar() {
		topSprite = assets.getSprite("topbar.png");
		Vector2 pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Constant.MainColor);
	}

	private void drawTransparentBackground() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		Color white = Constant.BackgroundColor;
		Color c = new Color(white.r, white.g, white.b, 127/255.f);

		shapeRenderer.setColor(c);

		float w = getResolution().x;
		float h = getResolution().y;

		shapeRenderer.rect(-w/2, -h/2, w, h);

		shapeRenderer.end();

		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	private void drawInterface() {
		spriteBatch.setProjectionMatrix(defaultMatrix);
		spriteBatch.begin();

		topSprite.draw(spriteBatch);
		counterLabel.draw(spriteBatch, 1);
		levelLabel.draw(spriteBatch, 1);
		pauseButton.draw(spriteBatch);

		spriteBatch.end();

		if (game.isPaused()) {
			drawTransparentBackground();

			spriteBatch.begin();

			dialogSprite.draw(spriteBatch);
			resumeButton.draw(spriteBatch);
			levelsButton.draw(spriteBatch);
			resetButton.draw(spriteBatch);
			pauseLabel.draw(spriteBatch, 1);

			spriteBatch.end();
		}
		spriteBatch.setProjectionMatrix(camera.combined);
	}
}