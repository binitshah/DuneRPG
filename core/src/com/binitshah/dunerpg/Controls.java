package com.binitshah.dunerpg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by binitshah on 4/4/17.
 *
 * Will be used to handle all interactions.
 *
 * TODO:
 *  - Switch out which images the textures call based on user preference
 *  - only show onscreen controls if device without keyboard
 *  - implement a and b keys
 */

public class Controls implements InputProcessor {

    //On Screen Controls
    private Texture up, down, right, left, primary, secondary;
    private Rectangle upBound, downBound, rightBound, leftBound;
    private Direction directionPressed;
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE;
    }
    private int upPointer, downPointer, rightPointer, leftPointer;
    private Circle primaryBound, secondaryBound;
    private boolean primaryPressed, secondaryPressed = false;
    private int primaryPointer, secondaryPointer;

    //Rendering
    private OrthographicCamera controlCamera;
    private float width = 480;
    private float height = 320;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    public Controls() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove

        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            //textures
            up = new Texture("up.png");
            down = new Texture("down.png");
            left = new Texture("left.png");
            right = new Texture("right.png");
            primary = new Texture("primary.png");
            secondary = new Texture("secondary.png");

            //bounds
            float rectLargerDimension = 43.6065f * width / height;
            float rectSmallerDimension = 35 * width / height;
            float circleRadius = 18 * width / height;
            upBound = new Rectangle(-width * 0.25f - rectSmallerDimension / 2 - 45, -height * 0.22f - rectLargerDimension / 2 + (rectSmallerDimension * 0.7f) + 10, rectSmallerDimension, rectLargerDimension);
            downBound = new Rectangle(-width * 0.25f - rectSmallerDimension / 2 - 45, -height * 0.22f - rectLargerDimension / 2 + (-rectSmallerDimension * 0.7f) + 10, rectSmallerDimension, rectLargerDimension);
            leftBound = new Rectangle(-width * 0.25f - rectLargerDimension / 2 + (-rectSmallerDimension * 0.7f) - 45, -height * 0.22f - rectSmallerDimension / 2 + 10, rectLargerDimension, rectSmallerDimension);
            rightBound = new Rectangle(-width * 0.25f - rectLargerDimension / 2 + (rectSmallerDimension * 0.7f) - 45, -height * 0.22f - rectSmallerDimension / 2 + 10, rectLargerDimension, rectSmallerDimension);
            primaryBound = new Circle(width * 0.28f - circleRadius + (-circleRadius * 0.8f) + 70, -height * 0.22f - circleRadius + (circleRadius * 0.8f) + 30, circleRadius);
            secondaryBound = new Circle(width * 0.28f - circleRadius + (circleRadius * 0.8f) + 70, -height * 0.22f - circleRadius + (-circleRadius * 0.8f) + 30, circleRadius);

            //camera
            this.controlCamera = new OrthographicCamera(width, height);
            this.controlCamera.position.set(width/2, height/2, 0);
        }

        //more camera
        directionPressed = Direction.NONE;
    }

    public void draw(SpriteBatch spriteBatch) {
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            spriteBatch.setProjectionMatrix(controlCamera.projection);
            spriteBatch.draw(up, upBound.getX(), upBound.getY(), upBound.getWidth(), upBound.getHeight());
            spriteBatch.draw(down, downBound.getX(), downBound.getY(), downBound.getWidth(), downBound.getHeight());
            spriteBatch.draw(left, leftBound.getX(), leftBound.getY(), leftBound.getWidth(), leftBound.getHeight());
            spriteBatch.draw(right, rightBound.getX(), rightBound.getY(), rightBound.getWidth(), rightBound.getHeight());
            spriteBatch.draw(primary, primaryBound.x - primaryBound.radius, primaryBound.y - primaryBound.radius, primaryBound.radius * 2, primaryBound.radius * 2);
            spriteBatch.draw(secondary, secondaryBound.x - secondaryBound.radius, secondaryBound.y - secondaryBound.radius, secondaryBound.radius * 2, secondaryBound.radius * 2);
        }

        //todo: remove
        String pos;
        switch (directionPressed) {
            case LEFT:
                //mainCamera.translate(-3, 0);
                Gdx.app.debug(TAG, "LEFT Button Pressed");
                break;
            case RIGHT:
                //mainCamera.translate(3, 0);
                Gdx.app.debug(TAG, "RIGHT Button Pressed");
                break;
            case UP:
                //mainCamera.translate(0, 2);
                Gdx.app.debug(TAG, "UP Button Pressed");
                break;
            case DOWN:
                //mainCamera.translate(0, -2);
                Gdx.app.debug(TAG, "DOWN Button Pressed");
                break;
        }

        if (primaryPressed) {
            Gdx.app.debug(TAG, "Primary Button Pressed");
        }
        if (secondaryPressed) {
            Gdx.app.debug(TAG, "Secondary Button Pressed");
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        //Directional
        if (keycode == Input.Keys.LEFT) {
            directionPressed = Direction.LEFT;
            return true;
        }
        if (keycode == Input.Keys.RIGHT) {
            directionPressed = Direction.RIGHT;
            return true;
        }
        if (keycode == Input.Keys.UP) {
            directionPressed = Direction.UP;
            return true;
        }
        if (keycode == Input.Keys.DOWN) {
            directionPressed = Direction.DOWN;
            return true;
        }

        //Action
        if (keycode == Input.Keys.A) {
            primaryPressed = true;
            return true;
        }
        if (keycode == Input.Keys.S) {
            secondaryPressed = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Directional
        if (keycode == Input.Keys.LEFT) {
            directionPressed = Direction.NONE;
            return true;
        }
        if (keycode == Input.Keys.RIGHT) {
            directionPressed = Direction.NONE;
            return true;
        }
        if (keycode == Input.Keys.UP) {
            directionPressed = Direction.NONE;
            return true;
        }
        if (keycode == Input.Keys.DOWN) {
            directionPressed = Direction.NONE;
            return true;
        }

        //Action
        if (keycode == Input.Keys.A) {
            primaryPressed = false;
            return true;
        }
        if (keycode == Input.Keys.S) {
            secondaryPressed = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            Vector3 touchPoint = new Vector3(screenX, screenY, 0);
            controlCamera.unproject(touchPoint);
            //Gdx.app.debug(TAG, "TouchPoint:: screenX: " + touchPoint.x + " | screenY: " + touchPoint.y);
            if (leftBound.contains(touchPoint.x, touchPoint.y)) {
                directionPressed = Direction.LEFT;
                leftPointer = pointer;
            }
            if (rightBound.contains(touchPoint.x, touchPoint.y)) {
                directionPressed = Direction.RIGHT;
                rightPointer = pointer;
            }
            if (upBound.contains(touchPoint.x, touchPoint.y)) {
                directionPressed = Direction.UP;
                upPointer = pointer;
            }
            if (downBound.contains(touchPoint.x, touchPoint.y)) {
                directionPressed = Direction.DOWN;
                downPointer = pointer;
            }
            if (primaryBound.contains(touchPoint.x, touchPoint.y)) {
                primaryPressed = true;
                primaryPointer = pointer;
            }
            if (secondaryBound.contains(touchPoint.x, touchPoint.y)) {
                secondaryPressed = true;
                secondaryPointer = pointer;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            if (pointer == primaryPointer) {
                primaryPressed = false;
            }
            if (pointer == secondaryPointer) {
                secondaryPressed = false;
            }
            if (pointer == upPointer || pointer == downPointer || pointer == leftPointer || pointer == rightPointer) {
                directionPressed = Direction.NONE;
            }

            return true;
        }

        return false;
    }



    public Direction getDirectionPressed() {
        return directionPressed;
    }

    public void setDirectionPressed(Direction directionPressed) {
        this.directionPressed = directionPressed;
    }

    public boolean isPrimaryPressed() {
        return primaryPressed;
    }

    public void setPrimaryPressed(boolean primaryPressed) {
        this.primaryPressed = primaryPressed;
    }

    public boolean isSecondaryPressed() {
        return secondaryPressed;
    }

    public void setSecondaryPressed(boolean secondaryPressed) {
        this.secondaryPressed = secondaryPressed;
    }

    public void dispose() {
        try {
            up.dispose();
            down.dispose();
            left.dispose();
            right.dispose();
            primary.dispose();
            secondary.dispose();
        } catch (NullPointerException e) {
            //I mean, like if they're empty, then don't dispose them... what does it matter?
        }
    }

    @Override
    public boolean keyTyped(char character) {
        //Gdx.app.debug(TAG, "Key Typed:: character: " + ((int) character));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Gdx.app.debug(TAG, "Touch Dragged:: screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //Gdx.app.debug(TAG, "Mouse Moved:: screenX: " + screenX + " | screenY: " + screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        //Gdx.app.debug(TAG, "Scrolled:: amount: " + amount);
        return false;
    }
}
