package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;

import com.badlogic.gdx.Game;
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

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class TestClass extends Game implements Screen {
    Float stateTime = 0.0f;
    Animation<TextureRegion> player;
    SpriteBatch batch;
    TextureRegion player_up[] = new TextureRegion[4];
    TextureRegion player_left[] = new TextureRegion[4];
    TextureRegion player_right[] = new TextureRegion[4];
    TextureRegion player_down[] = new TextureRegion[4];
    Rectangle upPad,downPad,leftPad,rightPad,playerRectangle;
    Texture sprite = new Texture("IPOP-Walking.png");
    String direction,currentDirection;
    private static OrthographicCamera camera;

    @Override
    public void create() {
        // Aquí se inicializan los recursos del juego, como las texturas, sonidos, etc.
        System.out.println("create");
        batch = new SpriteBatch();
        direction = "right";
        currentDirection = "right";
        playerRectangle = new Rectangle();
        playerRectangle.setX(50);
        playerRectangle.setY(50);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
        player_down[0] = new TextureRegion(sprite,0,0,50,50);
        player_down[1] = new TextureRegion(sprite,50,0,50,50);
        player_down[2] = new TextureRegion(sprite,100,0,50,50);

        player_left[0] = new TextureRegion(sprite,0,50,50,50);
        player_left[1] = new TextureRegion(sprite,50,50,50,50);
        player_left[2] = new TextureRegion(sprite,100,50,50,50);

        player_right[0] = new TextureRegion(sprite,0,100,50,50);
        player_right[1] = new TextureRegion(sprite,50,100,50,50);
        player_right[2] = new TextureRegion(sprite,100,100,50,50);

        player_up[0] = new TextureRegion(sprite,0,150,50,50);
        player_up[1] = new TextureRegion(sprite,50,150,50,50);
        player_up[2] = new TextureRegion(sprite,100,150,50,50);

        //TouchPads
        upPad = new Rectangle(0, SCR_WIDTH*2/3, SCR_WIDTH, SCR_HEIGHT/3);
        downPad = new Rectangle(0, 0, SCR_WIDTH, SCR_HEIGHT/3);
        leftPad = new Rectangle(0, 0, SCR_WIDTH/3, SCR_HEIGHT);
        rightPad = new Rectangle(SCR_WIDTH*2/3, 0, SCR_WIDTH/3, SCR_HEIGHT);
        this.render(Gdx.graphics.getDeltaTime());
    }


        @Override
        public void show() {
            // Este método se llama cuando la pantalla se vuelve visible.
            System.out.println("Show");
            this.create();
        }

        @Override
        public void render(float delta) {
            // Limpia la pantalla con un color específico
            Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

                direction = "left";
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))  {

                direction = "right";

            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {

                direction = "up";
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

                direction = "down";
            }
            currentDirection = direction;
            //GetDirection
            direction = virtual_joystick_control();
            walkDirection(direction);

            player = new Animation<>(0.33f,player_right);
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            TextureRegion frame = player.getKeyFrame(stateTime,true);
            batch.begin();
            batch.draw(frame,SCR_WIDTH,SCR_HEIGHT);
            batch.end();
            System.out.println("RENDER");
            System.out.println(delta);
            // Aquí se actualizan los elementos del juego, como los personajes, enemigos, etc.

            // Aquí se dibujan los elementos del juego en pantalla, como los personajes, fondos, etc.
        }

        @Override
        public void resize(int width, int height) {
            // Este método se llama cuando la pantalla cambia de tamaño.
        }

        @Override
        public void pause() {
            // Este método se llama cuando la aplicación se pausa, como cuando el usuario presiona el botón de inicio en Android.
        }

        @Override
        public void resume() {
            // Este método se llama cuando la aplicación se reanuda después de una pausa.
        }

        @Override
        public void hide() {
            // Este método se llama cuando la pantalla deja de ser visible.
        }

        @Override
        public void dispose() {
            // Aquí se liberan los recursos utilizados por la pantalla, como las texturas, sonidos, etc.
            batch.dispose();
            sprite.dispose();
    }

    protected String virtual_joystick_control() {
        // iterar per multitouch
        // cada "i" és un possible "touch" d'un dit a la pantalla
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                TestClass.camera.unproject(touchPos);
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
            player = new Animation<TextureRegion>(0.25f,player_right);
            playerRectangle.x += 50 * Gdx.graphics.getDeltaTime();
        }
        else if (direction.equals("left")) {
            player = new Animation<TextureRegion>(0.25f,player_left);
            playerRectangle.x -= 50 * Gdx.graphics.getDeltaTime();
        }
        else if (direction.equals("up")) {
            player = new Animation<TextureRegion>(0.25f,player_up);
            playerRectangle.y += 50 * Gdx.graphics.getDeltaTime();

        }
        else if (direction.equals("down")) {
            player = new Animation<TextureRegion>(0.25f,player_down);
            playerRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

        }
    }

}
