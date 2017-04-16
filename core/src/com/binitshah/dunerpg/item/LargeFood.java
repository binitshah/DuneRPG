package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class LargeFood extends Item {

    private static String description = "A large amount of spice laced food.";

    public LargeFood(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{2, 2}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setHealth(player.getHealth() + 30);
        player.setSpice(player.getSpice() + 15);
    }

    @Override
    public String getClassId() {
        return "largefood";
    }
}
