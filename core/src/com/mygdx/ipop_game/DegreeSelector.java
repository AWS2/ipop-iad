package com.mygdx.ipop_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.czyzby.websocket.WebSocket;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;
import static com.mygdx.ipop_game.utils.WebSocketsUtils.ADDRESS;
import static com.mygdx.ipop_game.utils.WebSocketsUtils.PORT;

public class DegreeSelector {
    SpriteBatch batch;
    Texture img,background;
    private static OrthographicCamera camera;
    WebSocket socket;
    String address = "localhost";
    int port = 8888;
    Float stateTime = 0.0f;
    public Rectangle degreeSelect;
    String direction = "",currentDirection = direction;

    Rectangle upPad, downPad, leftPad, rightPad;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;

    public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        batch = new SpriteBatch();
        //Canviar la variable de la feature segons la posicio de la flecha
        img = new Texture("goldSprite.jpg");
        //img = new Texture("sprite-animation4.jpg");
        degreeSelect = new Rectangle();
        degreeSelect.width = 64;
        degreeSelect.height = 64;
        degreeSelect.x = SCR_WIDTH / 2 - SCR_HEIGHT / 2;
        degreeSelect.y = 20;
        direction = "right";
        currentDirection = "right";

    }

    public void render () {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(img, degreeSelect.x, degreeSelect.y, 0, 0);
        batch.end();
    }

    public void dispose () {
        background.dispose();
        batch.dispose();
        img.dispose();
    }

}

