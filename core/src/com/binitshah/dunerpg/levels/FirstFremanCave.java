package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.DuneRPG;
import com.binitshah.dunerpg.characters.NPC;
import com.binitshah.dunerpg.characters.PaulAtreides;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/14/17.
 *
 * The first freman cave level in which Paul will battle jamis.
 */

public class FirstFremanCave extends Level {

    //State
    private DuneRPG game;

    //Information
    private static final float WIDTH = 480;
    private static final float HEIGHT = 320;
    private static String mapName = "cave.tmx";
    private static float[] clearColors = new float[]{0.09803921568f, 0.09411764705f, 0.09803921568f, 1};
    private static int splitLayer = 3; //the layer at which the sprites are drawn before the layer so that the layers appear on top the sprites.
    private float[] mapSpecificPlayerValues = {-11, -11, 22, 22, 2, 3};

    //Objects
    private PaulAtreides paulAtreides;
    private ArrayList<NPC> enemies;

    public FirstFremanCave(DuneRPG game) {
        super(mapName, FirstFremanCave.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT, splitLayer);
        this.game = game;
        this.paulAtreides = new PaulAtreides(this, mapSpecificPlayerValues);
    }

    @Override
    public void drawLevelAssets(float delta) {
        paulAtreides.draw(delta);
    }

    @Override
    public void endLevel() {
        BackTunnels testingNextLevel = new BackTunnels(game);
        game.setScreen(testingNextLevel);
    }

    @Override
    public void disposeAssets() {
        paulAtreides.dispose();
    }

    @Override
    public int getLayer(String layerName) {
        if (layerName.equals("other")) {
            return 5;
        } else if (layerName.equals("walls")) {
            return 6;
        }
        return -1;
    }

    @Override
    public ArrayList<NPC> getEnemies() {
        return enemies;
    }

    private static Vector2 findSpawnPoint(String mapName) {
        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            TiledMap tiledMap = tmxMapLoader.load(mapName);
            RectangleMapObject rectangleMapObject = ((RectangleMapObject) tiledMap.getLayers().get(5).getObjects().get("Spawn_Point"));
            return new Vector2(rectangleMapObject.getRectangle().getX() + rectangleMapObject.getRectangle().getWidth()/2, rectangleMapObject.getRectangle().getY() + rectangleMapObject.getRectangle().getHeight()/2);
        } catch (Exception e) {
            return null;
        }
    }
}
