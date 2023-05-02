package com.mygdx.ipop_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class IPOP extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    private static IPOP instancia = null;

    public static IPOP obtenerInstancia() {
        if (instancia == null) {
            instancia = new IPOP();
        }
        return instancia;
    }

    public IPOP() {  }

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen());
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void setTitle() {
        setFontSize(4);
        setFontColor(Color.BLACK);
    }

    public void setSubtitle() {
        setFontSize(3);
        setFontColor(Color.BLACK);
    }

    public void setFontSize(int size) {
        this.font.getData().setScale(size);
    }

    public void setFontColor(Color color) {
        this.font.setColor(color);
    }

    public void restartFont() {
        this.font = new BitmapFont();
    }

    static ArrayList<Family> families = new ArrayList<>();
    static ArrayList<String> informatica = new ArrayList<>();
    static ArrayList<String> administrativo = new ArrayList<>();
    static ArrayList<String> automocion = new ArrayList<>();
    static ArrayList<String> mecanica = new ArrayList<>();
    static ArrayList<String> produccion = new ArrayList<>();
    static ArrayList<String> aguas = new ArrayList<>();


    public static void loadResources() {

        Player.sprite = new Texture(Gdx.files.internal("IPOP-Walking.png"));

        TextureRegion[] player_front = new TextureRegion[4];
        TextureRegion[] player_left = new TextureRegion[4];
        TextureRegion[] player_right = new TextureRegion[4];
        TextureRegion[] player_down = new TextureRegion[4];
        TextureRegion[] player_show_room = new TextureRegion[16];

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

        Player.player_down = new Animation<>(0.25f, player_front);
        Player.player_up = new Animation<>(0.25f, player_down);
        Player.player_left = new Animation<>(0.25f, player_left);
        Player.player_right = new Animation<>(0.25f, player_right);

        informatica.add("Sistemes microinformatics i xarxes");
        informatica.add("Administracio de sistemes informatics en xarxa");
        informatica.add("Desenvolupament d aplicacions multiplataforma");
        informatica.add("Desenvolupament d aplicacions web");

        administrativo.add("Gestio administrativa");
        administrativo.add("Administracio i finances");
        administrativo.add("Assistencia a la direccio");

        automocion.add("Electromecanica de vehicles automobils");
        automocion.add("Automocio");

        mecanica.add("Mecanitzacio");
        mecanica.add("Programacio en produccio fabricacio mecanica");

        produccion.add("Manteniment electromecanics");
        produccion.add("Mecatronica industrial");

        aguas.add("Gestió de l aigua");

        families.add(new Family("Informatica", informatica));
        families.add(new Family("Administratiu", administrativo));
        families.add(new Family("Automocio", automocion));
        families.add(new Family("Manteniment", mecanica));
        families.add(new Family("Fabricacio Mecanica", produccion));
        families.add(new Family("Aigues", aguas));

    }

}