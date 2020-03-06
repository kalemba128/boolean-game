package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.GUI.Buttons;

public class LevelsScreen extends AbstractScreen{

	private final int numberOfLevels = 40;
	private int unlockedLevel;
	private String mode;

	/* Resources */
	private Sprite topSprite;
	private Buttons buttons;
	private Label titleLabel;

	public LevelsScreen(final BooleanGame game, String mode) {
		super(game);
		this.mode = mode;
		unlockLevels();
		createInterface();
	}

	@Override
	protected void init() {

	}


	@Override
	protected void update(float delta) {
		buttons.update();

		if (buttons.get("back").isClicked())
			game.setScreen(new ModeScreen(game));


		for (int i = 1; i <= numberOfLevels; i++){
			if (buttons.get("level" + i).isClicked() && i <= unlockedLevel) {
				System.out.println("level" + i);
				game.setScreen(new GameplayScreen(game, mode, i));
			}
		}
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

	private void unlockLevels(){
		int toUnlock = 0;

		if (mode.equals("normal"))
			toUnlock = game.getCurrentNormalLevel();
		else if (mode.equals("creative"))
			toUnlock = game.getCurrentCreativeLevel();

		unlockedLevel = toUnlock;
		if (unlockedLevel == 0) unlockedLevel = 1;
	}

	private void createInterface() {
		/// --- Buttons --- ///
        buttons = new Buttons();

		buttons.add("back");
		buttons.get("back").setRadius(40);
		buttons.get("back").setPosition(calc(20 + 40, 10 + 40));
		buttons.get("back").setSprite(assets.getSprite("back-button.png"));
		buttons.get("back").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		// --- Topbar --- //
		topSprite = assets.getSprite("topbar.png");
		Vector2 pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Appearance.MAIN_COLOR);

		// --- Title --- //
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Appearance.BACKGROUND_COLOR);
		titleLabel = new Label(mode, labelStyle);
		pos = calc(getResolution().x / 2 - titleLabel.getWidth()/2,
				50 + titleLabel.getHeight()/2 );
		titleLabel.setPosition(pos.x, pos.y);

        // --- Levels List --- //
		createLevelList();
	}

	private void createLevelList() {
		float weight = (5 * 120) + (4 * 15);
		float height = (8 * 120) + (7 * 15);
		Vector2 startPosition = new Vector2((-weight/2) + 60, (height/2) - 110);
		Vector2 position = new Vector2(startPosition.x, startPosition.y);
		float margin = 15;

		for (int i = 1; i <= numberOfLevels; i++) {
			String name = "level" + i;
			buttons.add(name);
			System.out.println("Buttton " + name + " ADDED");
			buttons.get(name).setRadius(60);
			buttons.get(name).setPosition(new Vector2(position.x, position.y));

			if (i <= unlockedLevel) {
				buttons.get(name).setSprite(assets.getSprite("level-button.png"));
				buttons.get(name).getSprite().setColor(Appearance.MAIN_COLOR);
				buttons.get(name).setText("" + i, assets.getFont("default32.fnt"));
			}
			else {
				buttons.get(name).setSprite(assets.getSprite("lock-button.png"));
				buttons.get(name).getSprite().setColor(Appearance.LOCK_COLOR);
			}

			position.x += 120 + margin;

			if (i % 5 == 0) {
				position.x = startPosition.x;
				position.y -= 120 + margin;
			}
		}
	}
}
