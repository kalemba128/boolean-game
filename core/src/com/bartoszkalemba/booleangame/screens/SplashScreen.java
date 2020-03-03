package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;

public class SplashScreen extends AbstractScreen{

	public SplashScreen(final BooleanGame game) {
		super(game);
	}


	Sprite logoSprite;

	@Override
	protected void init() {

		// Init assets
		assets.init();

		// Load Resources
		loadResources();
	}


	private void createLogo() {
		logoSprite = assets.getSprite("logo.png");
		int w = (int)logoSprite.getWidth();
		int h = (int)logoSprite.getHeight() - 90;
		logoSprite.setPosition(-w/2, (-h/2));
		logoSprite.setColor(Constant.MainColor);
	}

	private void loadResources() {
		createLogo();
		assets.loadTextures();
		assets.loadFonts();
		assets.loadAudio();
	}

	@Override
	protected void update(float delta)
	{
		if (game.getAssets().manager.update()) {
			game.setScreen(new MenuScreen(game));
		}

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		drawLoadBar(game.getAssets().manager.getProgress());

		spriteBatch.begin();
		logoSprite.draw(spriteBatch);
		spriteBatch.end();
	}

	private void drawLoadBar(float progress) {

		float w = logoSprite.getWidth();
		float h = 70;
		float x = (-w/2);
		float y = -(logoSprite.getY() + 90 + h);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.setColor(Constant.MainColor);
		shapeRenderer.rect(x, y, w, h); // back

		shapeRenderer.setColor(Constant.BackgroundColor);
		shapeRenderer.rect(x + 10, y + 10, w - 20, h - 20); //


		float progressScale = (w - 20) / 100;
		shapeRenderer.setColor(Constant.MainColor);
		shapeRenderer.rect(x + 10, y + 10, progress * progressScale * 100, h - 20); //

		shapeRenderer.end();
	}


}
