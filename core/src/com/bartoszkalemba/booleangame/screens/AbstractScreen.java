package com.bartoszkalemba.booleangame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bartoszkalemba.booleangame.Appearance;
import com.bartoszkalemba.booleangame.Assets;
import com.bartoszkalemba.booleangame.BooleanGame;
import com.bartoszkalemba.booleangame.GUI.Button;


public abstract class AbstractScreen implements Screen{

	protected BooleanGame game;
	protected OrthographicCamera camera;
	protected Viewport viewport;
	protected SpriteBatch spriteBatch;
	protected ShapeRenderer shapeRenderer;
	protected Assets assets;
	protected Matrix4 defaultMatrix;

	public AbstractScreen(BooleanGame game){
		this.game = game;
		assets = this.game.getAssets();

		camera = new OrthographicCamera(BooleanGame.RESOLUTION.x, BooleanGame.RESOLUTION.y);
		viewport = new StretchViewport(BooleanGame.RESOLUTION.x, BooleanGame.RESOLUTION.y, camera);

		defaultMatrix = camera.combined.cpy();

		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		init();
	}

	protected Vector2 getResolution(){
		return new Vector2(camera.viewportWidth,  camera.viewportHeight);
	}

	Vector2 calc(float x, float y) {
		Vector2 half = new Vector2(getResolution().x/2, getResolution().y/2);

		float X = -(half.x - x);
		float Y = (getResolution().y - y) - half.y;

		return new Vector2(X, Y);
	}

	protected abstract void init();

	protected abstract void update(float delta);

	public Vector2 mouse() {
		Vector3 m = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return  new Vector2(m.x, m.y);
	}

	@Override
	public void render(float delta) {
		Button.setMouse(mouse());
		update(delta);
		clearScreen();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(Appearance.BACKGROUND_COLOR.r,
				Appearance.BACKGROUND_COLOR.g,
				Appearance.BACKGROUND_COLOR.b,
				Appearance.BACKGROUND_COLOR.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void show() {Gdx.app.log("INFO", "SHOW ABSTRACT");}

	@Override
	public void resume() {
		game.setPaused(false);
		Gdx.app.log("INFO", "RESUME ABSTRACT");
	}
	
	@Override
	public void pause() {
		game.setPaused(true);
		Gdx.app.log("INFO", "PAUSE ABSTRACT");
	}
	
	@Override
	public void hide() {Gdx.app.log("INFO", "HIDE ABSTRACT");}
	
	@Override
	public void dispose() {
		game.dispose();
		Gdx.app.log("INFO", "DISPOSE ABSTRACT");
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		Gdx.app.log("INFO", "RESIZE ABSTRACT");
	}
}
