package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.Constant;
import com.bartoszkalemba.booleangame.GUI.Button;
import com.bartoszkalemba.booleangame.engine.GameManager;
import com.bartoszkalemba.booleangame.engine.Vertex;


public class InfoScreen extends AbstractScreen{


	Sprite topSprite;
	Button backButton;
	Label titleLabel;

	GameManager gameManager;
	Sprite handSprite, instr1Sprite, instr2Sprite;
	Vector2 handPosition = new Vector2(-72, 0);


	public InfoScreen(final BooleanGame game) {
		super(game);
	}

	@Override
	protected void init() {

		createInterface();

		createInstuction();
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


		handSprite = assets.getSprite("hand.png");
		handSprite.setPosition(-72, 0);
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
		//handSprite.setColor(handColor);


		instr1Sprite = assets.getSprite("instr1.png");
		instr2Sprite = assets.getSprite("instr2.png");

		instr1Sprite.setColor(Constant.MainColor);
		instr1Sprite.setPosition(-316, -200);

		instr2Sprite.setPosition(-316, -380);
	}

	private void createInterface() {
		createTopbar();
		createTitle();

		backButton = new Button(40, calc(20 + 40, 10 + 40), assets.getSprite("back-button.png"));
		backButton.sprite.setColor(Constant.BackgroundColor);


	}




	private void createTitle() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(assets.getFont("default48.fnt"), Constant.BackgroundColor);
		titleLabel = new Label("instructions", labelStyle);

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

		updateInstructions();
		backButton.update(mouse());


		if (backButton.isClick())
			game.setScreen(new MenuScreen(game));
	}

	@Override
	public void  render(float delta) {
		super.render(delta);

		gameManager.render();

		spriteBatch.begin();

		topSprite.draw(spriteBatch);
		backButton.draw(spriteBatch);
		titleLabel.draw(spriteBatch, 1);
		handSprite.draw(spriteBatch);

		instr1Sprite.draw(spriteBatch);
		instr2Sprite.draw(spriteBatch);

		spriteBatch.end();



	}

}
