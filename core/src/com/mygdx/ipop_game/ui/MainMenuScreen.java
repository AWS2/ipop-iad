package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input.TextInputListener;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.Player;

public class MainMenuScreen implements Screen, TextInputListener {

    private Texture background, selectName, selectCharacter, selectCicle, singlePlayer, multiPlayer, rankings;

    private Rectangle selectNameBtn, selectCharacterBtn, selectCicleBtn, singlePlayerBtn, multiPlayerBtn, rankingsBtn;
    final IPOP game;

    public MainMenuScreen(IPOP game) {
        this.game = game;
        IPOP.loadResources();

        background = new Texture(Gdx.files.internal("Mansion.png"));

        selectName = new Texture(Gdx.files.internal("select_name.png"));
        selectNameBtn = new Rectangle(900, 600, 500, 100);

        selectCicle = new Texture(Gdx.files.internal("select_cicle.png"));
        selectCicleBtn = new Rectangle(900, 490, 500, 100);

        selectCharacter = new Texture(Gdx.files.internal("select_character.png"));
        selectCharacterBtn = new Rectangle(900, 380, 500, 100);

        singlePlayer = new Texture(Gdx.files.internal("single_player.png"));
        singlePlayerBtn = new Rectangle(900, 270, 500, 100);

        multiPlayer = new Texture(Gdx.files.internal("multi_player.png"));
        multiPlayerBtn = new Rectangle(900, 160, 500, 100);

        rankings = new Texture(Gdx.files.internal("button-2.png"));
        rankingsBtn = new Rectangle(900, 50, 500, 100);
    }

    @Override
    public void show() {  }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(background, 0,0, 2400,1080);
        game.batch.draw(selectName,900 , 600, 500, 100);
        game.batch.draw(selectCicle,900 , 490, 500, 100);
        game.batch.draw(selectCharacter,900 , 380, 500, 100);
        game.batch.draw(singlePlayer,900 , 270, 500, 100);
        game.batch.draw(multiPlayer,900 , 160, 500, 100);
        game.batch.draw(rankings,900 , 50, 500, 100);

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (selectNameBtn.contains(touchX, touchY)) {
                //game.setScreen(new SelectNameScreen(game));
                Gdx.input.getTextInput(new TextInputListener() {
                    @Override
                    public void input(String text) {
                        Player.player_alias = text;
                    }
                    @Override
                    public void canceled() {

                    }
                }, "Select name", "", "Username");
            } else if (selectCicleBtn.contains(touchX, touchY)) {
                game.setScreen(new CicleSelectionScreen(game));
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