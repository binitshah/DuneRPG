package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class OrangeBiblePage extends Item {

    private static String description = "The Orange Bible is a religious text describing the Butlerian Jihad.";

    public OrangeBiblePage(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{1, 5}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.addToInventory(this);
    }

    @Override
    public String getClassId() {
        return "orangebible";
    }
}
