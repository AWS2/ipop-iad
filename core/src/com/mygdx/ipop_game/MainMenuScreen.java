package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final IPOP game;
    OrthographicCamera camera;

    Texture background, button;

    public MainMenuScreen(final IPOP game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
    }

    @Override
    public void show() {  }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        background = new Texture(Gdx.files.internal("Mansion.png"));
        button = new Texture(Gdx.files.internal("button.png"));

        Texture _button = new Texture(Gdx.files.internal("button.png"));
        game.batch.draw(
                background,
                0,
                0,
                0, 100,
                SCR_WIDTH,SCR_HEIGHT
        );
        game.batch.draw(
                _button,
                SCR_WIDTH / 2f - _button.getWidth() / 2f,
                50,
                0, 0,
                300, 80
        );
        game.batch.draw(
                _button,
                SCR_WIDTH / 2f - _button.getWidth() / 2f,
                150,
                0, 0,
                300, 80
        );
        game.batch.draw(
                _button,
                SCR_WIDTH / 2f - _button.getWidth() / 2f,
                250,
                0, 0,
                300, 80
        );
        game.setSubtitle();
        game.font.draw(
                game.batch,
                "IPOP Game",
                SCR_WIDTH / 2f - 125,
                SCR_HEIGHT - 50
        );
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen());
            dispose();
        }
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
    public void dispose() {  }
}