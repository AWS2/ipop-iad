package com.mygdx.ipop_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IPOP extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    ;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        //this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void setTitle() {
        setFontSize(4);
        setFontColor(Color.BLACK);
    }

    public void setSubtitle() {
        setFontSize(3);
        setFontColor(Color.BLACK);
    }

    public void setFontSize(int size) {
        this.font.getData().setScale(size);
    }

    public void setFontColor(Color color) {
        this.font.setColor(color);
    }

    public void restartFont() {
        this.font = new BitmapFont();
    }

}