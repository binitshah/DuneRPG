package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class SmallSpice extends Item {

    private static String description = "A small amount of spice.";

    public SmallSpice(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{0, 0}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setSpice(player.getSpice() + 20);
    }

    @Override
    public String getClassId() {
        return "smallspice";
    }
}