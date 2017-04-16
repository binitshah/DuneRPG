package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class Lasgun extends Item {

    private static String description = "The Lasgun is a continuous-wave laser projector weapon.";

    public Lasgun(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{2, 5}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.addToInventory(this);
    }

    @Override
    public String getClassId() {
        return "lasgun";
    }
}
