package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;


import static com.mygdx.game.Battle.money;
import static com.mygdx.game.Battle.troop;

class Soldat extends Actor {
    public Texture img;
    private ImageButton upg;
    private boolean isTouched;
    private Weapon weapon;
    private boolean canshoot;
    private long time;
    private long rechargeT;
    private int recharge;
    private Vector2 to;
    private Vector2 from;
    private boolean isMoving;

    public static final int SPEED = 80;
    private static final float SCALE = 1.5f;

    public Soldat(final ArrayList<ImageButton> btns, Stage stage, int x, int y) {
        to = new Vector2(x, y);
        from = new Vector2(x, y);
        stage.addActor(this);
        Skin skin = new Skin();
        skin.add("upg", new Texture("u.png"));

        upg = new ImageButton(skin.getDrawable("upg"));
        stage.addActor(upg);

        img = new Texture("p.png");
        upg.setBounds(1000, 200, 80, 80);
        upg.setVisible(false);

        upg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (money >= weapon.getPrice() && weapon.getName().equals("TT")) {
                    money -= weapon.getPrice();
                    weapon = new Weapon(new Sprite(new Texture("weapon.png")), 20, 300f, 70, 2000, "Uzi", 100, getX(), 100);
                } else if (money >= weapon.getPrice() && weapon.getName().equals("Uzi")) {
                    money -= weapon.getPrice();
                    weapon = new Weapon(new Sprite(new Texture("weapon.png")), 8, 200f, 200, 5000, "Saiga", 200, getX(), 100);
                } else if (money >= weapon.getPrice() && weapon.getName().equals("Saiga")) {
                    money -= weapon.getPrice();
                    weapon = new Weapon(new Sprite(new Texture("weapon.png")), 30, 400f, 50, 3500, "AK47", 0, getX(), 100);
                    upg.setVisible(false);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (int i = 0; i < btns.size(); i++) {
                    btns.get(i).setVisible(false);
                }
                upg.setVisible(false);
            }
        });

        setBounds(x, y, img.getWidth() * SCALE, img.getHeight() * SCALE);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean is = false;
                for (int i = 0; i < troop.size(); i++) {
                    if (troop.get(i).isTouched)
                        is = true;

                }
                if (pointer == 0 && !is) {
                    upg.setVisible(true);
                    isTouched = true;
                    for (int i = 0; i < btns.size(); i++) {
                        btns.get(i).setVisible(true);
                    }
                }
                return true;
            }
        });
        time = TimeUtils.millis();
        rechargeT = TimeUtils.millis();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getActions().isEmpty())
            isMoving = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(img, getX(), getY(), getWidth() / SCALE, getHeight() / SCALE);
    }

    public int fitstTarget(ArrayList<Rectangle> rects, ArrayList<Zombi> zombis) {
        float min = weapon.getDistance();
        int id = -1;
        Line line = new Line();
        boolean is = false;
        for (int i = 0; i < zombis.size(); i++) {
            float distance = getDistance(getX() + img.getWidth(), getY() + 30, zombis.get(i).getX(), zombis.get(i).getY() + 30);
            if (distance < min) {
                for (Rectangle rect : rects) {
                    line.set(getX() + img.getWidth(), getY() + 30, zombis.get(i).getX(), zombis.get(i).getY() + 30);
                    if (!line.contains(rect)) {
                        id = i;
                        min = distance;
                    } else {
                        is = true;
                    }
                }
                if (is) {
                    id = -1;
                    min = weapon.getDistance();
                }
                is = false;
            }
        }
        return id;
    }

    /*  public int fitstTarget(ArrayList<Rectangle> rect, ArrayList<Zombi> enemy) {
          double minDistance = weapon.getDistance();
          int id = 0;
          boolean is = false;
          boolean d = false;
          for (int i = 1; i < enemy.size(); i++) {
              Zombi zombi = enemy.get(i);
              if (minDistance > getDistance(getX(), getY() + 20, zombi.getX(), zombi.getY() + 20)) {
                  d = true;
                  for (int j = 0; j < rect.size(); j++) {
                      if (new Line(getX(), getY() + 20, zombi.getX(), zombi.getY() + 20).contains(rect.get(j)))
                          is = true;
                  }
                  if (!is) {
                      minDistance = getDistance(getX(), getY() + 20, zombi.getX(), zombi.getY() + 20);
                      id = i;
                  } else
                      id = -1;
                  is = false;

              }
          }
          if (!d)
              id = -1;
          return id;
      }


     */
    private float getDistance(float ax, float ay, float bx, float by) {
        return (float) Math.sqrt(((ax - bx) * (ax - bx) + (ay - by) * (ay - by)));
    }

    void move(Vector2 direction) {
        direction.nor();
        Vector2 velocity = direction.cpy().scl(SPEED * Gdx.graphics.getDeltaTime());
        setPosition(getX() + velocity.x, getY() + velocity.y);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        recharge = weapon.getMagazine();
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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public ImageButton getUpg() {
        return upg;
    }

    public int getRecharge() {
        return recharge;
    }

    public void setRecharge(int recharge) {
        this.recharge = recharge;
    }
}