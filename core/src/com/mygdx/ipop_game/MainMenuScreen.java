package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

import javax.lang.model.type.ArrayType;


public class MainMenuScreen extends ApplicationAdapter implements Screen {

    Stage actualStage;
    final Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    Rectangle up, down, left, right;

    String player_ocupation = "";

    @Override
    public void show() {
        switch (SceneController.scene) {
            case 1:
                actualStage = this.loadSelectName();
                break;
            case 2:
                //actualStage = this.loadCharacterSelector();
                break;
            case 3:
                actualStage = this.loadDegreeSelector();
                break;
            case 4:
                actualStage = this.loadSinglePlayer();
                break;
            case 5:
                actualStage = this.loadMultiplayer();
                break;
            case 6:
                actualStage = this.loadRankings();
                break;
            default:
                actualStage = this.loadMainMenu();
                break;
        }
    }

    private int famIndex = 0;
    private int famMax;

    private Stage loadDegreeSelector() {
        actualStage = SceneController.getStageActual();
        actualStage.clear();
        Gdx.input.setInputProcessor(actualStage);

        final TextButton title, subtitle;
        final TextButton[] family = {null};
        TextButton goBack;
        TextButton goNext;
        famMax = IPOP.families.size();

        final Skin defaultSkin = new Skin();
        Skin noSkin = new Skin();
        Skin goBackSkin = new Skin();
        Skin goNextSkin = new Skin();
        final TextButton.TextButtonStyle defaultBtnStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle noSkinBtnStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle goBackStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle goNextStyle = new TextButton.TextButtonStyle();
        defaultBtnStyle.font = new BitmapFont();
        noSkinBtnStyle.font = new BitmapFont();
        goBackStyle.font = new BitmapFont();
        goNextStyle.font = new BitmapFont();
        defaultBtnStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button-2.png")));
        goBackStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("back-button.png")));
        goNextStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("next-button.png")));
        defaultBtnStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("button-2.png")));
        goBackStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("back-button.png")));
        goNextStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("next-button.png")));

        defaultSkin.add("default", defaultBtnStyle, TextButton.TextButtonStyle.class);
        noSkin.add("default", noSkinBtnStyle, TextButton.TextButtonStyle.class);
        goBackSkin.add("default", goBackStyle, TextButton.TextButtonStyle.class);
        goNextSkin.add("default", goNextStyle, TextButton.TextButtonStyle.class);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4.0f);
        defaultBtnStyle.font = font;
        noSkinBtnStyle.font = font;

        title = new TextButton("Select Familiy", noSkin);
        title.setBounds(-200,900,2700,100);
        title.setStyle(noSkinBtnStyle);
        actualStage.addActor(title);

        subtitle = new TextButton(IPOP.families.get(famIndex).getName(), noSkin);
        subtitle.setBounds(-200,800,2700,100);
        subtitle.setStyle(noSkinBtnStyle);
        actualStage.addActor(subtitle);

        goBack = new TextButton("", goBackSkin);
        goBack.setBounds(200,400,115,115);
        goBack.setStyle(goBackStyle);
        goBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                if (famIndex == 0) { famIndex = IPOP.families.size() - 1; }
                else { famIndex -= 1; }
                System.out.println(famIndex);
                loadDegreeSelector();
            }
        });
        actualStage.addActor(goBack);

        goNext = new TextButton("", goNextSkin);
        goNext.setBounds(2000,400,115,115);
        goNext.setStyle(goNextStyle);
        goNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                if (famIndex == IPOP.families.size() - 1) { famIndex = 0; }
                else { famIndex += 1; }
                System.out.println(famIndex);

                loadDegreeSelector();
            }
        });
        actualStage.addActor(goNext);

        font = new BitmapFont();
        font.getData().setScale(2.5f);
        defaultBtnStyle.font = font;

        int startX= 800, startY = 650, item_heigth= 100;
        System.out.println(IPOP.families.get(famIndex).toString());
        System.out.println(IPOP.families.get(famIndex).getOcupations().size());
        System.out.println(IPOP.families.get(famIndex).getOcupations().toString());
        for (int i = 0; i < IPOP.families.get(famIndex).getOcupations().size(); i++) {
            family[0] = new TextButton(IPOP.families.get(famIndex).getOcupations().get(i), defaultSkin);
            if (IPOP.families.get(famIndex).getOcupations().get(i).length() > 30) {
                family[0].setBounds(startX-100,startY,900,item_heigth);
                family[0].setName(IPOP.families.get(famIndex).getOcupations().get(i));
            } else {
                family[0].setBounds(startX,startY,700,item_heigth);
                family[0].setName(IPOP.families.get(famIndex).getOcupations().get(i));
            }
            family[0].setStyle(defaultBtnStyle);
            startY -= (item_heigth + 25);
            family[0].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dropSound.play();
                    player_ocupation = event.getListenerActor().getName();
                    SceneController.scene = 1;
                    actualStage = loadMainMenu();
                }
            });
            actualStage.addActor(family[0]);
        }

        return actualStage;
    }

    private Stage loadMainMenu() {
        IPOP.loadResources();
        actualStage = SceneController.getStageActual();
        actualStage.clear();
        final TextButton title, selectName, selectCharacter, selectGrade, singlePlayer, multiplayer, rankings;
        Gdx.input.setInputProcessor(actualStage);
        Skin skin = new Skin();
        // Crear un estilo para el TextButton
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("button-2.png")));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("button-2.png")));

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
                dropSound.play();
                SceneController.scene = 1;
                actualStage = SceneController.selectNamePlayerStage;
            }
        });

        selectCharacter = new TextButton("Triar Personatge", skin);
        selectCharacter.setBounds(800,500,700,100);
        selectCharacter.setStyle(textButtonStyle);
        actualStage.addActor(selectCharacter);

        selectCharacter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                SceneController.scene = 2;
                //actualStage = loadCharacterSelector();
            }
        });

        selectGrade = new TextButton("Triar Cicle", skin);
        selectGrade.setBounds(800,400,700,100);
        selectGrade.setStyle(textButtonStyle);
        actualStage.addActor(selectGrade);

        selectGrade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                SceneController.scene = 3;
                System.out.println(SceneController.scene);
                actualStage = loadDegreeSelector();
            }
        });

        singlePlayer = new TextButton("Standalone", skin);
        singlePlayer.setBounds(800,300,700,100);
        singlePlayer.setStyle(textButtonStyle);
        actualStage.addActor(singlePlayer);
        singlePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
            }
        });

        multiplayer = new TextButton("Multiplayer", skin);
        multiplayer.setBounds(800,200,700,100);
        multiplayer.setStyle(textButtonStyle);
        actualStage.addActor(multiplayer);
        multiplayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                SceneController.scene = 4;
                actualStage = SceneController.multiPlayerStage;
            }
        });

        rankings = new TextButton("Veure Rankings", skin);
        rankings.setBounds(800,100,700,100);
        rankings.setStyle(textButtonStyle);
        actualStage.addActor(rankings);
        rankings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                SceneController.scene = 4;
                actualStage = loadRankings();
            }
        });
        return actualStage;
    }

    private Stage loadSelectName() {
        actualStage.clear();
        return actualStage;
    }

    private Stage loadSinglePlayer() {
        actualStage.clear();
        return actualStage;
    }

    private Stage loadMultiplayer() {
        actualStage.clear();
        return actualStage;
    }

    private Stage loadRankings() {
        actualStage.clear();
        ArrayList<Record> rankings = new ArrayList<>();
        rankings.add(new Record(1,10, "Nerea"));
        rankings.add(new Record(2,9, "Nerea"));
        rankings.add(new Record(3,8, "Nerea"));
        rankings.add(new Record(4,7, "Ivan"));
        rankings.add(new Record(5,6, "Ivan"));
        rankings.add(new Record(6,5, "Nerea"));
        rankings.add(new Record(7,4, "Ivan"));
        rankings.add(new Record(8,3, "Ivan"));
        rankings.add(new Record(9,2, "Nerea"));
        rankings.add(new Record(10,1, "Ivan"));

        final TextButton title, top1, top2, top3, top4, top5, top6, top7, top8, top9, top10, next, back, goBack;
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
        goBack = new TextButton("Go Back", skin);
        goBack.setBounds(25,25,300,100);
        goBack.setStyle(textButtonStyle);

        goBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
                SceneController.scene = 8;
                actualStage = loadMainMenu();
            }
        });
        actualStage.addActor(goBack);


        back = new TextButton("<= ", skin);
        back.setBounds(50,400,100,100);
        back.setStyle(textButtonStyle);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
            }
        });
        actualStage.addActor(back);

        top1 = new TextButton(rankings.get(0).name + " - " + rankings.get(0).score, skin);
        top1.setBounds(500,650,450,100);
        top1.setStyle(textButtonStyle);
        actualStage.addActor(top1);

        top2 = new TextButton(rankings.get(1).name + " - " + rankings.get(1).score, skin);
        top2.setBounds(500,525,450,100);
        top2.setStyle(textButtonStyle);
        actualStage.addActor(top2);

        top3 = new TextButton(rankings.get(2).name + " - " + rankings.get(2).score, skin);
        top3.setBounds(500,400,450,100);
        top3.setStyle(textButtonStyle);
        actualStage.addActor(top3);

        top4 = new TextButton(rankings.get(3).name + " - " + rankings.get(3).score, skin);
        top4.setBounds(500,275,450,100);
        top4.setStyle(textButtonStyle);
        actualStage.addActor(top4);

        top5 = new TextButton(rankings.get(4).name + " - " + rankings.get(4).score, skin);
        top5.setBounds(500,150,450,100);
        top5.setStyle(textButtonStyle);
        actualStage.addActor(top5);

        top6 = new TextButton(rankings.get(5).name + " - " + rankings.get(5).score, skin);
        top6.setBounds(1300,650,450,100);
        top6.setStyle(textButtonStyle);
        actualStage.addActor(top6);

        top7 = new TextButton(rankings.get(6).name + " - " + rankings.get(6).score, skin);
        top7.setBounds(1300,525,450,100);
        top7.setStyle(textButtonStyle);
        actualStage.addActor(top7);

        top8 = new TextButton(rankings.get(7).name + " - " + rankings.get(7).score, skin);
        top8.setBounds(1300,400,450,100);
        top8.setStyle(textButtonStyle);
        actualStage.addActor(top8);

        top9 = new TextButton(rankings.get(8).name + " - " + rankings.get(8).score, skin);
        top9.setBounds(1300,275,450,100);
        top9.setStyle(textButtonStyle);
        actualStage.addActor(top9);

        top10 = new TextButton(rankings.get(9).name + " - " + rankings.get(9).score, skin);
        top10.setBounds(1300,150,450,100);
        top10.setStyle(textButtonStyle);
        actualStage.addActor(top10);

        next = new TextButton(" => ", skin);
        next.setBounds(2000,400,100,100);
        next.setStyle(textButtonStyle);
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dropSound.play();
            }
        });
        actualStage.addActor(next);

        return actualStage;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        actualStage.act(delta);

        if (SceneController.scene == 2) {
            //this.loadCharacterSelector();
            actualStage.draw();

        } else {
            actualStage.draw();
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