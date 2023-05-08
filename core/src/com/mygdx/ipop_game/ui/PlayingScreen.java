package com.mygdx.ipop_game.ui;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

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
import com.mygdx.ipop_game.models.Totem;

import java.util.ArrayList;

public class PlayingScreen implements Screen {

    final IPOP game;
    int screenWidth = Gdx.graphics.getWidth(),screenHeight = Gdx.graphics.getHeight();
    int score = 0;
    Float stateTime = 0.0f;
    Animation<TextureRegion> player;
    SpriteBatch batch;
    TextureRegion player_up[] = new TextureRegion[4];
    TextureRegion player_left[] = new TextureRegion[4];
    TextureRegion player_right[] = new TextureRegion[4];
    TextureRegion player_down[] = new TextureRegion[4];
    Rectangle upPad,downPad,leftPad,rightPad,playerRectangle;
    Texture sprite = new Texture("IPOP-Walking.png"),background = new Texture("Map003.png"),totemSprite = new Texture("totem.png");
    String direction,currentDirection;
    private static OrthographicCamera camera;
    Boolean moving,soundPlayed = false;
    BitmapFont font = new BitmapFont(),scoreFont = new BitmapFont();

    float maxWidth = 200;
    float scrollSpeed = 200.0f;
    float textHeight = font.getLineHeight();
    ArrayList<Totem> totemsCorrectes = new ArrayList<>();
    ArrayList<Totem> totemsIncorrectes = new ArrayList<>();
    ArrayList<Totem> activeOnFieldTotems = new ArrayList<>();
    ArrayList<String> ocupacioInicial = new ArrayList<>();

    Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    Sound cyndaquilSound = Gdx.audio.newSound(Gdx.files.internal("CYNDAQUIL.wav"));

    public PlayingScreen(IPOP game) {
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

        //todo Revisar tamano de la TextureRegion para la Animacion
        player_down[0] = new TextureRegion(sprite,0,0,50,49);
        player_down[1] = new TextureRegion(sprite,50,0,50,49);
        player_down[2] = new TextureRegion(sprite,100,0,50,49);
        player_down[3] = new TextureRegion(sprite,50,0,50,49);

        player_left[0] = new TextureRegion(sprite,0,49,50,49);
        player_left[1] = new TextureRegion(sprite,50,49,50,49);
        player_left[2] = new TextureRegion(sprite,100,49,50,49);
        player_left[3] = new TextureRegion(sprite,50,49,50,49);

        player_right[0] = new TextureRegion(sprite,0,97,50,49);
        player_right[1] = new TextureRegion(sprite,50,97,50,49);
        player_right[2] = new TextureRegion(sprite,100,97,50,49);
        player_right[3] = new TextureRegion(sprite,50,97,50,49);

        player_up[0] = new TextureRegion(sprite,0,147,50,49);
        player_up[1] = new TextureRegion(sprite,50,147,50,49);
        player_up[2] = new TextureRegion(sprite,100,147,50,49);
        player_up[3] = new TextureRegion(sprite,50,147,50,49);

        //TouchPads
        upPad = new Rectangle(0, screenHeight*2/3, screenWidth, screenHeight);
        downPad = new Rectangle(0, 0, screenWidth, screenHeight/3);
        leftPad = new Rectangle(0, 0, screenWidth/3, screenHeight);
        rightPad = new Rectangle(screenWidth*2/3, 0, screenWidth/3, screenHeight);

        font.getData().setScale(3);
        font.setColor(Color.RED);
        scoreFont.getData().setScale(5);
        scoreFont.getData().setLineHeight(3);
        scoreFont.setColor(Color.WHITE);

        //Generate random number for the TextBox
        //textBox.setX()

        generacioTotems();


        /*textBox.setPosition(MathUtils.random(screenWidth),MathUtils.random(screenHeight));
        textBox.setWidth(300);
        textX = textBox.getX()+textBox.getWidth();
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        //Layout
        glyphLayout.setText(font,ocupacio);*/


        this.render(Gdx.graphics.getDeltaTime());
    }

