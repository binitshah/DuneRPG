package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.levels.Level;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/17/17.
 */

public class Kynes extends NPC {

    //Information
    private static String spriteSheetName = "Kynes.png";
    private ArrayList<String> dialogue;
    private int expChange = 0;

    public Kynes(String id, Level level, Rectangle personalBoundary) {
        super(id, spriteSheetName, level, personalBoundary);
        dialogue = new ArrayList<String>();
        dialogue.add("Paul: This is one of the Imperial Ecological Testing stations my father wanted as advance bases.");
        dialogue.add("Kynes: You’ve recognized this place correctly. For what would you use such a place, Paul Atreides?");
        dialogue.add("Paul: To make this planet a fit place for humans.");
        dialogue.add("Paul: I am an embarrassment to the Emperor and to all who would divide Arrakis as their spoil.");
        dialogue.add("Kynes: You have a plan … this much is obvious, Sire. You aim for the throne?");
        dialogue.add("Paul: From the throne, I will make Arrakis a paradise. This the the coin I offer you in exchange for your support.");
        dialogue.add("Kynes: My loyalty cannot… Your enemies have found you. Quickly now, find Jessica and leave into the caves. You must find your way to the place where I’ve secreted a ‘thopter. I will try to escape another way.");
    }

    @Override
    public void updatePlayerWon(Player player) {
        player.setExperience(player.getExperience() + expChange);
    }

    @Override
    public void updatePlayerLose(Player player) {
        player.setExperience(player.getExperience() - expChange);
    }
}
