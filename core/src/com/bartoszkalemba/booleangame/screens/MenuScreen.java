package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;
import com.bartoszkalemba.booleangame.GUI.Button;
import com.bartoszkalemba.booleangame.GUI.RectButton;
import com.bartoszkalemba.booleangame.engine.Vertex;

public class MenuScreen extends AbstractScreen{

	public MenuScreen(final BooleanGame game) {
		super(game);
	}

    Button playButton, soundButton, optionsButton, infoButton;
	Sprite logoSprite, barSprite;

	@Override
	protected void init() {


       	// Create interface
		createInterface();

		// Play music
		Button.sound = assets.getSound("button-click.mp3");
		RectButton.sound = assets.getSound("button-click.mp3");
		Vertex.sound = assets.getSound("vertex-click.mp3");



	}

	@Override
	protected void update(float delta) {
        playButton.update(mouse());
		soundButton.update(mouse());
		optionsButton.update(mouse());
		infoButton.update(mouse());

		//if (Gdx.input.isKeyJustPressed(Input.Keys.E))
		//	game.setScreen(new EdiorScreen(game));

        if (playButton.isClick())
        	game.setScreen(new ModeScreen(game));

		if (soundButton.isClick()) {
			game.setSound(!game.isSound());

			if (game.isSound()) {
				soundButton.sprite = assets.getSprite("sound-on-button.png");
				game.setSound(true);

			}
				else {
				soundButton.sprite = assets.getSprite("sound-off-button.png");
				game.setSound(false);
				}

			soundButton.sprite.setColor(Constant.BackgroundColor);
			soundButton.updateSprite();
		}

		if (optionsButton.isClick()) {
			game.setScreen(new OptionsScreen(game));
		}

		if (infoButton.isClick()) {
			game.setScreen(new InfoScreen(game));
		}

	}

	@Override
	public void render(float delta) {
		super.render(delta);



		spriteBatch.begin();
		logoSprite.draw(spriteBatch);
		playButton.draw(spriteBatch);
		barSprite.draw(spriteBatch);
		soundButton.draw(spriteBatch);
		optionsButton.draw(spriteBatch);
		infoButton.draw(spriteBatch);


		spriteBatch.end();
	}




	private void createInterface() {
		createLogo();
		createPlayButton();
		createBarButtons();
	}

	private void createBarButtons() {
		barSprite = assets.getSprite("buttons-bar.png");
		barSprite.setPosition(-(getResolution().x/2), -583);
		barSprite.setColor(Constant.MainColor);

		if (game.isSound())
			soundButton = new Button(75, calc(90 + 75, 1207 - 75), assets.getSprite("sound-on-button.png"));
		else
			soundButton = new Button(75, calc(90 + 75, 1207 - 75), assets.getSprite("sound-off-button.png"));
		soundButton.sprite.setColor(Constant.BackgroundColor);


		optionsButton = new Button(75, calc(285 + 75, 1207 - 75), assets.getSprite("options-button.png"));
		optionsButton.sprite.setColor(Constant.BackgroundColor);
		infoButton = new Button(75, calc(480 + 75, 1207 - 75), assets.getSprite("info-button.png"));
		infoButton.sprite.setColor(Constant.BackgroundColor);

	}

	private void createPlayButton() {
        playButton = new Button(300, new Vector2(0, 0), assets.getSprite("big-play-button.png"));
		playButton.sprite.setColor(Constant.MainColor);
	}


	private void createLogo() {
		logoSprite = assets.getSprite("logo.png");
		int w = (int)logoSprite.getWidth();
		int h = (int)logoSprite.getHeight();
		logoSprite.setPosition(-w/2, 420);
		logoSprite.setColor(Constant.MainColor);
	}


}
