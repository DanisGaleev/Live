package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.math.Rectangle;

class Soldat {
    private final ImageButton soldat;
    private ImageButton upgrate;
    private Weapon weapon;
    private boolean canshoot;
    private Vector2 position;
    private long time;
    private Vector2 to;
    private Vector2 from;
    private long rechargeT;
    private Rectangle rectangle;

    Soldat(final ImageButton soldat, Rectangle rectangle, ImageButton upgrate, boolean canshoot, long rechargeT, long time, Vector2 position) {
        this.position = position;
        this.soldat = soldat;
        this.soldat.setPosition(this.position.x, this.position.y);
        this.rechargeT = rechargeT;
        this.rectangle = rectangle;
        this.to = this.position;
        this.from = this.position;
        this.time = time;
        this.canshoot = canshoot;
        this.upgrate = upgrate;
        this.upgrate.setPosition(1000, 40);
        this.upgrate.setVisible(false);
        this.rectangle.setPosition(this.position);
        this.rectangle.setSize(this.soldat.getWidth(), this.soldat.getHeight());
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    float getX() {
        return this.soldat.getX();
    }

    void setX(float x) {
        this.soldat.setX(x);
        this.rectangle.setX(x);
    }

    void setY(float y) {
        this.soldat.setY(y);
        this.rectangle.setY(y);
    }

    float getY() {
        return this.soldat.getY();
    }

    Vector2 getPosition() {
        return this.position;
    }

    ImageButton getSoldat() {
        return soldat;
    }

    void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    Weapon getWeapon() {
        return weapon;
    }

    void setUpgrate(ImageButton upgrate) {
        this.upgrate = upgrate;
    }

    ImageButton getUpgrate() {
        return upgrate;
    }

    void setCanshoot(boolean canshoot) {
        this.canshoot = canshoot;
    }

    boolean getCanshoot() {
        return canshoot;
    }

    void setTime(long time) {
        this.time = time;
    }

    long getTime() {
        return time;
    }

    void setRechargeT(long rechargeT) {
        this.rechargeT = rechargeT;
    }

    long getRechargeT() {
        return rechargeT;
    }

    void setIstouch(boolean istouch) {
    }

    void setFrom(Vector2 from) {
        this.from = from;
    }

    public Vector2 getFrom() {
        return from;
    }

    void setTo(Vector2 to) {
        this.to = to;
    }

    Vector2 getTo() {
        return this.to;
    }

    void move(Vector2 direction, float speed) {
        direction.nor();
        Vector2 velocity = direction.cpy().scl(speed * Gdx.graphics.getDeltaTime());
        this.position.add(velocity);
        this.soldat.setPosition(this.position.x, this.position.y);
        this.rectangle.setPosition(this.position);
    }
}
