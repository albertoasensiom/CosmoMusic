package com.mygdx.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Piano2 extends Actor {

    private final Array<Key> keys;

    public Piano2(Texture texture) {
        this.keys = new Array<>();
        createKeys(texture);
    }

    private void createKeys(Texture texture) {
        // Define the positions and sizes of the keys
        float keyWidth = texture.getWidth() / 8f;
        float keyHeight = texture.getHeight() / 2f;
        float keySpacing = keyWidth / 8f;
        float bottomRowY = 0;
        float topRowY = keyHeight;

        // Create keys for the bottom row
        for (int i = 0; i < 8; i++) {
            float keyX = i * keyWidth;
            Key key = new Key(texture, keyX, bottomRowY, keyWidth, keyHeight);
            keys.add(key);
        }

        // Create keys for the top row
        for (int i = 0; i < 5; i++) {
            float keyX = (i + 1) * keyWidth + (i * keySpacing); // Offset by one key and add spacing
            Key key = new Key(texture, keyX, topRowY, keyWidth, keyHeight);
            keys.add(key);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Key key : keys) {
            key.draw(batch, parentAlpha);
        }
    }

    private static class Key extends Actor {
        private final Texture texture;
        private final float x, y, width, height;

        public Key(Texture texture, float x, float y, float width, float height) {
            this.texture = texture;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            setSize(width, height);
            setPosition(x, y);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
    }
}
