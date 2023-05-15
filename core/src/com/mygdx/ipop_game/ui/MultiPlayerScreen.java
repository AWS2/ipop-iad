package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Application;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.GameRecord;
import com.mygdx.ipop_game.models.Ocupacio;
import com.mygdx.ipop_game.models.Player;
import com.mygdx.ipop_game.models.Totem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.css.Rect;

import java.time.Instant;
import java.util.ArrayList;

public class MultiPlayerScreen implements Screen {

    public static String game_status = "playing";
    public static String game_totems;
    final IPOP game;
    Ocupacio ocupacioObject;
    int screenWidth = Gdx.graphics.getWidth(), screenHeight = Gdx.graphics.getHeight();
    int score = 0;
    int pasada = 0;
    SpriteBatch batch;
    Rectangle upPad, downPad, leftPad, rightPad, playerRectangle, homeBtn;
    Texture background = new Texture("Map001.png");
    Texture home = new Texture("menu_button.png");
    Texture totemSprite = new Texture("totem.png"),ballons = new Texture("Balloon.png");
    String direction, currentDirection;
    private static OrthographicCamera camera;
    Boolean moving, soundPlayed = false;
    BitmapFont font = new BitmapFont(), scoreFont = new BitmapFont(),characterFont = new BitmapFont();
    float elapsedTimeNewPlayer = 0f; // Tiempo transcurrido en segundos
    float duration = 3f; // Duración en segundos durante la cual se dibujará el texto
    boolean playerJoined = false; // Variable de control para determinar si se debe dibujar el texto o no

    Animation<TextureRegion> player,exclamation;
    TextureRegion[] ballon_exclamation = new TextureRegion[8];

    float scrollSpeed = 200.0f;
    Instant startPlaying;
    ArrayList<Totem> totemsCorrectes = new ArrayList<>();
    ArrayList<Totem> totemsIncorrectes = new ArrayList<>();
    ArrayList<Totem> activeOnFieldTotems = new ArrayList<>();
    ArrayList<String> ocupacioInicial = new ArrayList<>();
    ArrayList<Player> players;
    Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    Sound cyndaquilSound = Gdx.audio.newSound(Gdx.files.internal("CYNDAQUIL.wav"));
    int TOTEMS_TO_REACH = 5;
    int corTotems = 0;
    int totalTotems = 0;
    public float stateTime = 0f;
    public float lastSend = 0f;


    WebSocket socket;
    String address = "localhost";
    int port = 3001;


    public MultiPlayerScreen(IPOP game) {
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

        //Fonts info
        font.getData().setScale(3);
        font.setColor(Color.RED);
        characterFont.getData().setScale(3);
        characterFont.setColor(Color.BLACK);
        scoreFont.getData().setScale(5);
        scoreFont.getData().setLineHeight(3);
        scoreFont.setColor(Color.WHITE);

        ballon_exclamation[0] = new TextureRegion(ballons, 0, 0, 47, 50);
        ballon_exclamation[1] = new TextureRegion(ballons, 47, 0, 47, 50);
        ballon_exclamation[2] = new TextureRegion(ballons, 94, 0, 47, 50);
        ballon_exclamation[3] = new TextureRegion(ballons, 141, 0, 47, 50);
        ballon_exclamation[4] = new TextureRegion(ballons, 188, 0, 47, 50);
        ballon_exclamation[5] = new TextureRegion(ballons, 235, 0, 47, 50);
        ballon_exclamation[6] = new TextureRegion(ballons, 282, 0, 47, 50);
        ballon_exclamation[7] = new TextureRegion(ballons, 329, 0, 47, 50);
        exclamation = new Animation<>(0.2f,ballon_exclamation);
        players = new ArrayList<>();
        players.add(new Player("Manolo",1, new int[]{1050, 350}));
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            // en Android el host és accessible per 10.0.2.2
            address = "10.0.2.2";
        socket = WebSockets.newSocket(WebSockets.toWebSocketUrl(address, port));
        socket.setSendGracefully(false);
        socket.addListener((WebSocketListener) new MyWSListener());
        socket.connect();

        JSONObject json = new JSONObject();
        json.put("type", "startGame");
        json.put("nameCycle", Player.player_ocupation);
        json.put("player_alias", Player.player_alias);
        json.put("player_sprite", Player.player_character);

        socket.send(json.toString());


        generacioTotems();
        startPlaying = Instant.now();
        this.render(Gdx.graphics.getDeltaTime());
    }

