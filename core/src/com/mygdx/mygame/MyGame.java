package com.mygdx.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;

public class MyGame extends Game {
    @Override
    public void create() {
        this.setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render(); // Important to call render() method of the current screen
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}