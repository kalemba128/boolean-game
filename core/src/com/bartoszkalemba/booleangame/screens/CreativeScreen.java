package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;
import com.bartoszkalemba.booleangame.GUI.Button;


public class CreativeScreen extends AbstractScreen{

	private final int numberLevels = 30; // liczba poziomow
	private int unlockLevel;

	Sprite topSprite;
	Button backButton;
	Label titleLabel;

	Button[] levelButtons;

	public CreativeScreen(final BooleanGame game) {
		super(game);
	}

	@Override
	protected void init() {

		if (game.getUnlockLevelCreative() == 0)
			unlockLevel = 1;
		else unlockLevel = game.getUnlockLevelCreative();


		createInterface();
	}

	private void createInterface() {
		createTopbar();
		createTitle();

		backButton = new Button(40, calc(20 + 40, 10 + 40), assets.getSprite("back-button.png"));
		backButton.sprite.setColor(Constant.BackgroundColor);

		createLevelList();

	}

	private void createLevelList() {

		float w = (5 * 120) + (4 * 15);
		float h = (6 * 120) + (5 * 15);

		Vector2 startPosition = new Vector2((-w/2) + 60, (h/2) - 110);
		Vector2 position = new Vector2(startPosition.x, startPosition.y);
		float pad = 15;
		levelButtons = new Button[numberLevels];


		for (int i = 0; i < numberLevels; i++) {

			if ((i + 1) <= unlockLevel) {
				levelButtons[i] = new Button(60, new Vector2(position.x, position.y), assets.getSprite("level-button.png"));
				levelButtons[i].sprite.setColor(Constant.MainColor);
				int levelText = (i+1);
				levelButtons[i].setText("" + levelText, assets.getFont("default32.fnt"));
			}
			else {
				levelButtons[i] = new Button(60, new Vector2(position.x, position.y), assets.getSprite("lock-button.png"));
				levelButtons[i].sprite.setColor(Constant.LockColor);
			}



			position.x += 120 + pad;

			if ((i+1) % 5 == 0)
			{
				position.x = startPosition.x;
				position.y -= 120 + pad;
			}

		}

	}

	private void createTitle() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Constant.BackgroundColor);
		titleLabel = new Label("creative", labelStyle);

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

		for (Button b : levelButtons) {
			b.update(mouse());

			if (b.isClick() && b.label != null)
			{
				int level = Integer.parseInt(b.getText());
				System.out.println("level " + level);
				game.setScreen(new GameplayScreen(game, -level));
			}
		}


		if (backButton.isClick())
			game.setScreen(new ModeScreen(game));
	}

	@Override
	public void  render(float delta) {
		super.render(delta);

		spriteBatch.begin();

		topSprite.draw(spriteBatch);
		backButton.draw(spriteBatch);
		titleLabel.draw(spriteBatch, 1);


		for (Button b : levelButtons)
			b.draw(spriteBatch);


		spriteBatch.end();
	}

}
