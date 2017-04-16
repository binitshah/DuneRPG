package com.binitshah.dunerpg.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 *
 * A generic item which will have specific characteristics defined by any item that extends this.
 */

public abstract class Item {

    //general
    private Level level;
    private boolean isEnabled = true;
    private Rectangle itemBoundary;
    private final String TAG = "LOGDUNERPG"; //todo: remove

    //information
    private String id;
    private String description;

    //rendering
    private OrthographicCamera itemCamera;
    private SpriteBatch spriteBatch;
    private float statetime = 0;
    private TextureRegion itemSprite;

    public Item(String id, String description, int[] tileCoordinate, Level level, Rectangle itemBoundary) {
        this.id = id;
        this.description = description;
        this.itemBoundary = itemBoundary;
        this.level = level;

        //rendering
        Texture spriteSheetRedundant = new Texture(Gdx.files.internal("items.png"));
        itemSprite = new TextureRegion(spriteSheetRedundant, 32*tileCoordinate[0], 32*tileCoordinate[1], 32, 32);

        this.spriteBatch = this.level.getSpriteBatch();
        this.itemCamera = new OrthographicCamera(this.level.getWidth(), this.level.getHeight());
        this.itemCamera.position.set(this.level.getWidth()/2, this.level.getHeight()/2, 0);
    }

    public void draw(float delta) {
        spriteBatch.setProjectionMatrix(level.getCamera().combined);
        statetime += delta;
        if (isEnabled) {
            spriteBatch.draw(itemSprite, itemBoundary.getX(), itemBoundary.getY(), itemBoundary.getWidth(), itemBoundary.getHeight());
        }
    }

    public abstract void changePlayer(Player player);
    public abstract String getClassId();

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Rectangle getItemBoundary() {
        return itemBoundary;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
