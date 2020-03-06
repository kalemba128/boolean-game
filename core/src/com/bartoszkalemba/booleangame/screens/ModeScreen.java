package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;;
import com.bartoszkalemba.booleangame.GUI.Buttons;


public class ModeScreen extends AbstractScreen{

	private Buttons buttons;
	private Sprite topSprite;
	private Label titleLabel;


	public ModeScreen(final BooleanGame game) {
		super(game);
	}

	@Override
	protected void init() {

		buttons = new Buttons();
		createInterface();
	}

	@Override
	protected void update(float delta) {
		buttons.update();

		if (buttons.get("normal").isClicked())
			game.setScreen(new LevelsScreen(game, "normal"));

		if (buttons.get("creative").isClicked())
			game.setScreen(new LevelsScreen(game, "creative"));

		if (buttons.get("back").isClicked())
			game.setScreen(new MenuScreen(game));
	}

	@Override
	public void  render(float delta) {
		super.render(delta);

		spriteBatch.begin();
		topSprite.draw(spriteBatch);
		titleLabel.draw(spriteBatch, 1);
		buttons.draw(spriteBatch);
		spriteBatch.end();
	}


	private void createInterface() {

		/* Topbar Sprite */
		topSprite = assets.getSprite("topbar.png");
		Vector2 pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Appearance.MAIN_COLOR);

		/* Label */
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Appearance.BACKGROUND_COLOR);
		titleLabel = new Label("select mode", labelStyle);
		pos = calc(getResolution().x / 2 - titleLabel.getWidth()/2, 50 + titleLabel.getHeight()/2);
		titleLabel.setPosition(pos.x, pos.y);

		/* Back Button */
		buttons.add("back");
		buttons.get("back").setRadius(40);
		buttons.get("back").setPosition(calc(20 + 40, 10 + 40));
		buttons.get("back").setSprite(assets.getSprite("back-button.png"));
		buttons.get("back").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/* Normal mode Button */
		buttons.add("normal");
		buttons.get("normal").setSize(new Vector2(548, 154));
		buttons.get("normal").setPosition(calc(86, 505));
		buttons.get("normal").setSprite(assets.getSprite("button-mode-normal.png"));
		buttons.get("normal").getSprite().setColor(Appearance.MAIN_COLOR);

		/* Creative mode Button */
		buttons.add("creative");
		buttons.get("creative").setSize(new Vector2(548, 154));
		buttons.get("creative").setPosition(calc(86, 717));
		buttons.get("creative").setSprite(assets.getSprite("button-mode-creative.png"));
		buttons.get("creative").getSprite().setColor(Appearance.MAIN_COLOR);

		/* Random mode Button */
		buttons.add("random");
		buttons.get("random").setSize(new Vector2(548, 154));
		buttons.get("random").setPosition(calc(86, 931));
		buttons.get("random").setSprite(assets.getSprite("button-mode-random.png"));
		buttons.get("random").getSprite().setColor(Appearance.LOCK_COLOR);
	}
}
