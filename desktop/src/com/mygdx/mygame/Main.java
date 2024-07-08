package com.mygdx.mygame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration(); // Create LWJGL3 application configuration
        config.setForegroundFPS(60); // Set the frame rate to 60 FPS
        config.setTitle("Cosmo Music"); // Set the window title
        config.setWindowedMode(800, 600); // Initial window size
        config.setResizable(false); // Make the window non-resizable
        new Lwjgl3Application(new MyGame(), config); // Create and start a new application with the given configuration
    }
}