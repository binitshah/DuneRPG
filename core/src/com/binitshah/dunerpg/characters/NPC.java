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

/**
 * Created by binitshah on 4/16/17.
 *
 * Generic Non-Person Character
 */

public class NPC {

    //general
    private Level level;
    private String id;
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


    public NPC (String spriteSheetName, Level level, float[] npcVals) {
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
        spriteBatch.setProjectionMatrix(npcCamera.projection);
        statetime += delta;
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

//        boolean isColliding = false;
//        TextureRegion currentFrame;
//        switch (controls.getDirectionPressed()) {
//            case UP:
//                isColliding = false;
//                currentFrame = walkBackward.getKeyFrame(statetime, true);
//                spriteBatch.draw(currentFrame, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
//                directionLastOriented = Player.Orientation.BACKWARD;
//                for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                    Rectangle wallRec = rectangleObject.getRectangle();
//                    Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y + npcVals[4] + playerBounds[1], playerBounds[2], playerBounds[3]);
//                    if (Intersector.overlaps(wallRec, playerRec)) {
//                        isColliding = true;
//                    }
//                }
//                if (!isColliding) {
//                    level.getCamera().translate(0, npcVals[4]);
//                }
//                break;
//            case DOWN:
//                isColliding = false;
//                currentFrame = walkForward.getKeyFrame(statetime, true);
//                spriteBatch.draw(currentFrame, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
//                directionLastOriented = Player.Orientation.FORWARD;
//                for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                    Rectangle wallRec = rectangleObject.getRectangle();
//                    Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y - npcVals[4] + playerBounds[1], playerBounds[2], playerBounds[3]);
//                    if (Intersector.overlaps(wallRec, playerRec)) {
//                        isColliding = true;
//                    }
//                }
//                if (!isColliding) {
//                    level.getCamera().translate(0, -npcVals[4]);
//                }
//                break;
//            case LEFT:
//                isColliding = false;
//                currentFrame = walkLeft.getKeyFrame(statetime, true);
//                spriteBatch.draw(currentFrame, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
//                directionLastOriented = Player.Orientation.LEFT;
//                for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                    Rectangle wallRec = rectangleObject.getRectangle();
//                    Rectangle playerRec = new Rectangle(level.getCamera().position.x - npcVals[5] + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
//                    if (Intersector.overlaps(wallRec, playerRec)) {
//                        isColliding = true;
//                    }
//                }
//                if (!isColliding) {
//                    level.getCamera().translate(-npcVals[5], 0);
//                }
//                break;
//            case RIGHT:
//                isColliding = false;
//                currentFrame = walkRight.getKeyFrame(statetime, true);
//                spriteBatch.draw(currentFrame, npcVals[0], npcVals[1], npcVals[2], npcVals[3]);
//                directionLastOriented = Player.Orientation.RIGHT;
//                for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
//                    Rectangle wallRec = rectangleObject.getRectangle();
//                    Rectangle playerRec = new Rectangle(level.getCamera().position.x + npcVals[5] + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
//                    if (Intersector.overlaps(wallRec, playerRec)) {
//                        isColliding = true;
//                    }
//                }
//                if (!isColliding) {
//                    level.getCamera().translate(npcVals[5], 0);
//                }
//                break;
//        }

//        for (RectangleMapObject rectangleObject : otherObjects.getByType(RectangleMapObject.class)) {
//            Rectangle otherRec = rectangleObject.getRectangle();
//            Rectangle playerRec = new Rectangle(level.getCamera().position.x + playerBounds[0], level.getCamera().position.y + playerBounds[1], playerBounds[2], playerBounds[3]);
//            if (Intersector.overlaps(otherRec, playerRec)) {
//                String objectClass = (String) rectangleObject.getProperties().get("class");
//                if (objectClass != null) {
//                    if (objectClass.equals("teleport")) {
//                        if (rectangleObject.getName().equals("Teleport_To_Lava_Island")) {
//                            Rectangle spawnLavaIsland = ((RectangleMapObject) otherObjects.get("Spawn_Lava_Island")).getRectangle();
//                            level.getCamera().position.set(spawnLavaIsland.getX() + spawnLavaIsland.getWidth()/2, spawnLavaIsland.getY() + spawnLavaIsland.getHeight()/2, 0.0f);
//                        } else if (rectangleObject.getName().equals("Teleport_From_Lava_Island")) {
//                            Rectangle spawnFromLavaIsland = ((RectangleMapObject) otherObjects.get("Spawn_From_Lava_Island")).getRectangle();
//                            level.getCamera().position.set(spawnFromLavaIsland.getX() + spawnFromLavaIsland.getWidth()/2, spawnFromLavaIsland.getY() + spawnFromLavaIsland.getHeight()/2, 0.0f);
//                        } else if (rectangleObject.getName().equals("Teleport_From_Water_Island")) {
//                            Rectangle spawnFromWaterIsland = ((RectangleMapObject) otherObjects.get("Spawn_From_Water_Island")).getRectangle();
//                            level.getCamera().position.set(spawnFromWaterIsland.getX() + spawnFromWaterIsland.getWidth()/2, spawnFromWaterIsland.getY() + spawnFromWaterIsland.getHeight()/2, 0.0f);
//                        } else if (rectangleObject.getName().equals("Teleport_To_Water_Island")) {
//                            Rectangle spawnWaterIsland = ((RectangleMapObject) otherObjects.get("Spawn_Water_Island")).getRectangle();
//                            level.getCamera().position.set(spawnWaterIsland.getX() + spawnWaterIsland.getWidth()/2, spawnWaterIsland.getY() + spawnWaterIsland.getHeight()/2, 0.0f);
//                        }
//                    } else if (objectClass.equals("exit")) {
//                        level.endLevel();
//                    }
//                }
//            }
//        }
    }
}
