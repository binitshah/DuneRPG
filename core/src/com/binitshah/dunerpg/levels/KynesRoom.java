package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.DuneRPG;

/**
 * Created by binitshah on 4/14/17.
 * todo:
 *  - replace contructor with level specific values
 */

public abstract class KynesRoom extends Level {

    //State
    private DuneRPG game;

    //Information
    private static final float WIDTH = 480;
    private static final float HEIGHT = 320;
    private static String mapName = "Kynes Room.tmx";
    private static float[] clearColors = new float[]{0.09803921568f, 0.09411764705f, 0.09803921568f, 1};

    public KynesRoom(DuneRPG game) {
        super(mapName, KynesRoom.findSpawnPoint(mapName), clearColors, WIDTH, HEIGHT);
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
