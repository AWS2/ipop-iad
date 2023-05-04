package com.mygdx.ipop_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class TestClass extends Game implements Screen {

    @Override
    public void create() {
        // Aquí se inicializan los recursos del juego, como las texturas, sonidos, etc.
        System.out.println("create");
        Texture sprite = new Texture("IPOP-Walking.png");
        TextureRegion player_up[] = new TextureRegion[4];
        TextureRegion player_left[] = new TextureRegion[4];
        TextureRegion player_right[] = new TextureRegion[4];
        TextureRegion player_down[] = new TextureRegion[4];

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

    }


        @Override
        public void show() {
            // Este método se llama cuando la pantalla se vuelve visible.
            System.out.println("Show");
        }

        @Override
        public void render(float delta) {
            // Limpia la pantalla con un color específico
            Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            System.out.println("RENDER");
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

    }
}