    //Metodo que llamaremos cada vez que el usuario colisione contra el Totem correcto hasta 5 veces
    private void generacioTotems() {
        //todo fer que el text es fiqui damunt del totem
        for (int i = 0; i < 3; i++) {

            Rectangle totemBox = new Rectangle();
            GlyphLayout glyphLayout = new GlyphLayout();
            float textX = 0;
            String ocupacio = "Ocupacio"+i;
            glyphLayout.setText(font,ocupacio);

            //Comprovar que no hi hagi totems en aquella posicio

            //Despues de la primera pasada se añadiran los incorrectos verificando su posicion
            if (i > 0) {
                System.out.println(totemsCorrectes);
                Totem totem = new Totem(MathUtils.random(screenWidth-300),MathUtils.random(screenHeight-300),192,192,totemSprite,"Informatica",ocupacio,totemBox,glyphLayout,textX,dropSound,false);
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
                Totem totem = new Totem(MathUtils.random(screenWidth-300),MathUtils.random(screenHeight-300),192,192    ,totemSprite,"Informatica",ocupacio,totemBox,glyphLayout,textX,cyndaquilSound,true);
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
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //todo Revisar por cuando se le da al downPad a veces vuelve a la posicion incial por defecto
        //Puede que se este llamando a la clase de nuevo? Y por eso se reinicie
        // Limpia la pantalla con un color específico
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        moving = false;
        //glyphLayout.setText(font,ocupacio);

        //Frame por defecto
        //TextureRegion frame = player.getKeyFrame(stateTime,true);

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
        //GetDirection
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
                            score++;
                            activeOnFieldTotems.clear();
                            ocupacioInicial.clear();
                            generacioTotems();
                            //O solo eliminar el incorrecto
                        } else {
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
        batch.draw(frame,playerRectangle.getX(),playerRectangle.getY(),SCR_WIDTH/4,SCR_HEIGHT/4);
        scoreFont.draw(batch,"Score = "+String.valueOf(score),100,screenHeight/10);

        drawTotems(activeOnFieldTotems);
        //batch.draw(activeOnFieldTotems.get(0).getImage(),activeOnFieldTotems.get(0).getX(),activeOnFieldTotems.get(0).getY());
        //font.draw(batch,glyphLayout,textX,textBox.getY());
        //}


        //batch.end();

        batch.end();

        /*System.out.println( textX + glyphLayout.width);
        textX -= scrollSpeed * Gdx.graphics.getDeltaTime();


        //todo Revisar porque no me hace las pasadas del substring
        if (textBox.x > textX + glyphLayout.width) {
            System.out.println("Canvi");
            textX = textBox.getX()+textBox.getWidth();
        } else {

                String substring = ocupacio.substring(1);

                glyphLayout.setText(font,substring);
                if (textBox.x > textX + glyphLayout.width) {
                    ocupacio = substring;
                    substring = ocupacio.substring(1);
                    glyphLayout.setText(font,substring);
                    System.out.println(substring);
                    System.out.println("Es mas peque");
                }


        }*/



    }
    protected String virtual_joystick_control() {
        // iterar per multitouch
        // cada "i" és un possible "touch" d'un dit a la pantalla
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                PlayingScreen.camera.unproject(touchPos);
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
        //Revisar per el Key input
        return currentDirection;
    }
    public void walkDirection(String direction, Boolean moving) {
        if (moving) {
            if (direction.equals("right")) {
                player = new Animation<TextureRegion>(0.1f,player_right);
                playerRectangle.x += 500 * Gdx.graphics.getDeltaTime();
            }
            else if (direction.equals("left")) {
                player = new Animation<TextureRegion>(0.1f,player_left);
                playerRectangle.x -= 500 * Gdx.graphics.getDeltaTime();

            }
            else if (direction.equals("up")) {
                player = new Animation<TextureRegion>(0.1f,player_up);
                playerRectangle.y += 500 * Gdx.graphics.getDeltaTime();

            }
            else if (direction.equals("down")) {
                player = new Animation<TextureRegion>(0.1f,player_down);
                playerRectangle.y -= 500 * Gdx.graphics.getDeltaTime();

            }
        } else {
            player = new Animation<TextureRegion>(999.9f,player_down);
        }


    }

    /*public int setTextPosition(Rectangle textBox) {
        System.out.println(textX);
        if (textX - textBox.getWidth() < textBox.getX()) {
            textX = textX-10;
            return textX;
        } else {
            textX = (int) (textBox.getX()+textBox.getWidth()+100);
            return textX;
        }
    }*/
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

    // Resto de métodos de la interfaz Screen
    // ...

    @Override
    public void dispose() {
        batch.dispose();
        sprite.dispose();
        background.dispose();
    }
}