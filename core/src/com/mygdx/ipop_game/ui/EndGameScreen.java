package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.GameRecord;
import com.mygdx.ipop_game.models.Player;
import com.mygdx.ipop_game.models.Record;
import com.mygdx.ipop_game.utils.ApiWs;
import com.mygdx.ipop_game.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class EndGameScreen implements Screen {

    private Texture background, itemBackground, saveScore;

    private Rectangle saveScoreBtn;
    final IPOP game;
    final GameRecord gr;
    public float stateTime = 0f;
    private ArrayList<Record> rankings = new ArrayList<>();
    private ArrayList<Record> allRankings = new ArrayList<>();

    public EndGameScreen(IPOP game, GameRecord gr) {
        this.game = game;
        this.gr = gr;
        background = new Texture(Gdx.files.internal("Mansion.png"));
        itemBackground = new Texture(Gdx.files.internal("panel-background.png"));

        saveScore = new Texture(Gdx.files.internal("save_score_button.png"));
        saveScoreBtn = new Rectangle(920 , 50, 450, 125);

    }

    @Override
    public void show() {  }

    int x = 600;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(background, 0, 0, 2400, 1080);
        game.batch.draw(itemBackground, 400, 100, 1500, 750);
        game.batch.draw(saveScore, 920 , 50, 450, 125);
        game.font.getData().setScale(5.0f);

        game.font.draw(
                game.batch,
                "Username: " + gr.aliasPlayer,
                x, 700
        );
        int inTotems = gr.totalTotems - gr.correctTotems;
        int score = gr.correctTotems - (inTotems * 2);
        game.font.draw(
                game.batch,
                "Score:  " + score,
                x, 600
        );
        long segundos = Duration.between(gr.timeStart, gr.timeEnd).getSeconds();
        game.font.draw(
                game.batch,
                "Game Duration:  " + segundos + "s",
                x, 500
        );
        game.font.draw(
                game.batch,
                "Correct Totems: " + gr.correctTotems + " / " + (gr.totalTotems),
                x, 400
        );


        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (saveScoreBtn.contains(touchX, touchY)) {
                if (saveScoreBtn.contains(touchX, touchY)) {
                    try {
                        JSONObject json = new JSONObject();
                        json.put("aliasPlayer", gr.aliasPlayer);
                        json.put("timeStart", gr.timeStart);
                        json.put("timeEnd", gr.timeEnd);
                        json.put("correctTotems", gr.correctTotems);
                        json.put("wrongTotems", gr.correctTotems - gr.totalTotems);
                        json.put("nameCycle", gr.playerOcupation);
                        System.out.println(json);
                        StringBuffer stb = new ApiWs().sendPost(WebServiceConstants.api + "api/set_ranking",json);
                        System.out.println(stb);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        }

        game.batch.end();
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