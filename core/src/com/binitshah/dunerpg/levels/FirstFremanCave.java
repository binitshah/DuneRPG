package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.DuneRPG;
import com.binitshah.dunerpg.characters.FremenEasy;
import com.binitshah.dunerpg.characters.FremenHard;
import com.binitshah.dunerpg.characters.FremenHardest;
import com.binitshah.dunerpg.characters.FremenMedium;
import com.binitshah.dunerpg.characters.HarkonnenEasy;
import com.binitshah.dunerpg.characters.HarkonnenHard;
import com.binitshah.dunerpg.characters.HarkonnenMedium;
import com.binitshah.dunerpg.characters.Jamis;
import com.binitshah.dunerpg.characters.NPC;
import com.binitshah.dunerpg.characters.PaulAtreides;
import com.binitshah.dunerpg.characters.Piter;
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
 * Created by binitshah on 4/14/17.
 *
 * The first freman cave level in which Paul will battle jamis.
 */

public class FirstFremanCave extends Level {

    //State
    private DuneRPG game;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    //Information
    private static final float WIDTH = 480;
    private static final float HEIGHT = 320;
    private static String mapName = "cave.tmx";
    private static float[] clearColors = new float[]{0.09803921568f, 0.09411764705f, 0.09803921568f, 1};
    private static int splitLayer = 3; //the layer at which the sprites are drawn before the layer so that the layers appear on top the sprites.
    private float[] mapSpecificPlayerValues = {-11, -11, 22, 22, 2, 3};

    //Objects
    private PaulAtreides paulAtreides;
    private ArrayList<NPC> npcs;
    private ArrayList<Item> items;

    public FirstFremanCave(DuneRPG game) {
        super(mapName, FirstFremanCave.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT, splitLayer);
        this.game = game;
        this.paulAtreides = new PaulAtreides(this, mapSpecificPlayerValues);

        //load npcs
        npcs = new ArrayList<NPC>();
        ArrayList<RectangleMapObject> boundaries = new ArrayList<RectangleMapObject>();
        for (RectangleMapObject npcOrBound : getTiledMap().getLayers().get(getLayer("npcs")).getObjects().getByType(RectangleMapObject.class)) {
            String objectClass = (String) npcOrBound.getProperties().get("class");
            if (objectClass != null) {
                if (objectClass.equals("boundary")) {
                    boundaries.add(npcOrBound);
                } else {
                    if (objectClass.equals("Fremen1")) {
                        Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 22, 22);
                        npcs.add(new FremenEasy(npcOrBound.getName(), this, personalBounds));
                    } else if (objectClass.equals("Fremen2")) {
                        Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 22, 22);
                        npcs.add(new FremenMedium(npcOrBound.getName(), this, personalBounds));
                    } else if (objectClass.equals("Fremen3")) {
                        Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 22, 22);
                        npcs.add(new FremenHard(npcOrBound.getName(), this, personalBounds));
                    } else if (objectClass.equals("Fremen4")) {
                        Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 22, 22);
                        npcs.add(new FremenHardest(npcOrBound.getName(), this, personalBounds));
                    } else if (objectClass.equals("Jamis")) {
                        Rectangle personalBounds = new Rectangle(npcOrBound.getRectangle().getX(), npcOrBound.getRectangle().getY(), 22, 22);
                        npcs.add(new Jamis(npcOrBound.getName(), this, personalBounds));
                    } else {
                        Gdx.app.debug(TAG, "Unable to find objec through class:: classid: " + objectClass + " | name: " + npcOrBound.getName() + " | bounds: " + npcOrBound.getRectangle().toString());
                    }
                }
            } else {
                Gdx.app.debug(TAG, "class id for enemy was null:: name: " + npcOrBound.getName() + " | bounds: " + npcOrBound.getRectangle().toString());
            }
        }

        //assign boundaries to npcs
        for (RectangleMapObject boundary : boundaries) {
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
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new Thumper(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("stillsuit")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new StillSuit(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("smallspice")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new SmallSpice(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("mediumspice")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new MediumSpice(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("largespice")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new LargeSpice(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("smallwater")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new SmallWater(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("mediumwater")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new MediumWater(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("largewater")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new LargeWater(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("smallfood")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new SmallFood(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("mediumfood")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new MediumFood(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("largefood")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new LargeFood(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("lasgun")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
                    items.add(new Lasgun(item.getName(), this, itemBoundary));
                } else if (objectClass.equals("orangebible")) {
                    Rectangle itemBoundary = new Rectangle(item.getRectangle().getX(), item.getRectangle().getY(), 22, 22);
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
        } else if (layerName.equals("npcs")) {
            return 7;
        } else if (layerName.equals("items")) {
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
            RectangleMapObject rectangleMapObject = ((RectangleMapObject) tiledMap.getLayers().get(5).getObjects().get("Spawn_Point"));
            return new Vector2(rectangleMapObject.getRectangle().getX() + rectangleMapObject.getRectangle().getWidth()/2, rectangleMapObject.getRectangle().getY() + rectangleMapObject.getRectangle().getHeight()/2);
        } catch (Exception e) {
            return null;
        }
    }
}
