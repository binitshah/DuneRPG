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
import com.binitshah.dunerpg.levels.BackTunnels;
import com.binitshah.dunerpg.levels.FirstFremanCave;
import com.binitshah.dunerpg.levels.KynesRoom;

public class DuneRPG extends Game {
	
	@Override
	public void create () {
//		KynesRoom firstLevel = new KynesRoom(this);
//		setScreen(firstLevel);
//      BackTunnels firstLevel = new BackTunnels(this);
//      setScreen(firstLevel);
		FirstFremanCave firstLevel = new FirstFremanCave(this);
		setScreen(firstLevel);
	}

	@Override
	public void render () {
        super.render();
	}

	@Override
    public void resize(int newWidth, int newHeight) {
        super.resize(newWidth, newHeight);
    }
	
	@Override
	public void dispose () {
        super.dispose();
	}
}
