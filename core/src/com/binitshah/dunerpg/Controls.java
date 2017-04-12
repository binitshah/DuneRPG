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

class Controls implements InputProcessor {

    //On Screen Controls
    private Texture up, down, right, left, primary, secondary;
    private Rectangle upBound, downBound, rightBound, leftBound;
    private boolean upBoundPressed, downBoundPressed, rightBoundPressed, leftBoundPressed = false;
    private int upBoundPointer, downBoundPointer, rightBoundPointer, leftBoundPointer;
    private Circle primaryBound, secondaryBound;
    private boolean primaryBoundPressed, secondaryBoundPressed = false;
    private int primaryBoundPointer, secondaryBoundPointer;

    //Rendering
    private OrthographicCamera controlCamera;
    private OrthographicCamera mainCamera;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    Controls(float width, float height, OrthographicCamera mainCamera) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove

        //textures
        up = new Texture("up.png");
        down = new Texture("down.png");
        left = new Texture("left.png");
        right = new Texture("right.png");
        primary = new Texture("primary.png");
        secondary = new Texture("secondary.png");

        //bounds
        float rectLargerDimension = 43.6065f * width/height;
        float rectSmallerDimension = 35 * width/height;
        float circleRadius = 20 * width/height;
        upBound = new Rectangle(-width*0.25f - rectSmallerDimension/2, -height*0.22f - rectLargerDimension/2 + (rectSmallerDimension*0.7f), rectSmallerDimension, rectLargerDimension);
        downBound = new Rectangle(-width*0.25f - rectSmallerDimension/2, -height*0.22f - rectLargerDimension/2 + (-rectSmallerDimension*0.7f), rectSmallerDimension, rectLargerDimension);
        leftBound = new Rectangle(-width*0.25f - rectLargerDimension/2 + (-rectSmallerDimension*0.7f), -height*0.22f - rectSmallerDimension/2, rectLargerDimension, rectSmallerDimension);
        rightBound = new Rectangle(-width*0.25f - rectLargerDimension/2 + (rectSmallerDimension*0.7f), -height*0.22f - rectSmallerDimension/2, rectLargerDimension, rectSmallerDimension);
        primaryBound = new Circle(width*0.28f - circleRadius + (-circleRadius*0.8f), -height*0.22f - circleRadius + (circleRadius*0.8f), circleRadius);
        secondaryBound = new Circle(width*0.28f - circleRadius + (circleRadius*0.8f), -height*0.22f - circleRadius + (-circleRadius*0.8f), circleRadius);

        //cameras
        this.mainCamera = mainCamera;
        this.controlCamera = new OrthographicCamera(width, height);
        this.controlCamera.position.set(width/2, height/2, 0);
    }

    void draw(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(controlCamera.projection);
        spriteBatch.draw(up, upBound.getX(), upBound.getY(), upBound.getWidth(), upBound.getHeight());
        spriteBatch.draw(down, downBound.getX(), downBound.getY(), downBound.getWidth(), downBound.getHeight());
        spriteBatch.draw(left, leftBound.getX(), leftBound.getY(), leftBound.getWidth(), leftBound.getHeight());
        spriteBatch.draw(right, rightBound.getX(), rightBound.getY(), rightBound.getWidth(), rightBound.getHeight());
        spriteBatch.draw(primary, primaryBound.x, primaryBound.y, primaryBound.radius * 2, primaryBound.radius * 2);
        spriteBatch.draw(secondary, secondaryBound.x, secondaryBound.y, secondaryBound.radius * 2, secondaryBound.radius * 2);
        if (leftBoundPressed) {
            mainCamera.translate(-16, 0);
        }
        if (rightBoundPressed) {
            mainCamera.translate(16, 0);
        }
        if (upBoundPressed) {
            mainCamera.translate(0, 16);
        }
        if (downBoundPressed) {
            mainCamera.translate(0, -16);
        }
        if (primaryBoundPressed) {
            Gdx.app.debug(TAG, "Primary Button Pressed");
        }
        if (secondaryBoundPressed) {
            Gdx.app.debug(TAG, "Secondary Button Pressed");
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.debug(TAG, "Key Down:: keycode: " + keycode);
        if (keycode == Input.Keys.LEFT) {
            leftBoundPressed = true;
        }
        if (keycode == Input.Keys.RIGHT) {
            rightBoundPressed = true;
        }
        if (keycode == Input.Keys.UP) {
            upBoundPressed = true;
        }
        if (keycode == Input.Keys.DOWN) {
            downBoundPressed = true;
        }
        if (keycode == Input.Keys.A) {
            primaryBoundPressed = true;
        }
        if (keycode == Input.Keys.S) {
            secondaryBoundPressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.debug(TAG, "Key Up:: keycode: " + keycode);
        if (keycode == Input.Keys.LEFT) {
            leftBoundPressed = false;
        }
        if (keycode == Input.Keys.RIGHT) {
            rightBoundPressed = false;
        }
        if (keycode == Input.Keys.UP) {
            upBoundPressed = false;
        }
        if (keycode == Input.Keys.DOWN) {
            downBoundPressed = false;
        }
        if (keycode == Input.Keys.A) {
            primaryBoundPressed = false;
        }
        if (keycode == Input.Keys.S) {
            secondaryBoundPressed = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Gdx.app.debug(TAG, "Key Typed:: character: " + ((int) character));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug(TAG, "Touch Down:: screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer + " | button: " + button);

        Vector3 touchPoint = new Vector3(screenX, screenY, 0);
        controlCamera.unproject(touchPoint);
        Gdx.app.debug(TAG, "TouchPoint:: screenX: " + touchPoint.x + " | screenY: " + touchPoint.y);
        if (leftBound.contains(touchPoint.x, touchPoint.y)) {
            Gdx.app.debug(TAG, "leftBound");
            leftBoundPressed = true;
            leftBoundPointer = pointer;
        }
        if (rightBound.contains(touchPoint.x, touchPoint.y)) {
            Gdx.app.debug(TAG, "rightBound");
            rightBoundPressed = true;
            rightBoundPointer = pointer;
        }
        if (upBound.contains(touchPoint.x, touchPoint.y)) {
            Gdx.app.debug(TAG, "upBound");
            upBoundPressed = true;
            upBoundPointer = pointer;
        }
        if (downBound.contains(touchPoint.x, touchPoint.y)) {
            Gdx.app.debug(TAG, "downBound");
            downBoundPressed = true;
            downBoundPointer = pointer;
        }
        if (primaryBound.contains(touchPoint.x, touchPoint.y)) {
            primaryBoundPressed = true;
            primaryBoundPointer = pointer;
        }
        if (secondaryBound.contains(touchPoint.x, touchPoint.y)) {
            secondaryBoundPressed = true;
            secondaryBoundPointer = pointer;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug(TAG, "Touch Up:: screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer + " | button: " + button);
        if (pointer == leftBoundPointer) {
            leftBoundPressed = false;
        }
        if (pointer == rightBoundPointer) {
            rightBoundPressed = false;
        }
        if (pointer == upBoundPointer) {
            upBoundPressed = false;
        }
        if (pointer == downBoundPointer) {
            downBoundPressed = false;
        }
        if (pointer == primaryBoundPointer) {
            primaryBoundPressed = false;
        }
        if (pointer == secondaryBoundPointer) {
            secondaryBoundPressed = false;
        }
        return false;
    }

    void dispose() {
        up.dispose();
        down.dispose();
        left.dispose();
        right.dispose();
        primary.dispose();
        secondary.dispose();
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
