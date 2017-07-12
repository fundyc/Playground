package com.luigi.playground.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.luigi.playground.Playground;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Missile2Kim";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Playground(), config);
	}
}
