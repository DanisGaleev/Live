package com.mygdx.game;

import com.badlogic.gdx.Game;
public class Life extends Game {
    @Override
    public void create() {
        setScreen(new Battle(this));
    }
}
