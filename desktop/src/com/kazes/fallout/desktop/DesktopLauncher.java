package com.kazes.fallout.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kazes.fallout.test.Survivor;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1100;
		config.height = 600;
		config.samples = 3;
		config.backgroundFPS = 60;
		//config.fullscreen = true;

		//config.depth = 5;
		new LwjglApplication(new Survivor(), config);
	}
}
