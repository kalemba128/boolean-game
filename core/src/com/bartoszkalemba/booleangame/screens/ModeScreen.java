package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;
import com.bartoszkalemba.booleangame.GUI.Button;
import com.bartoszkalemba.booleangame.GUI.RectButton;


public class ModeScreen extends AbstractScreen{


	Sprite topSprite;
	Button backButton;
	Label titleLabel;

	RectButton normalButton, creativeButton, randomButton;

	public ModeScreen(final BooleanGame game) {
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

		normalButton = new RectButton(new Vector2(548, 154), calc(86, 505), assets.getSprite("button-mode-normal.png"));
		normalButton.sprite.setColor(Constant.MainColor);

		creativeButton = new RectButton(new Vector2(548, 154), calc(86, 717), assets.getSprite("button-mode-creative.png"));
		creativeButton.sprite.setColor(Constant.MainColor);

		randomButton = new RectButton(new Vector2(548, 154), calc(86, 931), assets.getSprite("button-mode-random.png"));
		randomButton.sprite.setColor(Constant.LockColor);

	}


	private void createTitle() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Constant.BackgroundColor);
		titleLabel = new Label("select mode", labelStyle);

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
		normalButton.update(mouse());
		randomButton.update(mouse());
		creativeButton.update(mouse());

		if (normalButton.isClick())
			game.setScreen(new LevelsScreen(game));

		if (creativeButton.isClick())
			game.setScreen(new CreativeScreen(game));

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
		normalButton.draw(spriteBatch);
		creativeButton.draw(spriteBatch);
		randomButton.draw(spriteBatch);

		spriteBatch.end();
	}

}
