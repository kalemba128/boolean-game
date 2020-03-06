package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.GUI.Button;
import com.bartoszkalemba.booleangame.GUI.Buttons;
import com.bartoszkalemba.booleangame.engine.GameManager;

public class GameplayScreen extends AbstractScreen {

	private int currentLevel;
	private String mode;
	private GameManager gameManager;
	private Sprite topSprite, dialogSprite;

	private float CameraZoomSpeed = 20.f;

	/// ---- Interface ---- ///
	private Label counterLabel, levelLabel, pauseLabel;
	private Buttons buttons;

	private float targetZoom = 1;

	private enum State {
		AnimationStart,
		Play,
		AnimationEnd
	}

	private State state = State.AnimationStart;

	public GameplayScreen(BooleanGame game, String mode, int level) {
		super(game);
		currentLevel = level;
		this.mode = mode;
		game.setPaused(false);
		createInterface();
		loadLevel();
		camera.zoom = 10;
	}


	@Override
	protected void init() {

	}

	@Override
	protected void update(float delta) {
		updateCounterLabel(gameManager.getLevel().counterSteps);
		updateAnimation(delta);
		buttons.update();

		if (state == State.Play) {
			if (!game.isPaused()){
				gameManager.update(mouse());

				if (gameManager.isWin()) {
					if (game.isSound())
						assets.getSound("win.mp3").play();

					state = State.AnimationEnd;
				}

				buttons.get("resume").hide();
				buttons.get("levels").hide();
				buttons.get("reset").hide();
			}else{
				buttons.get("resume").show();
				buttons.get("levels").show();
				buttons.get("reset").show();
			}
		}

		if (buttons.get("pause").isClicked())
			game.setPaused(!game.isPaused());

		if (buttons.get("resume").isClicked())
			game.setPaused(false);

		if (buttons.get("levels").isClicked())
			game.setScreen(new LevelsScreen(game, mode));

		if (buttons.get("reset").isClicked())
			game.setScreen(new GameplayScreen(game, mode, currentLevel));
	}

	public void render(float delta) {
		super.render(delta);
		gameManager.render();
		drawInterface();
	}

	private void loadLevel() {
		String levelPath = "levels/" + mode + "/level" + currentLevel;

		if (mode.equals("normal"))
			game.unlockNextNormalLevel(currentLevel);
		else if (mode.equals("creative"))
			game.unlockNextCreativeLevel(currentLevel);

		gameManager = new GameManager();
		gameManager.getLevel().load(levelPath);
		gameManager.setMouse(mouse());
		gameManager.setSpriteBatch(spriteBatch);
		gameManager.setShapeRenderer(shapeRenderer);
		gameManager.getBulletsManager().setGame(game);
	}

	private void nextLevel(){
		if (currentLevel > 0)currentLevel++;
		if (currentLevel < 0)currentLevel--;

		game.setScreen(new GameplayScreen(game, mode, currentLevel));
	}

	private void updateAnimation(float delta){
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

			if (camera.zoom == 10) nextLevel();
		}
	}


	/////////////////////////////////////////////////////////
	/// --- INTERFACE --- ///
	/////////////////////////////////////////////////////////

	private void createInterface() {
		/* ---- Buttons ---- */
		buttons = new Buttons();

		/// Pause Button ///
		buttons.add("pause");
		buttons.get("pause").setRadius(40);
		buttons.get("pause").setPosition(calc(getResolution().x - 60, 50));
		buttons.get("pause").setSprite(assets.getSprite("pause-button.png"));
		buttons.get("pause").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/// Resume Button ///
		buttons.add("resume");
		buttons.get("resume").setRadius(65);
		buttons.get("resume").setPosition(calc(295 + 65, 759 - 65));
		buttons.get("resume").setSprite(assets.getSprite("resume-button.png"));
		buttons.get("resume").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/// Levels Button ///
		buttons.add("levels");
		buttons.get("levels").setRadius(50);
		buttons.get("levels").setPosition(calc(170 + 50, 744 - 50));
		buttons.get("levels").setSprite(assets.getSprite("levels-button.png"));
		buttons.get("levels").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/// Reset Button ///
		buttons.add("reset");
		buttons.get("reset").setRadius(50);
		buttons.get("reset").setPosition(calc(450 + 50, 744 - 50));
		buttons.get("reset").setSprite(assets.getSprite("reset-button.png"));
		buttons.get("reset").getSprite().setColor(Appearance.BACKGROUND_COLOR);


		/* Sprites */
		/// Dialog Sprite ///
		dialogSprite = assets.getSprite("dialog.png");
		Vector2 pos = calc(0, 805);
		dialogSprite.setPosition(pos.x, pos.y);
		dialogSprite.setColor(Appearance.MAIN_COLOR);

		/// Top bar Sprite ///
		topSprite = assets.getSprite("topbar.png");
		pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Appearance.MAIN_COLOR);


		/* Labels */
		/// Pause ///
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default72.fnt"), Appearance.BACKGROUND_COLOR);
		pauseLabel = new Label("pause", labelStyle);
		pos = new Vector2(-pauseLabel.getWidth()/2, 70);
		pauseLabel.setPosition(pos.x, pos.y);

		/// Level ///
		labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Appearance.BACKGROUND_COLOR);
		levelLabel = new Label("#"+currentLevel, labelStyle);
		pos = calc(20, 50 + levelLabel.getHeight()/2);
		levelLabel.setPosition(pos.x, pos.y);

		/// Counter ///
		labelStyle = new Label.LabelStyle(assets.getFont("default72.fnt"), Appearance.BACKGROUND_COLOR);
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


	private void drawTransparentBackground() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		Color color = Appearance.BACKGROUND_COLOR.cpy();
		color.a = 127/255.f;
		shapeRenderer.setColor(color);

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
		buttons.get("pause").draw(spriteBatch);

		spriteBatch.end();

		if (game.isPaused()) {
			drawTransparentBackground();
			spriteBatch.begin();
			dialogSprite.draw(spriteBatch);
			buttons.get("levels").draw(spriteBatch);
			buttons.get("resume").draw(spriteBatch);
			buttons.get("reset").draw(spriteBatch);
			pauseLabel.draw(spriteBatch, 1);
			spriteBatch.end();
		}
		spriteBatch.setProjectionMatrix(camera.combined);
	}
}