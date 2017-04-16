package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class SmallFood extends Item {

    private static String description = "A small amount of spice laced food.";

    public SmallFood(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{0, 2}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setHealth(player.getHealth() + 10);
        player.setSpice(player.getSpice() + 5);
    }

    @Override
    public String getClassId() {
        return "smallfood";
    }
}
