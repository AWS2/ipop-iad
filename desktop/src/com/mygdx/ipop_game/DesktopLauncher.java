package com.mygdx.ipop_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;

import java.awt.DisplayMode;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("IPOP Game");
		config.setWindowSizeLimits(1500,800,2000,1080);
		config.setTitle("Animation");
		CommonWebSockets.initiate();
		new Lwjgl3Application(new IPOP(), config);
	}
}
