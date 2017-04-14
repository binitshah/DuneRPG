package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binitshah.dunerpg.Controls;

/**
 * Created by binitshah on 4/14/17.
 *
 * Generic level handles all the animation for a day to day level.
 */

public abstract class Level implements Screen {

    //Scene
    private TiledMap tiledMap;
    private OrthographicCamera orthographicCamera;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Viewport viewport;
    private float width;
    private float height;

    //Rendering
    private SpriteBatch spriteBatch;
    private String mapName = "";
    private Rectangle spawnPoint;
    private float[] clearColor;

    //Controls
    private Controls controls;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    public Level(String mapName, Rectangle spawnPoint, float[] clearColor, float width, float height) {
        this.mapName = mapName;
        this.spawnPoint = spawnPoint;
        this.clearColor = clearColor;

        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove

        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            tiledMap = tmxMapLoader.load(mapName);
        } catch (Exception e) {
            Gdx.app.debug(TAG, "Unable to load map: " + mapName);
            Gdx.app.debug(TAG, "Error: " + e.getMessage());
            Gdx.app.exit();
        }

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        orthographicCamera = new OrthographicCamera();
        viewport = new FillViewport(width, height, orthographicCamera);
        viewport.apply();

        orthographicCamera.position.set(spawnPoint.getX(), spawnPoint.getY(), 0.0f);

        spriteBatch = new SpriteBatch();
        controls = new Controls(width, height, orthographicCamera);

        Gdx.input.setInputProcessor(controls);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(orthographicCamera);
        orthogonalTiledMapRenderer.render();
        orthographicCamera.update();

        spriteBatch.begin();
        controls.draw(spriteBatch);
        drawLevelAssets(delta);
        spriteBatch.end();
    }

    @Override
    public void resize(int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public OrthographicCamera getOrthographicCamera() {
        return orthographicCamera;
    }

    public void setOrthographicCamera(OrthographicCamera orthographicCamera) {
        this.orthographicCamera = orthographicCamera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }

    @Override
    public void dispose() {
        try {
            tiledMap.dispose();
            orthogonalTiledMapRenderer.dispose();
            spriteBatch.dispose();
            controls.dispose();
        } catch (NullPointerException e) {
            //if something's null then it's already disposed. so instead of skipping it, you're gonna give me an exception. you make so much sense...
        }
    }

    abstract void endLevel();
    abstract void drawLevelAssets(float delta);
    abstract void disposeAssets();
}
