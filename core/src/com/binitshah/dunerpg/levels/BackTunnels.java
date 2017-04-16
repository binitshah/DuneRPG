package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.DuneRPG;
import com.binitshah.dunerpg.characters.HarkonnenEasy;
import com.binitshah.dunerpg.characters.PaulAtreides;

/**
 * Created by binitshah on 4/14/17.
 *
 * The caves that Paul uses to escape from the harkonnen.
 */

public class BackTunnels extends Level {

    //State
    private DuneRPG game;

    //Information
    private static final float WIDTH = 960;
    private static final float HEIGHT = 640;
    private static String mapName = "tunnels.tmx";
    private static float[] clearColors = new float[]{0, 0, 0, 1};
    private static int splitLayer = 100; //the layer at which the sprites are drawn before the layer so that the layers appear on top the sprites.
    private static float[] mapSpecificPlayerValues = {-22, -22, 44, 44, 4, 6};

    //Objects
    private PaulAtreides paulAtreides;
    private HarkonnenEasy enemyOne;

    public BackTunnels(DuneRPG game) {
        super(mapName, BackTunnels.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT, splitLayer);
        this.game = game;
        this.paulAtreides = new PaulAtreides(this, mapSpecificPlayerValues);
        RectangleMapObject enemyOne = ((RectangleMapObject) getTiledMap().getLayers().get(2).getObjects().get("enemy one"));
        this.enemyOne = new HarkonnenEasy(this, new float[]{enemyOne.getRectangle().getX(),enemyOne.getRectangle().getY(), 44, 44});
    }

    @Override
    public void drawLevelAssets(float delta) {
        paulAtreides.draw(delta);
        enemyOne.draw(delta);
    }

    @Override
    public void endLevel() {
        //implementation pending
    }

    @Override
    public void disposeAssets() {
        paulAtreides.dispose();
    }

    @Override
    public int getLayer(String layerName) {
        if (layerName.equals("collision")) {
            return 3;
        } else if (layerName.equals("other")) {
            return 4;
        }
        return -1;
    }

    private static Vector2 findSpawnPoint(String mapName) {
        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            TiledMap tiledMap = tmxMapLoader.load(mapName);
            RectangleMapObject rectangleMapObject = ((RectangleMapObject) tiledMap.getLayers().get(4).getObjects().get("Spawn_Point"));
            return new Vector2(rectangleMapObject.getRectangle().getX() + rectangleMapObject.getRectangle().getWidth()/2, rectangleMapObject.getRectangle().getY() + rectangleMapObject.getRectangle().getHeight()/2);
        } catch (Exception e) {
            return null;
        }
    }
}
