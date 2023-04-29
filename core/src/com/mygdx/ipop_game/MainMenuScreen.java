package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MainMenuScreen implements Screen {

    Stage actualStage;

    @Override
    public void show() {
        switch (SceneController.scene) {
            case 0:
                System.out.println("Scene 0");
                actualStage = this.loadMainMenu();
                break;
            case 1:
                System.out.println("Scene 1");
                actualStage = this.loadSelectName();
                break;
            case 2:
                System.out.println("Scene 2");
                actualStage = this.loadCharacterSelector();
                break;
            case 3:
                System.out.println("Scene 3");
                actualStage = this.loadSelectName();
                break;
            case 4:
                System.out.println("Scene 4");
                actualStage = this.loadSinglePlayer();
                break;
            case 5:
                System.out.println("Scene 5");
                actualStage = this.loadMultiplayer();
                break;
            case 6:
                System.out.println("Scene 3");
                actualStage = this.loadRankings();
                break;
            default:
                actualStage = this.loadMainMenu();
                break;
        }
    }

    private Stage loadMainMenu() {
        actualStage = SceneController.getStageActual();
        final TextButton title, selectName, selectCharacter, selectGrade, singlePlayer, multiplayer, rankings;
        Gdx.input.setInputProcessor(actualStage);
        Skin skin = new Skin();
        // Crear un estilo para el TextButton
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));

        // Asignar el estilo al Skin
        skin.add("default", textButtonStyle, TextButton.TextButtonStyle.class);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4.0f);
        textButtonStyle.font = font;

        title = new TextButton("IPOP GAME", skin);
        title.setBounds(-200,800,2700,100);
        title.setStyle(textButtonStyle);
        actualStage.addActor(title);

        font = new BitmapFont();
        font.getData().setScale(2.0f);
        textButtonStyle.font = font;

        // Crear el TextButton y añadirlo al Stage
        selectName = new TextButton("Triar Nom", skin);
        selectName.setBounds(800,600,700,100);
        selectName.setStyle(textButtonStyle);
        actualStage.addActor(selectName);

        selectName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectName.setText("¡Hizo clic en el botón!");
                SceneController.scene = 0;
            }
        });

        selectCharacter = new TextButton("Triar Personatge", skin);
        selectCharacter.setBounds(800,500,700,100);
        selectCharacter.setStyle(textButtonStyle);
        actualStage.addActor(selectCharacter);

        selectCharacter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectCharacter.setText("¡Hizo clic en el botón!");
                SceneController.scene = 1;
            }
        });

        selectGrade = new TextButton("Triar Cicle", skin);
        selectGrade.setBounds(800,400,700,100);
        selectGrade.setStyle(textButtonStyle);
        actualStage.addActor(selectGrade);

        selectGrade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectGrade.setText("¡Hizo clic en el botón!");
                SceneController.scene = 2;
            }
        });

        singlePlayer = new TextButton("Standalone", skin);
        singlePlayer.setBounds(800,300,700,100);
        singlePlayer.setStyle(textButtonStyle);
        actualStage.addActor(singlePlayer);
        singlePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                singlePlayer.setText("¡Hizo clic en el botón!");
                SceneController.scene = 3;
            }
        });

        multiplayer = new TextButton("Multiplayer", skin);
        multiplayer.setBounds(800,200,700,100);
        multiplayer.setStyle(textButtonStyle);
        actualStage.addActor(multiplayer);
        multiplayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                multiplayer.setText("¡Hizo clic en el botón!");
                SceneController.scene = 4;
            }
        });

        rankings = new TextButton("Veure Rankings", skin);
        rankings.setBounds(800,100,700,100);
        rankings.setStyle(textButtonStyle);
        actualStage.addActor(rankings);
        rankings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rankings.setText("¡Hizo clic en el botón!");
                SceneController.scene = 5;
            }
        });
        return actualStage;
    }

    private Stage loadSelectName() { return actualStage; }

    private Stage loadCharacterSelector() { return actualStage; }

    private Stage loadSinglePlayer() {
        return actualStage;
    }

    private Stage loadMultiplayer() { return actualStage; }

    private Stage loadRankings() {
        System.out.println("here");
        final TextButton title, selectName, selectCharacter, selectGrade, singlePlayer, multiplayer, rankings;
        Gdx.input.setInputProcessor(actualStage);
        Skin skin = new Skin();
        // Crear un estilo para el TextButton
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));

        // Asignar el estilo al Skin
        skin.add("default", textButtonStyle, TextButton.TextButtonStyle.class);


        BitmapFont font = new BitmapFont();
        font.getData().setScale(4.0f);
        textButtonStyle.font = font;

        title = new TextButton("Rankings", skin);
        title.setBounds(-200,800,2700,100);
        title.setStyle(textButtonStyle);
        actualStage.addActor(title);

        font = new BitmapFont();
        font.getData().setScale(2.0f);
        textButtonStyle.font = font;

        // Crear el TextButton y añadirlo al Stage
        selectName = new TextButton("Go Back", skin);
        selectName.setBounds(800,600,300,100);
        selectName.setStyle(textButtonStyle);
        actualStage.addActor(selectName);

        selectName.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectName.setText("¡Hizo clic en el botón!");
                SceneController.goToMainMenu();
            }
        });
        return actualStage;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        actualStage.act(delta);
        actualStage.draw();
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