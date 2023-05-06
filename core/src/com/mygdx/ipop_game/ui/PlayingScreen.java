package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.ui.MainMenuScreen;

public class PlayingScreen implements Screen {

    private Texture spriteSheet;
    final IPOP game;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Rectangle characterBounds;
    private SpriteBatch spriteBatch;

    public PlayingScreen(IPOP game) {
        this.game = game;
        spriteSheet = new Texture(Gdx.files.internal("IPOP-Walking.png"));
        TextureRegion[][] spriteSheetFrames = TextureRegion.split(spriteSheet, 32, 32);
        Array<TextureRegion> animationFrames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                animationFrames.add(spriteSheetFrames[i][j]);
            }
        }

        animation = new Animation<TextureRegion>(0.25f, animationFrames);
        stateTime = 0f;

        characterBounds = new Rectangle(100, 100, 128, 128); // Rectángulo de colisión del personaje
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {  }

    @Override
    public void render(float delta) {
        stateTime += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, characterBounds.x, characterBounds.y);
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (characterBounds.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {  }

    @Override
    public void pause() {  }

    @Override
    public void resume() {  }

    @Override
    public void hide() {  }

    @Override
    public void dispose() {
        spriteSheet.dispose();
        spriteBatch.dispose();
    }
}