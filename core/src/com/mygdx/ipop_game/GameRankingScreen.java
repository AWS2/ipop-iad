package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameRankingScreen implements Screen {

    final IPOP game;
    OrthographicCamera camera;

    private ArrayList<Record> rankings = new ArrayList<>();

    Texture background, button;

    public GameRankingScreen(final IPOP game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        this.rankings = new ArrayList<>();
        rankings.add(new Record(0,123, "Ivan"));
        rankings.add(new Record(1,456, "Paco"));
        rankings.add(new Record(2,789, "Ivan"));
        rankings.add(new Record(3,111, "Paco"));
        rankings.add(new Record(4,222, "Paco"));
        rankings.add(new Record(5,333, "Ivan"));
        rankings.add(new Record(6,444, "Ivan"));
        rankings.add(new Record(7,555, "Paco "));
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

        game.font.draw(
                game.batch,
                rankings.get(0).name + "  -  " + rankings.get(0).score,
                50,
                350
        );

        game.font.draw(
                game.batch,
                rankings.get(1).name + "  -  " + rankings.get(1).score,
                50,
                275
        );

        game.font.draw(
                game.batch,
                rankings.get(2).name + "  -  " + rankings.get(2).score,
                50,
                200
        );

        game.font.draw(
                game.batch,
                rankings.get(3).name + "  -  " + rankings.get(3).score,
                50,
                125
        );

        game.font.draw(
                game.batch,
                rankings.get(4).name + "  -  " + rankings.get(4).score,
                500,
                350
        );

        game.font.draw(
                game.batch,
                rankings.get(5).name + "  -  " + rankings.get(5).score,
                500,
                275
        );

        game.font.draw(
                game.batch,
                rankings.get(6).name + "  -  " + rankings.get(6).score,
                500,
                200
        );

        game.font.draw(
                game.batch,
                rankings.get(7).name + "  -  " + rankings.get(7).score,
                500,
                125
        );

        game.batch.draw(
                _button,
                SCR_WIDTH / 2f - _button.getWidth() / 2f,
                0,
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