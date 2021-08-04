package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Zombi {
    private Sprite zombi;
    private Vector2 position;
    private Rectangle rectangle;

    Zombi(Sprite zombi, Rectangle rectangle, Vector2 position) {
        this.zombi = zombi;
        this.rectangle = rectangle;
        this.position = position;
        this.zombi.setPosition(this.position.x, this.position.y);
        this.rectangle.setPosition(this.position);
        this.rectangle.setSize(this.zombi.getWidth(), this.zombi.getHeight());
    }
    float getX() {
        return zombi.getX();
    }

    void setX(float x) {
        this.zombi.setX(x);
        this.rectangle.x = x;
    }
float getY(){
        return zombi.getY();
}
    Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    Sprite getZombi() {
        return zombi;
    }

    public void setZombi(Sprite zombi) {
        this.zombi = zombi;
    }

    void draw(SpriteBatch batch) {
        this.zombi.draw(batch);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    void move(Vector2 direction, float speed) {
        direction.nor();
        Vector2 velocity = direction.cpy().scl(speed * Gdx.graphics.getDeltaTime());
        this.position.add(velocity);
        this.zombi.setPosition(this.position.x, this.position.y);
        this.rectangle.setPosition(this.position);
    }

}