    public void updateTotemFromServer() {
        String gameTotems = "{\"status\":\"ok\",\"type\":\"game_totems\",\"message\":{\"totems\":[{\"idTotem\":1,\"text\":\"Totem 1\",\"cycleLabel\":\"Cycle 1\",\"posX\":100,\"posY\":200,\"width\":50,\"height\":50},{\"idTotem\":2,\"text\":\"Totem 2\",\"cycleLabel\":\"Cycle 2\",\"posX\":150,\"posY\":250,\"width\":60,\"height\":60},{\"idTotem\":3,\"text\":\"Totem 3\",\"cycleLabel\":\"Cycle 3\",\"posX\":200,\"posY\":300,\"width\":70,\"height\":70},{\"idTotem\":4,\"text\":\"Totem 4\",\"cycleLabel\":\"Cycle 4\",\"posX\":250,\"posY\":350,\"width\":80,\"height\":80},{\"idTotem\":5,\"text\":\"Totem 5\",\"cycleLabel\":\"Cycle 5\",\"posX\":300,\"posY\":400,\"width\":90,\"height\":90},{\"idTotem\":6,\"text\":\"Totem 6\",\"cycleLabel\":\"Cycle 6\",\"posX\":350,\"posY\":450,\"width\":100,\"height\":100},{\"idTotem\":7,\"text\":\"Totem 7\",\"cycleLabel\":\"Cycle 7\",\"posX\":400,\"posY\":500,\"width\":110,\"height\":110},{\"idTotem\":8,\"text\":\"Totem 8\",\"cycleLabel\":\"Cycle 8\",\"posX\":450,\"posY\":550,\"width\":120,\"height\":120},{\"idTotem\":9,\"text\":\"Totem 9\",\"cycleLabel\":\"Cycle 9\",\"posX\":500,\"posY\":600,\"width\":130,\"height\":130},{\"idTotem\":10,\"text\":\"Totem 10\",\"cycleLabel\":\"Cycle 10\",\"posX\":550,\"posY\":650,\"width\":140,\"height\":140}]}}";
        game_totems = gameTotems;
        JSONObject response = new JSONObject(MultiPlayerScreen.game_totems);
        JSONArray totemsArray = response.getJSONObject("message").getJSONArray("totems");

        ArrayList<Totem> totemsList = new ArrayList<>();
        for (int i = 0; i < totemsArray.length(); i++) {
            JSONObject totemObject = totemsArray.getJSONObject(i);
            int idTotem = totemObject.getInt("idTotem");
            String text = totemObject.getString("text");
            String cycleLabel = totemObject.getString("cycleLabel");
            int posX = totemObject.getInt("posX");
            int posY = totemObject.getInt("posY");
            int width = totemObject.getInt("width");
            int height = totemObject.getInt("height");
            Rectangle totemBox = new Rectangle(posX, posY, width, height);
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, text);
            Totem totem = new Totem(idTotem, posX, posY, width, height, totemSprite, cycleLabel, text, totemBox, glyphLayout, 0, dropSound);
            totemBox.setPosition(totem.getX(),totem.getY()+50);
            totemBox.setWidth(300);
            totem.setTextX(totemBox.getX()+totemBox.getWidth());
            totemsList.add(totem);
        }

