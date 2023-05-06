package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.Player;

public class CharacterSelectionScreen implements Screen {

    private Texture background, itemBackground, goBack, goNext;

    private Rectangle goBackBtn, goNextBtn;
    final IPOP game;
    public float stateTime = 0f;

    public CharacterSelectionScreen(IPOP game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("Mansion.png"));
        itemBackground = new Texture(Gdx.files.internal("panel-background.png"));

        goBack = new Texture(Gdx.files.internal("back-button.png"));
        goBackBtn = new Rectangle(200 , 400, 100, 100);

        goNext = new Texture(Gdx.files.internal("next-button.png"));
        goNextBtn = new Rectangle(2000 , 400, 100, 100);

    }

    @Override
    public void show() {  }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        System.out.println(Player.player_show_room.get(Player.player_character));
        TextureRegion frame = Player.player_show_room.get(Player.player_character).getKeyFrame(stateTime, true);
        game.batch.draw(background, 0, 0, 2400, 1080);
        game.batch.draw(itemBackground, 400, 100, 1500, 650);
        game.font.getData().setScale(3.0f);
        game.batch.draw(
                frame,
                Player.transform[0], Player.transform[1],
                0, 0,
                frame.getRegionWidth(), frame.getRegionHeight(),
                6, 6,
                0
        );
        game.batch.draw(goBack, 200, 400, 100, 100);
        game.batch.draw(goNext, 2000, 400, 100, 100);


        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (goNextBtn.contains(touchX, touchY)) {
                if (Player.player_character == Player.player_show_room.size() - 1) {
                    Player.player_character = 0;
                } else { Player.player_character++;}
                System.out.println(Player.player_character);
            } else if (goBackBtn.contains(touchX, touchY)) {
                if (Player.player_character == 0) {
                    Player.player_character = Player.player_show_room.size() - 1;
                } else { Player.player_character--;}
                System.out.println(Player.player_character);
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