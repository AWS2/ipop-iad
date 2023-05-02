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

    Integer number = 0;
    Boolean firstTime = true;
    Table familia = new Table(),cicles = new Table();
    TextButton degreeNameBtn,exitBtn,degreeBtn;
    TextButton.TextButtonStyle selected,exit,currentDegree;
    @Override
    public void show() {
        switch (SceneController.scene) {
            case 1:
                actualStage = this.loadSelectName();
                break;
            case 2:
                actualStage = this.loadCharacterSelector();
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

    private Stage loadDegreeSelector() {
        actualStage = SceneController.getStageActual();
        actualStage.clear();
        Gdx.input.setInputProcessor(actualStage);

        final TextButton title;
        TextButton family;
        IPOP.loadResources();
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

        title = new TextButton("Select Familiy", skin);
        title.setBounds(-200,800,2700,100);
        title.setStyle(textButtonStyle);
        actualStage.addActor(title);

        font = new BitmapFont();
        font.getData().setScale(2.5f);
        textButtonStyle.font = font;
        int startX= 800, startY = 650;
        int item_heigth= 100;
        for (int i = 0; i < IPOP.families.size(); i++) {
            family = new TextButton(IPOP.families.get(i), skin);
            if (IPOP.families.get(i).length() > 30) {
                family.setBounds(startX-100,startY,900,item_heigth);
            } else {
                family.setBounds(startX,startY,700,item_heigth);

            }
            family.setStyle(textButtonStyle);
            startY -= (item_heigth + 25);
            family.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dropSound.play();
                    SceneController.scene = 1;
                    actualStage = SceneController.selectNamePlayerStage;
                }
            });
            actualStage.addActor(family);
        }

        return actualStage;
    }

    private Stage loadMainMenu() {
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
                actualStage = loadCharacterSelector();
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

    int stateTime = 0;
    int pasada = 0;
    private Stage loadCharacterSelector() {
        System.out.println("Inside");
        final int[] direction = {0};
        actualStage.clear();
        actualStage = SceneController.getStageActual();
        final TextButton title, goBack;
        IPOP.loadResources();
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

        title = new TextButton("Character Show Room", skin);
        title.setBounds(-200,800,2700,100);
        title.setStyle(textButtonStyle);
        actualStage.addActor(title);

        BitmapFont font1 = new BitmapFont();
        font1.getData().setScale(2.0f);
        textButtonStyle.font = font1;

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

        Rectangle player = new Rectangle();
        player.width = 50;
        player.height = 50;
        player.x = SCR_WIDTH / 2;
        player.y = SCR_HEIGHT / 2;
        player.setPosition(player.x, player.y);

        TextureRegion up[] = new TextureRegion[4];
        TextureRegion down[] = new TextureRegion[4];
        TextureRegion left[] = new TextureRegion[4];
        TextureRegion right[] = new TextureRegion[4];

        Texture playerTexture = new Texture("IPOP-Walking.png");
        down[0] = new TextureRegion(playerTexture,0,0,50,50);
        down[1] = new TextureRegion(playerTexture,50,0,50,50);
        down[2] = new TextureRegion(playerTexture,100,0,50,50);
        down[3] = new TextureRegion(playerTexture,150,0,50,50);

        left[0] = new TextureRegion(playerTexture,0,50,50,50);
        left[1] = new TextureRegion(playerTexture,50,50,50,50);
        left[2] = new TextureRegion(playerTexture,100,50,50,50);
        left[3] = new TextureRegion(playerTexture,150,50,50,50);

        right[0] = new TextureRegion(playerTexture,0,100,50,50);
        right[1] = new TextureRegion(playerTexture,50,100,50,50);
        right[2] = new TextureRegion(playerTexture,100,100,50,50);
        right[3] = new TextureRegion(playerTexture,150,100,50,50);

        up[0] = new TextureRegion(playerTexture,0,150,50,50);
        up[1] = new TextureRegion(playerTexture,50,150,50,50);
        up[2] = new TextureRegion(playerTexture,100,150,50,50);
        up[3] = new TextureRegion(playerTexture,150,150,50,50);

        Animation<TextureRegion> playerAnimation = new Animation<TextureRegion>(0.25f,right);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = playerAnimation.getKeyFrame(stateTime,true);
        Image rightAnimation = walkAnimation("Right");
        rightAnimation.setBounds(1025,375,50,50);
        actualStage.addActor(rightAnimation);
        Image leftAnimation = walkAnimation("Left");
        leftAnimation.setBounds(925,375,50,50);
        actualStage.addActor(leftAnimation);
        Image downAnimation = walkAnimation("Down");
        downAnimation.setBounds(1125,375,50,50);
        actualStage.addActor(downAnimation);
        Image upAnimation = walkAnimation("Up");
        upAnimation.setBounds(1225,375,50,50);
        actualStage.addActor(upAnimation);
        //actualStage.addActor(frame);
       /* up = new Rectangle(0, SCR_HEIGHT * 2 / 3f, SCR_WIDTH, SCR_HEIGHT / 3f);
        down = new Rectangle(0, 0, SCR_WIDTH, SCR_HEIGHT / 3f);
        left = new Rectangle(0, 0, SCR_WIDTH / 3f, SCR_HEIGHT);
        right = new Rectangle(SCR_WIDTH * 2 / 3f, 0, SCR_WIDTH / 3f, SCR_HEIGHT);

        actualStage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (up.contains(x, y)) {
                    direction[0] = 0;
                    return true;
                } else if (down.contains(x, y)) {
                    direction[0] = 1;
                    return true;
                } else if (left.contains(x, y)) {
                    direction[0] = 2;
                    return true;
                } else {
                    direction[0] = 3;
                    return right.contains(x, y);
                }
            }
        });

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame;
        Texture shadow = new Texture(Gdx.files.internal("Shadow1.png"));

        switch (direction[0]){
            case 0:
                frame = Player.player_up.getKeyFrame(stateTime, true);
                break;
            case 1:
                frame = Player.player_down.getKeyFrame(stateTime, true);
                break;
            case 2:
                frame = Player.player_left.getKeyFrame(stateTime, true);
                break;
            case 3:
                frame = Player.player_right.getKeyFrame(stateTime, true);
                break;
            default: // IDLE
                frame = Player.player_down.getKeyFrames()[1];
                break;
        }

        Image playerImg = new Image(frame);
        Image shadowImg = new Image(shadow);
        shadowImg.setBounds(750,300,750,550);
        actualStage.addActor(shadowImg);
        playerImg.setBounds(1025,375,200,200);
        actualStage.addActor(playerImg);*/
        return actualStage;
    }

    protected int virtual_joystick_control() {
        for (int i = 0; i < 10; i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                if (up.contains(touchPos.x, touchPos.y)) {
                    return 0;
                } else if (down.contains(touchPos.x, touchPos.y)) {
                    return 1;
                } else if (left.contains(touchPos.x, touchPos.y)) {
                    return 2;
                } else if (right.contains(touchPos.x, touchPos.y)) {
                    return 3;
                }
            }
        return 4;
    }

    private Image walkAnimation(String direction) {
        Image stageFrame;
        if (direction.equals("Right")) {
            TextureRegion right[] = new TextureRegion[4];
            Texture playerTexture = new Texture("IPOP-Walking.png");
            if(pasada+1 < 3) {
                pasada++;
            } else {
                pasada = 0;
            }

            //right[0] = new TextureRegion(playerTexture,number*50,100,50,50);
        /*right[1] = new TextureRegion(playerTexture,50,100,50,50);
        right[2] = new TextureRegion(playerTexture,100,100,50,50);
        right[3] = new TextureRegion(playerTexture,150,100,50,50);*/
            Animation<TextureRegion> playerAnimation = new Animation<TextureRegion>(0.25f,right);
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion frame = playerAnimation.getKeyFrame(stateTime,true);
           stageFrame  = new Image( new TextureRegion(playerTexture,pasada*50,100,50,50));

        } else if (direction.equals("Left")) {
            TextureRegion left[] = new TextureRegion[4];
            Texture playerTexture = new Texture("IPOP-Walking.png");
            if(pasada+1 < 3) {
                pasada++;
            } else {
                pasada = 0;
            }

            stageFrame  = new Image( new TextureRegion(playerTexture,pasada*50,50,50,50));
        }   else if (direction.equals("Down")) {
            TextureRegion down[] = new TextureRegion[4];
            Texture playerTexture = new Texture("IPOP-Walking.png");
            if(pasada+1 < 3) {
                pasada++;
            } else {
                pasada = 0;
            }

            stageFrame  = new Image( new TextureRegion(playerTexture,pasada*50,0,50,50));
        } else {
            TextureRegion up[] = new TextureRegion[4];
            Texture playerTexture = new Texture("IPOP-Walking.png");
            if(pasada+1 < 3) {
                pasada++;
            } else {
                pasada = 0;
            }

            stageFrame  = new Image( new TextureRegion(playerTexture,pasada*50,100,50,50));
        }
        return stageFrame;

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

        top6 = new TextButton(rankings.get(0).name + " - " + rankings.get(0).score, skin);
        top6.setBounds(1300,650,450,100);
        top6.setStyle(textButtonStyle);
        actualStage.addActor(top6);

        top7 = new TextButton(rankings.get(1).name + " - " + rankings.get(1).score, skin);
        top7.setBounds(1300,525,450,100);
        top7.setStyle(textButtonStyle);
        actualStage.addActor(top7);

        top8 = new TextButton(rankings.get(2).name + " - " + rankings.get(2).score, skin);
        top8.setBounds(1300,400,450,100);
        top8.setStyle(textButtonStyle);
        actualStage.addActor(top8);

        top9 = new TextButton(rankings.get(3).name + " - " + rankings.get(3).score, skin);
        top9.setBounds(1300,275,450,100);
        top9.setStyle(textButtonStyle);
        actualStage.addActor(top9);

        top10 = new TextButton(rankings.get(4).name + " - " + rankings.get(4).score, skin);
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
            this.loadCharacterSelector();
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