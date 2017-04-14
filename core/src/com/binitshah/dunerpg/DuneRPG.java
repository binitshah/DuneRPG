package com.binitshah.dunerpg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DuneRPG extends Game {

    //Scene
    private TiledMap tiledMap;
    private OrthographicCamera orthographicCamera;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Viewport viewport;
    private final float WIDTH = 480;
    private final float HEIGHT = 320;

    //Rendering
    private SpriteBatch spriteBatch;

    //Controls
    private Controls controls;

    //Logging
    private final String TAG = "LOGDUNERPG"; //todo: remove
	
	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG); //todo: remove

        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        tiledMap = tmxMapLoader.load("cave.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        orthographicCamera = new OrthographicCamera();
        viewport = new FillViewport(WIDTH, HEIGHT, orthographicCamera);
        viewport.apply();

        Rectangle spawnPoint = findSpawnPoint();
        orthographicCamera.position.set(spawnPoint.getX(), spawnPoint.getY(), 0.0f);

        spriteBatch = new SpriteBatch();
        controls = new Controls(WIDTH, HEIGHT, orthographicCamera);

        Gdx.input.setInputProcessor(controls);
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0.09803921568f, 0.09411764705f, 0.09803921568f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.setView(orthographicCamera);
        orthogonalTiledMapRenderer.render();
        orthographicCamera.update();

        spriteBatch.begin();
        controls.draw(spriteBatch);
        spriteBatch.end();
	}

	@Override
    public void resize(int newWidth, int newHeight) {
        viewport.update(newWidth, newHeight);
    }
	
	@Override
	public void dispose () {
        tiledMap.dispose();
        orthogonalTiledMapRenderer.dispose();
        spriteBatch.dispose();
        controls.dispose();
	}

    private Rectangle findSpawnPoint() {
        try {
            RectangleMapObject rectangleMapObject = ((RectangleMapObject) tiledMap.getLayers().get(5).getObjects().get("Spawn Point"));
            return rectangleMapObject.getRectangle();
        } catch (Exception e) {
            Gdx.app.debug(TAG, "Failed to retrieve spawn point from Spawn Point Object on Objects layer.");
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
            return new Rectangle(layer.getWidth() * layer.getTileWidth()/2, layer.getHeight() * layer.getTileHeight()/2, 10, 10);
        }
    }
}
