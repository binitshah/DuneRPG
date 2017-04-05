package com.binitshah.dunerpg;

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
    private Circle primaryBound, secondaryBound;

    //Rendering
    private OrthographicCamera orthographicCamera;

    //Logging
    private final String TAG = "LOGDUNERPG";

    Controls(float width, float height, OrthographicCamera orthographicCamera) {
        Gdx.app.debug(TAG, "Constructor:: width: " + width + " | height: " + height);
        //textures
        up = new Texture("up.png");
        down = new Texture("down.png");
        left = new Texture("left.png");
        right = new Texture("right.png");
        primary = new Texture("primary.png");
        secondary = new Texture("secondary.png");

        //bounds
        float rectLargerDimension = 76;
        float rectSmallerDimension = 61;
        float circleRadius = 35;
        float x = width*0.2f - rectSmallerDimension/2;
        float y = height*0.25f - rectLargerDimension/2 + (rectSmallerDimension*0.7f);
        Gdx.app.debug(TAG, "upBound:: x: " + x + " | y: " + y + " | width: " + rectSmallerDimension + " | height: " + rectLargerDimension);
        upBound = new Rectangle(width*0.2f - rectSmallerDimension/2, height*0.25f - rectLargerDimension/2 + (rectSmallerDimension*0.7f), rectSmallerDimension, rectLargerDimension);
        downBound = new Rectangle(width*0.2f - rectSmallerDimension/2, height*0.25f - rectLargerDimension/2 + (-rectSmallerDimension*0.7f), rectSmallerDimension, rectLargerDimension);
        leftBound = new Rectangle(width*0.2f - rectLargerDimension/2 + (-rectSmallerDimension*0.7f), height*0.25f - rectSmallerDimension/2, rectLargerDimension, rectSmallerDimension);
        rightBound = new Rectangle(width*0.2f - rectLargerDimension/2 + (rectSmallerDimension*0.7f), height*0.25f - rectSmallerDimension/2, rectLargerDimension, rectSmallerDimension);
        primaryBound = new Circle(width*0.8f - circleRadius + (-circleRadius*0.8f), height*0.2f - circleRadius + (circleRadius*0.8f), circleRadius);
        secondaryBound = new Circle(width*0.8f - circleRadius + (circleRadius*0.8f), height*0.2f - circleRadius + (-circleRadius*0.8f), circleRadius);

        //camera
        this.orthographicCamera = orthographicCamera;
    }

    void dispose() {
        up.dispose();
        down.dispose();
        left.dispose();
        right.dispose();
        primary.dispose();
        secondary.dispose();
    }

    void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(up, upBound.getX(), upBound.getY(), upBound.getWidth(), upBound.getHeight());
        spriteBatch.draw(down, downBound.getX(), downBound.getY(), downBound.getWidth(), downBound.getHeight());
        spriteBatch.draw(left, leftBound.getX(), leftBound.getY(), leftBound.getWidth(), leftBound.getHeight());
        spriteBatch.draw(right, rightBound.getX(), rightBound.getY(), rightBound.getWidth(), rightBound.getHeight());
        spriteBatch.draw(primary, primaryBound.x, primaryBound.y, primaryBound.radius * 2, primaryBound.radius * 2);
        spriteBatch.draw(secondary, secondaryBound.x, secondaryBound.y, secondaryBound.radius * 2, secondaryBound.radius * 2);
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.debug(TAG, "Key Down:: keycode: " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.debug(TAG, "Key Up:: keycode: " + keycode);
        if(keycode == Input.Keys.LEFT)
            orthographicCamera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            orthographicCamera.translate(32,0);
        if(keycode == Input.Keys.UP)
            orthographicCamera.translate(0,32);
        if(keycode == Input.Keys.DOWN)
            orthographicCamera.translate(0,-32);
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
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        orthographicCamera.unproject(touchPos);
        if (upBound.contains(touchPos.x, touchPos.y)) {
            keyDown(Input.Keys.UP);
        }
        if (downBound.contains(touchPos.x, touchPos.y)) {
            keyDown(Input.Keys.DOWN);
        }
        if (leftBound.contains(touchPos.x, touchPos.y)) {
            keyDown(Input.Keys.LEFT);
        }
        if (rightBound.contains(touchPos.x, touchPos.y)) {
            keyDown(Input.Keys.RIGHT);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug(TAG, "Touch Up:: screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer + " | button: " + button);
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        orthographicCamera.unproject(touchPos);
        if (upBound.contains(touchPos.x, touchPos.y)) {
            keyUp(Input.Keys.UP);
        }
        if (downBound.contains(touchPos.x, touchPos.y)) {
            keyUp(Input.Keys.DOWN);
        }
        if (leftBound.contains(touchPos.x, touchPos.y)) {
            keyUp(Input.Keys.LEFT);
        }
        if (rightBound.contains(touchPos.x, touchPos.y)) {
            keyUp(Input.Keys.RIGHT);
        }
        return false;
    }

    //The methods below are not used

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Gdx.app.debug(TAG, "Touch Dragged:: screenX: " + screenX + " | screenY: " + screenY + " | pointer: " + pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.app.debug(TAG, "Mouse Moved:: screenX: " + screenX + " | screenY: " + screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Gdx.app.debug(TAG, "Scrolled:: amount: " + amount);
        return false;
    }
}
