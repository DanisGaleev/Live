package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Iterator;


public class Battle implements Screen {
    private ArrayList<Zombi> zombisRigth;
    private ArrayList<Zombi> zombisLeft;
    private SpriteBatch batch;
    private Texture map;
    private OrthographicCamera camera;
    private ArrayList<Soldat> troop;
    private Game game;
    private int money = 0;
    private boolean direction = true;
    private long zombi_time;
    private int recharge;
    private BitmapFont bitmapFont;
    private Stage stage;
    private ArrayList<ImageButton> points;

    Battle(Game game) {
        map = new Texture("map.png");
        batch = new SpriteBatch();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        points = new ArrayList<>();
        zombisRigth = new ArrayList<>();
        zombisLeft = new ArrayList<>();
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(0, 0, 0, 1);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Skin skin = new Skin();
        skin.add("o", new Texture("o.png"));
        skin.add("person", new Texture("p.png"));
        skin.add("TT", new Texture("u.png"));
        skin.add("Uzi", new Texture("u.png"));
        skin.add("Saiga", new Texture("u.png"));
        skin.add("AK47", new Texture("u.png"));
        createButton(new ImageButton(skin.getDrawable("o")), 320, 100);
        createButton(new ImageButton(skin.getDrawable("o")), 600, 130);
        createButton(new ImageButton(skin.getDrawable("o")), 380, 236);
        createButton(new ImageButton(skin.getDrawable("o")), 640, 355);
        createButton(new ImageButton(skin.getDrawable("o")), 370, 465);
        troop = new ArrayList<>();
        troop.add(new Soldat(new ImageButton(skin.getDrawable("person")), new Rectangle(), new ImageButton(skin.getDrawable("Uzi")), false, TimeUtils.millis(), TimeUtils.millis(), new Vector2(250, 80)));
        troop.get(0).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 8, 300f, 50, 3000, 1000f, "TT", 20, 250, 100));
        recharge = (troop.get(0).getWeapon().getMagazine());
        stage.addActor(troop.get(0).getSoldat());
        stage.addActor(troop.get(0).getUpgrate());
    }

    @Override
    public void show() {
        troop.get(0).getSoldat().addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (int j = 0; j < points.size(); j++) {
                    points.get(j).setVisible(true);
                }
                troop.get(0).setIstouch(true);
                troop.get(0).getUpgrate().setVisible(true);
                troop.get(0).getUpgrate().addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (money >= troop.get(0).getWeapon().getPrice() && troop.get(0).getWeapon().getName().equals("TT")) {
                            money -= troop.get(0).getWeapon().getPrice();
                            troop.get(0).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 20, 300f, 70, 2000, 1000f, "Uzi", 100, troop.get(0).getX(), 100));
                        } else if (money >= troop.get(0).getWeapon().getPrice() && troop.get(0).getWeapon().getName().equals("Uzi")) {
                            money -= troop.get(0).getWeapon().getPrice();
                            troop.get(0).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 8, 200f, 200, 5000, 1000f, "Saiga", 200, troop.get(0).getX(), 100));
                        } else if (money >= troop.get(0).getWeapon().getPrice() && troop.get(0).getWeapon().getName().equals("Saiga")) {
                            money -= troop.get(0).getWeapon().getPrice();
                            troop.get(0).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 30, 400f, 50, 3500, 1000f, "AK47", 0, troop.get(0).getX(), 100));
                            troop.get(0).getUpgrate().setVisible(false);
                        }
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        for (int j = 0; j < points.size(); j++) {
                            points.get(j).setVisible(false);
                        }
                        troop.get(0).getUpgrate().setVisible(false);
                        troop.get(0).setIstouch(false);
                    }
                });

                points.get(0).addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        for (int k = 0; k < points.size(); k++) {
                            points.get(k).setVisible(false);
                        }
                        troop.get(0).setFrom(new Vector2(troop.get(0).getPosition()));
                        troop.get(0).setTo(new Vector2(points.get(0).getX(), points.get(0).getY() - 20));
                        troop.get(0).getUpgrate().setVisible(false);
                        troop.get(0).setIstouch(false);
                    }
                });

                points.get(1).addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        for (int k = 0; k < points.size(); k++) {
                            points.get(k).setVisible(false);
                        }
                        troop.get(0).setFrom(new Vector2(troop.get(0).getPosition()));
                        troop.get(0).setTo(new Vector2(points.get(1).getX(), points.get(1).getY() - 20));
                        troop.get(0).getUpgrate().setVisible(false);
                        troop.get(0).setIstouch(false);
                    }
                });
                points.get(2).addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        for (int k = 0; k < points.size(); k++) {
                            points.get(k).setVisible(false);
                        }
                        troop.get(0).setFrom(new Vector2(troop.get(0).getPosition()));
                        troop.get(0).setTo(new Vector2(points.get(2).getX(), points.get(2).getY() - 20));
                        troop.get(0).getUpgrate().setVisible(false);
                        troop.get(0).setIstouch(false);
                    }
                });
                points.get(3).addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        for (int k = 0; k < points.size(); k++) {
                            points.get(k).setVisible(false);
                        }
                        troop.get(0).setFrom(new Vector2(troop.get(0).getPosition()));
                        troop.get(0).setTo(new Vector2(points.get(3).getX(), points.get(3).getY() - 20));
                        troop.get(0).getUpgrate().setVisible(false);
                        troop.get(0).setIstouch(false);
                    }
                });
                points.get(4).addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        for (int k = 0; k < points.size(); k++) {
                            points.get(k).setVisible(false);
                        }
                        troop.get(0).setFrom(new Vector2(troop.get(0).getPosition()));
                        troop.get(0).setTo(new Vector2(points.get(4).getX(), points.get(4).getY() - 20));
                        troop.get(0).getUpgrate().setVisible(false);
                        troop.get(0).setIstouch(false);
                    }
                });
                return true;
            }
        });
        //zombiSpawn(Gdx.graphics.getWidth());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        batch.draw(map, 360, 80, map.getWidth() / 4, map.getHeight() / 4);


        bitmapFont.draw(batch, "Your money = " + money + " $", 50, Gdx.graphics.getHeight() - 50);

        for (int i = 0; i < troop.size(); i++) {
            troop.get(i).getWeapon().draw(batch);
        }
        for (int i = 0; i < zombisRigth.size(); i++) {
            zombisRigth.get(i).draw(batch);
        }
        for (int i = 0; i < zombisLeft.size(); i++) {
            zombisLeft.get(i).draw(batch);
        }

        stage.act();
        stage.draw();

        batch.end();

        if (troop.size() > 0) {
            move_all();
            //overlaps_all();
            distance_all();
            //cartridge_fly_recharge();

           /* if (TimeUtils.millis() - zombi_time > 500) {
                if (direction)
                    zombiSpawn(-20);
                else
                    zombiSpawn(Gdx.graphics.getWidth());
                direction = !direction;
            }
            */
        } else {
            game.setScreen(new Lose(game));
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
        batch.dispose();
    }

    private void cartridgesMake(float x, float y, Vector2 direction) {
        for (int i = 0; i < troop.size(); i++) {
            troop.get(i).getWeapon().getCartridges().add(new Cartridges(new Sprite(new Texture("cartridge.png")), new Rectangle(), new Vector2(x, y), direction));
        }
    }

    private void zombiSpawn(float spawnX) {
        if (spawnX < 0)
            zombisLeft.add(new Zombi(new Sprite(new Texture("z.png")), new Rectangle(), new Vector2(spawnX, (float) 80.0)));
        else
            zombisRigth.add(new Zombi(new Sprite(new Texture("z.png")), new Rectangle(), new Vector2(spawnX, (float) 80.0)));
        zombi_time = TimeUtils.millis();
    }

    private void createButton(ImageButton button, float x, float y) {
        button.setX(x);
        button.setY(y);
        button.setVisible(false);
        points.add(button);
        stage.addActor(button);
    }

    private void move_all() {
        System.out.println(troop.get(0).getPosition());
        if (troop.get(0).getTo().y > 105 && troop.get(0).getY() < 105) {
            if (350 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (355 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, 1), 60f);
        } else if (troop.get(0).getTo().y > 211 && troop.get(0).getY() < 211) {
            if (450 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (455 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, 1), 60f);
        } else if (troop.get(0).getTo().y > 330 && troop.get(0).getY() < 330) {
            if (609 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (614 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, 1), 60f);
        } else if (troop.get(0).getTo().y > 440 && troop.get(0).getY() < 440) {
            if (413 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (418 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, 1), 60f);
        } else if (troop.get(0).getY() > 330 && troop.get(0).getY() > troop.get(0).getTo().y) {
            if (413 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (418 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, -1), 60f);
        } else if (troop.get(0).getY() > 211 && troop.get(0).getY() > troop.get(0).getTo().y) {
            if (609 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (614 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, -1), 60f);
        } else if (troop.get(0).getY() > 105 && troop.get(0).getY() > troop.get(0).getTo().y) {
            if (450 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (455 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, -1), 60f);
        } else if (troop.get(0).getY() > 75 && troop.get(0).getY() > troop.get(0).getTo().y) {
            if (350 > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            else if (355 < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
            else
                troop.get(0).move(new Vector2(0, -1), 60f);
        } else {
            if (troop.get(0).getTo().x > troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(1, 0), 60);
            if (troop.get(0).getTo().x < troop.get(0).getPosition().x)
                troop.get(0).move(new Vector2(-1, 0), 60);
        }

        troop.get(0).getWeapon().setX(troop.get(0).getX());
        troop.get(0).getWeapon().setY(troop.get(0).getY() + 20);

        for (Zombi zombic : zombisLeft) {
            zombic.move(new Vector2(1, 0), 50f);
        }
        for (Zombi zombic : zombisRigth) {
            zombic.move(new Vector2(-1, 0), 50f);
        }

        for (int j = 0; j < troop.get(0).getWeapon().getCartridges().size(); j++) {
            troop.get(0).getWeapon().getCartridges().get(j).move(troop.get(0).getWeapon().getSpeed());
        }
    }

    private void overlaps_all() {
        for (Iterator<Soldat> iterator = troop.iterator(); iterator.hasNext(); ) {
            Soldat soldat = iterator.next();
            for (Zombi zombic : zombisRigth) {
                if (soldat.getRectangle().overlaps(zombic.getRectangle())) {
                    iterator.remove();
                }
            }
            for (Zombi zombic : zombisLeft) {
                if (soldat.getRectangle().overlaps(zombic.getRectangle()))
                    iterator.remove();
            }
        }

        for (Iterator<Cartridges> iterator = troop.get(0).getWeapon().getCartridges().iterator(); iterator.hasNext(); ) {
            Cartridges cartridge = iterator.next();
            for (Iterator<Zombi> iterator1 = zombisRigth.iterator(); iterator1.hasNext(); ) {
                Zombi zombi = iterator1.next();
                if (zombi.getRectangle().overlaps(cartridge.getRectangle())) {
                    money += 10;
                    iterator.remove();
                    iterator1.remove();
                }
            }
            for (Iterator<Zombi> iterator1 = zombisLeft.iterator(); iterator1.hasNext(); ) {
                Zombi zombi = iterator1.next();
                if (zombi.getRectangle().overlaps(cartridge.getRectangle())) {
                    money += 10;
                    iterator.remove();
                    iterator1.remove();
                }
            }
        }
    }

    private void distance_all() {
        for (Iterator<Cartridges> iterator = troop.get(0).getWeapon().getCartridges().iterator(); iterator.hasNext(); ) {
            Cartridges cartridge = iterator.next();
            if (Math.abs(cartridge.getX() - troop.get(0).getX()) > troop.get(0).getWeapon().getDistance()) {
                iterator.remove();
            }
        }

        for (Iterator<Zombi> iterator = zombisRigth.iterator(); iterator.hasNext(); ) {
            Zombi zombi = iterator.next();
            if (zombi.getX() <= -zombi.getZombi().getWidth()) {
                iterator.remove();
            }
        }
        for (Iterator<Zombi> iterator = zombisLeft.iterator(); iterator.hasNext(); ) {
            Zombi zombi = iterator.next();
            if (zombi.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
            }
        }
    }

    private void cartridge_fly_recharge() {
        if (TimeUtils.millis() - troop.get(0).getTime() > troop.get(0).getWeapon().getRecharge()) {
            troop.get(0).setCanshoot(true);
            recharge = troop.get(0).getWeapon().getMagazine();
            troop.get(0).setTime(TimeUtils.millis());
        }

        if (troop.get(0).getCanshoot()) {
            if (recharge > 0) {
                if (zombisLeft.size() > 0 && zombisRigth.size() > 0) {
                    if (troop.get(0).getPosition().x - zombisLeft.get(0).getPosition().x < troop.get(0).getWeapon().getDistance() && troop.get(0).getPosition().x - zombisLeft.get(0).getPosition().x < zombisRigth.get(0).getPosition().x - troop.get(0).getPosition().x) {
                        if (TimeUtils.millis() - troop.get(0).getRechargeT() > troop.get(0).getWeapon().getTime()) {
                            recharge--;
                            System.out.println(troop.get(0).getPosition() + "   " + zombisLeft.get(0).getPosition());
                            cartridgesMake(troop.get(0).getX(), troop.get(0).getY() + 20, troop.get(0).getPosition().cpy().sub(zombisLeft.get(0).getPosition().cpy()).nor());
                            troop.get(0).setRechargeT(TimeUtils.millis());
                        }

                    } else if (zombisRigth.get(0).getPosition().x - troop.get(0).getPosition().x < troop.get(0).getWeapon().getDistance() && troop.get(0).getPosition().x - zombisLeft.get(0).getPosition().x > zombisRigth.get(0).getPosition().x - troop.get(0).getPosition().x) {

                    }


               /* if (zombisLeft.size() > 0 && zombisRigth.size() > 0 && (Math.abs(zombisRigth.get(0).getX() - troop.get(0).getX()) < troop.get(0).getWeapon().getDistance() || Math.abs(zombisLeft.get(0).getX() - troop.get(0).getX()) < troop.get(0).getWeapon().getDistance())) {
                    if (Math.abs(zombisLeft.get(0).getX() - troop.get(0).getX()) > Math.abs(zombisRigth.get(0).getX() - troop.get(0).getX())) {
                        if (TimeUtils.millis() - troop.get(0).getRechargeT() > troop.get(0).getWeapon().getTime()) {
                            recharge--;
                            cartridgesMake(troop.get(0).getX(), troop.get(0).getY() + 20, troop.get(0).getPosition().sub(zombisLeft.get(0).getPosition()).nor());
                            troop.get(0).setRechargeT(TimeUtils.millis());
                        }
                    }
                    if (Math.abs(zombisLeft.get(0).getX() - troop.get(0).getX()) <= Math.abs(zombisRigth.get(0).getX() - troop.get(0).getX())) {
                        if (TimeUtils.millis() - troop.get(0).getRechargeT() > troop.get(0).getWeapon().getTime()) {
                            recharge--;
                            cartridgesMake(troop.get(0).getX(), troop.get(0).getY() + 20, zombisRigth.get(0).getPosition().sub(troop.get(0).getPosition()).nor());
                            troop.get(0).setRechargeT(TimeUtils.millis());
                        }
                    }

                */


                }
            } else {
                troop.get(0).setTime(TimeUtils.millis());
                troop.get(0).setCanshoot(false);
            }

        }
    }
}
