package com.mygdx.mygame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Cosmo Music");
        config.setWindowedMode(800, 600); // Initial window size
        config.setResizable(false);
        new Lwjgl3Application(new MyGame(), config);
    }
}
