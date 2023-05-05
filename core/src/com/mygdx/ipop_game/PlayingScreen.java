package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class PlayingScreen implements Screen {

    final IPOP game;
    Float stateTime = 0.0f;
    Animation<TextureRegion> player;
    SpriteBatch batch;
    TextureRegion player_up[] = new TextureRegion[4];
    TextureRegion player_left[] = new TextureRegion[4];
    TextureRegion player_right[] = new TextureRegion[4];
    TextureRegion player_down[] = new TextureRegion[4];
    Rectangle upPad,downPad,leftPad,rightPad,playerRectangle;
    Texture sprite = new Texture("IPOP-Walking.png"),background = new Texture("Map003.png");
    String direction,currentDirection;
    private static OrthographicCamera camera;
    Boolean moving;

    public PlayingScreen(IPOP game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        this.game = game;
        batch = new SpriteBatch();
        direction = "right";
        currentDirection = "right";
        playerRectangle = new Rectangle();
        playerRectangle.setX(50);
        playerRectangle.setY(50);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        //todo Revisar tamano de la TextureRegion para la Animacion
        player_down[0] = new TextureRegion(sprite,0,0,50,50);
        player_down[1] = new TextureRegion(sprite,50,0,50,50);
        player_down[2] = new TextureRegion(sprite,100,0,50,50);
        player_down[3] = new TextureRegion(sprite,50,0,50,50);

        player_left[0] = new TextureRegion(sprite,0,50,50,50);
        player_left[1] = new TextureRegion(sprite,50,50,50,50);
        player_left[2] = new TextureRegion(sprite,100,50,50,50);
        player_left[3] = new TextureRegion(sprite,50,50,50,50);

        player_right[0] = new TextureRegion(sprite,0,100,50,50);
        player_right[1] = new TextureRegion(sprite,50,100,50,50);
        player_right[2] = new TextureRegion(sprite,100,100,50,50);
        player_right[3] = new TextureRegion(sprite,50,100,50,50);

        player_up[0] = new TextureRegion(sprite,0,150,50,50);
        player_up[1] = new TextureRegion(sprite,50,150,50,50);
        player_up[2] = new TextureRegion(sprite,100,150,50,50);
        player_up[3] = new TextureRegion(sprite,50,150,50,50);

        //TouchPads
        upPad = new Rectangle(0, SCR_HEIGHT*2/3, SCR_WIDTH, SCR_HEIGHT);
        downPad = new Rectangle(0, 0, SCR_WIDTH, SCR_HEIGHT/3);
        leftPad = new Rectangle(0, 0, SCR_WIDTH/3, SCR_HEIGHT);
        rightPad = new Rectangle(SCR_WIDTH*2/3, 0, SCR_WIDTH/3, SCR_HEIGHT);

        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        this.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //todo Revisar por cuando se le da al downPad a veces vuelve a la posicion incial por defecto
        //Puede que se este llamando a la clase de nuevo? Y por eso se reinicie
        // Limpia la pantalla con un color específico
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        moving = false;

        //Frame por defecto
        TextureRegion frame = player.getKeyFrame(stateTime,true);

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
        //GetDirection
        if (moving) {
            currentDirection = direction;
            direction = virtual_joystick_control();
            walkDirection(direction);

            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            frame = player.getKeyFrame(stateTime,true);
        }

        batch.begin();
        batch.draw(background,0,0);
        batch.draw(frame,playerRectangle.getX(),playerRectangle.getY(),SCR_WIDTH/4,SCR_HEIGHT/4);
        batch.end();
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
                    return "up";
                } else if (downPad.contains(touchPos.x, touchPos.y)) {
                    return "down";
                } else if (leftPad.contains(touchPos.x, touchPos.y)) {
                    return "left";
                } else if (rightPad.contains(touchPos.x, touchPos.y)) {
                    return "right";
                }
            }
        //Revisar per el Key input
        return currentDirection;
    }
    public void walkDirection(String direction) {


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

    // Resto de métodos de la interfaz Screen
    // ...

    @Override
    public void dispose() {
        batch.dispose();
        sprite.dispose();
        background.dispose();
    }
}