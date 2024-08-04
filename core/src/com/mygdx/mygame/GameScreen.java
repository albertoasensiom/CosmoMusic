package com.mygdx.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class GameScreen implements Screen {
	//Declaring variables
	private SpriteBatch batch;
    private Texture cellTexture;
    private Texture playerTexture;
    private Texture specialTileTexture;
    private Texture specialTileTexture2;
    private Texture enemyTexture;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private int playerX, playerY;
    private int life;
    private int stamina;
    private float staminaRecoveryTimer;
    private Array<Enemy> enemies; // To store enemies generated
    private final int CELL_SIZE = 32; // Size of each cell
    private final int WORLD_WIDTH = 13 * CELL_SIZE; // 13 columns
    private final int WORLD_HEIGHT = 20 * CELL_SIZE; // 20 rows
    private final int PLAYER_SIZE = 20; // New size for the player
    private final int PLAYER_OFFSET = (CELL_SIZE - PLAYER_SIZE) / 2; // Offset to center the player in the tile
    private Music backgroundMusic; // For background music
    private Sound soundC, soundCSharp, soundD, soundDSharp, soundE, soundF, soundFSharp, soundG, soundGSharp, soundA, soundASharp, soundB, soundC2; // For key sounds


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

    private void spawnEnemy() {
        // Calculate the x-coordinate based on the center of each tile
        float tileCenterX = CELL_SIZE; // Center of each tile
        int columnIndex = (int) (Math.random() * 23); // Randomly select a column index
        float x = columnIndex * CELL_SIZE + (CELL_SIZE / 2f) - (enemyTexture.getWidth() / 2f) + (CELL_SIZE / 2f);

        // Speed parameters
        float minSpeed = 3;   // Minimum speed (adjust as needed)
        float maxSpeed = 8;   // Maximum speed (adjust as needed)
        float speed = minSpeed + (float) (Math.random() * (maxSpeed - minSpeed));

        enemies.add(new Enemy(enemyTexture, x, WORLD_HEIGHT, speed));
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0); // Center the camera in the world
        camera.update();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1f); // Set font size

        // Initialize life and stamina
        life = 3;
        stamina = 100;
        staminaRecoveryTimer = 0;

        // Create the texture checking if they exist
        if (Gdx.files.internal("tile-background.png").exists() && 
            Gdx.files.internal("planet12.png").exists() && 
            Gdx.files.internal("white-tiles.png").exists() &&
            Gdx.files.internal("black-tiles.png").exists() &&
            Gdx.files.internal("enemy.png").exists()) {
            cellTexture = new Texture("tile-background.png"); 
            playerTexture = new Texture("planet12.png");
            specialTileTexture = new Texture("white-tiles.png");
            specialTileTexture2 = new Texture("black-tiles.png");
            enemyTexture = new Texture("enemy.png");
        } else {
            throw new RuntimeException("One or more files do not exist in the assets");
        }
        
        playerX = 0;
        playerY = 0;

        // Initialize enemies
        enemies = new Array<>();
        spawnEnemy();
        
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("CosmoMusicBackground.mp3"));
        backgroundMusic.setLooping(true); // Loop the music
        backgroundMusic.play(); // Start playing the music

        // Initialize sound effects
        soundC = Gdx.audio.newSound(Gdx.files.internal("C.mp3"));
        soundCSharp = Gdx.audio.newSound(Gdx.files.internal("CSharp.mp3"));
        soundD = Gdx.audio.newSound(Gdx.files.internal("D.mp3"));
        soundDSharp = Gdx.audio.newSound(Gdx.files.internal("DSharp.mp3"));
        soundE = Gdx.audio.newSound(Gdx.files.internal("E.mp3"));
        soundF = Gdx.audio.newSound(Gdx.files.internal("F.mp3"));
        soundFSharp = Gdx.audio.newSound(Gdx.files.internal("FSharp.mp3"));
        soundG = Gdx.audio.newSound(Gdx.files.internal("G.mp3"));
        soundGSharp = Gdx.audio.newSound(Gdx.files.internal("GSharp.mp3"));
        soundA = Gdx.audio.newSound(Gdx.files.internal("A.mp3"));
        soundASharp = Gdx.audio.newSound(Gdx.files.internal("ASharp.mp3"));
        soundB = Gdx.audio.newSound(Gdx.files.internal("B.mp3"));
        soundC2 = Gdx.audio.newSound(Gdx.files.internal("C2.mp3"));

    }

    private void updateEnemies(float delta) {
        // Create a list to collect enemies that should be removed
        Array<Enemy> enemiesToRemove = new Array<>();

        // Update enemies and mark for removal if they are out of screen
        for (Enemy enemy : enemies) {
            enemy.update(delta);

            // Check if enemy is out of screen
            if (enemy.isOutOfScreen()) {
                enemiesToRemove.add(enemy);
            }

            // Check for collision with the player
            if (isCollision(playerX * CELL_SIZE + PLAYER_OFFSET, playerY * CELL_SIZE + PLAYER_OFFSET, PLAYER_SIZE, enemy.getX(), enemy.getY(), enemy.getSize())) {
                life--; // Decrease life by 1
                enemiesToRemove.add(enemy); // Remove the enemy after collision
                if (life <= 0) {
                    // Handle game over logic if needed
                }
            }
        }

        // Remove enemies that have fallen off the screen or collided with the player
        enemies.removeAll(enemiesToRemove, true);

        // Spawn new enemies periodically
        if (Math.random() < 0.03) {
            spawnEnemy();
        }
    }

    private boolean isCollision(float playerX, float playerY, float playerSize, float enemyX, float enemyY, float enemySize) {
        return playerX < enemyX + enemySize &&
               playerX + playerSize > enemyX &&
               playerY < enemyY + enemySize &&
               playerY + playerSize > enemyY;
    }

    @Override
    public void render(float delta) {
        // Clear the screen with the specified color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();

        // Set batch to use camera's projection matrix
        batch.setProjectionMatrix(camera.combined);

        // Begin batch rendering
        batch.begin();

        // Draw tiles
        drawTiles();

        // Draw player
        drawPlayer();

        // Draw enemies
        drawEnemies();

        // Draw life and stamina counters
        drawCounters();

        // End batch rendering
        batch.end();

        // Handle user input
        handleInput(delta);

        // Update enemies logic and remove off-screen enemies
        updateEnemies(delta);

        // Recover stamina over time
        recoverStamina(delta);
    }

    // Method to draw tiles based on their positions
    private void drawTiles() {
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
    }

    // Method to draw the player with new size and centered in the tile
    private void drawPlayer() {
        batch.draw(playerTexture, playerX * CELL_SIZE + PLAYER_OFFSET, playerY * CELL_SIZE + PLAYER_OFFSET, PLAYER_SIZE, PLAYER_SIZE);
    }

    // Method to draw enemies
    private void drawEnemies() {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    // Method to draw life and stamina counters
    private void drawCounters() {
        font.draw(batch, "Life: " + life, 10, WORLD_HEIGHT - 10);
        font.draw(batch, "Stamina: " + stamina, WORLD_WIDTH - 100, WORLD_HEIGHT - 10);
    }

    private void handleInput(float delta) {
        int oldPlayerX = playerX;
        int oldPlayerY = playerY;

     // Check for key presses and play corresponding sounds
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if (canMoveTo(0)) {
                moveTo(0);
                soundC.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            if (canMoveTo(1)) {
                moveTo(1);
                soundCSharp.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            if (canMoveTo(2)) {
                moveTo(2);
                soundD.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if (canMoveTo(3)) {
                moveTo(3);
                soundDSharp.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (canMoveTo(4)) {
                moveTo(4);
                soundE.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (canMoveTo(5)) {
                moveTo(5);
                soundF.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            if (canMoveTo(6)) {
                moveTo(6);
                soundFSharp.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            if (canMoveTo(7)) {
                moveTo(7);
                soundG.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            if (canMoveTo(8)) {
                moveTo(8);
                soundGSharp.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (canMoveTo(9)) {
                moveTo(9);
                soundA.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            if (canMoveTo(10)) {
                moveTo(10);
                soundASharp.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            if (canMoveTo(11)) {
                moveTo(11);
                soundB.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            if (canMoveTo(12)) {
                moveTo(12);
                soundC2.play();
            }
        }
    }

    private boolean canMoveTo(int index) {
        int newPlayerX = keyPositions[index][0];
        int newPlayerY = keyPositions[index][1];

        int distanceX = Math.abs(newPlayerX - playerX);

        int requiredStamina = 0;
        if (distanceX == 1) {
            requiredStamina = 5;
        } else if (distanceX == 2) {
            requiredStamina = 10;
        } else if (distanceX >= 3) {
            requiredStamina = 20;
        }

        return stamina >= requiredStamina;
    }

    private void moveTo(int index) {
        int newPlayerX = keyPositions[index][0];
        int newPlayerY = keyPositions[index][1];

        int distanceX = Math.abs(newPlayerX - playerX);

        int requiredStamina = 0;
        if (distanceX == 1) {
            requiredStamina = 5;
        } else if (distanceX == 2) {
            requiredStamina = 10;
        } else if (distanceX >= 3) {
            requiredStamina = 20;
        }

        if (stamina >= requiredStamina) {
            playerX = newPlayerX;
            playerY = newPlayerY;
            stamina = Math.max(0, stamina - requiredStamina);
        }
    }

    private void recoverStamina(float delta) {
        staminaRecoveryTimer += delta;
        if (staminaRecoveryTimer >= 1) {
            stamina = Math.min(100, stamina + 10);
            staminaRecoveryTimer = 0;
        }
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
    public void dispose() { // This method is very important. It frees up memory when an object/method/texture/etc. is no longer needed.

        batch.dispose();
        cellTexture.dispose();
        playerTexture.dispose();
        specialTileTexture.dispose();
        specialTileTexture2.dispose();
        enemyTexture.dispose();
        font.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        
        // Dispose of music and sound effects
        backgroundMusic.dispose();
        soundC.dispose();
        soundCSharp.dispose();
        soundD.dispose();
        soundDSharp.dispose();
        soundE.dispose();
        soundF.dispose();
        soundFSharp.dispose();
        soundG.dispose();
        soundGSharp.dispose();
        soundA.dispose();
        soundASharp.dispose();
        soundB.dispose();
        soundC2.dispose();
    }
}
