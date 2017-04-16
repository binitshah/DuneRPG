package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class HarkonnenEasy extends NPC {

    //Information
    private static String spriteSheetName = "Harkonnen1.png";

    public HarkonnenEasy(String id, Level level, Rectangle personalBoundary) {
        super(id, spriteSheetName, level, personalBoundary);
    }
}
