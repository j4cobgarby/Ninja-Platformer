package io.github.j4cobgarby.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.j4cobgarby.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		final int WIDTH = 1080;
		final int HEIGHT = 720;
		
		config.width = WIDTH;
		config.height = HEIGHT;
		
		new LwjglApplication(new Main(), config);
	}
}
