package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/17/17.
 */

public class SmugglerLeader extends NPC {

    //Information
    private static String spriteSheetName = "SmugglerLeader.png";
    private int expChange = 85;

    public SmugglerLeader(String id, Level level, Rectangle personalBoundary) {
        super(id, spriteSheetName, level, personalBoundary);
    }

    @Override
    public void updatePlayerWon(Player player) {
        player.setExperience(player.getExperience() + expChange);
    }

    @Override
    public void updatePlayerLose(Player player) {
        player.setExperience(player.getExperience() - expChange);
    }
}
