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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binitshah.dunerpg.Controls;
import com.binitshah.dunerpg.item.Item;
import com.binitshah.dunerpg.levels.Level;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/14/17.
 * A generic player.
 *
 */

public abstract class Player {

    //general
    private Level level;
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
    private MapObjects wallObjects;
    private float[] playerBounds = {-4, -10, 8, 1}; //[0] - xoffset, [1] - yoffset, [2] - width, [3] - height
    private MapObjects otherObjects;

    //Rendering
    private OrthographicCamera playerCamera;
    private SpriteBatch spriteBatch;
    private float statetime = 0;
    private float[] playerVals;

    //Controls
    private Controls controls;


    public Player (String spriteSheetName, Level level, float[] mapSpecificPlayerValues) {
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

        Texture spriteSheetRedundant = new Texture(Gdx.files.internal(spriteSheetName));
        stillForward = new TextureRegion(spriteSheetRedundant, 0, 400, 100, 100);
        stillBackward = new TextureRegion(spriteSheetRedundant, 100, 400, 100, 100);
        stillLeft = new TextureRegion(spriteSheetRedundant, 0, 500, 100, 100);
        stillRight = new TextureRegion(spriteSheetRedundant, 100, 500, 100, 100);

        //Controls
        this.controls = this.level.getControls();

        //Rendering
        this.spriteBatch = this.level.getSpriteBatch();
        this.directionLastOriented = Orientation.FORWARD;
        this.playerCamera = new OrthographicCamera(this.level.getWidth(), this.level.getHeight());
        this.playerCamera.position.set(this.level.getWidth()/2, this.level.getHeight()/2, 0);
        this.playerVals = mapSpecificPlayerValues;

        //Collision
        wallObjects = this.level.getTiledMap().getLayers().get(this.level.getLayer("walls")).getObjects();
        otherObjects = this.level.getTiledMap().getLayers().get(this.level.getLayer("other")).getObjects();
    }

