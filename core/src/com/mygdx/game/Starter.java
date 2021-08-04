/*package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Starter implements Screen {
    BitmapFont bitmapFont;
    Battle battle;
    SpriteBatch batch;
    Game game;
    long time;

    Starter(Game game) {
        this.game = game;
        battle = new Battle(game);
    }

    @Override
    public void show() {
        bitmapFont = new BitmapFont();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.384f, 0.384f, 0.192f, 1);
        batch.begin();
        time = TimeUtils.millis();
        bitmapFont.draw(batch, "Tap to start", Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - bitmapFont.getLineHeight());
        batch.end();
        if (Gdx.input.justTouched()) {
            while (TimeUtils.millis() - time < 3000) {

            }
            game.setScreen(battle);
        }
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

    }
}
*/