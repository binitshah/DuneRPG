package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.Controls;
import com.binitshah.dunerpg.item.Item;
import com.binitshah.dunerpg.levels.Level;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/14/17.
 *
 * Paul Atreides (Atriedes?) (lol jk i know the spelling) (i didn't google it) is our main character in this game.
 */

public class PaulAtreides extends Player {

    //Information
    private static String spriteSheetName = "paulsprites.png";

    //Stats
    private ArrayList<Item> inventory;
    private int health = 100;
    private int water = 100;
    private int experience = 0;
    private int spice = 0;
    private int attackPower = 10;
    private int level = 0;

    public PaulAtreides(Level level, float[] mapSpecificPlayerValues) {
        super(spriteSheetName, level, mapSpecificPlayerValues);
        inventory = new ArrayList<Item>();
    }

    @Override
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    @Override
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    @Override
    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setWater(int water) {
        this.water = water;
    }

    @Override
    public int getWater() {
        return water;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void setSpice(int spice) {
        this.spice = spice;
    }

    @Override
    public int getSpice() {
        return spice;
    }

    @Override
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
