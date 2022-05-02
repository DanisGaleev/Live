package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Line {
    private Vector2 a, b;

    public Line() {
        a = new Vector2();
        b = new Vector2();
    }

    public Line(float ax, float ay, float bx, float by) {
        a = new Vector2(ax, ay);
        b = new Vector2(bx, by);
    }

    public Line(Vector2 a, Vector2 b) {
        this.a = a;
        this.b = b;
    }

    public boolean contains(Rectangle rect) {
        float px1 = a.x;
        float py1 = a.y;
        float px2 = b.x;
        float py2 = b.y;
        //float x2 = rect.x + rect.width;
        //float y2 = rect.y + rect.height;
        //float x1 = rect.x;
        //float y1 = rect.y;

        float intervalX = (px2 - px1) / 100;
        float intervalY = (py2 - py1) / 100;

        boolean is = false;

        for (int i = 0; i < 101; i++) {
            float x = px1 + i * intervalX;
            float y = py1 + i * intervalY;
            if (rect.contains(x, y)) {
                is = true;
                break;
            }
        }

        return is;/* Math.min(px1, px2) <= Math.max(x1, x2) && Math.max(px1, px2) >= Math.min(x1, x2) &&
                Math.min(py1, py2) <= Math.max(y1, y2) && Math.max(py1, py2) >= Math.min(y1, y2);
                */
    }

    public void set(Vector2 a, Vector2 b) {
        this.a = a;
        this.b = b;
    }

    public void set(float ax, float ay, float bx, float by) {
        a = new Vector2(ax, ay);
        b = new Vector2(bx, by);
    }
}
