package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/17/17.
 */

public class FremenEasy extends NPC {

    //Information
    private static String spriteSheetName = "Fremen1.png";

    public FremenEasy(String id, Level level, Rectangle personalBoundary) {
        super(id, spriteSheetName, level, personalBoundary);
    }
}
