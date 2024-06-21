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
    private Texture specialTileTexture;
    private Texture specialTileTexture2;
    private OrthographicCamera camera;
    private Viewport viewport;
    private int playerX, playerY;
    private final int CELL_SIZE = 32; // Size of each cell
    private final int WORLD_WIDTH = 13 * CELL_SIZE; // 13 columns
    private final int WORLD_HEIGHT = 20 * CELL_SIZE; // 20 rows
    private final int PLAYER_SIZE = 16; // New size for the player
    private final int PLAYER_OFFSET = (CELL_SIZE - PLAYER_SIZE) / 2; // Offset to center the player in the tile

    // Positions for the player to move to, corresponding to each key
    private final int[][] keyPositions = {
        {0, 0},  // 'a' -> 1st white tile C
        {1, 1},  // 'w' -> 1st black tile C#
        {2, 0},  // 's' -> 2nd white tile D
        {3, 1},  // 'e' -> 2nd black tile D#
        {4, 0},  // 'd' -> 3rd white tile E
        {5, 0},  // 'f' -> 4th white tile F
        {6, 1},  // 't' -> 3rd black tile F#
        {7, 0},  // 'g' -> 5th white tile G
        {8, 1},  // 'y' -> 4th black tile G#
        {9, 0},  // 'h' -> 6th white tile A
        {10, 1}, // 'u' -> 5th black tile A#
        {11, 0}, // 'j' -> 7th white tile B
        {12, 0}  // 'k' -> 8th white tile C
    };

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0); // Center the camera in the world
        camera.update();
        batch = new SpriteBatch();
        
        // Create the texture checking if they exist
        if (Gdx.files.internal("tile-background.png").exists() && 
            Gdx.files.internal("planet12.png").exists() && 
            Gdx.files.internal("white-tiles.png").exists() &&
            Gdx.files.internal("black-tiles.png").exists()) {
            cellTexture = new Texture("tile-background.png"); 
            playerTexture = new Texture("planet12.png");
            specialTileTexture = new Texture("white-tiles.png");
            specialTileTexture2 = new Texture("black-tiles.png");
        } else {
            throw new RuntimeException("One or more files do not exist in the assets");
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
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 13; col++) {
                // Check if we are at the bottom row and in a special column
                if (row == 0 && (col == 0 || col == 2 || col == 4 || col == 5 || col == 7 || col == 9 || col == 11 || col == 12)) {
                    batch.draw(specialTileTexture, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } 
                // Check if we are at the second bottom row and in a special column
                else if (row == 1 && (col == 1 || col == 3 || col == 6 || col == 8 || col == 10)) {
                    batch.draw(specialTileTexture2, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } 
                else {
                    batch.draw(cellTexture, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // Draw the player with new size and centered in the tile
        batch.draw(playerTexture, playerX * CELL_SIZE + PLAYER_OFFSET, playerY * CELL_SIZE + PLAYER_OFFSET, PLAYER_SIZE, PLAYER_SIZE);

        batch.end(); // Ends the batch, flushing its contents to the GPU.

        handleInput(); // Handles user input.
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) moveTo(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) moveTo(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) moveTo(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) moveTo(3);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) moveTo(4);
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) moveTo(5);
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) moveTo(6);
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) moveTo(7);
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) moveTo(8);
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) moveTo(9);
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) moveTo(10);
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) moveTo(11);
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) moveTo(12);
    }

    private void moveTo(int index) {
        playerX = keyPositions[index][0];
        playerY = keyPositions[index][1];
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
        specialTileTexture.dispose();
        specialTileTexture2.dispose();
    }
}
