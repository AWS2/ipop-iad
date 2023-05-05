package com.mygdx.ipop_game.utils;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.utils.WebSockets;



public class SelectNameScreen implements Screen {

    final Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    Texture background, saveName;
    Rectangle saveNameBtn;
    TextField nameField;

    final IPOP game;
    Stage stage;

    public SelectNameScreen(IPOP game) {
        this.game = game;

        //selectName = new Texture(Gdx.files.internal("button-2.png"));
        background = new Texture(Gdx.files.internal("Mansion.png"));
        saveName = new Texture(Gdx.files.internal("save_name.png"));
        Texture buttonTexture = new Texture(Gdx.files.internal("ui/button.png"));
    }

    @Override
    public void show() {  }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0,0, 2400,1080);
        game.batch.draw(saveName, 900,50, 600,100);


        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(6.5f);
        game.font = titleFont;
        game.font.draw(game.batch, "Select Name", 900,950);

        stage.act();
        stage.draw();
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