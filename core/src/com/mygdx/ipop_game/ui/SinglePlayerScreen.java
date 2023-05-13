package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.GameRecord;
import com.mygdx.ipop_game.models.Ocupacio;
import com.mygdx.ipop_game.models.Player;
import com.mygdx.ipop_game.models.Totem;

import java.time.Instant;
import java.util.ArrayList;

public class SinglePlayerScreen implements Screen {

    final IPOP game;
    Ocupacio ocupacioObject;
    int screenWidth = Gdx.graphics.getWidth(), screenHeight = Gdx.graphics.getHeight();
    int score = 0;
    int pasada = 0;
    Float stateTime = 0.0f;
    SpriteBatch batch;
    Rectangle upPad, downPad, leftPad, rightPad, playerRectangle, homeBtn;
    Texture background = new Texture("Map003.png");
    Texture home = new Texture("menu_button.png");
    Texture scoreBar;
    Texture totemSprite = new Texture("totem.png");
    String direction, currentDirection;
    private static OrthographicCamera camera;
    Boolean moving, soundPlayed = false;
    BitmapFont font = new BitmapFont(), scoreFont = new BitmapFont();

    Animation<TextureRegion> player;

    float maxWidth = 200;
    float scrollSpeed = 200.0f;
    float textHeight = font.getLineHeight();
    Instant startPlaying;
    ArrayList<Totem> totemsCorrectes = new ArrayList<>();
    ArrayList<Totem> totemsIncorrectes = new ArrayList<>();
    ArrayList<Totem> activeOnFieldTotems = new ArrayList<>();
    ArrayList<String> ocupacioInicial = new ArrayList<>();

    Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    Sound cyndaquilSound = Gdx.audio.newSound(Gdx.files.internal("CYNDAQUIL.wav"));
    int TOTEMS_TO_REACH = 5;
    int corTotems = 0;
    int totalTotems = 0;

    public SinglePlayerScreen(IPOP game) {
        camera = new OrthographicCamera();
        this.game = game;
        batch = new SpriteBatch();
        direction = "right";
        currentDirection = "right";
        playerRectangle = new Rectangle();
        playerRectangle.setX(50);
        playerRectangle.setY(50);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        //TouchPads
        upPad = new Rectangle(0, screenHeight*2/3, screenWidth, screenHeight);
        downPad = new Rectangle(0, 0, screenWidth, screenHeight/3);
        leftPad = new Rectangle(0, 0, screenWidth/3, screenHeight);
        rightPad = new Rectangle(screenWidth*2/3, 0, screenWidth/3, screenHeight);

        homeBtn = new Rectangle(100,900,100,100);

        font.getData().setScale(3);
        font.setColor(Color.RED);
        scoreFont.getData().setScale(5);
        scoreFont.getData().setLineHeight(3);
        scoreFont.setColor(Color.WHITE);

        generacioTotems();
        startPlaying = Instant.now();
        this.render(Gdx.graphics.getDeltaTime());
    }

