package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.GUI.Button;
import com.bartoszkalemba.booleangame.GUI.Buttons;


public class OptionsScreen extends AbstractScreen{


	private Sprite topSprite;
	private Button backButton;
	private Label titleLabel;
	private Button[] colorButtons;
	private Buttons buttons;

	public OptionsScreen(final BooleanGame game) {
		super(game);
	}

	@Override
	protected void init() {
		createInterface();
	}

	private void createInterface() {

		/* Back button */
		buttons = new Buttons();
		buttons.add("back");
		buttons.get("back").setRadius(40);
		buttons.get("back").setPosition(calc(20 + 40, 10 + 40));
		buttons.get("back").setSprite(assets.getSprite("back-button.png"));
		buttons.get("back").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/* Title */
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Appearance.BACKGROUND_COLOR);
		titleLabel = new Label("change colors", labelStyle);
		Vector2 pos = calc(getResolution().x / 2 - titleLabel.getWidth()/2, 50 + titleLabel.getHeight()/2 );
		titleLabel.setPosition(pos.x, pos.y);

		/* Topbar */
		topSprite = assets.getSprite("topbar.png");
		pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Appearance.MAIN_COLOR);

		/* Colorlist */
		createColorsList();
	}

	private void createColorsList() {
		float w = (4 * 130) + (4 * 20);
		float h = (4 * 130) + (5 * 20);

		Vector2 startPosition = new Vector2((-w/2) + 65, (h/2) - 110);
		Vector2 position = new Vector2(startPosition.x, startPosition.y);

		float margin = 20;

		for (int i = 1; i <= 16; i++) {
			String spritePath = "colors/" + i + ".png";
			String name = "color" + i;
			buttons.add(name);
			buttons.get(name).setRadius(65);
			buttons.get(name).setPosition(new Vector2(position.x, position.y));
			buttons.get(name).setSprite(assets.getSprite(spritePath));

			position.x += 130 + margin;
			if (i % 4 == 0) {
				position.x = startPosition.x;
				position.y -= 130 + margin;
			}
		}
	}

	@Override
	protected void update(float delta) {
		buttons.update();

		for (int i = 1; i <= 16; i++) {
			String spritePath = "colors/" + i + ".png";
			String name = "color" + i;

			if (buttons.get(name).isClicked()) {
				game.setAppearanceSettings(i-1);
				game.setScreen(new OptionsScreen(game));
			}
		}

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
}
