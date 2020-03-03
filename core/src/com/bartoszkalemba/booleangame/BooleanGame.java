package com.bartoszkalemba.booleangame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.bartoszkalemba.booleangame.screens.SplashScreen;

public class BooleanGame extends Game {

	/* SIZES OF WINDOW TO TESTING APP ON DESKTOP */
	public static Vector2 RESOLUTION = new Vector2(720, 1280); // target resolution
	public final static int WINDOW_WIDTH = 360;
	public final static int WINDOW_HEIGHT = (WINDOW_WIDTH * (int)RESOLUTION.y) / (int)RESOLUTION.x;

	public final static String GAME_PREFS = "com.bartoszkalemba.boolean.prefs";
	public final static String GAME_LEVELS = "com.bartoszkalemba.boolean.prefs.levels";
	public final static String GAME_LEVELS_CREATIVE = "com.bartoszkalemba.boolean.prefs.creative";
	public final static String GAME_SOUND = "com.bartoszkalemba.boolean.prefs.sound";
	public final static String GAME_COLORS = "com.bartoszkalemba.boolean.prefs.appearance";

	// ---- PRIVATE ---- //
	private boolean paused;
	private static boolean sound = true;
	private Preferences prefs;
	private Assets assets;
	private Appearance appearance = new Appearance();

	@Override
	public void create () {
		init();
	}

	@Override
	public void dispose () {}

	private void init() {
		assets = new Assets();

		prefs =  Gdx.app.getPreferences(GAME_PREFS);

		if (!prefs.contains(GAME_SOUND))
			setSound(sound);
		else setSound(prefs.getBoolean(GAME_SOUND));

		int colorSet = prefs.getInteger(GAME_COLORS);
		setAppearance(colorSet);

		this.setScreen(new SplashScreen(this));
	}

	public void setAppearance(int set){
		Constant.BackgroundColor = appearance.getBackground(set);
		Constant.MainColor = appearance.getMain(set);
		Constant.LockColor = appearance.getLock(set);

		prefs.putInteger(GAME_COLORS, set);
		prefs.flush();
	}


	public void unlockLevel(int level) {

		int currentLevel = prefs.getInteger(GAME_LEVELS);

		if (currentLevel < level) {
			prefs.putInteger(GAME_LEVELS, level);
			prefs.flush();
		}
	}

	public void unlockLevelCreative(int level) {

		int currentLevel = prefs.getInteger(GAME_LEVELS_CREATIVE);

		if (currentLevel < level) {
			prefs.putInteger(GAME_LEVELS_CREATIVE, level);
			prefs.flush();
		}
	}

	public int getUnlockLevel() {
		return prefs.getInteger(GAME_LEVELS);
	}
	public int getUnlockLevelCreative() {
		return prefs.getInteger(GAME_LEVELS_CREATIVE);
	}




	// ---- GETERS & SETTERS ----- //
	public Assets getAssets(){return assets;}

	public boolean isPaused() {return paused;}
	public void setPaused(boolean paused) {this.paused = paused;}

	public void setSound(boolean sound) {
		this.sound = sound;
		prefs.putBoolean(GAME_SOUND, sound);
		prefs.flush();

	}

	public static boolean isSound() {
		return sound;
	}
}
