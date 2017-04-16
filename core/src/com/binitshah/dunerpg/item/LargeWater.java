package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class LargeWater extends Item {

    private static String description = "A large amount of water.";

    public LargeWater(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{2, 1}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setWater(player.getWater() + 30);
    }

    @Override
    public String getClassId() {
        return "largewater";
    }
}
