package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.DuneRPG;
import com.binitshah.dunerpg.characters.HarkonnenEasy;
import com.binitshah.dunerpg.characters.HarkonnenHard;
import com.binitshah.dunerpg.characters.HarkonnenMedium;
import com.binitshah.dunerpg.characters.NPC;
import com.binitshah.dunerpg.characters.PaulAtreides;
import com.binitshah.dunerpg.characters.Piter;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/14/17.
 *
 * The caves that Paul uses to escape from the harkonnen.
 */

public class BackTunnels extends Level {

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

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
    private ArrayList<NPC> enemies;

    public BackTunnels(DuneRPG game) {
        super(mapName, BackTunnels.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT, splitLayer);
        this.game = game;
        this.paulAtreides = new PaulAtreides(this, mapSpecificPlayerValues);

        //load npcs
        enemies = new ArrayList<NPC>();
        ArrayList<RectangleMapObject> boundaries = new ArrayList<RectangleMapObject>();
        for (RectangleMapObject enemyOrBoundary : getTiledMap().getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            String objectClass = (String) enemyOrBoundary.getProperties().get("class");
            if (objectClass != null) {
                if (objectClass.equals("boundary")) {
                    boundaries.add(enemyOrBoundary);
                } else {
                    if (objectClass.equals("Harkonnen1")) {
                        enemies.add(new HarkonnenEasy(enemyOrBoundary.getName(), this, new float[]{enemyOrBoundary.getRectangle().getX(), enemyOrBoundary.getRectangle().getY(), 44, 44}, enemyOrBoundary.getRectangle()));
                    } else if (objectClass.equals("Harkonnen2")) {
                        enemies.add(new HarkonnenMedium(enemyOrBoundary.getName(), this, new float[]{enemyOrBoundary.getRectangle().getX(), enemyOrBoundary.getRectangle().getY(), 44, 44}, enemyOrBoundary.getRectangle()));
                    } else if (objectClass.equals("Harkonnen3")) {
                        enemies.add(new HarkonnenHard(enemyOrBoundary.getName(), this, new float[]{enemyOrBoundary.getRectangle().getX(), enemyOrBoundary.getRectangle().getY(), 44, 44}, enemyOrBoundary.getRectangle()));
                    } else if (objectClass.equals("Piter")) {
                        enemies.add(new Piter(enemyOrBoundary.getName(), this, new float[]{enemyOrBoundary.getRectangle().getX(), enemyOrBoundary.getRectangle().getY(), 44, 44}, enemyOrBoundary.getRectangle()));
                    } else {
                        Gdx.app.debug(TAG, "Unable to find objec through class:: classid: " + objectClass + " | name: " + enemyOrBoundary.getName() + " | bounds: " + enemyOrBoundary.getRectangle().toString());
                    }
                }
            } else {
                Gdx.app.debug(TAG, "class id was null:: name: " + enemyOrBoundary.getName() + " | bounds: " + enemyOrBoundary.getRectangle().toString());
            }
        }

        //assign boundaries to npcs
        for (RectangleMapObject boundary : boundaries) {
            try {
                boolean assigned = false;
                String enemyName = boundary.getName().substring(0, boundary.getName().indexOf("_boundary"));
                for (NPC enemy : enemies) {
                    if (enemy.getId().equals(enemyName)) {
                        assigned = true;
                        enemy.setActivationBoundary(boundary.getRectangle());
                    }
                }
                if (!assigned) {
                    Gdx.app.debug(TAG, "Unable to assign boundary:: name: " + boundary.getName() + " | bounds: " + boundary.getRectangle().toString());
                }
            } catch (Exception e) {
                if (boundary.getName() != null) {
                    Gdx.app.debug(TAG, "Some Null or String index out of bounds Exception. Probably a object missing a name and we called substing on it. Find the culprit using this info:: name: " + boundary.getName() + " | boundary: " + boundary.getRectangle().toString());
                } else {
                    Gdx.app.debug(TAG, "Some Null or String index out of bounds Exception. Probably a object missing a name and we called substing on it. Find the culprit using this info:: boundary: " + boundary.getRectangle().toString());
                }
            }
        }

        //disable all npcs without bounds, something went wrong
        for (NPC enemy : enemies) {
            if (enemy.getActivationBoundary() == null) {
                enemy.setEnabled(false);
                Gdx.app.debug(TAG, "enemy has no activitation boundary, is being disabled:: id: " + enemy.getId());
            }
        }

    }

    @Override
    public void drawLevelAssets(float delta) {
        paulAtreides.draw(delta);

        for (NPC enemy : enemies) {
            if (enemy.isEnabled()) {
                enemy.draw(delta);
            }
        }
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
            return 5;
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
