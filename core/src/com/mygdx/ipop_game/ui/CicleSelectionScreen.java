package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.Player;

public class CicleSelectionScreen implements Screen {

    private Texture background, itemBackground, firstOcupation, secondOcupation, thirdOcupation, fourthOcupation, goBack, goNext;

    private Rectangle firstOcupationBtn, secondOcupationBtn, thirdOcupationBtn, fourthOcupationBtn, goBackBtn, goNextBtn;
    final IPOP game;
    int famIndex = 0;

    public CicleSelectionScreen(IPOP game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("Mansion.png"));
        itemBackground = new Texture(Gdx.files.internal("panel-background.png"));

        firstOcupation = new Texture(Gdx.files.internal("select_name.png"));
        firstOcupationBtn = new Rectangle(900, 600, 500, 100);

        secondOcupation = new Texture(Gdx.files.internal("select_character.png"));
        secondOcupationBtn = new Rectangle(900, 490, 500, 100);

        thirdOcupation = new Texture(Gdx.files.internal("select_cicle.png"));
        thirdOcupationBtn = new Rectangle(900, 380, 500, 100);

        fourthOcupation = new Texture(Gdx.files.internal("single_player.png"));
        fourthOcupationBtn = new Rectangle(900, 270, 500, 100);

        goBack = new Texture(Gdx.files.internal("back-button.png"));
        goBackBtn = new Rectangle(200 , 400, 100, 100);

        goNext = new Texture(Gdx.files.internal("next-button.png"));
        goNextBtn = new Rectangle(2000 , 400, 100, 100);

    }

    @Override
    public void show() {  }

    int startX = 600;
    int startY = 600;
    int marginVertical = 100;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0,0, 2400,1080);
        game.batch.draw(itemBackground, 450,100, 1500,650);
        game.font.getData().setScale(3.0f);
        for (int i = 0; i < IPOP.families.get(famIndex).getOcupations().size(); i++) {
            game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(i).toUpperCase() , startX,(startY - (marginVertical * i)));
        }

        game.batch.draw(goBack,200 , 400, 100, 100);
        game.batch.draw(goNext,2000 , 400, 100, 100);


        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (goNextBtn.contains(touchX, touchY)) {
                if (famIndex == IPOP.families.size() - 1) { famIndex = 0; }
                else {famIndex++;}
            } else if (goBackBtn.contains(touchX, touchY)) {
                if (famIndex == 0) { famIndex = IPOP.families.size() - 1; }
                else { famIndex--; }
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

}