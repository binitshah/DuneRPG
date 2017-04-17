package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/17/17.
 */

public class SmugglerHard extends NPC {

    //Information
    private static String spriteSheetName = "Smuggler3.png";
    private int expChange = 45;

    public SmugglerHard(String id, Level level, Rectangle personalBoundary) {
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
