package com.binitshah.dunerpg.levels;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binitshah.dunerpg.Controls;
import com.binitshah.dunerpg.characters.NPC;

import java.util.ArrayList;

/**
 * Created by binitshah on 4/14/17.
 *
 * Generic level handles all the animation for a day to day level.
 */

public abstract class Level implements Screen {

    //Scene
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapWithAssetsRenderer mapRenderer;
    private Viewport viewport;
    private float width;
    private float height;

    //Rendering
    private SpriteBatch spriteBatch;
    private String mapName = "";
    private Vector2 spawnPoint;
    private float[] clearColor;
    private float delta;

    //Controls
    private Controls controls;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove

    public Level(String mapName, Vector2 spawnPoint, float[] clearColor, float width, float height, int splitLayer) {
        this.mapName = mapName;
        this.spawnPoint = spawnPoint;
        this.clearColor = clearColor;
        this.width = width;
        this.height = height;

        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove

        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            tiledMap = tmxMapLoader.load(mapName);
        } catch (Exception e) {
            Gdx.app.debug(TAG, "Unable to load map: " + mapName);
            Gdx.app.debug(TAG, "Error: " + e.getMessage());
            Gdx.app.exit();
        }

        mapRenderer = new OrthogonalTiledMapWithAssetsRenderer(tiledMap, splitLayer);
        camera = new OrthographicCamera();
        viewport = new FillViewport(this.width, this.height, camera);
        viewport.apply();

        camera.position.set(spawnPoint.x, spawnPoint.y, 0.0f);

        spriteBatch = new SpriteBatch();
        controls = new Controls();

        Gdx.input.setInputProcessor(controls);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        this.delta = delta;
        Gdx.gl.glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3]);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();
        camera.update();

        spriteBatch.begin();
        controls.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        disposeAssets();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Controls getControls() {
        return controls;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public String getMapName() {
        return mapName;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public void dispose() {
        try {
            tiledMap.dispose();
            mapRenderer.dispose();
            spriteBatch.dispose();
            controls.dispose();
        } catch (NullPointerException e) {
            //if something's null then it's already disposed. so instead of skipping it, you're gonna give me an exception. you make so much sense...
        }
    }

    public abstract void endLevel();
    public abstract void drawLevelAssets(float delta);
    public abstract void disposeAssets();
    public abstract int getLayer(String layerName);
    public abstract ArrayList<NPC> getEnemies();

    private class OrthogonalTiledMapWithAssetsRenderer extends OrthogonalTiledMapRenderer {
        private int split;

        OrthogonalTiledMapWithAssetsRenderer(TiledMap map, int split) {
            super(map);
            this.split = split;
        }

        @Override
        public void render() {
            if (split < map.getLayers().getCount()) {
                beginRender();
                int currentLayer = 0;
                for (MapLayer layer : map.getLayers()) {
                    if (layer.isVisible()) {
                        if (layer instanceof TiledMapTileLayer) {
                            if(currentLayer == split){
                                batch.end();
                                spriteBatch.begin();
                                drawLevelAssets(delta);
                                spriteBatch.end();
                                batch.begin();
                            }

                            renderTileLayer((TiledMapTileLayer)layer);
                            currentLayer++;
                        } else if (layer instanceof TiledMapImageLayer) {
                            renderImageLayer((TiledMapImageLayer)layer);
                        }
                    }
                }
                endRender();
            } else {
                beginRender();
                for (MapLayer layer : map.getLayers()) {
                    if (layer.isVisible()) {
                        if (layer instanceof TiledMapTileLayer) {
                            renderTileLayer((TiledMapTileLayer)layer);
                        } else if (layer instanceof TiledMapImageLayer) {
                            renderImageLayer((TiledMapImageLayer)layer);
                        }
                    }
                }
                endRender();
                spriteBatch.begin();
                drawLevelAssets(delta);
                spriteBatch.end();
            }
        }
    }
}
