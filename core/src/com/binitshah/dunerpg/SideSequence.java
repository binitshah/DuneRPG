package com.binitshah.dunerpg;

import com.badlogic.gdx.Screen;

/**
 * Created by binitshah on 4/16/17.
 */

public class SideSequence implements Screen {

    //General
    boolean isBattle = true;
    DuneRPG game;
    Screen parent;

    public SideSequence(boolean isBattle, DuneRPG game, Screen parent) {
        this.isBattle = isBattle;
        this.game = game;
        this.parent = parent;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {

    }
}
