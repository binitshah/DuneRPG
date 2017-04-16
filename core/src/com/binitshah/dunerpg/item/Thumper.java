package com.binitshah.dunerpg.item;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 *
 * This item is pretty crazy, it will attract sandworms and destroy them.
 */

public class Thumper extends Item {

    private static String description = "The Thumper grants the ability to attract sand worms to attack the enemy.";

    public Thumper(String id, Level level, Rectangle itemBoundary) {
        super(id, description, new int[]{0, 5}, level, itemBoundary);
    }

    @Override
    public void changePlayer(Player player) {
        player.addToInventory(this);
    }

    @Override
    public String getClassId() {
        return "thumper";
    }
}
