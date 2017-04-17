package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class Piter extends NPC {

    //Information
    private static String spriteSheetName = "Piter.png";
    private int expChange = 60;

    public Piter(String id, Level level, Rectangle personalBoundary) {
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
