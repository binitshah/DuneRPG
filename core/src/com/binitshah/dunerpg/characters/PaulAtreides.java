package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.binitshah.dunerpg.Controls;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/14/17.
 *
 * Paul Atreides (Atriedes?) (lol jk i know the spelling) (i didn't google it) is our main character in this game.
 */

public class PaulAtreides extends Player {

    //Information
    private static String spriteSheetName = "paulsprites.png";

    public PaulAtreides(Level level) {
        super(spriteSheetName, level);
    }
}
