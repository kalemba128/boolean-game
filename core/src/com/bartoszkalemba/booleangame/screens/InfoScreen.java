package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.GUI.Buttons;
import com.bartoszkalemba.booleangame.engine.GameManager;
import com.bartoszkalemba.booleangame.engine.Vertex;



public class InfoScreen extends AbstractScreen{


	private GameManager gameManager;
	private Sprite topSprite, handSprite, instr1Sprite, instr2Sprite;
	private Buttons buttons;
	private Label titleLabel;

	Vector2 handPosition = new Vector2(-72, 0);


	public InfoScreen(final BooleanGame game) {
		super(game);
	}

	@Override
	protected void init() {
		createInterface();
		createInstuction();
	}

	@Override
	protected void update(float delta) {
		buttons.update();
		updateInstructions();

		if (buttons.get("back").isClicked())
			game.setScreen(new MenuScreen(game));
	}

	@Override
	public void  render(float delta) {
		super.render(delta);
		gameManager.render();

		spriteBatch.begin();

		/* Interface drawing*/
		titleLabel.draw(spriteBatch, 1);
		topSprite.draw(spriteBatch);
		buttons.draw(spriteBatch);

		handSprite.draw(spriteBatch);
		instr1Sprite.draw(spriteBatch);
		instr2Sprite.draw(spriteBatch);

		spriteBatch.end();
	}

	private void createInterface() {
		/* Buttons */
		buttons = new Buttons();
		buttons.add("back");
		buttons.get("back").setRadius(40);
		buttons.get("back").setPosition(calc(20 + 40, 10 + 40));
		buttons.get("back").setSprite(assets.getSprite("back-button.png"));
		buttons.get("back").getSprite().setColor(Appearance.BACKGROUND_COLOR);

		/* Top Sprite */
		topSprite = assets.getSprite("topbar.png");
		Vector2 pos = calc(0, 100);
		topSprite.setPosition(pos.x, pos.y);
		topSprite.setColor(Appearance.MAIN_COLOR);

		/* Hand Sprite */
		handSprite = assets.getSprite("hand.png");
		handSprite.setPosition(-72, 0);

		/* Labels */
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Appearance.BACKGROUND_COLOR);
		titleLabel = new Label("instructions", labelStyle);
		pos = calc(getResolution().x / 2 - titleLabel.getWidth()/2, 50 + titleLabel.getHeight()/2 );
		titleLabel.setPosition(pos.x, pos.y);
	}

	void createInstuction(){
		gameManager = new GameManager();

		int posY = 400;
		gameManager.getLevel().addVertex(-120, posY, Vertex.State.Negative);
		gameManager.getLevel().addVertex(0, posY, Vertex.State.Positive);
		gameManager.getLevel().addVertex(120, posY, Vertex.State.Negative);
		gameManager.getLevel().addConnection(gameManager.getLevel().getVertices().get(0),
				gameManager.getLevel().getVertices().get(1));
		gameManager.getLevel().addConnection(gameManager.getLevel().getVertices().get(1),
				gameManager.getLevel().getVertices().get(2));

		gameManager.setMouse(mouse());
		gameManager.setSpriteBatch(spriteBatch);
		gameManager.setShapeRenderer(shapeRenderer);
		gameManager.getBulletsManager().setGame(game);
	}

	boolean handDir = true;
	private void updateInstructions(){

		gameManager.getBulletsManager().update();

		if (handPosition.y <= 200 && handDir)
			handPosition.y += 150 * Gdx.graphics.getDeltaTime();

		if (handPosition.y >= 200 && handDir) {
			gameManager.hitVertex(gameManager.getLevel().getVertices().get(1));
			handDir = false;
		}

		if (handPosition.y >= 100 && !handDir)
			handPosition.y -= 150 * Gdx.graphics.getDeltaTime();

		if (handPosition.y <= 100 && !handDir)
			handDir = true;

		handSprite.setPosition(handPosition.x, handPosition.y);

		Color handColor = handSprite.getColor();
		float alpha = (handPosition.y * 1.2f);
		if (alpha <= 1) alpha = 1;
		handColor.a = alpha / 255.f;
		//handSprite.setColors(handColor);


		instr1Sprite = assets.getSprite("instr1.png");
		instr2Sprite = assets.getSprite("instr2.png");

		instr1Sprite.setColor(Appearance.MAIN_COLOR);
		instr1Sprite.setPosition(-316, -200);

		instr2Sprite.setPosition(-316, -380);
	}

}
