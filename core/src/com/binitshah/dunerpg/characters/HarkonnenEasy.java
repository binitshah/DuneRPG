package com.binitshah.dunerpg.characters;

import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class HarkonnenEasy extends NPC {

    //Information
    private static String spriteSheetName = "Harkonnen1.png";

    public HarkonnenEasy(Level level, float[] npcValues) {
        super(spriteSheetName, level, npcValues);
    }
}
