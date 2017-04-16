package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class HarkonnenMedium extends NPC {

    //Information
    private static String spriteSheetName = "Harkonnen2.png";

    public HarkonnenMedium(String id, Level level, float[] npcValues, Rectangle personalBoundary) {
        super(id, spriteSheetName, level, npcValues, personalBoundary);
    }

}
