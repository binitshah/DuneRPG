package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.binitshah.dunerpg.Controls;
import com.binitshah.dunerpg.levels.Level;

import java.util.Arrays;

/**
 * Created by binitshah on 4/16/17.
 *
 * Generic Non-Person Character
 */

public class NPC {

    //general
    private Level level;
    private String id;
    private boolean isEnabled = true;
    private boolean isEngaged = false;
    private Rectangle activationBoundary;
    private Rectangle personalBoundary;
    private final String TAG = "LOGDUNERPG"; //todo: remove

    //Motion
    private Animation<TextureRegion> walkForward, walkBackward, walkRight, walkLeft;
    private TextureRegion stillForward, stillBackward, stillRight, stillLeft;
    private NPC.Orientation directionLastOriented;
    private enum Orientation {
        FORWARD, BACKWARD, LEFT, RIGHT;
    }

    //Rendering
    private OrthographicCamera npcCamera;
    private SpriteBatch spriteBatch;
    private float statetime = 0;
    private float[] npcVals;


    public NPC (String id, String spriteSheetName, Level level, float[] npcVals, Rectangle personalBoundary) {
        //General
        this.id = id;
        this.level = level;
        this.personalBoundary = personalBoundary;
        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove

        //Motion
        int cols = 2, rows = 6;
        Texture spriteSheet = new Texture(Gdx.files.internal(spriteSheetName));
        TextureRegion[][] textureRegion = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols, spriteSheet.getHeight() / rows);

        TextureRegion[] frames = new TextureRegion[cols];
        for (int j = 0; j < cols; j++) {
            frames[j] = textureRegion[0][j];
        }
        walkForward = new Animation<TextureRegion>(0.166f, frames);

        frames = new TextureRegion[cols];
        for (int j = 0; j < cols; j++) {
            frames[j] = textureRegion[1][j];
        }
        walkBackward = new Animation<TextureRegion>(0.166f, frames);

        frames = new TextureRegion[cols];
        for (int j = 0; j < cols; j++) {
            frames[j] = textureRegion[2][j];
        }
        walkRight = new Animation<TextureRegion>(0.166f, frames);

        frames = new TextureRegion[cols];
        for (int j = 0; j < cols; j++) {
            frames[j] = textureRegion[3][j];
        }
        walkLeft = new Animation<TextureRegion>(0.166f, frames);

        Texture spriteSheetRedundant = new Texture(Gdx.files.internal(spriteSheetName));
        stillForward = new TextureRegion(spriteSheetRedundant, 0, 128, 32, 32);
        stillBackward = new TextureRegion(spriteSheetRedundant, 32, 128, 32, 32);
        stillLeft = new TextureRegion(spriteSheetRedundant, 0, 160, 32, 32);
        stillRight = new TextureRegion(spriteSheetRedundant, 32, 160, 32, 32);

        //Rendering
        this.spriteBatch = this.level.getSpriteBatch();
        this.directionLastOriented = NPC.Orientation.FORWARD;
        this.npcCamera = new OrthographicCamera(this.level.getWidth(), this.level.getHeight());
        this.npcCamera.position.set(this.level.getWidth()/2, this.level.getHeight()/2, 0);
        this.npcVals = npcVals;
    }

    public void draw(float delta) {
        //Gdx.app.debug(TAG, "Drawn at: " + Arrays.toString(npcVals));
        spriteBatch.setProjectionMatrix(level.getCamera().combined);
        statetime += delta;
        if (isEnabled) {
            if (isEngaged) {
                drawNPCEngaged();
            } else {
                drawNPCIdle();
            }
        }
    }

    private void drawNPCIdle() {
        switch (directionLastOriented) {
            case FORWARD:
                spriteBatch.draw(stillForward, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
                break;
            case BACKWARD:
                spriteBatch.draw(stillBackward, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
                break;
            case LEFT:
                spriteBatch.draw(stillLeft, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
                break;
            case RIGHT:
                spriteBatch.draw(stillRight, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
                break;
        }
    }

    private void drawNPCEngaged() {

    }

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isEngaged() {
        return isEngaged;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setEngaged(boolean engaged) {
        isEngaged = engaged;
    }

    public Rectangle getActivationBoundary() {
        return activationBoundary;
    }

    public void setActivationBoundary(Rectangle activationBoundary) {
        this.activationBoundary = activationBoundary;
    }

    public Rectangle getPersonalBoundary() {
        return personalBoundary;
    }
}