    //Metodo que llamaremos cada vez que el usuario colisione contra el Totem correcto hasta 5 veces
    private void generacioTotems() {
        //todo fer que el text es fiqui damunt del totem
        for (int i = 0; i < 3; i++) {

            Rectangle totemBox = new Rectangle();
            GlyphLayout glyphLayout = new GlyphLayout();
            float textX = 0;


            //Comprovar que no hi hagi totems en aquella posicio

            //Despues de la primera pasada se añadiran los incorrectos verificando su posicion
            if (i > 0) {
                String ocupacio = "";
                if (i == 1) {
                    ocupacio = llistaOcupacions("Gestio administrativa");
                } else {
                    ocupacio = llistaOcupacions("Electromecanica de vehicles automobils");
                }

                System.out.println(totemsCorrectes);
                Totem totem = new Totem(1, MathUtils.random(screenWidth-300),MathUtils.random(screenHeight-300),192,192,totemSprite,"Informatica",ocupacio,totemBox,glyphLayout,textX,dropSound,false);
                totemBox.setPosition(totem.getX(),totem.getY()+50);
                totemBox.setWidth(300);
                totem.setTextX(totemBox.getX()+totemBox.getWidth());
                //Layout
                glyphLayout.setText(font,ocupacio);
                for (int j = 0; j < totemsCorrectes.size(); j++) {
                    if (j < activeOnFieldTotems.size() && activeOnFieldTotems.get(j).getX() != totem.getX() && activeOnFieldTotems.get(j).getY() != totem.getY()) {
                        activeOnFieldTotems.add(totem);
                        ocupacioInicial.add(activeOnFieldTotems.get(i).getOcupacio());
                        totemsIncorrectes.add(totem);
                    }

                }

                //Durante la primera pasada añadiremos el totem correcto
            } else {
                String ocupacio = llistaOcupacions(Player.player_ocupation);

                glyphLayout.setText(font,ocupacio);
                Totem totem = new Totem(1, MathUtils.random(screenWidth-300),MathUtils.random(screenHeight-300),192,192    ,totemSprite,"Informatica",ocupacio,totemBox,glyphLayout,textX,cyndaquilSound,true);
                totemBox.setPosition(totem.getX(),totem.getY()+50);
                totemBox.setWidth(300);
                totem.setTextX(totemBox.getX()+totemBox.getWidth());
                //Layout
                activeOnFieldTotems.add(totem);
                ocupacioInicial.add(activeOnFieldTotems.get(i).getOcupacio());
                totemsCorrectes.add(totem);
            }
        }
    }

    private void drawTotems (ArrayList<Totem> activeOnFieldTotems) {
        for (int i = 0; i < activeOnFieldTotems.size(); i++) {
            //Dibujar el Totem y la Imagen
            batch.draw(activeOnFieldTotems.get(i).getImage(),activeOnFieldTotems.get(i).getX(),activeOnFieldTotems.get(i).getY());
            font.draw(batch,activeOnFieldTotems.get(i).getGlyphLayout(),activeOnFieldTotems.get(i).getTextX(),activeOnFieldTotems.get(i).getTextBox().getY());
            //batch.draw(activeOnFieldTotems.get(0).getImage(),activeOnFieldTotems.get(0).getX(),activeOnFieldTotems.get(0).getY());
            //font.draw(batch,glyphLayout,textX,textBox.getY());
            float newTextX = activeOnFieldTotems.get(i).getTextX() - (scrollSpeed * Gdx.graphics.getDeltaTime());
            activeOnFieldTotems.get(i).setTextX(newTextX);
            //activeOnFieldTotems.get(i).setTextX(activeOnFieldTotems.get(i).getTextX() -= scrollSpeed * Gdx.graphics.getDeltaTime()) ;


            float elapsedTime = 0f;
            //Revisar aixo i quan es va actualitzant realment
            float updateInterval = 0.01f;
            //todo Revisar porque no me hace las pasadas del substring
            if (activeOnFieldTotems.get(i).getTextBox().x > activeOnFieldTotems.get(i).getTextX() + activeOnFieldTotems.get(i).getGlyphLayout().width) {
                activeOnFieldTotems.get(i).setTextX(activeOnFieldTotems.get(i).getTextBox().x+activeOnFieldTotems.get(i).getTextBox().getWidth());
                //Tornar a generar el String inicial
                elapsedTime = 0f; // reiniciar el temporizador
                activeOnFieldTotems.get(i).setOcupacio(ocupacioInicial.get(i));
            } else {
                // actualizar solo si ha pasado suficiente tiempo
                elapsedTime += Gdx.graphics.getDeltaTime();
                if (elapsedTime >= updateInterval) {
                    if (activeOnFieldTotems.get(i).getOcupacio().length() > 1) {
                        activeOnFieldTotems.get(i).getGlyphLayout().setText(font,activeOnFieldTotems.get(i).getOcupacio().substring(1));
                        if (activeOnFieldTotems.get(i).getTextBox().x > activeOnFieldTotems.get(i).getTextX() + activeOnFieldTotems.get(i).getGlyphLayout().width) {

                            String substring = activeOnFieldTotems.get(i).getOcupacio().substring(1);
                            activeOnFieldTotems.get(i).setOcupacio(substring);
                            substring = activeOnFieldTotems.get(i).getOcupacio().substring(1);
                            activeOnFieldTotems.get(i).getGlyphLayout().setText(font,substring);
                            System.out.println(substring);
                            activeOnFieldTotems.get(i).setTextX(activeOnFieldTotems.get(i).getTextX() - scrollSpeed * Gdx.graphics.getDeltaTime());
                            elapsedTime = 0f; // reiniciar el temporizador
                        } else {
                            activeOnFieldTotems.get(i).getGlyphLayout().setText(font,ocupacioInicial.get(i));

                        }

                    }

                }
            }
        }
    }

