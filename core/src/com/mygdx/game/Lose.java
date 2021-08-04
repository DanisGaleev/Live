package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Lose implements Screen {
    private SpriteBatch batch;
    private BitmapFont bitmapFont;
    Game game;

    Lose(Game game) {
        this.game = game;
        bitmapFont = new BitmapFont();
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
        batch.begin();
        bitmapFont.setColor(1,0,0,1);
        bitmapFont.draw(batch, "Your troop was killed by zombis, try again", Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - bitmapFont.getLineHeight());
        batch.end();
        if(Gdx.input.justTouched())
            dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
batch.dispose();
bitmapFont.dispose();
    }
}
