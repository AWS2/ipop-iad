package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input.TextInputListener;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.Player;

public class MainMenuScreen implements Screen, TextInputListener {

    private Texture disabledSinglePlayer, disabledMultiPlayer, check, background, selectName, selectCharacter, selectCicle, singlePlayer, multiPlayer, rankings;

    private Rectangle selectNameBtn, selectCharacterBtn, selectCicleBtn, singlePlayerBtn, multiPlayerBtn, rankingsBtn;
    final IPOP game;

    static boolean nameSelected = true;
    static boolean cicleSelected = true;
    static boolean characterSelected = true;

    public MainMenuScreen(IPOP game) {
        this.game = game;
        IPOP.loadResources();

        background = new Texture(Gdx.files.internal("Mansion.png"));

        selectName = new Texture(Gdx.files.internal("select_name.png"));
        selectNameBtn = new Rectangle(900, 500, 500, 100);

        selectCicle = new Texture(Gdx.files.internal("select_cicle.png"));
        selectCicleBtn = new Rectangle(900, 390, 500, 100);

        selectCharacter = new Texture(Gdx.files.internal("select_character.png"));
        selectCharacterBtn = new Rectangle(900, 280, 500, 100);

        singlePlayer = new Texture(Gdx.files.internal("single_player.png"));
        disabledSinglePlayer = new Texture(Gdx.files.internal("disable_sp.png"));
        singlePlayerBtn = new Rectangle(900, 170, 500, 100);

        multiPlayer = new Texture(Gdx.files.internal("multi_player.png"));
        disabledMultiPlayer = new Texture(Gdx.files.internal("disable_mp.png"));
        multiPlayerBtn = new Rectangle(900, 60, 500, 100);

        rankings = new Texture(Gdx.files.internal("ranking_button.png"));
        rankingsBtn = new Rectangle(2100, 800, 150, 150);

        check = new Texture(Gdx.files.internal("check_button.png"));

    }

    @Override
    public void show() {  }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(background, 0,0, 2400,1080);
        game.batch.draw(selectName,900 , 500, 500, 100);
        game.batch.draw(selectCicle,900 , 390, 500, 100);
        game.batch.draw(selectCharacter,900 , 280, 500, 100);

        if (characterSelected && nameSelected && cicleSelected) {
            game.batch.draw(singlePlayer,900 , 170, 500, 100);
            game.batch.draw(multiPlayer,900 , 60, 500, 100);
        } else {
            game.batch.draw(disabledSinglePlayer,900 , 170, 500, 100);
            game.batch.draw(disabledMultiPlayer,900 , 60, 500, 100);
        }

        game.batch.draw(rankings,2100, 800, 150, 150);

        game.font.getData().setScale(3f);
        if (characterSelected) {
            game.font.draw(game.batch,"Character selected",100 , 300);
            game.batch.draw(check, 25,250, 75,75);
        }

        if (nameSelected) {
            game.font.draw(game.batch,"Name selected",100 , 200);
            game.batch.draw(check, 25,150, 75,75);
        }

        if (cicleSelected) {
            game.font.draw(game.batch,"Cicle selected",100 , 100);
            game.batch.draw(check, 25,50, 75,75);
        }


        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (selectNameBtn.contains(touchX, touchY)) {
                Gdx.input.getTextInput(new TextInputListener() {
                    @Override
                    public void input(String text) {
                        Player.player_alias = text;
                        MainMenuScreen.nameSelected = true;
                    }
                    @Override
                    public void canceled() { }
                }, "Select name", "", "Username");
            } else if (selectCicleBtn.contains(touchX, touchY)) {
                game.setScreen(new CicleSelectionScreen(game));
            } else if (selectCharacterBtn.contains(touchX, touchY)) {
                game.setScreen(new CharacterSelectionScreen(game));
            } else if (singlePlayerBtn.contains(touchX, touchY)) {
                if (cicleSelected && nameSelected && characterSelected) {
                    game.setScreen(new SinglePlayerScreen(game));
                }
            } else if (multiPlayerBtn.contains(touchX, touchY)) {
                if (cicleSelected && nameSelected && characterSelected) {
                    game.setScreen(new MultiPlayerScreen(game));
                }
            }else if (rankingsBtn.contains(touchX, touchY)) {
                game.setScreen(new RankingsScreen(game));
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
    public void dispose() {
        game.batch.dispose();
        game.batch.dispose();
    }

    @Override
    public void input(String text) {
        System.out.println(text);
    }

    @Override
    public void canceled() {

    }
}