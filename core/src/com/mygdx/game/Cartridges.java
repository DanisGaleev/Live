package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Cartridges {
    private Sprite cartridge;
    private Rectangle rectangle;
    private Vector2 position;
    private final Vector2 direction;
    Cartridges(Sprite cartridge, Rectangle rectangle, Vector2 position, Vector2 direction) {
        this.cartridge = cartridge;
        this.rectangle = rectangle;
        this.position = position;
        this.cartridge.setX(position.x);
        this.cartridge.setY(position.y);
        this.rectangle.setPosition(this.position);
        this.rectangle.width = cartridge.getWidth();
        this.rectangle.height = cartridge.getHeight();
        this.direction = direction;
    }

    float getX() {
        return cartridge.getX();
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    void draw(SpriteBatch batch) {
        cartridge.draw(batch);
    }

    void move(float speed) {
        direction.nor();
        Vector2 velocity = direction.cpy().scl(speed * Gdx.graphics.getDeltaTime());
        this.position.add(velocity);
        this.cartridge.setPosition(this.position.x, this.position.y);
        this.rectangle.setPosition(this.position);
    }
}
