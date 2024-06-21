package com.mygdx.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Texture cellTexture;
    private Texture playerTexture;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int playerX, playerY;
    private final int CELL_SIZE = 32; // Size of each cell
    private final int WORLD_WIDTH = 13 * CELL_SIZE; // 13 columns
    private final int WORLD_HEIGHT = 20 * CELL_SIZE; // 20 rows

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0); // Center the camera in the world
        camera.update();
        batch = new SpriteBatch();
        
        
     // Create the texture checking if they exist
        if (Gdx.files.internal("tile-background.png").exists() && Gdx.files.internal("planet12.png").exists()) {
            cellTexture = new Texture("tile-background.png"); 
            playerTexture = new Texture("planet12.png");
        } else {
            throw new RuntimeException("These file does not exist in the assets");
        }
        
        
        playerX = 0;
        playerY = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen with the specified color.
        camera.update(); // Updates the camera's matrices.

        batch.setProjectionMatrix(camera.combined); // Sets the projection matrix for rendering.
        batch.begin(); // Starts a new batch for rendering.

        // Draw the tiles
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 13; col++) {
                batch.draw(cellTexture, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the player
        batch.draw(playerTexture, playerX * CELL_SIZE, playerY * CELL_SIZE);

        batch.end(); // Ends the batch, flushing its contents to the GPU.

        handleInput(); // Handles user input.
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) playerX = Math.max(0, playerX - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) playerY = Math.max(0, playerY - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) playerX = Math.min(12, playerX + 1); // 12 because the max column index is 12 (0-12 = 13 columns)
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) playerY = Math.min(19, playerY + 1); // 19 because the max row index is 19 (0-19 = 20 rows)
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        cellTexture.dispose();
        playerTexture.dispose();
    }
}
