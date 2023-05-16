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
import java.util.Objects;

public class MultiPlayerScreen implements Screen {

    public static String game_status = "playing";
    public static String game_totems;
    public static String players_str;
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
    ArrayList<Totem> activeOnFieldTotems = new ArrayList<>();
    ArrayList<String> ocupacioInicial = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
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

        //TextureRegion de la animacion de la exclamacion
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

        //Totems que estaran en el campo
        activeOnFieldTotems = new ArrayList<>();
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

        //generacioTotems();
        startPlaying = Instant.now();

        lastSend = (int) stateTime;
        json = new JSONObject();
        json.put("nameCycle", Player.player_ocupation);
        json.put("player_alias", Player.player_alias);
        json.put("player_sprite", Player.player_character);
        json.put("player_x", Player.transform[0]);
        json.put("player_y", Player.transform[1]);

        socket.send(json.toString());

        this.render(Gdx.graphics.getDeltaTime());
    }

    public void updateTotemFromServer() {
        //Coge los totems desde el servidor en caso de no tener totems
        if (MultiPlayerScreen.game_totems == null) { return; }
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
            //glyphLayout.setText(font, text);
            Totem totem = new Totem(idTotem, posX, posY, width, height, totemSprite, cycleLabel, text, totemBox, glyphLayout, 0, dropSound);
            totemBox.setPosition(totem.getX(),totem.getY()+50);
            totemBox.setWidth(300);
            totem.setTextX(totemBox.getX()+totemBox.getWidth());
            totemsList.add(totem);
        }
        //Limpia los que hubiera y genera los proporcionados por el servidor
        activeOnFieldTotems.clear();
        activeOnFieldTotems.addAll(totemsList);
        int i = 0;
    }

    public void updatePlayersFromServer() {
        //Los jugadores tambien busca sus posiciones desde el servidor
        if (MultiPlayerScreen.players_str == null) { return; }
        JSONObject response = new JSONObject(MultiPlayerScreen.players_str);
        JSONArray playersArray = response.getJSONObject("message").getJSONArray("players");

        ArrayList<Player> playersList = new ArrayList<>();
        for (int i = 0; i < playersArray.length(); i++) {
            JSONObject playerObject = playersArray.getJSONObject(i);
            String player_alias = playerObject.getString("name");
            int player_character = playerObject.getInt("spriteSelected");
            int[] player_transform = new int[]{playerObject.getInt("posX"), playerObject.getInt("posY")};
            playersList.add(new Player(
                    player_alias,
                    player_character,
                    player_transform
            ));
        }
        players.clear();
        players.addAll(playersList);
        int i = 1;
    }

    //Generacion totem Local
    private void generacioTotems() {
        for (int i = 0; i < 2; i++) {

            float textX = 0;
            //Generar Totems Correctes
            if (i == 0) {
                for (int j = 0; j < 5; j++) {

                    Rectangle totemBox = new Rectangle();
                    GlyphLayout glyphLayout = new GlyphLayout();
                    String ocupacio = llistaOcupacions(Player.player_ocupation);
                    //Para saber el tamaño del texto y asignarle ya un color
                    glyphLayout.setText(font,ocupacio);
                    //Calcular la X y la Y del Totem random
                    Vector2 totemPosition = new Vector2(MathUtils.random((background.getWidth())-300),MathUtils.random((background.getHeight())-300));
                    //Constructor
                    Totem totem = new Totem(i+j,totemPosition.x,totemPosition.y,192,192,totemSprite,"Informatica",ocupacio,totemBox,glyphLayout,textX,cyndaquilSound,true);
                    //TotemBox es la caja donde añadiremos el texto
                    totemBox.setPosition(totem.getX(),totem.getY()+50);
                    totemBox.setWidth(300);
                    totem.setTextX(totemBox.getX()+totemBox.getWidth());
                    //Layout
                    //Añadimos el Totem al Array de Totems activos
                    activeOnFieldTotems.add(totem);
                    ocupacioInicial.add(activeOnFieldTotems.get(j).getOcupacio());
                    //totemsCorrectes.add(totem);
                    //Cambiamos el valor del texto del siguiente totem
                    pasada++;
                }
            } else {
                for (int j = 0; j < 5; j++) {
                    //Totem incorrectes
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
                            //totemsIncorrectes.add(totem);
                            pasada++;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void drawTotems (ArrayList<Totem> activeOnFieldTotems) {
        //Recorremos los totems que nos hayan pasado
        if (activeOnFieldTotems.size() == 0) {
            return;
        }
        for (Totem totem : activeOnFieldTotems) {
            System.out.println(totem.toString());
            // Dibujar el Totem y su Texto
            batch.draw(totem.getImage(), totem.getX(), totem.getY());
            font.draw(batch, totem.getGlyphLayout(), totem.getTextX(), totem.getTextBox().getY());

            // Esta variable es la que calcula el desplazamiento del Texto
            float newTextX = totem.getTextX() - (scrollSpeed * Gdx.graphics.getDeltaTime());
            totem.setTextX(newTextX);

            // Parámetros que calculan el tiempo
            float elapsedTime = 0f;
            float updateInterval = 0.01f;

            if (totem.getTextBox().x > totem.getTextX() + totem.getGlyphLayout().width) {
                // Si el Texto ha salido  de la caja de texto, lo reiniciamos al inicio
                // Basicamente el efecto de volvcompletamenteer a empezar
                totem.setTextX(totem.getTextBox().x + totem.getTextBox().getWidth());
                elapsedTime = 0f; // Reiniciar el temporizador
                totem.setOcupacio(ocupacioInicial.get(activeOnFieldTotems.indexOf(totem)));
            } else {
                elapsedTime += Gdx.graphics.getDeltaTime();
                //Cada 0.1 segundos se hace el siguiente calculo
                if (totem.getOcupacio().length() > 1) {
                    // Actualizar el Texto eliminando el primer carácter
                    totem.getGlyphLayout().setText(font, totem.getOcupacio().substring(1));
                    if (totem.getTextBox().x > totem.getTextX() + totem.getGlyphLayout().width) {
                        // Si el Texto ha salido completamente de la caja de texto después de la actualización, continuar con el siguiente carácter
                        String substring = totem.getOcupacio().substring(1);
                        totem.setOcupacio(substring);
                        substring = totem.getOcupacio().substring(1);
                        totem.getGlyphLayout().setText(font, substring);
                        totem.setTextX(totem.getTextX() - scrollSpeed * Gdx.graphics.getDeltaTime());
                        elapsedTime = 0f; // Reiniciar el temporizador
                    } else {
                        // Si el Texto no ha salido de la caja de texto, restaurar el Texto original
                        //totem.getGlyphLayout().setText(font, ocupacioInicial.get(activeOnFieldTotems.indexOf(totem)));
                    }
                }
            }
        }

    }

    private String llistaOcupacions(String cicle) {
        //Vamos haciendo una pasada por cada iteracion de generar un Totem
        //Asi que en caso de llegar al final volveriamos a empezar desde el principio
        if (pasada > 4) {
            pasada = 0;
        }
        ArrayList<String> ocupacions = new ArrayList<>();
        //Las ocupaciones que tendremos segun el ciclo elegido
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
        //Mientras haya totems en el campo ejecutar el render
        updatePlayersFromServer();
        updateTotemFromServer();

            //Mover los rectangulos para desplazarnos segun se va moviendo la camara con el jugador
            upPad.setPosition(camera.position.x - screenWidth/2, camera.position.y - screenHeight/2 + screenHeight*2/3);
            downPad.setPosition(camera.position.x - screenWidth/2, camera.position.y - screenHeight/2);
            leftPad.setPosition(camera.position.x - screenWidth/2, camera.position.y - screenHeight/2);
            rightPad.setPosition(camera.position.x - screenWidth/2 + screenWidth*2/3, camera.position.y - screenHeight/2);

            // Obtener las coordenadas de la cámara
            float cameraX = camera.position.x - camera.viewportWidth / 2;
            float cameraY = camera.position.y - camera.viewportHeight / 2;
            //Coordenada relativa a la posicion actual de la camara
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
            //Generar los Totems y Players desde el Servidor
            updateTotemFromServer();
            updatePlayersFromServer();

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
                //Si no quedan mas totems enviar el siguiente Json
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

            //Mover las direcciones segun las flechas del Teclado
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
            //Revisar si se esta eligiendo una posicion tambien segun el TouchPad
            direction = virtual_joystick_control();
            //Si se esta presionando para moverse entonces se movera en la direccion especificada
            walkDirection(direction,moving);

            //Limit screen movement
            if(playerRectangle.x < 0) playerRectangle.x = 0;
            if(playerRectangle.x > background.getWidth() - 150) playerRectangle.x = background.getWidth() - 150;
            if(playerRectangle.y < 0) playerRectangle.y = 0;
            if(playerRectangle.y > background.getHeight() - 150) playerRectangle.y = background.getHeight() - 150;

            //Revisar que no haya colision
            for (int i = activeOnFieldTotems.size() - 1; i >= 0; i--) {
                Totem totem = activeOnFieldTotems.get(i);
                //Si la posicion X del jugador esta dentro de la X y la anchura del Totem pasamos al siguiente If
                if (playerRectangle.x >= totem.getX() && playerRectangle.x <= totem.getX() + totem.getWidth()
                        //Si la posicion Y del jugador esta dentro de la Y del Totem y menor a la altura
                        && playerRectangle.y >= totem.getY() && playerRectangle.y <= totem.getY() + totem.getHeight()) {
                    //Entonces es cuando se recoge el totem y tenemos una variable para solo emitir el sonido 1 vez
                    if (!soundPlayed) {
                        if (totem.getSound() != null) {
                            totem.getSound().play();
                            //Luego de emitir el sonido se comprueba si el totem es correcto o incorrecto
                            if (totem.getCorrectTotem()) {
                                //Si es correcto se suma de los totems correctos y los totems totales
                                corTotems++;
                                totalTotems++;
                                //Eliminamos el totem del campo y marcamos que al jugador le queda uno menos
                                activeOnFieldTotems.remove(i);
                                Player.totemsLeft --;
                                sendTotemToServer(totem);
                                //Variable de prueba que se tendrá que poner para cuando un nuevo jugador se una
                                playerJoined = true;


                            } else {
                                //Eliminar de totems correctos
                                corTotems--;
                                totalTotems++;
                                //Enviar totem al server
                                sendTotemToServer(totem);
                                //Eliminar del campo
                                activeOnFieldTotems.remove(i);
                            }
                        }

                    }
                }
            }




            // Mover la cámara junto al jugador
            camera.position.set(playerRectangle.x, playerRectangle.y, 0);
            //Ir actualizando la posicion de la camara todoo el rato para que siga al player
            camera.update();

            batch.begin();
            //Pintamos el mapa desde el principio
            batch.draw(background,0,0,background.getWidth(),background.getHeight());
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            //Hacemos que ambas animaciones (movimiento player y exclamacion de nuevo player) esten en loop
            TextureRegion frame = player.getKeyFrame(stateTime,true);
            TextureRegion ballon = exclamation.getKeyFrame(stateTime,true);
            //Dibujamos la animacion del player y asignamos que su nombre le vaya siguiendo la posicion
            batch.draw(frame,playerRectangle.getX(),playerRectangle.getY(),Player.scale[0],Player.scale[1]);
            characterFont.draw(batch,Player.player_alias,playerRectangle.getX(),playerRectangle.getY()+playerRectangle.getHeight());

            //Dibujar otros players
            for (Player player: players) {
                Texture sprite;
                if (!Objects.equals(player.players_alias, Player.player_alias)) {
                    //todo Verificar Sprite del player principal para asignar a los demas el contrario
                    if (Player.player_character == 0) {
                        //batch.draw(playerArray.);
                        player.players_character = 1;
                        Player.player_down.get(1).getKeyFrameIndex(1);
                        //Pintar la posicion idle en los otros
                    } else {
                        //batch.draw(playerArray.);
                        player.players_character = 0;
                        Player.player_down.get(0).getKeyFrameIndex(1);
                        //Pintar la posicion idle en los otros
                    }
                    batch.draw(Player.player_down.get(0).getKeyFrames()[1],player.players_transform[0],player.players_transform[1],Player.scale[0],Player.scale[1]);
                    characterFont.draw(batch,player.players_alias,player.players_transform[0],player.players_transform[1]);
                }
            }

            //Si se une un usuario entonces se mostrara el texto y animacion durante duration (3 segundos)
            if (playerJoined) {
                elapsedTimeNewPlayer += delta;
                if (elapsedTimeNewPlayer >= duration) {
                    playerJoined = false; // Dejar de dibujar el texto
                    elapsedTimeNewPlayer = 0f;
                }

                //Dibujar el nombre del Player que se haya unido todo(se tendra que añadir al Array de players)
                scoreFont.draw(batch,"Peter de Sistemes Microinformatics i Xarxes s ha unit",cameraX+Player.scale[0]+Player.scale[1],cameraY+screenHeight-100);
                batch.draw(ballon,cameraX,cameraY+screenHeight-164,Player.scale[0],Player.scale[1]);
            }

            //batch.draw(frame,(screenWidth)/2,(screenHeight)/2,Player.scale[0],Player.scale[1]);
            //Pintar el dibujo del boton Home
            batch.draw(home, 100 + cameraX, 900 + cameraY, 100, 100);
            //Seguin los Totems correctos se dibujara un grafico de puntos o otro
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

            //Llamar al metodo que dibujara los totems del Array que se le pase
            drawTotems(activeOnFieldTotems);

            //Calcular si se ha presionado el Home
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

    //Calcular la direccion que se ha presionado
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
        //Velocidad del player
        double speed = 15000 * Gdx.graphics.getDeltaTime();
        if (moving) {
            switch (direction) {
                //Segun cada direccion se animara el player y se movera X posiciones tambien se pasaran los parametros del jugador a su objeto
                case "right":
                    player = new Animation<>(0.1f, Player.player_right.get(Player.player_character).getKeyFrames());
                    playerRectangle.x += speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;
                    break;
                case "left":
                    player = new Animation<>(0.1f, Player.player_left.get(Player.player_character).getKeyFrames());
                    playerRectangle.x -= speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;
                    break;
                case "up":
                    player = new Animation<>(0.1f, Player.player_up.get(Player.player_character).getKeyFrames());
                    playerRectangle.y += speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;
                    break;
                case "down":
                    player = new Animation<>(0.1f, Player.player_down.get(Player.player_character).getKeyFrames());
                    playerRectangle.y -= speed * Gdx.graphics.getDeltaTime();
                    Player.transform[0] = (int) playerRectangle.x;
                    Player.transform[1] = (int) playerRectangle.y;

                    break;
            }
        } else {
            //Se crea la animacion de Exclamation y el Idle de player
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
        System.out.println("openWS");
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
        String type = response.getString("type");
        if (type.equals("finish")) {
            MultiPlayerScreen.game_status = "finish";
            return true;
        }
        if (type.equals("game_totems")) {
            MultiPlayerScreen.game_totems = response.toString();
            return true;
        }
        if (type.equals("players")) {
            MultiPlayerScreen.players_str = packet;
            System.out.println(MultiPlayerScreen.players_str);
            return true;
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
