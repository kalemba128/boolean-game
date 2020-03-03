package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;
import com.bartoszkalemba.booleangame.GUI.Button;


public class OptionsScreen extends AbstractScreen{


	Sprite topSprite;
	Button backButton;
	Label titleLabel;

	Button[] colorButtons;


	public OptionsScreen(final BooleanGame game) {
		super(game);
	}

	@Override
	protected void init() {

		createInterface();
	}

	private void createInterface() {
		createTopbar();
		createTitle();

		backButton = new Button(40, calc(20 + 40, 10 + 40), assets.getSprite("back-button.png"));
		backButton.sprite.setColor(Constant.BackgroundColor);

		createColorsList();

	}

	private void createColorsList() {

		float w = (4 * 130) + (4 * 20);
		float h = (4 * 130) + (5 * 20);

		Vector2 startPosition = new Vector2((-w/2) + 65, (h/2) - 110);
		Vector2 position = new Vector2(startPosition.x, startPosition.y);

		float pad = 20;
		colorButtons = new Button[16];

		for (int i = 0; i < 16; i++) {

			String spritePath = "colors/" + (i+1) + ".png";
			colorButtons[i]= new Button(65, new Vector2(position.x, position.y), assets.getSprite(spritePath));

			position.x += 130 + pad;

			if ((i+1) % 4 == 0) {
				position.x = startPosition.x;
				position.y -= 130 + pad;
			}
		}
	}


	private void createTitle() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Constant.BackgroundColor);
		titleLabel = new Label("change colors", labelStyle);

		float w = titleLabel.getWidth();
		float h = titleLabel.getHeight();

		Vector2 pos = calc(getResolution().x / 2 - w/2, 50 + h/2 );
		titleLabel.setPosition(pos.x, pos.y);
	}
	private void createTopbar() {
		topSprite = assets.getSprite("topbar.png");
		Vector2 pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Constant.MainColor);
	}


	@Override
	protected void update(float delta) {
		backButton.update(mouse());

		int colorSet = 0;
		for (Button b : colorButtons) {
			b.update(mouse());

			if (b.isClick()) {
				game.setColors(colorSet);
				game.setScreen(new OptionsScreen(game));
			}

			colorSet++;
		}


		if (backButton.isClick())
			game.setScreen(new MenuScreen(game));
	}

	@Override
	public void  render(float delta) {
		super.render(delta);

		spriteBatch.begin();

		topSprite.draw(spriteBatch);
		backButton.draw(spriteBatch);
		titleLabel.draw(spriteBatch, 1);

		for (Button b : colorButtons)
			b.draw(spriteBatch);

		spriteBatch.end();
	}

}
