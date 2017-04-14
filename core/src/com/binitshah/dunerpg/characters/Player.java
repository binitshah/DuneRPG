package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binitshah.dunerpg.Controls;

import java.util.Arrays;

/**
 * Created by binitshah on 4/14/17.
 * A generic player.
 *
 * todo:
 *  - check collisions
 *  - render player
 *  - handle user interaction
 */

public class Player {

    //Motion
    private Animation<TextureRegion> walkForward, walkBackward, walkRight, walkLeft;
    private TextureRegion stillForward, stillBackward, stillRight, stillLeft;
    private Orientation directionLastOriented;
    private enum Orientation {
        FORWARD, BACKWARD, LEFT, RIGHT;
    }

    //Collisions
    private String blockedKey = "blocked";

    //Rendering
    private SpriteBatch spriteBatch;
    private float statetime = 0;
    private TextureRegion currentFrame;
    private float width;
    private float height;

    //Controls
    private Controls controls;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    public Player (String spriteSheetName, SpriteBatch spriteBatch, Controls controls, float width, float height) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove
        
        this.width = width;
        this.height = height;

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
        Gdx.app.debug(TAG, "walk left: " + Arrays.toString(frames));

        Texture spriteSheetRedundant = new Texture(Gdx.files.internal("paulsprites.png"));
        stillForward = new TextureRegion(spriteSheetRedundant, 0, 400, 100, 100);
        stillBackward = new TextureRegion(spriteSheetRedundant, 100, 400, 100, 100);
        stillLeft = new TextureRegion(spriteSheetRedundant, 0, 500, 100, 100);
        stillRight = new TextureRegion(spriteSheetRedundant, 100, 500, 100, 100);

        this.spriteBatch = spriteBatch;
        this.controls = controls;
        this.directionLastOriented = Orientation.FORWARD;
    }

    public void draw(float delta) {
        statetime += delta;
        if (controls.getDirectionPressed() == Controls.Direction.NONE) {
            switch (directionLastOriented) {
                case FORWARD:
                    spriteBatch.draw(stillForward, width*0.65f, height*0.65f, 32, 32);
                    break;
                case BACKWARD:
                    spriteBatch.draw(stillBackward, width*0.65f, height*0.65f, 32, 32);
                    break;
                case LEFT:
                    spriteBatch.draw(stillLeft, width*0.65f, height*0.65f, 32, 32);
                    break;
                case RIGHT:
                    spriteBatch.draw(stillRight, width*0.65f, height*0.65f, 32, 32);
                    break;
            }
        } else {
            switch (controls.getDirectionPressed()) {
                case UP:
                    currentFrame = walkBackward.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, width*0.65f, height*0.65f, 32, 32);
                    directionLastOriented = Orientation.BACKWARD;
                    break;
                case DOWN:
                    currentFrame = walkForward.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, width*0.65f, height*0.65f, 32, 32);
                    directionLastOriented = Orientation.FORWARD;
                    break;
                case LEFT:
                    currentFrame = walkLeft.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, width*0.65f, height*0.65f, 32, 32);
                    directionLastOriented = Orientation.LEFT;
                    break;
                case RIGHT:
                    currentFrame = walkRight.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, width*0.65f, height*0.65f, 32, 32);
                    directionLastOriented = Orientation.RIGHT;
                    break;
            }
        }
    }

    public void dispose() {
        spriteBatch.dispose();
    }

}
