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
	public final static String GAME_LEVELS_NORMAL = "com.bartoszkalemba.boolean.prefs.levels";
	public final static String GAME_LEVELS_CREATIVE = "com.bartoszkalemba.boolean.prefs.creative";
	public final static String GAME_SOUND = "com.bartoszkalemba.boolean.prefs.sound";
	public final static String GAME_APPEARANCE = "com.bartoszkalemba.boolean.prefs.appearance";

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

		getSoundSettings();
		getAppearanceSettings();

		this.setScreen(new SplashScreen(this));
	}

	/// --- APPEARANCE --- ///
	public void getAppearanceSettings(){
		int set = prefs.getInteger(GAME_APPEARANCE);
		Appearance.MAIN_COLOR = appearance.getMainColor(set);
		Appearance.BACKGROUND_COLOR = appearance.getBackgroundColor(set);
		Appearance.LOCK_COLOR = appearance.getLockColor(set);
	}

	public void setAppearanceSettings(int colorSet){
		Appearance.MAIN_COLOR = appearance.getMainColor(colorSet);
		Appearance.BACKGROUND_COLOR = appearance.getBackgroundColor(colorSet);
		Appearance.LOCK_COLOR = appearance.getLockColor(colorSet);
		prefs.putInteger(GAME_APPEARANCE, colorSet);
		prefs.flush();
	}


	/// --- LEVELS --- ///
	public void unlockNextNormalLevel(int level) {

		int currentLevel = prefs.getInteger(GAME_LEVELS_NORMAL);

		if (currentLevel < level) {
			prefs.putInteger(GAME_LEVELS_NORMAL, level);
			prefs.flush();
		}
	}

	public void unlockNextCreativeLevel(int level) {

		int currentLevel = prefs.getInteger(GAME_LEVELS_CREATIVE);

		if (currentLevel < level) {
			prefs.putInteger(GAME_LEVELS_CREATIVE, level);
			prefs.flush();
		}
	}

	public int getCurrentNormalLevel() {
		return prefs.getInteger(GAME_LEVELS_NORMAL);
	}
	public int getCurrentCreativeLevel() {
		return prefs.getInteger(GAME_LEVELS_CREATIVE);
	}




	// ---- GETERS & SETTERS ----- //
	public Assets getAssets(){return assets;}

	public boolean isPaused() {return paused;}
	public void setPaused(boolean paused) {this.paused = paused;}


	/// --- SOUND --- ///
	private void getSoundSettings(){
		if (!prefs.contains(GAME_SOUND))
			setSoundSettings(sound);
		else setSoundSettings(prefs.getBoolean(GAME_SOUND));
	}

	public void setSoundSettings(boolean sound) {
		this.sound = sound;
		prefs.putBoolean(GAME_SOUND, sound);
		prefs.flush();
	}

	public static boolean isSound() {
		return sound;
	}
}
