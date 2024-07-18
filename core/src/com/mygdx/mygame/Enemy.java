package com.mygdx.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    private Texture texture;
    private float x, y;
    private float speed;
    private final int TILE_SIZE = 32; // Assuming each tile is 32x32 pixels

    public Enemy(Texture texture, float x, float y, float speed) {
        // Assuming texture is already adjusted to the size of a tile (e.g., 16x16 pixels)
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update(float delta) {
        y -= (speed * TILE_SIZE) * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, TILE_SIZE, TILE_SIZE);
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getSize() {
        return TILE_SIZE;
    }

    // Method to check if enemy is out of screen
    public boolean isOutOfScreen() {
        return y + TILE_SIZE < 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
