package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.ipop_game.ApiWs;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.Player;
import com.mygdx.ipop_game.models.Record;
import com.mygdx.ipop_game.utils.WebSockets;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RankingsScreen implements Screen {

    private Texture background, itemBackground, goBack, goNext, mainMenu;

    private Rectangle goBackBtn, goNextBtn, mainMenuBtn;
    final IPOP game;
    public float stateTime = 0f;
    private ArrayList<Record> rankings = new ArrayList<>();
    private ArrayList<Record> allRankings = new ArrayList<>();

    public RankingsScreen(IPOP game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("Mansion.png"));
        itemBackground = new Texture(Gdx.files.internal("panel-background.png"));

        goBack = new Texture(Gdx.files.internal("back-button.png"));
        goBackBtn = new Rectangle(200 , 400, 100, 100);

        goNext = new Texture(Gdx.files.internal("next-button.png"));
        goNextBtn = new Rectangle(2000 , 400, 100, 100);

        mainMenu = new Texture(Gdx.files.internal("menu_button.png"));
        mainMenuBtn = new Rectangle(25 , 900, 100, 100);

        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));
        rankings.add(new Record(1, 100,"Ivan"));

    }

    @Override
    public void show() {  }


    int x = 600;
    int start = 0, end = 5, maxRank = 5;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 2400, 1080);
        game.batch.draw(mainMenu, 25 , 900, 100, 100);
        game.batch.draw(itemBackground, 400, 100, 1500, 750);
        game.font.getData().setScale(5.0f);

        game.font.draw(
                game.batch,
                rankings.get(0).name + " - " + rankings.get(1).score,
                x, 700
        );
        game.font.draw(
                game.batch,
                rankings.get(1).name + " - " + rankings.get(2).score,
                x, 600
        );
        game.font.draw(
                game.batch,
                rankings.get(2).name + " - " + rankings.get(2).score,
                x, 500
        );
        game.font.draw(
                game.batch,
                rankings.get(3).name + " - " + rankings.get(3).score,
                x, 400
        );
        game.font.draw(
                game.batch,
                rankings.get(4).name + " - " + rankings.get(4).score,
                x, 300
        );
        game.batch.draw(goBack, 200, 400, 100, 100);
        game.batch.draw(goNext, 2000, 400, 100, 100);


        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (goNextBtn.contains(touchX, touchY)) {
                try {
                    start+=maxRank;
                    end+=maxRank;
                    JSONObject json = new JSONObject();
                    json.put("start", start);
                    json.put("end", end);
                    JSONObject response = new JSONObject(new ApiWs().sendPost("api/rankings", json));
                    System.out.println(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (goBackBtn.contains(touchX, touchY)) {
                if (start - maxRank >= 1) {
                    try {
                        start-=maxRank;
                        end-=maxRank;
                        JSONObject json = new JSONObject();
                        json.put("start", start);
                        json.put("end", end);
                        JSONObject response = new JSONObject(new ApiWs().sendPost("api/rankings", json));
                        System.out.println(response);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (mainMenuBtn.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));
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