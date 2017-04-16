package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class MediumFood extends Item {

    private static String description = "A normal amount of spice laced food.";

    public MediumFood(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{1, 2}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.setHealth(player.getHealth() + 20);
        player.setSpice(player.getSpice() + 10);
    }

    @Override
    public String getClassId() {
        return "mediumfood";
    }
}
