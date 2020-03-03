package com.bartoszkalemba.booleangame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bartoszkalemba.booleangame.BooleanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BooleanGame.WINDOW_WIDTH;
		config.height = BooleanGame.WINDOW_HEIGHT;
		config.samples = 4;
		new LwjglApplication(new BooleanGame(), config);
	}
}
