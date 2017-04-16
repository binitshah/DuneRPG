package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class LargeSpice extends Item {

    private static String description = "A large amount of spice.";

    public LargeSpice(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{2, 0}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setSpice(player.getSpice() + 60);
    }

    @Override
    public String getClassId() {
        return "largespice";
    }
}
