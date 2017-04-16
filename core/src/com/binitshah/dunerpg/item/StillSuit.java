package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 *
 * Is pretty awesome. Does a thing to the water thing. :)
 */

public class StillSuit extends Item {

    private static String description = "The StillSuit enables greater conservation of water.";

    public StillSuit(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{2, 4}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.addToInventory(this);
    }

    @Override
    public String getClassId() {
        return "stillsuit";
    }
}
