package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.DuneRPG;
import com.binitshah.dunerpg.characters.FremenEasy;
import com.binitshah.dunerpg.characters.FremenHard;
import com.binitshah.dunerpg.characters.FremenHardest;
import com.binitshah.dunerpg.characters.FremenMedium;
import com.binitshah.dunerpg.characters.NPC;
import com.binitshah.dunerpg.characters.PaulAtreides;
import com.binitshah.dunerpg.characters.SmugglerEasy;
import com.binitshah.dunerpg.characters.SmugglerHard;
import com.binitshah.dunerpg.characters.SmugglerMedium;
import com.binitshah.dunerpg.characters.Stilgar;
import com.binitshah.dunerpg.item.Item;
import com.binitshah.dunerpg.item.LargeFood;
import com.binitshah.dunerpg.item.LargeSpice;
import com.binitshah.dunerpg.item.LargeWater;
import com.binitshah.dunerpg.item.Lasgun;
import com.binitshah.dunerpg.item.MediumFood;
import com.binitshah.dunerpg.item.MediumSpice;
import com.binitshah.dunerpg.item.MediumWater;
import com.binitshah.dunerpg.item.OrangeBiblePage;
import com.binitshah.dunerpg.item.SmallFood;
import com.binitshah.dunerpg.item.SmallSpice;
import com.binitshah.dunerpg.item.SmallWater;
import com.binitshah.dunerpg.item.StillSuit;
import com.binitshah.dunerpg.item.Thumper;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/17/17.
 */

public class SecondFremenCave extends Level {

    //State
    private DuneRPG game;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    //Information
    private static final float WIDTH = 960;
    private static final float HEIGHT = 640;
    private static String mapName = "cavetwo.tmx";
    private static float[] clearColors = new float[]{0, 0, 0, 1};
    private static int splitLayer = 100; //the layer at which the sprites are drawn before the layer so that the layers appear on top the sprites.
    private static float[] mapSpecificPlayerValues = {-22, -22, 44, 44, 4, 6};

    //Objects
    private PaulAtreides paulAtreides;
    private ArrayList<NPC> npcs;
    private ArrayList<Item> items;

    public SecondFremenCave(DuneRPG game) {
        super(mapName, SecondFremenCave.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT, splitLayer);
        this.game = game;
        this.paulAtreides = new PaulAtreides(this, mapSpecificPlayerValues);

        //load npcs
        npcs = new ArrayList<NPC>();
        for (RectangleMapObject npcOrBound : getTiledMap().getLayers().get(getLayer("npcs")).getObjects().getByType(RectangleMapObject.class)) {
            String objectClass = (String) npcOrBound.getProperties().get("class");
            if (objectClass != null) {
                if (objectClass.equals("Fremen1")) {
                    Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 44, 44);
                    npcs.add(new FremenEasy(npcOrBound.getName(), this, personalBounds));
                } else if (objectClass.equals("Fremen2")) {
                    Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 44, 44);
                    npcs.add(new FremenMedium(npcOrBound.getName(), this, personalBounds));
                } else if (objectClass.equals("Fremen3")) {
                    Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 44, 44);
                    npcs.add(new FremenHard(npcOrBound.getName(), this, personalBounds));
                } else if (objectClass.equals("Fremen4")) {
                    Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 44, 44);
                    npcs.add(new FremenHardest(npcOrBound.getName(), this, personalBounds));
                } else if (objectClass.equals("Stilgar")) {
                    Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 44, 44);
                    npcs.add(new Stilgar(npcOrBound.getName(), this, personalBounds));
                } else {
                    Gdx.app.debug(TAG, "Unable to find objec through class:: classid: " + objectClass + " | name: " + npcOrBound.getName() + " | bounds: " + npcOrBound.getRectangle().toString());
                }
            } else {
                Gdx.app.debug(TAG, "class id for enemy was null:: name: " + npcOrBound.getName() + " | bounds: " + npcOrBound.getRectangle().toString());
            }
        }

        //assign boundaries to npcs
        for (RectangleMapObject boundary : getTiledMap().getLayers().get(getLayer("boundary")).getObjects().getByType(RectangleMapObject.class)) {
            try {
                boolean assigned = false;
                String npcName = boundary.getName().substring(0, boundary.getName().indexOf("_boundary"));
                for (NPC npc : npcs) {
                    if (npc.getId().equals(npcName)) {
                        assigned = true;
                        npc.setActivationBoundary(boundary.getRectangle());
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
        for (NPC npc : npcs) {
            if (npc.getActivationBoundary() == null) {
                npc.setEnabled(false);
                Gdx.app.debug(TAG, "enemy has no activitation boundary, is being disabled:: id: " + npc.getId());
            }
        }

        //load items
        items = new ArrayList<Item>();
        for (RectangleMapObject item : getTiledMap().getLayers().get(getLayer("items")).getObjects().getByType(RectangleMapObject.class)) {
            String objectClass = (String) item.getProperties().get("class");
            if (objectClass != null) {
                if (objectClass.equals("thumper")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new Thumper(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("stillsuit")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new StillSuit(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("smallspice")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new SmallSpice(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("mediumspice")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new MediumSpice(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("largespice")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new LargeSpice(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("smallwater")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new SmallWater(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("mediumwater")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new MediumWater(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("largewater")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new LargeWater(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("smallfood")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new SmallFood(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("mediumfood")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new MediumFood(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("largefood")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new LargeFood(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("lasgun")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new Lasgun(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("orangebible")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 44, 44);
                    items.add(new OrangeBiblePage(item.getName(), this, itemBoundary));
                } else {
                    Gdx.app.debug(TAG, "Unable to find item through class:: classid: " + objectClass + " | name: " + item.getName() + " | bounds: " + item.getRectangle().toString());
                }
            } else {
                Gdx.app.debug(TAG, "class id for item was null:: name: " + item.getName() + " | bounds: " + item.getRectangle().toString());
            }
        }
    }

    @Override
    public void drawLevelAssets(float delta) {
        paulAtreides.draw(delta);

        for (NPC npc : npcs) {
            if (npc.isEnabled()) {
                npc.draw(delta);
            }
        }

        for (Item item : items) {
            if (item.isEnabled()) {
                item.draw(delta);
            }
        }
    }

    @Override
    public void endLevel() {
        //Not sure yet..
    }

    @Override
    public void disposeAssets() {
        paulAtreides.dispose();
    }

    @Override
    public int getLayer(String layerName) {
        if (layerName.equals("npcs")) {
            return 7;
        } else if (layerName.equals("walls")) {
            return 3;
        } else if (layerName.equals("items")) {
            return 6;
        } else if (layerName.equals("other")) {
            return 9;
        } else if (layerName.equals("boundary")) {
            return 8;
        }
        return -1;
    }

    @Override
    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    @Override
    public ArrayList<Item> getItems() {
        return items;
    }

    private static Vector2 findSpawnPoint(String mapName) {
        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            TiledMap tiledMap = tmxMapLoader.load(mapName);
            RectangleMapObject rectangleMapObject = ((RectangleMapObject) tiledMap.getLayers().get(9).getObjects().get("Spawn_Point"));
            return new Vector2(rectangleMapObject.getRectangle().getX() + rectangleMapObject.getRectangle().getWidth()/2, rectangleMapObject.getRectangle().getY() + rectangleMapObject.getRectangle().getHeight()/2);
        } catch (Exception e) {
            return null;
        }
    }
}
