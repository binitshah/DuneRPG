package com.binitshah.dunerpg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class DuneRPG extends ApplicationAdapter{

    //Tiled Map
    TiledMap tiledMap;
    OrthographicCamera orthographicCamera;
    TiledMapRenderer tiledMapRenderer;

    //Rendering
    SpriteBatch spriteBatch;
    float height;
    float width;

    //Controls
    Controls controls;
	
	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, width, height);
        orthographicCamera.update();

        tiledMap = new TmxMapLoader().load("desert.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        spriteBatch = new SpriteBatch();
        controls = new Controls(width, height, orthographicCamera);

        Gdx.input.setInputProcessor(controls);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthographicCamera.update();

        tiledMapRenderer.setView(orthographicCamera);
        tiledMapRenderer.render();

        spriteBatch.begin();
        controls.draw(spriteBatch);
        spriteBatch.end();
	}
	
	@Override
	public void dispose () {
        tiledMap.dispose();
        spriteBatch.dispose();
        controls.dispose();
	}
}