        // Imprimir los objetos Totem en el ArrayList
        /*for (Totem totem : totemsList) {
            System.out.println(totem.toString());
        }*/

    }

    //Metodo que llamaremos cada vez que el usuario colisione contra el Totem correcto hasta 5 veces
    private void generacioTotems() {
        for (int i = 0; i < 2; i++) {

            float textX = 0;
            //Generar Totems Correctes
            if (i == 0) {
                for (int j = 0; j < 5; j++) {

                    Rectangle totemBox = new Rectangle();
                    GlyphLayout glyphLayout = new GlyphLayout();
                    String ocupacio = llistaOcupacions(Player.player_ocupation);

                    glyphLayout.setText(font,ocupacio);
                    Vector2 totemPosition = new Vector2(MathUtils.random((background.getWidth())-300),MathUtils.random((background.getHeight())-300));
                    Totem totem = new Totem(i+j,totemPosition.x,totemPosition.y,192,192,totemSprite,"Informatica",ocupacio,totemBox,glyphLayout,textX,cyndaquilSound,true);
                    totemBox.setPosition(totem.getX(),totem.getY()+50);
                    totemBox.setWidth(300);
                    totem.setTextX(totemBox.getX()+totemBox.getWidth());
                    //Layout
                    activeOnFieldTotems.add(totem);
                    ocupacioInicial.add(activeOnFieldTotems.get(j).getOcupacio());
                    totemsCorrectes.add(totem);
                    pasada++;
                }
            } else {
                for (int j = 0; j < 5; j++) {

                    Rectangle totemBox = new Rectangle();
                    GlyphLayout glyphLayout = new GlyphLayout();
                    String ocupacio = "";
                    ocupacio = llistaOcupacions("Gestio administrativa");
                    Totem totem = new Totem(i+j,MathUtils.random((background.getWidth())-300),MathUtils.random((background.getHeight())-300),192,192,totemSprite,"Administracio",ocupacio,totemBox,glyphLayout,textX,dropSound,false);
                    totemBox.setPosition(totem.getX(),totem.getY()+50);
                    totemBox.setWidth(300);
                    totem.setTextX(totemBox.getX()+totemBox.getWidth());
                    //Layout
                    glyphLayout.setText(font,ocupacio);
                    for (int k = 0; k < activeOnFieldTotems.size(); k++) {
                        if (k < activeOnFieldTotems.size() && activeOnFieldTotems.get(k).getX() != totem.getX() && activeOnFieldTotems.get(k).getY() != totem.getY()) {
                            activeOnFieldTotems.add(totem);
                            ocupacioInicial.add(totem.getOcupacio());
                            totemsIncorrectes.add(totem);
                            pasada++;
                            break;
                        }

                    }
                }




            }

            //Comprovar que no hi hagi totems en aquella posicio

            //Despues de la primera pasada se añadiran los incorrectos verificando su posicion

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
            ocupacions.add("Personal tècnic instal·lador-reparador d equips informàtics");
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
        upPad.setPosition(camera.position.x - screenWidth/2, camera.position.y - screenHeight/2 + screenHeight*2/3);
        downPad.setPosition(camera.position.x - screenWidth/2, camera.position.y - screenHeight/2);
        leftPad.setPosition(camera.position.x - screenWidth/2, camera.position.y - screenHeight/2);
        rightPad.setPosition(camera.position.x - screenWidth/2 + screenWidth*2/3, camera.position.y - screenHeight/2);

        // Obtener las coordenadas de la cámara
        float cameraX = camera.position.x - camera.viewportWidth / 2;
        float cameraY = camera.position.y - camera.viewportHeight / 2;
        homeBtn.setPosition(100 + cameraX, 900 + cameraY);

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        moving = false;
        if (stateTime - lastSend > 1.0f) {
            lastSend = (int) stateTime;
            JSONObject json = new JSONObject();
            json.put("nameCycle", Player.player_ocupation);
            json.put("player_alias", Player.player_alias);
            json.put("player_sprite", Player.player_character);
            json.put("player_x", Player.transform[0]);
            json.put("player_y", Player.transform[1]);

            socket.send(json.toString());
        }
        updateTotemFromServer();

        if (MultiPlayerScreen.game_status.equals("finish")) {
            JSONObject json = new JSONObject();
            json.put("game_status", "finish");
            json.put("player_won", Player.player_alias);
            socket.send(json.toString());
            game.setScreen(
                    new EndGameScreen(
                            game,
                            new GameRecord(
                                    corTotems,
                                    totalTotems,
                                    Player.player_ocupation,
                                    Player.player_alias,
                                    startPlaying,
                                    Instant.now()
                            )));
        } else if (corTotems == TOTEMS_TO_REACH || Player.totemsLeft == 0) {
            JSONObject json = new JSONObject();
            json.put("game_status", "finish");
            json.put("player_won", Player.player_alias);
            socket.send(json.toString());
            game.setScreen(
                    new EndGameScreen(
                            game,
                            new GameRecord(
                                    corTotems,
                                    totalTotems,
                                    Player.player_ocupation,
                                    Player.player_alias,
                                    startPlaying,
                                    Instant.now()
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
        if(playerRectangle.x > background.getWidth() - 150) playerRectangle.x = background.getWidth() - 150;
        if(playerRectangle.y < 0) playerRectangle.y = 0;
        if(playerRectangle.y > background.getHeight() - 150) playerRectangle.y = background.getHeight() - 150;

        //Revisar que no haya colision
        for (int i = activeOnFieldTotems.size() - 1; i >= 0; i--) {
            Totem totem = activeOnFieldTotems.get(i);
            if (playerRectangle.x >= totem.getX() && playerRectangle.x <= totem.getX() + totem.getWidth()
                    && playerRectangle.y >= totem.getY() && playerRectangle.y <= totem.getY() + totem.getHeight()) {
                if (!soundPlayed) {
                    totem.getSound().play();
                    if (totem.getCorrectTotem()) {
                        corTotems++;
                        totalTotems++;
                        activeOnFieldTotems.remove(i);
                        Player.totemsLeft --;
                        sendTotemToServer(totem);
                        playerJoined = true;
                        //Calculos para dibujar en caso de que se una un nuevo jugador
                        // Actualizar el tiempo transcurrido

                    } else {
                        corTotems--;
                        totalTotems++;
                        sendTotemToServer(totem);
                        activeOnFieldTotems.remove(i);
                    }
                }
            }
        }




        // Mover la cámara junto al jugador
        camera.position.set(playerRectangle.x, playerRectangle.y, 0);
        camera.update();

        batch.begin();
        batch.draw(background,0,0,background.getWidth(),background.getHeight());
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        TextureRegion frame = player.getKeyFrame(stateTime,true);
        TextureRegion ballon = exclamation.getKeyFrame(stateTime,true);
        //Player
        batch.draw(frame,playerRectangle.getX(),playerRectangle.getY(),Player.scale[0],Player.scale[1]);
        characterFont.draw(batch,Player.player_alias,playerRectangle.getX(),playerRectangle.getY()+playerRectangle.getHeight());

        //Dibujar otros players
        for (Player playerArray: players) {
            Texture sprite;

            if (Player.player_character == 0) {
                //batch.draw(playerArray.);
                playerArray.players_character = 1;
                Player.player_down.get(0).getKeyFrameIndex(1);
                batch.draw(Player.player_down.get(0).getKeyFrames()[1],playerArray.players_transform[0],playerArray.players_transform[1],Player.scale[0],Player.scale[1]);
                characterFont.draw(batch,playerArray.players_alias,playerArray.players_transform[0],playerArray.players_transform[1]);

            } else {
                playerArray.players_character = 0;
                batch.draw(Player.player_down.get(1).getKeyFrames()[1],playerArray.players_transform[0],playerArray.players_transform[1],Player.scale[0],Player.scale[1]);

            }

        }

        if (playerJoined) {
            elapsedTimeNewPlayer += delta;
            if (elapsedTimeNewPlayer >= duration) {
                playerJoined = false; // Dejar de dibujar el texto
                elapsedTimeNewPlayer = 0f;
            }

            //Ambos PlayerScale son de 128px
            scoreFont.draw(batch,"Peter de Sistemes Microinformatics i Xarxes s ha unit",cameraX+Player.scale[0]+Player.scale[1],cameraY+screenHeight-100);
            batch.draw(ballon,cameraX,cameraY+screenHeight-164,Player.scale[0],Player.scale[1]);
        }

        //batch.draw(frame,(screenWidth)/2,(screenHeight)/2,Player.scale[0],Player.scale[1]);
        batch.draw(home, 100 + cameraX, 900 + cameraY, 100, 100);
        if (corTotems > 0) {
            batch.draw(IPOP.score_bar[corTotems], 800 + cameraX, 950 + cameraY, 750, 100);
        } else {
            if (corTotems == 0) {
                batch.draw(IPOP.score_bar[0], 800 + cameraX, 900 + cameraY, 750, 100);
            } else {
                int barWidth = 750;
                int barHeight = 100;
                int barX = (int) (800 + cameraX);
                int barY = (int) (900 + cameraY);
                int wrongBarIndex = Math.abs(corTotems);

                batch.draw(IPOP.wrong_score_bar[wrongBarIndex], barX, barY, barWidth, barHeight);
            }
        }

        drawTotems(activeOnFieldTotems);

        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                MultiPlayerScreen.camera.unproject(touchPos);
                if (homeBtn.contains(touchPos.x, touchPos.y)) {
                    game.setScreen(new MainMenuScreen(game));
                }
            }

        batch.end();
    }

    private void sendTotemToServer(Totem totem) {
        JSONObject json = new JSONObject();
        json.put("totem_id", String.valueOf(totem.getId()));
        //Dades del totem
        json.put("posX",String.valueOf(totem.getX()));
        json.put("posY",String.valueOf(totem.getY()));
        json.put("width",String.valueOf(totem.getWidth()));
        json.put("height",String.valueOf(totem.getHeight()));
        json.put("cycleLabel",String.valueOf(totem.getOcupacio()));
        json.put("correct",String.valueOf(totem.getCorrectTotem()));


        socket.send(json.toString());
    }

    protected String virtual_joystick_control() {
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                MultiPlayerScreen.camera.unproject(touchPos);
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
        double speed = 50000 * Gdx.graphics.getDeltaTime();
        if (moving) {
                if (direction.equals("right")) {
                    player = new Animation<>(0.1f, Player.player_right.get(Player.player_character).getKeyFrames());
                    playerRectangle.x += speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;
                }
                else if (direction.equals("left")) {
                    player = new Animation<>(0.1f, Player.player_left.get(Player.player_character).getKeyFrames());
                    playerRectangle.x -= speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;
                }
                else if (direction.equals("up")) {
                    player = new Animation<>(0.1f, Player.player_up.get(Player.player_character).getKeyFrames());
                    playerRectangle.y += speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;
                }
                else if (direction.equals("down")) {
                    player = new Animation<>(0.1f, Player.player_down.get(Player.player_character).getKeyFrames());
                    playerRectangle.y -= speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;

                }
        } else {
            exclamation = new Animation<>(0.2f,ballon_exclamation);

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

class MyWSListener implements WebSocketListener {

    @Override
    public boolean onOpen(WebSocket webSocket) {
        return false;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        System.out.println(reason);
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        JSONObject response = new JSONObject(packet);
        if (response.getString("game_status").equals("finish")) {
            MultiPlayerScreen.game_status = "finish";
        } else if (response.getString("type").equals("game_totems")) {
            //PARA HACER PRUEBAS -> MultiPlayerScreen.game_totems = "{\"status\":\"ok\",\"message\":{\"totems\":[{\"idTotem\":1,\"text\":\"Totem 1\",\"cycleLabel\":\"Cycle 1\",\"posX\":100,\"posY\":200,\"width\":50,\"height\":50},{\"idTotem\":2,\"text\":\"Totem 2\",\"cycleLabel\":\"Cycle 2\",\"posX\":150,\"posY\":250,\"width\":60,\"height\":60},{\"idTotem\":3,\"text\":\"Totem 3\",\"cycleLabel\":\"Cycle 3\",\"posX\":200,\"posY\":300,\"width\":70,\"height\":70},{\"idTotem\":4,\"text\":\"Totem 4\",\"cycleLabel\":\"Cycle 4\",\"posX\":250,\"posY\":350,\"width\":80,\"height\":80},{\"idTotem\":5,\"text\":\"Totem 5\",\"cycleLabel\":\"Cycle 5\",\"posX\":300,\"posY\":400,\"width\":90,\"height\":90},{\"idTotem\":6,\"text\":\"Totem 6\",\"cycleLabel\":\"Cycle 6\",\"posX\":350,\"posY\":450,\"width\":100,\"height\":100},{\"idTotem\":7,\"text\":\"Totem 7\",\"cycleLabel\":\"Cycle 7\",\"posX\":400,\"posY\":500,\"width\":110,\"height\":110},{\"idTotem\":8,\"text\":\"Totem 8\",\"cycleLabel\":\"Cycle 8\",\"posX\":450,\"posY\":550,\"width\":120,\"height\":120},{\"idTotem\":9,\"text\":\"Totem 9\",\"cycleLabel\":\"Cycle 9\",\"posX\":500,\"posY\":600,\"width\":130,\"height\":130},{\"idTotem\":10,\"text\":\"Totem 10\",\"cycleLabel\":\"Cycle 10\",\"posX\":550,\"posY\":650,\"width\":140,\"height\":140}]}}\n";
            MultiPlayerScreen.game_totems = response.getString("message");
            System.out.println(MultiPlayerScreen.game_totems);
        }
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        return false;
    }
}
