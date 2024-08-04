package com.mygdx.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {

    private MyGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont font;
    private Texture cellTexture;
    private final int WORLD_WIDTH = 13 * 32; // 13 columns
    private final int WORLD_HEIGHT = 20 * 32; // 20 rows

    public MainMenu(MyGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        this.camera.update();
        this.font = new BitmapFont(); // Using a default font
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(2.5f); // Scale font for larger size and better clarity

        // Load the background texture
        this.cellTexture = new Texture("tile-background.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK); // Clear the screen with black color
        camera.update();

        // Rendering
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Draw the background
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 13; col++) {
                batch.draw(cellTexture, col * 32, row * 32, 32, 32);
            }
        }

        // Draw text "COSMO MUSIC"
        font.draw(batch, "COSMO MUSIC", WORLD_WIDTH / 2 - 150, WORLD_HEIGHT / 2 + 50);

        // Draw "Start Game" button
        float startX = WORLD_WIDTH / 2 - 75;
        float startY = WORLD_HEIGHT / 2;
        font.draw(batch, "Start Game", startX, startY);

        // Draw "Exit" button
        float exitX = WORLD_WIDTH / 2 - 75;
        float exitY = WORLD_HEIGHT / 2 - 50;
        font.draw(batch, "Exit", exitX, exitY);

        batch.end();

        // Handle mouse clicks
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            y = WORLD_HEIGHT - y; // Invert the Y coordinate

            if (x > startX && x < startX + 150 && y > startY - 30 && y < startY) {
                game.setScreen(new GameScreen()); // Switch to the game screen
            }

            if (x > exitX && x < exitX + 150 && y > exitY - 30 && y < exitY) {
                Gdx.app.exit(); // Exit the game
            }
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
    public void dispose() {
        batch.dispose();
        font.dispose();
        cellTexture.dispose();
    }
}
