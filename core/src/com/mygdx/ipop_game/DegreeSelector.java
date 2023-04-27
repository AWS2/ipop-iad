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
    public Rectangle player,pokemon;
    String direction = "",currentDirection = direction;

    Rectangle upPad, downPad, leftPad, rightPad;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;

//...

    //runningAnimation = new Animation<TextureRegion>(0.033f, atlas.findRegions("running"), Animation.PlayMode.LOOP);


    public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        batch = new SpriteBatch();
        //Canviar la variable de la feature segons la posicio de la flecha
        img = new Texture("goldSprite.jpg");
        //img = new Texture("sprite-animation4.jpg");
        player = new Rectangle();
        player.width = 64;
        player.height = 64;
        player.x = 800 / 2 - 64 / 2;
        player.y = 20;
        pokemon = new Rectangle();
        pokemon.width = 64;
        pokemon.height = 64;
        pokemon.x = (800 / 2-64)   - 64 / 2;
        pokemon.y = 20;
        direction = "right";
        currentDirection = "right";
        // per cada frame cal indicar x,y,amplada,alçada

		/*if( Gdx.app.getType()== Application.ApplicationType.Android )
			// en Android el host és accessible per 10.0.2.2
			address = "10.0.2.2";
		socket = WebSockets.newSocket(WebSockets.toWebSocketUrl(address, port));
		socket.setSendGracefully(false);
		socket.addListener((WebSocketListener) new AnimationGame().MyWSListener());
		socket.connect();
		socket.send("Enviar dades");
	}*/
    }

    public void render () {
        //ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        //socket.send(direction);

        batch.begin();

        //Calcular la direccio

// si volem invertir el sentit, ho podem fer amb el paràmetre scaleX=-1
        batch.draw(background,0,0);
        /*batch.draw(frame, player.x, player.y, 0, 0,
                frame.getRegionWidth(),frame.getRegionHeight(),1,1,0);
        batch.draw(framePoke,pokemon.x,pokemon.y,0,0, framePoke.getRegionWidth(),framePoke.getRegionHeight(),1,1,0);*/
        batch.end();
    }

    public void dispose () {
        background.dispose();
        batch.dispose();
        img.dispose();
    }


    public boolean onOpen(WebSocket webSocket) {
        return false;
    }

    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        return false;
    }

    public boolean onMessage(WebSocket webSocket, String packet) {
        System.out.println(packet);
        return false;
    }

    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        return false;
    }

    public boolean onError(WebSocket webSocket, Throwable error) {
        return false;
    }
}

}
