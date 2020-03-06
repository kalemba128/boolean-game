package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.GUI.Buttons;


public class MenuScreen extends AbstractScreen{

	private Buttons buttons;
	private Sprite logoSprite, barSprite;


	public MenuScreen(final BooleanGame game) {
		super(game);

	}

	@Override
	protected void init() {
       	// Create interface
		buttons = new Buttons();
		createInterface();
	}

	@Override
	protected void update(float delta) {
		updateInterface();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		spriteBatch.begin();
		logoSprite.draw(spriteBatch);
		barSprite.draw(spriteBatch);
		buttons.draw(spriteBatch);
		spriteBatch.end();
	}


	private void createInterface() {
		/* ---- LOGO ---- */
		logoSprite = assets.getSprite("logo.png");
		logoSprite.setPosition(-logoSprite.getWidth()/2, 420);
		logoSprite.setColor(Appearance.MAIN_COLOR);

		/* ---- BAR ---- */
		barSprite = assets.getSprite("buttons-bar.png");
		barSprite.setPosition(-(getResolution().x/2), -583);
		barSprite.setColor(Appearance.MAIN_COLOR);

		/* ---- BUTTONS ---- */
		/* Play Button */
		buttons.add("play");
		buttons.get("play").setRadius(300);
		buttons.get("play").setPosition(new Vector2(0, 0));
		buttons.get("play").setSprite(assets.getSprite("big-play-button.png"));
		buttons.get("play").getSprite().setColor(Appearance.MAIN_COLOR);

		/* Sound Button */
		buttons.add("sound");
		buttons.get("sound").setRadius(75);
		buttons.get("sound").setPosition(calc(90 + 75, 1207 - 75));
		if (game.isSound())buttons.get("sound").setSprite(assets.getSprite("sound-on-button.png"));
		else buttons.get("sound").setSprite(assets.getSprite("sound-off-button.png"));
		buttons.get("sound").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/* Options Button */
		buttons.add("options");
		buttons.get("options").setRadius(75);
		buttons.get("options").setPosition(calc(285 + 75, 1207 - 75));
		buttons.get("options").setSprite(assets.getSprite("options-button.png"));
		buttons.get("options").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/* Info Button */
		buttons.add("info");
		buttons.get("info").setRadius(75);
		buttons.get("info").setPosition(calc(480 + 75, 1207 - 75));
		buttons.get("info").setSprite(assets.getSprite("info-button.png"));
		buttons.get("info").getSprite().setColor(Appearance.BACKGROUND_COLOR);

	}

	private void updateInterface(){
		buttons.update();

		if (buttons.get("play").isClicked())
			game.setScreen(new ModeScreen(game));

		if (buttons.get("sound").isClicked()) {
			game.setSoundSettings(!game.isSound());

			if (game.isSound()) {
				buttons.get("sound").setSprite(assets.getSprite("sound-on-button.png"));
				game.setSoundSettings(true);

			}
			else {
				buttons.get("sound").setSprite(assets.getSprite("sound-off-button.png"));
				game.setSoundSettings(false);
			}

			buttons.get("sound").getSprite().setColor(Appearance.BACKGROUND_COLOR);
		}

		if (buttons.get("options").isClicked()) {
			game.setScreen(new OptionsScreen(game));
		}

		if (buttons.get("info").isClicked()) {
			game.setScreen(new InfoScreen(game));
		}
	}
}
