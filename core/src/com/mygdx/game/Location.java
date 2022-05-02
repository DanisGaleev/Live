package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;

public class Location {
    private String locName;
    private ArrayList<Rectangle> walls;

    public Location(){

    }

    public Location(String locName, ArrayList<Rectangle> walls) {
        this.locName = locName;
        this.walls = walls;
    }

    public Location(FileHandle file) {
        Json json = new Json(JsonWriter.OutputType.json);
        this.locName = json.fromJson(Location.class, file).locName;
    }

    public String toJson() {
        return new Json().toJson(new Location(locName, walls));
    }

    public void fromJson(FileHandle file) {
        this.locName = new Json().fromJson(Location.class, file).locName;
        this.walls = new Json().fromJson(Location.class, file).walls;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public ArrayList<Rectangle> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Rectangle> walls) {
        this.walls = walls;
    }
}
