package com.appia.sliding.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.appia.sliding.SlidingGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 640;

//		config.width = 240;
//		config.height = 320;

//		config.width = 720;
//		config.height = 1280;
		new LwjglApplication(new SlidingGame(), config);
	}
}
