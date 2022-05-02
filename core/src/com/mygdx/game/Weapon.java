package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

 class Weapon {
    private Sprite weapon;
    private ArrayList<Cartridges> cartridges;
    private final int magazine;
    private final int time;
    private final int recharge;
    private final String name;
    private final float distance;
    private final int price;

    Weapon(Sprite weapon, int magazine, float distance, int time, int recharge, String name, int price, float x, float y) {
        this.weapon = weapon;
        this.weapon.setX(x);
        this.weapon.setY(y);
        this.magazine = magazine;
        this.recharge = recharge;
        this.name = name;
        this.time = time;
        this.price = price;
        this.distance = distance;
        cartridges = new ArrayList<>();
    }

    ArrayList<Cartridges> getCartridges() {
        return cartridges;
    }

    void setCartridges(ArrayList<Cartridges> cartridges) {
        this.cartridges = cartridges;
    }

    Sprite getWeapon() {
        return weapon;
    }

    int getMagazine() {
        return magazine;
    }

    void draw(SpriteBatch batch) {
        for (int i = 0; i < cartridges.size(); i++) {
            cartridges.get(i).draw(batch);
        }
        weapon.draw(batch);
    }
    void setX(float x){
        this.weapon.setX(x);
    }
     void setY(float y){
         this.weapon.setY(y);
     }

     int getTime() {
        return time;
    }

    int getRecharge() {
        return recharge;
    }


    String getName() {
        return name;
    }

    float getDistance() {
        return distance;
    }

    int getPrice() {
        return price;
    }
}
