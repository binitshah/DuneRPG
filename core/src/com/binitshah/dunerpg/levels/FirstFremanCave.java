package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.DuneRPG;
import com.binitshah.dunerpg.characters.PaulAtreides;

/**
 * Created by binitshah on 4/14/17.
 * todo:
 *  - replace contructor with level specific values
 */

public class FirstFremanCave extends Level {

    //State
    private DuneRPG game;

    //Information
    private static final float WIDTH = 480;
    private static final float HEIGHT = 320;
    private static String mapName = "cave.tmx";
    private static float[] clearColors = new float[]{0.09803921568f, 0.09411764705f, 0.09803921568f, 1};

    //Objects
    PaulAtreides paulAtreides;

    public FirstFremanCave(DuneRPG game) {
        super(mapName, FirstFremanCave.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT);
        this.game = game;
        paulAtreides = new PaulAtreides(getSpriteBatch(), getControls(), WIDTH, HEIGHT);
    }

    @Override
    public void drawLevelAssets(float delta) {
        paulAtreides.draw(delta);
    }

    @Override
    public void endLevel() {
//        KynesRoom testNextLevel = new KynesRoom(game);
//        game.setScreen(testNextLevel);
        //implementation pending
    }

    @Override
    public void disposeAssets() {
        paulAtreides.dispose();
    }

    private static Rectangle findSpawnPoint(String mapName) {
        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            TiledMap tiledMap = tmxMapLoader.load(mapName);
            RectangleMapObject rectangleMapObject = ((RectangleMapObject) tiledMap.getLayers().get(5).getObjects().get("Spawn Point"));
            return rectangleMapObject.getRectangle();
        } catch (Exception e) {
            return null;
        }
    }
}