    public void draw(float delta) {
        spriteBatch.setProjectionMatrix(playerCamera.projection);
        statetime += delta;
        if (controls.getDirectionPressed() == Controls.Direction.NONE) {
            switch (directionLastOriented) {
                case FORWARD:
                    spriteBatch.draw(stillForward, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    break;
                case BACKWARD:
                    spriteBatch.draw(stillBackward, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    break;
                case LEFT:
                    spriteBatch.draw(stillLeft, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    break;
                case RIGHT:
                    spriteBatch.draw(stillRight, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    break;
            }
        } else {
            boolean isColliding = false;
            TextureRegion currentFrame;
            switch (controls.getDirectionPressed()) {
                case UP:
                    isColliding = false;
                    currentFrame = walkBackward.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    directionLastOriented = Orientation.BACKWARD;
                    for (RectangleMapObject rectangleObject : wallObjects.getByType(RectangleMapObject.class)) {
                        Rectangle wallRec = rectangleObject.getRectangle();
                        Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y + playerVals[4] + playerBounds[1], playerBounds[2], playerBounds[3]);
                        if (Intersector.overlaps(wallRec, playerRec)) {
                            isColliding = true;
                        }
                    }
                    if (!isColliding) {
                        level.getCamera().translate(0, playerVals[4]);
                    }
                    break;
                case DOWN:
                    isColliding = false;
                    currentFrame = walkForward.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    directionLastOriented = Orientation.FORWARD;
                    for (RectangleMapObject rectangleObject : wallObjects.getByType(RectangleMapObject.class)) {
                        Rectangle wallRec = rectangleObject.getRectangle();
                        Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y - playerVals[4] + playerBounds[1], playerBounds[2], playerBounds[3]);
                        if (Intersector.overlaps(wallRec, playerRec)) {
                            isColliding = true;
                        }
                    }
                    if (!isColliding) {
                        level.getCamera().translate(0, -playerVals[4]);
                    }
                    break;
                case LEFT:
                    isColliding = false;
                    currentFrame = walkLeft.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    directionLastOriented = Orientation.LEFT;
                    for (RectangleMapObject rectangleObject : wallObjects.getByType(RectangleMapObject.class)) {
                        Rectangle wallRec = rectangleObject.getRectangle();
                        Rectangle playerRec = new Rectangle(level.getCamera().position.x - playerVals[5] + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
                        if (Intersector.overlaps(wallRec, playerRec)) {
                            isColliding = true;
                        }
                    }
                    if (!isColliding) {
                        level.getCamera().translate(-playerVals[5], 0);
                    }
                    break;
                case RIGHT:
                    isColliding = false;
                    currentFrame = walkRight.getKeyFrame(statetime, true);
                    spriteBatch.draw(currentFrame, playerVals[0], playerVals[1], playerVals[2], playerVals[3]);
                    directionLastOriented = Orientation.RIGHT;
                    for (RectangleMapObject rectangleObject : wallObjects.getByType(RectangleMapObject.class)) {
                        Rectangle wallRec = rectangleObject.getRectangle();
                        Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerVals[5] + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
                        if (Intersector.overlaps(wallRec, playerRec)) {
                            isColliding = true;
                        }
                    }
                    if (!isColliding) {
                        level.getCamera().translate(playerVals[5], 0);
                    }
                    break;
            }
        }

        if (level.getMapName().equals("cave.tmx")) {
            checkCaveOtherLayerForCollisions();
        } else if (level.getMapName().equals("tunnels.tmx")) {
            checkTunnelsNPCLayerForCollisions();
            checkTunnelsItemsLayerForCollisions();
            checkTunnelsOtherLayerForCollisions();
        }
    }

    private void checkTunnelsItemsLayerForCollisions() {

    }

    private void checkTunnelsNPCLayerForCollisions() {
        for (NPC enemy : level.getEnemies()) {
            if (enemy.isEnabled()) {
                Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
                if (Intersector.overlaps(enemy.getPersonalBoundary(), playerRec)) {
                    Gdx.app.debug(TAG, "PersonalBounds");
                } else if (Intersector.overlaps(enemy.getActivationBoundary(), playerRec)) {
                    Gdx.app.debug(TAG, "ActivationBound");
                }
            }
        }
    }

    private void checkTunnelsOtherLayerForCollisions() {
        for (RectangleMapObject rectangleObject : otherObjects.getByType(RectangleMapObject.class)) {
            Rectangle otherRec = rectangleObject.getRectangle();
            Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
            if (Intersector.overlaps(otherRec, playerRec)) {
                String objectClass = (String) rectangleObject.getProperties().get("class");
                if (objectClass != null) {
                    if (objectClass.equals("exit")) {
                        level.endLevel();
                    }
                }
            }
        }
    }

    private void checkCaveOtherLayerForCollisions() {
        for (RectangleMapObject rectangleObject : otherObjects.getByType(RectangleMapObject.class)) {
            Rectangle otherRec = rectangleObject.getRectangle();
            Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
            if (Intersector.overlaps(otherRec, playerRec)) {
                String objectClass = (String) rectangleObject.getProperties().get("class");
                if (objectClass != null) {
                    if (objectClass.equals("teleport")) {
                        if (rectangleObject.getName().equals("Teleport_To_Lava_Island")) {
                            Rectangle spawnLavaIsland = ((RectangleMapObject) otherObjects.get("Spawn_Lava_Island")).getRectangle();
                            level.getCamera().position.set(spawnLavaIsland.getX() + spawnLavaIsland.getWidth()/2, spawnLavaIsland.getY() + spawnLavaIsland.getHeight()/2, 0.0f);
                        } else if (rectangleObject.getName().equals("Teleport_From_Lava_Island")) {
                            Rectangle spawnFromLavaIsland = ((RectangleMapObject) otherObjects.get("Spawn_From_Lava_Island")).getRectangle();
                            level.getCamera().position.set(spawnFromLavaIsland.getX() + spawnFromLavaIsland.getWidth()/2, spawnFromLavaIsland.getY() + spawnFromLavaIsland.getHeight()/2, 0.0f);
                        } else if (rectangleObject.getName().equals("Teleport_From_Water_Island")) {
                            Rectangle spawnFromWaterIsland = ((RectangleMapObject) otherObjects.get("Spawn_From_Water_Island")).getRectangle();
                            level.getCamera().position.set(spawnFromWaterIsland.getX() + spawnFromWaterIsland.getWidth()/2, spawnFromWaterIsland.getY() + spawnFromWaterIsland.getHeight()/2, 0.0f);
                        } else if (rectangleObject.getName().equals("Teleport_To_Water_Island")) {
                            Rectangle spawnWaterIsland = ((RectangleMapObject) otherObjects.get("Spawn_Water_Island")).getRectangle();
                            level.getCamera().position.set(spawnWaterIsland.getX() + spawnWaterIsland.getWidth()/2, spawnWaterIsland.getY() + spawnWaterIsland.getHeight()/2, 0.0f);
                        }
                    } else if (objectClass.equals("exit")) {
                        level.endLevel();
                    }
                }
            }
        }
    }

    public abstract void addToInventory(Item item);
    public abstract ArrayList<Item> getInventory();
    public abstract void removeFromInventory(Item item);

    public abstract void setHealth(int health);
    public abstract int getHealth();

    public abstract void setWater(int water);
    public abstract int getWater();

    public abstract void setExperience(int experience);
    public abstract int getExperience();

    public abstract void setSpice(int spice);
    public abstract int getSpice();

    public abstract void setAttackPower(int attackPower);
    public abstract int getAttackPower();

    public abstract void setLevel(int level);
    public abstract int getLevel();


    public void dispose() {
        //nothing to dispose of
    }

}
