package com.binitshah.dunerpg.characters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binitshah.dunerpg.Controls;
import com.binitshah.dunerpg.levels.FirstFremanCave;
import com.binitshah.dunerpg.levels.Level;

import java.util.Arrays;

import static sun.audio.AudioPlayer.player;

/**
 * Created by binitshah on 4/14/17.
 * A generic player.
 *
 * todo:
 *  - check collisions
 *  - handle user interaction
 */

public class Player {

    //general
    Level level;
    private final String TAG = "LOGDUNERPG"; //todo: remove

    //Motion
    private Animation<TextureRegion> walkForward, walkBackward, walkRight, walkLeft;
    private TextureRegion stillForward, stillBackward, stillRight, stillLeft;
    private Orientation directionLastOriented;
    private enum Orientation {
        FORWARD, BACKWARD, LEFT, RIGHT;
    }

    //Collisions
    private String blockedKey = "blocked";
    private MapObjects collisionObjects;

    //Rendering
    private OrthographicCamera playerCamera;
    private SpriteBatch spriteBatch;
    private float statetime = 0;
    private TextureRegion currentFrame;

    //Controls
    private Controls controls;


    public Player (String spriteSheetName, Level level) {
        //General
        this.level = level;
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

        Texture spriteSheetRedundant = new Texture(Gdx.files.internal("paulsprites.png"));
        stillForward = new TextureRegion(spriteSheetRedundant, 0, 400, 100, 100);
        stillBackward = new TextureRegion(spriteSheetRedundant, 100, 400, 100, 100);
        stillLeft = new TextureRegion(spriteSheetRedundant, 0, 500, 100, 100);
        stillRight = new TextureRegion(spriteSheetRedundant, 100, 500, 100, 100);

        //Rendering
        this.spriteBatch = this.level.getSpriteBatch();
        this.controls = this.level.getControls();
        this.directionLastOriented = Orientation.FORWARD;
        this.playerCamera = new OrthographicCamera(this.level.getWidth(), this.level.getHeight());
        this.playerCamera.position.set(this.level.getWidth()/2, this.level.getHeight()/2, 0);

        //Collision
        collisionObjects = this.level.getTiledMap().getLayers().get(this.level.getCollisionLayer()).getObjects();
    }

    public void draw(float delta) {
        spriteBatch.setProjectionMatrix(playerCamera.projection);
        statetime += delta;
        if (controls.getDirectionPressed() == Controls.Direction.NONE) {
            switch (directionLastOriented) {
                case FORWARD:
                    spriteBatch.draw(stillForward, -11, -11, 22, 22);
                    break;
                case BACKWARD:
                    spriteBatch.draw(stillBackward, -11, -11, 22, 22);
                    break;
                case LEFT:
                    spriteBatch.draw(stillLeft, -11, -11, 22, 22);
                    break;
                case RIGHT:
                    spriteBatch.draw(stillRight, -11, -11, 22, 22);
                    break;
            }
        } else {
            switch (controls.getDirectionPressed()) {
                case UP:
                    directionLastOriented = Orientation.BACKWARD;
                    currentFrame = walkBackward.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, -11, -11, 22, 22);
//                    for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                        Rectangle wallRec = rectangleObject.getRectangle();
//                        Rectangle playerRec = new Rectangle(level.getCamera().position.x, level.getCamera().position.y, 10, 10);
//                        if (!Intersector.overlaps(wallRec, playerRec)) {
//                            level.getCamera().translate(0, 2);
//
//                        }
//                    }
                    break;
                case DOWN:
                    directionLastOriented = Orientation.FORWARD;
                    currentFrame = walkForward.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, -11, -11, 22, 22);
//                    for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                        Rectangle wallRec = rectangleObject.getRectangle();
//                        Rectangle playerRec = new Rectangle(level.getCamera().position.x, level.getCamera().position.y, 10, 10);
//                        if (!Intersector.overlaps(wallRec, playerRec)) {
//                            level.getCamera().translate(0, -2);
//
//                        }
//                    }
                    break;
                case LEFT:
                    directionLastOriented = Orientation.LEFT;
                    currentFrame = walkLeft.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, -11, -11, 22, 22);
//                    for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                        Rectangle wallRec = rectangleObject.getRectangle();
//                        Rectangle playerRec = new Rectangle(level.getCamera().position.x, level.getCamera().position.y, 10, 10);
//                        if (!Intersector.overlaps(wallRec, playerRec)) {
//                            level.getCamera().translate(-3, 0);
//
//                        }
//                    }
                    break;
                case RIGHT:
                    directionLastOriented = Orientation.RIGHT;
                    currentFrame = walkRight.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, -11, -11, 22, 22);
//                    for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                        Rectangle wallRec = rectangleObject.getRectangle();
//                        Rectangle playerRec = new Rectangle(level.getCamera().position.x, level.getCamera().position.y, 10, 10);
//                        if (!Intersector.overlaps(wallRec, playerRec)) {
//                            level.getCamera().translate(3, 0);
//
//                        }
//                    }
                    break;
            }
        }
    }

    public void dispose() {
        spriteBatch.dispose();
    }

}
