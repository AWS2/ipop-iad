package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.Player;

public class CicleSelectionScreen implements Screen {

    private Texture mainMenu, background, itemBackground, firstOcupation, secondOcupation, thirdOcupation, fourthOcupation, goBack, goNext;

    private Rectangle mainMenuBtn, firstOcupationBtn, secondOcupationBtn, thirdOcupationBtn, fourthOcupationBtn, goBackBtn, goNextBtn;
    final IPOP game;
    int famIndex = 0;

    public CicleSelectionScreen(IPOP game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("Mansion.png"));
        itemBackground = new Texture(Gdx.files.internal("panel-background.png"));

        firstOcupation = new Texture(Gdx.files.internal("select_name.png"));
        firstOcupationBtn = new Rectangle(0, 0, 0, 0);

        secondOcupation = new Texture(Gdx.files.internal("select_character.png"));
        secondOcupationBtn = new Rectangle(0, 0, 0, 0);

        thirdOcupation = new Texture(Gdx.files.internal("select_cicle.png"));
        thirdOcupationBtn = new Rectangle(0, 0, 0, 0);

        fourthOcupation = new Texture(Gdx.files.internal("single_player.png"));
        fourthOcupationBtn = new Rectangle(0, 0, 0, 0);

        goBack = new Texture(Gdx.files.internal("back-button.png"));
        goBackBtn = new Rectangle(200, 400, 100, 100);

        goNext = new Texture(Gdx.files.internal("next-button.png"));
        goNextBtn = new Rectangle(2000, 400, 100, 100);

        mainMenu = new Texture(Gdx.files.internal("menu_button.png"));
        mainMenuBtn = new Rectangle(25, 900, 100, 100);

    }

    @Override
    public void show() {
    }

    int startX = 600;
    int startY = 700;
    int marginVertical = 100;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, 2400, 1080);
        game.batch.draw(itemBackground, 450, 100, 1500, 650);
        game.batch.draw(mainMenu, 25, 900, 100, 100);
        game.font.getData().setScale(3.0f);

        switch (IPOP.families.get(famIndex).getOcupations().size()) {
            case 1:
                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(0).toUpperCase(), startX, (startY - (marginVertical)));
                firstOcupationBtn = new Rectangle(startX, (startY - (marginVertical) - 40), 1200, 50);

                break;
            case 2:
                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(0).toUpperCase(), startX, (startY - (marginVertical)));
                firstOcupationBtn = new Rectangle(startX, (startY - (marginVertical) - 40), 1200, 50);

                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(1).toUpperCase(), startX, (startY - (marginVertical * 2)));
                secondOcupationBtn = new Rectangle(startX, (startY - (marginVertical * 2) - 40), 1200, 50);
                break;
            case 3:
                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(0).toUpperCase(), startX, (startY - (marginVertical)));
                firstOcupationBtn = new Rectangle(startX, (startY - (marginVertical) - 40), 1200, 50);

                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(1).toUpperCase(), startX, (startY - (marginVertical * 2)));
                secondOcupationBtn = new Rectangle(startX, (startY - (marginVertical * 2) - 40), 1200, 50);

                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(2).toUpperCase(), startX, (startY - (marginVertical * 3)));
                thirdOcupationBtn = new Rectangle(startX, (startY - (marginVertical * 3) - 40), 1200, 50);
                break;
            case 4:
                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(0).toUpperCase(), startX, (startY - (marginVertical)));
                firstOcupationBtn = new Rectangle(startX, (startY - (marginVertical) - 40), 1200, 50);

                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(1).toUpperCase(), startX, (startY - (marginVertical * 2)));
                secondOcupationBtn = new Rectangle(startX, (startY - (marginVertical * 2) - 40), 1200, 50);

                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(2).toUpperCase(), startX, (startY - (marginVertical * 3)));
                thirdOcupationBtn = new Rectangle(startX, (startY - (marginVertical * 3) - 40), 1200, 50);

                game.font.draw(game.batch, IPOP.families.get(famIndex).getOcupations().get(3).toUpperCase(), startX, (startY - (marginVertical * 4)));
                fourthOcupationBtn = new Rectangle(startX, (startY - (marginVertical * 4) - 40), 1200, 50);

                break;
        }

        game.batch.draw(goBack, 200, 400, 100, 100);
        game.batch.draw(goNext, 2000, 400, 100, 100);


        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (goNextBtn.contains(touchX, touchY)) {
                if (famIndex == IPOP.families.size() - 1) {
                    famIndex = 0;
                } else {
                    famIndex++;
                }
            } else if (goBackBtn.contains(touchX, touchY)) {
                if (famIndex == 0) {
                    famIndex = IPOP.families.size() - 1;
                } else {
                    famIndex--;
                }
            } else if (firstOcupationBtn.contains(touchX, touchY)) {
                Player.player_ocupation = IPOP.families.get(famIndex).getOcupations().get(0);
                MainMenuScreen.cicleSelected = true;
                game.setScreen(new MainMenuScreen(game));
            } else if (secondOcupationBtn.contains(touchX, touchY)) {
                Player.player_ocupation = IPOP.families.get(famIndex).getOcupations().get(1);
                MainMenuScreen.cicleSelected = true;
                game.setScreen(new MainMenuScreen(game));
            } else if (thirdOcupationBtn.contains(touchX, touchY)) {
                Player.player_ocupation = IPOP.families.get(famIndex).getOcupations().get(2);
                MainMenuScreen.cicleSelected = true;
                game.setScreen(new MainMenuScreen(game));
            } else if (fourthOcupationBtn.contains(touchX, touchY)) {
                Player.player_ocupation = IPOP.families.get(famIndex).getOcupations().get(3);
                MainMenuScreen.cicleSelected = true;
                game.setScreen(new MainMenuScreen(game));
            } else if (mainMenuBtn.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        game.batch.dispose();
    }

}