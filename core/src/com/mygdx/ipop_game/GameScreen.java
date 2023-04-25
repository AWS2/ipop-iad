package com.mygdx.ipop_game;

import static com.mygdx.ipop_game.utils.GameUtils.SCR_HEIGHT;
import static com.mygdx.ipop_game.utils.GameUtils.SCR_WIDTH;
import static com.mygdx.ipop_game.utils.WebSocketsUtils.ADDRESS;
import static com.mygdx.ipop_game.utils.WebSocketsUtils.PORT;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;

import org.json.JSONObject;

import java.util.Arrays;

public class GameScreen implements ApplicationListener, Screen {
	public static WebSocket socket;
	private int lastSend = 0;
	Texture player;
	Texture background;
	TextureRegion bgRegion;
	Rectangle up, down, left, right;
	private int UP = 1, DOWN = 0, LEFT = 2, RIGHT = 3, IDLE = 4;
	SpriteBatch batch;
	float state_time;
	public TextureRegion[] player_front = new TextureRegion[4];
	public TextureRegion[] player_left = new TextureRegion[4];
	public TextureRegion[] player_right = new TextureRegion[4];
	public TextureRegion[] player_down = new TextureRegion[4];
	public float stateTime = 0f;

	@Override
	public void create() {

		if (Gdx.app.getType() == Application.ApplicationType.Android)
			ADDRESS = "10.0.2.2";
		socket = WebSockets.newSocket(WebSockets.toWebSocketUrl(ADDRESS, PORT));
		socket.setSendGracefully(false);
		socket.addListener(new MyWSListener());
		socket.connect();
		socket.send("Enviar dades");

		loadResources();


		up = new Rectangle(0, SCR_HEIGHT * 2 / 3f, SCR_WIDTH, SCR_HEIGHT / 3f);
		down = new Rectangle(0, 0, SCR_WIDTH, SCR_HEIGHT / 3f);
		left = new Rectangle(0, 0, SCR_WIDTH / 3f, SCR_HEIGHT);
		right = new Rectangle(SCR_WIDTH * 2 / 3f, 0, SCR_WIDTH / 3f, SCR_HEIGHT);

		batch = new SpriteBatch();

		background = new Texture(Gdx.files.internal("Map003.png"));
		bgRegion = new TextureRegion(background);

		state_time = 0f;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion frame = Player.player_down.getKeyFrame(stateTime, true);
		bgRegion.setRegion(0, 0, SCR_WIDTH, SCR_HEIGHT);
		batch.begin();
		batch.draw(bgRegion, 0, 0);
		background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		int direction = virtual_joystick_control();

		switch (direction){
			case 0:
				Player.transform[1] += Player.speed;
				break;
			case 1:
				Player.transform[1] -= Player.speed;
				break;
			case 2:
				Player.transform[0] += Player.speed;
				break;
			case 3:
				Player.transform[0] -= Player.speed;
				break;
			default: // IDLE
				break;
		}

		batch.draw(
				frame,
				Player.transform[0], Player.transform[1],
				0, 0,
				frame.getRegionWidth(), frame.getRegionHeight(),
				Player.scale[0], Player.scale[1],
				0
		);
		batch.end();

		if (stateTime - lastSend > 1.0f) {
			lastSend = (int) stateTime;
			JSONObject json = new JSONObject();
			json.put("player_x", Player.transform[0]);
			json.put("player_y", Player.transform[1]);
			socket.send(json.toString());
		}
	}

	@Override
	public void show() {  }

	@Override
	public void render(float delta) {  }

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
		player.dispose();
	}

	protected int virtual_joystick_control() {
		for (int i = 0; i < 10; i++)
			if (Gdx.input.isTouched(i)) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				if (up.contains(touchPos.x, touchPos.y)) {
					return UP;
				} else if (down.contains(touchPos.x, touchPos.y)) {
					return DOWN;
				} else if (left.contains(touchPos.x, touchPos.y)) {
					return LEFT;
				} else if (right.contains(touchPos.x, touchPos.y)) {
					return RIGHT;
				}
			}
		return IDLE;
	}

	public void loadResources() {

		Player.sprite = new Texture(Gdx.files.internal("IPOP-Walking.png"));

		player_front[0] = new TextureRegion(Player.sprite, 7, 3, 33, 45);
		player_front[1] = new TextureRegion(Player.sprite, 55, 1, 33, 47);
		player_front[2] = new TextureRegion(Player.sprite, 102, 2, 33, 46);
		player_front[3] = new TextureRegion(Player.sprite, 55, 1, 33, 47);

		player_right[0] = new TextureRegion(Player.sprite, 5, 50, 36, 46);
		player_right[1] = new TextureRegion(Player.sprite, 53, 49, 36, 47);
		player_right[2] = new TextureRegion(Player.sprite, 101, 50, 36, 46);
		player_right[3] = new TextureRegion(Player.sprite, 53, 49, 36, 47);

		player_left[0] = new TextureRegion(Player.sprite, 6, 98, 36, 46);
		player_left[1] = new TextureRegion(Player.sprite, 54, 97, 37, 47);
		player_left[2] = new TextureRegion(Player.sprite, 102, 98, 36, 46);
		player_left[3] = new TextureRegion(Player.sprite, 54, 97, 37, 47);

		player_down[0] = new TextureRegion(Player.sprite, 7, 146, 33, 46);
		player_down[1] = new TextureRegion(Player.sprite, 55, 145, 33, 47);
		player_down[2] = new TextureRegion(Player.sprite, 104, 146, 33, 46);
		player_down[3] = new TextureRegion(Player.sprite, 55, 145, 33, 47);

		Player.player_front = new Animation<>(0.25f, player_front);
		Player.player_down = new Animation<>(0.25f, player_down);
		Player.player_left = new Animation<>(0.25f, player_left);
		Player.player_right = new Animation<>(0.25f, player_right);

	}

	class MyWSListener implements WebSocketListener {

		@Override
		public boolean onOpen(WebSocket webSocket) {
			System.out.println("Opening...");
			return false;
		}

		@Override
		public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
			System.out.println("Closing...\n" + reason);
			return false;
		}

		@Override
		public boolean onMessage(WebSocket webSocket, String packet) {
			System.out.println("Message: " + packet);
			return false;
		}

		@Override
		public boolean onMessage(WebSocket webSocket, byte[] packet) {
			System.out.println("Message: " + Arrays.toString(packet));
			return false;
		}

		@Override
		public boolean onError(WebSocket webSocket, Throwable error) {
			System.out.println("ERROR:" + error.toString());
			return false;
		}
	}

}
