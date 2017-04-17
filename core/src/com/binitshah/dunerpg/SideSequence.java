package com.binitshah.dunerpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binitshah.dunerpg.characters.Jessica;
import com.binitshah.dunerpg.characters.Kynes;
import com.binitshah.dunerpg.characters.NPC;
import com.binitshah.dunerpg.characters.Player;
import com.binitshah.dunerpg.levels.Level;

/**
 * Created by binitshah on 4/16/17.
 */

public class SideSequence implements Screen {

    //General
    private DuneRPG game;
    private Level parent;
    private Player player;
    private NPC npc;

    //rendering
    private OrthographicCamera sideSeqCamera;
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    //textures
    Texture background;

    public SideSequence(Level parent, Player player, NPC npc) {
        this.game = parent.getGame();
        this.parent = parent;
        this.player = player;
        this.npc = npc;

        //rendering
        this.sideSeqCamera = new OrthographicCamera(this.parent.getWidth(), this.parent.getHeight());
        this.sideSeqCamera.position.set(this.parent.getWidth()/2, this.parent.getHeight()/2, 0);
        this.spriteBatch = new SpriteBatch();
        this.font = new BitmapFont();

        if (this.parent.getMapName().equals("kynesroom.tmx")) {
            background  = new Texture("smugglerdesertbackground.png");
            this.font.setColor(Color.BLACK);
        } else if (this.parent.getMapName().equals("cave.tmx")) {
            background  = new Texture("fremencavebackground.png");
            this.font.setColor(Color.WHITE);
        } else if (this.parent.getMapName().equals("tunnels.tmx")) {
            background = new Texture("fremencavebackground.png");
            this.font.setColor(Color.WHITE);
        } else if (this.parent.getMapName().equals("miner.tmx")) {
            background = new Texture("smugglerdesertbackground.png");
            this.font.setColor(Color.BLACK);
        } else if (this.parent.getMapName().equals("cavetwo.tmx")) {
            background = new Texture("fremencavebackground.png");
            this.font.setColor(Color.WHITE);
        } else if (this.parent.getMapName().equals("desert.tmx")) {
            background = new Texture("smugglerdesertbackground.png");
            this.font.setColor(Color.BLACK);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(sideSeqCamera.projection);
        spriteBatch.begin();
        spriteBatch.draw(background, - this.parent.getWidth()/2, - this.parent.getHeight()/2, this.parent.getWidth(), this.parent.getHeight());
        player.drawSideSequence(delta, spriteBatch);
        npc.drawSideSequence(delta, spriteBatch);
        font.draw(spriteBatch, "Welcome to the jungle", - this.parent.getWidth()*0.3f, - this.parent.getHeight()*0.2f);
        spriteBatch.end();
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
