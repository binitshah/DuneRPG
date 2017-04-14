package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.DuneRPG;

/**
 * Created by binitshah on 4/14/17.
 * todo:
 *  - replace contructor with level specific values
 */

public abstract class BackTunnels extends Level {

    private static final float WIDTH = 480;
    private static final float HEIGHT = 320;
    private static String mapName = "Kynes Room.tmx";
    private static float[] clearColors = new float[]{1, 1, 1, 1};
    private DuneRPG game;

    public BackTunnels(DuneRPG game) {
        super(mapName, BackTunnels.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT);
        this.game = game;
    }

    @Override
    public void endLevel() {
        //implementation pending
    }

    private static Rectangle findSpawnPoint(String mapName) {
        //implementation pending.
        return new Rectangle();
    }
}
