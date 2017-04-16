package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class MediumWater extends Item {

    private static String description = "A normal amount of water.";

    public MediumWater(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{1, 1}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setWater(player.getWater() + 20);
    }

    @Override
    public String getClassId() {
        return "mediumwater";
    }
}