    private String llistaOcupacions(String cicle) {
        if (pasada > 4) {
            pasada = 0;
        }
        ArrayList<String> ocupacions = new ArrayList<>();

        if (cicle.equals("Sistemes microinformatics i xarxes")) {
            ocupacions.add("Personal tècnic instal·lador-reparador d’equips informàtics");
            ocupacions.add("Personal tècnic de suport informàtic.");
            ocupacions.add("Personal tècnic de xarxes de dades. ");
            ocupacions.add("Comercials de microinformàtica. ");
            ocupacions.add("Personal operador de sistemes.");
        } else if (cicle.equals("Gestio administrativa")) {
            ocupacions.add("Recepcionista. ");
            ocupacions.add("Personal auxiliar administratiu.");
            ocupacions.add("Personal ajudant d oficina. ");
            ocupacions.add("Personal administratiu comercial. ");
            ocupacions.add("Personal empleat de tresoreria. ");
        } else if (cicle.equals("Electromecanica de vehicles automobils")) {
            ocupacions.add("Electronicistes de vehicles. ");
            ocupacions.add("Personal mecànic d automòbils. ");
            ocupacions.add("Electricistes d automòbils. ");
            ocupacions.add("Personal electromecànic d automòbils. ");
            ocupacions.add("Personal reparador de sistemes pneumàtics i hidràulics");
        } else {
            ocupacions.add("Personal ajustador operari de màquines eina.");
            ocupacions.add("Personal polidor de metalls i afilador d eines. ");
            ocupacions.add("Personal operador de màquines eina. ");
            ocupacions.add("Personal operador de robots industrials. ");
            ocupacions.add("Personal torner, fresador i mandrinador.");
        }

        return ocupacions.get(pasada);
    }
    @Override
    public void show() {  }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        moving = false;

