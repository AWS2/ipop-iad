package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
        game.font.draw(game.batch, "IPOP Game", SCR_WIDTH / 2f - 100, SCR_HEIGHT / 2f + 100);
        background = new Texture(Gdx.files.internal("Mansion.png"));
        button = new Texture(Gdx.files.internal("button.png"));

        TextButton _button = new TextButton("Iniciar juego", new TextButton.TextButtonStyle(
                new TextureRegionDrawable(button),
                null,
                null,
                game.font
        ));
        _button.setPosition(100, 100);



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