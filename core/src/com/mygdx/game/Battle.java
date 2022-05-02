package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


public class Battle implements Screen {
    private ImageButton pause;
    private ArrayList<Zombi> zombis;
    private SpriteBatch batch;
    private Texture map;
    private OrthographicCamera camera;
    static ArrayList<Soldat> troop;
    private Game game;
    public static int money = 0;
    private boolean direction = true;
    private long zombi_time;
    private Location loc;
    private BitmapFont bitmapFont;
    private Stage stage;
    private ArrayList<ImageButton> points;

    Battle(Game game) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setElementType(Location.class, "walls", Rectangle.class);
        loc = json.fromJson(Location.class, Gdx.files.internal("Locations\\Mission1.json"));
        map = new Texture(loc.getLocName());
        batch = new SpriteBatch();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        points = new ArrayList<>();
        zombis = new ArrayList<>();
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
        //  zombis.add(new Zombi(new Sprite(new Texture("z.png")), new Rectangle(), new Vector2(30, 80)));
        //zombis.add(new Zombi(new Sprite(new Texture("z.png")), new Rectangle(), new Vector2(1200, 80)));
        troop.add(new Soldat(points, stage, 170, 80));
        troop.get(0).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 8, 400, 50, 3000, "TT", 20, 170, 100));
        troop.add(new Soldat(points, stage, 195, 80));
        troop.get(1).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 8, 400, 50, 3000, "TT", 20, 250, 100));
        troop.add(new Soldat(points, stage, 220, 80));
        troop.get(2).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 8, 400, 50, 3000, "TT", 20, 170, 100));
        troop.add(new Soldat(points, stage, 250, 80));
        troop.get(3).setWeapon(new Weapon(new Sprite(new Texture("weapon.png")), 8, 800, 50, 3000, "TT", 20, 250, 100));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        camera.update();
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(map, 360, 80, map.getWidth() / 4, map.getHeight() / 4);

        bitmapFont.draw(batch, Gdx.graphics.getFramesPerSecond() + "", 50, Gdx.graphics.getHeight() - 50);//"Your money = " + money + " $", 50, Gdx.graphics.getHeight() - 50);

        for (int i = 0; i < troop.size(); i++) {
            troop.get(i).getWeapon().draw(batch);
        }
        for (Zombi zombi : zombis) {
            zombi.draw(batch);
        }
        batch.end();
        stage.act();
        stage.draw();

        if (troop.size() > 0) {
            move_all();
            overlaps_all();
            distance_all();
            soldatAim();
            //cartridge_fly_recharge();

            if (TimeUtils.millis() - zombi_time > 500) {
                if (direction)
                    zombiSpawn(-20);
                else
                    zombiSpawn(Gdx.graphics.getWidth());
                direction = !direction;
            }


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
        stage.dispose();
    }

    public void soldatAim() {
        for (Soldat soldat : troop) {
            if (TimeUtils.millis() - soldat.getTime() > soldat.getWeapon().getRecharge()) {
                soldat.setCanshoot(true);
                soldat.setRecharge(soldat.getWeapon().getMagazine());
                soldat.setTime(TimeUtils.millis());
            }
            if (soldat.getCanshoot()) {
                if (soldat.getRecharge() > 0) {
                    if (!zombis.isEmpty()) {
                        int id = soldat.fitstTarget(loc.getWalls(), zombis);
                        if (id != -1) {
                            if (TimeUtils.millis() - soldat.getRechargeT() > soldat.getWeapon().getTime()) {
                                soldat.setRecharge(soldat.getRecharge() - 1);
                                Vector2 dir = new Vector2(zombis.get(id).getX(), zombis.get(id).getY() + 30).sub(soldat.getX() + soldat.img.getWidth(), soldat.getY() + 30).nor();
                                cartridgesMake(soldat, soldat.getX() + soldat.img.getWidth(), soldat.getY() + 30, dir);
                                soldat.setRechargeT(TimeUtils.millis());
                            }
                        }
                    }
                } else {
                    soldat.setTime(TimeUtils.millis());
                    soldat.setCanshoot(false);
                }
            }
        }
    }

    private void cartridgesMake(Soldat soldat, float x, float y, Vector2 direction) {
        soldat.getWeapon().getCartridges().add(new Cartridges(new Sprite(new Texture("cartridge.png")), new Rectangle(), new Vector2(x, y), direction));
    }

    private void zombiSpawn(float spawnX) {
        zombis.add(new Zombi(new Sprite(new Texture("z.png")), new Rectangle(), new Vector2(spawnX, 80)));
        if (spawnX < 0)
            zombis.get(zombis.size() - 1).setDirection(new Vector2(1, 0));
        else
            zombis.get(zombis.size() - 1).setDirection(new Vector2(-1, 0));
        zombi_time = TimeUtils.millis();

    }

    private void createButton(ImageButton button, final float X, final float Y) {
        button.setX(X);
        button.setY(Y);
        button.setVisible(false);
        points.add(button);
        stage.addActor(button);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == 0) {
                    for (Soldat soldat : troop) {
                        if (soldat.isTouched()) {
                            soldat.setTouched(false);
                            soldat.setTo(new Vector2(X, Y));
                            soldat.setMoving(true);
                            soldat.getUpg().setVisible(false);
                            for (int i = 0; i < points.size(); i++) {
                                points.get(i).setVisible(false);
                            }
                            break;
                        }
                    }
                }
                return true;
            }
        });
    }

    private void move_all() {
        for (Soldat soldat : troop) {
            if (soldat.getActions().isEmpty()) {
                if (soldat.getTo().y >= 105 && soldat.getY() < 105) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(355, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(355, 105, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y >= 215 && soldat.getY() < 215) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(450, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(450, 215, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y >= 336 && soldat.getY() < 336) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(609, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(609, 336, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y >= 441 && soldat.getY() < 441) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(413, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(413, 441, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y <= 355 && soldat.getY() > 336) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(413, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(413, 336, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y <= 236 && soldat.getY() > 215) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(609, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(609, 215, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y <= 130 && soldat.getY() > 105) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(450, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(450, 105, 1));
                    soldat.addAction(se);
                } else if (soldat.getTo().y <= 100 && soldat.getY() > 80) {
                    SequenceAction se = new SequenceAction();
                    se.addAction(Actions.moveTo(355, soldat.getY(), 1));
                    se.addAction(Actions.moveTo(355, 80, 1));
                    soldat.addAction(se);
                } else {
                    if (soldat.getTo().x != soldat.getX())
                        soldat.addAction(Actions.moveTo(soldat.getTo().x, soldat.getY(), 1));
                }
            }
            soldat.getWeapon().setX(soldat.getX());
            soldat.getWeapon().setY(soldat.getY() + 20);
        }

        for (Zombi zombi : zombis) {
            zombi.move();
        }
        for (Soldat soldat : troop) {
            for (Cartridges car : soldat.getWeapon().getCartridges()) {
                car.move();
            }
        }
    }

    private void overlaps_all() {
        for (Soldat soldat : troop) {
            for (Iterator<Cartridges> iterator = soldat.getWeapon().getCartridges().iterator(); iterator.hasNext(); ) {
                Cartridges car = iterator.next();
                for (Iterator<Zombi> iterator1 = zombis.iterator(); iterator1.hasNext();) {
                    Zombi zombi = iterator1.next();
                    if (zombi.getRectangle().overlaps(car.getRectangle())) {
                        iterator.remove();
                        iterator1.remove();
                        break;
                    }
                }
            }
        }
        for (Iterator<Zombi> iterator = zombis.iterator(); iterator.hasNext(); ) {
            Zombi zombi = iterator.next();
            for (Soldat soldat : troop) {
                for (Iterator<Cartridges> car = soldat.getWeapon().getCartridges().iterator(); car.hasNext(); ) {
                    Cartridges carr = car.next();
                    if (carr.getRectangle().overlaps(zombi.getRectangle())) {
                        iterator.remove();
                        car.remove();
                    }
                }
            }
        }
       /* for (Iterator<Soldat> iterator = troop.iterator(); iterator.hasNext(); ) {
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

        */
    }

    private void distance_all() {
        for (Soldat soldat : troop) {
            for (Iterator<Cartridges> iterator = soldat.getWeapon().getCartridges().iterator(); iterator.hasNext(); ) {
                Cartridges car = iterator.next();
                if (car.getX() > Gdx.graphics.getWidth() + 100 || car.getX() < -100)
                    iterator.remove();
            }
        }
        for (Iterator<Zombi> iterator = zombis.iterator(); iterator.hasNext(); ) {
            Zombi zombi = iterator.next();
            if (zombi.getX() < -100 || zombi.getX() > Gdx.graphics.getWidth() + 100)
                iterator.remove();
        }
       /* for (Iterator<Zombi> iterator = zombisRigth.iterator(); iterator.hasNext(); ) {
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

        */
    }

   /* private void cartridge_fly_recharge() {
        for (Soldat soldat : troop) {
            if (TimeUtils.millis() - soldat.getTime() > soldat.getWeapon().getRecharge()) {
                soldat.setCanshoot(true);
                soldat.setRecharge(soldat.getWeapon().getMagazine());
                soldat.setTime(TimeUtils.millis());
            }

        }
        for (Soldat soldat : troop) {
            if (soldat.getCanshoot()) {
                if (soldat.getRecharge() > 0) {
                    if (!zombis.isEmpty()) {
                        {
                            if (TimeUtils.millis() - troop.get(0).getRechargeT() > troop.get(0).getWeapon().getTime()) {
                                recharge--;
                                //System.out.println(troop.get(0).getPosition() + "   " + zombisLeft.get(0).getPosition());
                                cartridgesMake(troop.get(0).getX(), troop.get(0).getY() + 20, troop.get(0).getPosition().cpy().sub(zombisLeft.get(0).getPosition().cpy()).nor());
                                troop.get(0).setRechargeT(TimeUtils.millis());
                            }

                        } else
                        if (zombisRigth.get(0).getPosition().x - troop.get(0).getPosition().x < troop.get(0).getWeapon().getDistance() && troop.get(0).getPosition().x - zombisLeft.get(0).getPosition().x > zombisRigth.get(0).getPosition().x - troop.get(0).getPosition().x) {

                        }


                    }
                } else {
                    troop.get(0).setTime(TimeUtils.millis());
                    troop.get(0).setCanshoot(false);
                }

            }
        }
    }*/
}