        if (corTotems == TOTEMS_TO_REACH) {
            game.setScreen(new EndGameScreen(game, new GameRecord(
                    corTotems,totalTotems, Player.player_ocupation, Player.player_alias, startPlaying, Instant.now()
            )));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = "left";
            moving = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))  {
            direction = "right";
            moving = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction = "up";
            moving = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction = "down";
            moving = true;
        }
        currentDirection = direction;
        direction = virtual_joystick_control();
        walkDirection(direction,moving);

        //Limit screen movement
        if(playerRectangle.x < 0) playerRectangle.x = 0;
        if(playerRectangle.x > screenWidth - 150) playerRectangle.x = screenWidth - 150;
        if(playerRectangle.y < 0) playerRectangle.y = 0;
        if(playerRectangle.y > screenHeight - 150) playerRectangle.y = screenHeight - 150;

        //Revisar que no haya colision
        for (int i = 0; i < activeOnFieldTotems.size(); i++) {
            //Que la X no sea igual o menor + la width

            if ((playerRectangle.x >= activeOnFieldTotems.get(i).getX()) && playerRectangle.x <=
                    activeOnFieldTotems.get(i).getX()+activeOnFieldTotems.get(i).getWidth()) {
                if ((playerRectangle.y >= activeOnFieldTotems.get(i).getY()) && playerRectangle.y <=
                        activeOnFieldTotems.get(i).getY()+activeOnFieldTotems.get(i).getHeight()) {
                    //Limita el sonido
                    if (!soundPlayed) {
                        activeOnFieldTotems.get(i).getSound().play();
                        //Verifica si hay que actualizar todos los totems si es correcto
                        if (activeOnFieldTotems.get(i).getCorrectTotem()) {
                            corTotems++;
                            totalTotems++;
                            pasada++;
                            System.out.println(corTotems);
                            activeOnFieldTotems.clear();
                            ocupacioInicial.clear();
                            generacioTotems();
                            //O solo eliminar el incorrecto
                        } else {
                            corTotems--;
                            totalTotems++;
                            System.out.println(corTotems);
                            System.out.println(totalTotems);
                            activeOnFieldTotems.remove(i);
                        }
                    }
                }
            }
        }

        batch.begin();
        batch.draw(background,0,0);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        TextureRegion frame = player.getKeyFrame(stateTime,true);
        batch.draw(frame,playerRectangle.getX(),playerRectangle.getY(),Player.scale[0],Player.scale[1]);
        batch.draw(home, 100,900,100,100);
        if (corTotems > 0){
            batch.draw(IPOP.score_bar[corTotems], 800,950,750,100);
        } else {
            if (corTotems == 0) {
                batch.draw(IPOP.score_bar[0], 800,900,750,100);
            } else {
                batch.draw(IPOP.wrong_score_bar[Math.abs(corTotems)], 800,900,750,100);
            }
        }

        drawTotems(activeOnFieldTotems);

        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                SinglePlayerScreen.camera.unproject(touchPos);
                if (homeBtn.contains(touchPos.x, touchPos.y)) {
                    game.setScreen(new MainMenuScreen(game));
                }
            }

        batch.end();
    }
    protected String virtual_joystick_control() {
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                SinglePlayerScreen.camera.unproject(touchPos);
                if (upPad.contains(touchPos.x, touchPos.y)) {
                    moving = true;
                    return "up";
                } else if (downPad.contains(touchPos.x, touchPos.y)) {
                    moving = true;
                    return "down";
                } else if (leftPad.contains(touchPos.x, touchPos.y)) {
                    moving = true;
                    return "left";
                } else if (rightPad.contains(touchPos.x, touchPos.y)) {
                    moving = true;
                    return "right";
                }
            }
        return currentDirection;
    }
    public void walkDirection(String direction, Boolean moving) {
        if (moving) {
            if (direction.equals("right")) {
                player = new Animation<>(0.1f, Player.player_right.get(Player.player_character).getKeyFrames());
                playerRectangle.x += 500 * Gdx.graphics.getDeltaTime();
            }
            else if (direction.equals("left")) {
                player = new Animation<>(0.1f, Player.player_left.get(Player.player_character).getKeyFrames());
                playerRectangle.x -= 500 * Gdx.graphics.getDeltaTime();
            }
            else if (direction.equals("up")) {
                player = new Animation<>(0.1f, Player.player_up.get(Player.player_character).getKeyFrames());
                playerRectangle.y += 500 * Gdx.graphics.getDeltaTime();
            }
            else if (direction.equals("down")) {
                player = new Animation<>(0.1f, Player.player_down.get(Player.player_character).getKeyFrames());
                playerRectangle.y -= 500 * Gdx.graphics.getDeltaTime();
            }
        } else {
            player = new Animation<>(9999f, Player.player_down.get(Player.player_character).getKeyFrames());
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
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}