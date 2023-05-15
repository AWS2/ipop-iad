package com.mygdx.ipop_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.ipop_game.models.Family;
import com.mygdx.ipop_game.models.Player;
import com.mygdx.ipop_game.ui.MainMenuScreen;

import java.util.ArrayList;

public class IPOP extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public ShapeRenderer shapeRenderer;

    private static IPOP instancia = null;

    public static IPOP obtenerInstancia() {
        if (instancia == null) {
            instancia = new IPOP();
        }
        return instancia;
    }

    public IPOP() {
    }

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void setFontSize(int size) {
        this.font.getData().setScale(size);
    }

    public void setFontColor(Color color) {
        this.font.setColor(color);
    }

    public static ArrayList<Family> families = new ArrayList<>();
    public static ArrayList<String> informatica = new ArrayList<>();
    public static ArrayList<String> administrativo = new ArrayList<>();
    public static ArrayList<String> automocion = new ArrayList<>();
    public static ArrayList<String> mecanica = new ArrayList<>();
    public static ArrayList<String> produccion = new ArrayList<>();
    public static ArrayList<String> aguas = new ArrayList<>();
    public static Texture[] score_bar = new Texture[7];
    public static Texture[] wrong_score_bar = new Texture[7];


    public static void loadResources() {

        Player.sprite = new Texture(Gdx.files.internal("IPOP-Walking.png"));
        Texture scoreBar = new Texture(Gdx.files.internal("score_bar.png"));

        Player.player_down = new ArrayList<>();
        Player.player_up = new ArrayList<>();
        Player.player_left = new ArrayList<>();
        Player.player_right = new ArrayList<>();
        Player.player_show_room = new ArrayList<>();

        TextureRegion[] player_front = new TextureRegion[4];
        TextureRegion[] player_left = new TextureRegion[4];
        TextureRegion[] player_right = new TextureRegion[4];
        TextureRegion[] player_down = new TextureRegion[4];
        TextureRegion[] player_show_room = new TextureRegion[16];

        player_front[0] = new TextureRegion(Player.sprite, 7, 3, 33, 45);
        player_front[1] = new TextureRegion(Player.sprite, 55, 1, 33, 47);
        player_front[2] = new TextureRegion(Player.sprite, 102, 2, 33, 46);
        player_front[3] = new TextureRegion(Player.sprite, 55, 1, 33, 47);

        player_left[0] = new TextureRegion(Player.sprite, 5, 50, 36, 46);
        player_left[1] = new TextureRegion(Player.sprite, 53, 49, 36, 47);
        player_left[2] = new TextureRegion(Player.sprite, 101, 50, 36, 46);
        player_left[3] = new TextureRegion(Player.sprite, 53, 49, 36, 47);

        player_right[0] = new TextureRegion(Player.sprite, 6, 98, 36, 46);
        player_right[1] = new TextureRegion(Player.sprite, 54, 97, 37, 47);
        player_right[2] = new TextureRegion(Player.sprite, 102, 98, 36, 46);
        player_right[3] = new TextureRegion(Player.sprite, 54, 97, 37, 47);

        player_down[0] = new TextureRegion(Player.sprite, 7, 146, 33, 46);
        player_down[1] = new TextureRegion(Player.sprite, 55, 145, 33, 47);
        player_down[2] = new TextureRegion(Player.sprite, 104, 146, 33, 46);
        player_down[3] = new TextureRegion(Player.sprite, 55, 145, 33, 47);

        player_show_room[0] = player_front[0];
        player_show_room[1] = player_front[1];
        player_show_room[2] = player_front[2];
        player_show_room[3] = player_front[3];

        player_show_room[4] = player_right[0];
        player_show_room[5] = player_right[1];
        player_show_room[6] = player_right[2];
        player_show_room[7] = player_right[3];

        player_show_room[8] = player_left[0];
        player_show_room[9] = player_left[1];
        player_show_room[10] = player_left[2];
        player_show_room[11] = player_left[3];

        player_show_room[12] = player_down[0];
        player_show_room[13] = player_down[1];
        player_show_room[14] = player_down[2];
        player_show_room[15] = player_down[3];

        Player.player_down.add(new Animation<>(0.25f, player_front));
        Player.player_up.add(new Animation<>(0.25f, player_down));
        Player.player_left.add(new Animation<>(0.25f, player_left));
        Player.player_right.add(new Animation<>(0.25f, player_right));
        Player.player_show_room.add(new Animation<>(0.25f, player_show_room));

        player_front = new TextureRegion[4];
        player_left = new TextureRegion[4];
        player_right = new TextureRegion[4];
        player_down = new TextureRegion[4];
        player_show_room = new TextureRegion[16];

        player_front[0] = new TextureRegion(Player.sprite, 149, 3, 35, 45);
        player_front[1] = new TextureRegion(Player.sprite, 198, 2, 35, 46);
        player_front[2] = new TextureRegion(Player.sprite, 247, 3, 35, 44);
        player_front[3] = new TextureRegion(Player.sprite, 198, 2, 35, 46);

        player_left[0] = new TextureRegion(Player.sprite, 152, 52, 37, 44);
        player_left[1] = new TextureRegion(Player.sprite, 200, 51, 36, 45);
        player_left[2] = new TextureRegion(Player.sprite, 248, 52, 35, 44);
        player_left[3] = new TextureRegion(Player.sprite, 200, 51, 36, 45);

        player_right[0] = new TextureRegion(Player.sprite, 146, 100, 37, 44);
        player_right[1] = new TextureRegion(Player.sprite, 195, 99, 34, 45);
        player_right[2] = new TextureRegion(Player.sprite, 244, 100, 35, 44);
        player_right[3] = new TextureRegion(Player.sprite, 195, 99, 34, 45);

        player_down[0] = new TextureRegion(Player.sprite, 150, 147, 35, 45);
        player_down[1] = new TextureRegion(Player.sprite, 198, 146, 35, 46);
        player_down[2] = new TextureRegion(Player.sprite, 246, 147, 35, 45);
        player_down[3] = new TextureRegion(Player.sprite, 198, 146, 35, 46);

        player_show_room[0] = player_front[0];
        player_show_room[1] = player_front[1];
        player_show_room[2] = player_front[2];
        player_show_room[3] = player_front[3];

        player_show_room[4] = player_right[0];
        player_show_room[5] = player_right[1];
        player_show_room[6] = player_right[2];
        player_show_room[7] = player_right[3];

        player_show_room[8] = player_left[0];
        player_show_room[9] = player_left[1];
        player_show_room[10] = player_left[2];
        player_show_room[11] = player_left[3];

        player_show_room[12] = player_down[0];
        player_show_room[13] = player_down[1];
        player_show_room[14] = player_down[2];
        player_show_room[15] = player_down[3];

        Player.player_down.add(new Animation<>(0.25f, player_front));
        Player.player_up.add(new Animation<>(0.25f, player_down));
        Player.player_left.add(new Animation<>(0.25f, player_left));
        Player.player_right.add(new Animation<>(0.25f, player_right));
        Player.player_show_room.add(new Animation<>(0.25f, player_show_room));

        score_bar[0] = new Texture(Gdx.files.internal("score_0.png"));
        score_bar[1] = new Texture(Gdx.files.internal("score_1.png"));
        score_bar[2] = new Texture(Gdx.files.internal("score_2.png"));
        score_bar[3] = new Texture(Gdx.files.internal("score_3.png"));
        score_bar[4] = new Texture(Gdx.files.internal("score_4.png"));
        score_bar[5] = new Texture(Gdx.files.internal("score_5.png"));
        score_bar[6] = new Texture(Gdx.files.internal("score_5.png"));

        wrong_score_bar[0] = new Texture(Gdx.files.internal("score_0.png"));
        wrong_score_bar[1] = new Texture(Gdx.files.internal("wrong_score_bar_1.png"));
        wrong_score_bar[2] = new Texture(Gdx.files.internal("wrong_score_bar_2.png"));
        wrong_score_bar[3] = new Texture(Gdx.files.internal("wrong_score_bar_3.png"));
        wrong_score_bar[4] = new Texture(Gdx.files.internal("wrong_score_bar_4.png"));
        wrong_score_bar[5] = new Texture(Gdx.files.internal("wrong_score_bar_5.png"));
        wrong_score_bar[6] = new Texture(Gdx.files.internal("wrong_score_bar_5.png"));

        informatica = new ArrayList<>();
        informatica.add("Sistemes microinformatics i xarxes");
        informatica.add("Administracio de sistemes informatics en xarxa");
        informatica.add("Desenvolupament d aplicacions multiplataforma");
        informatica.add("Desenvolupament d aplicacions web");

        administrativo = new ArrayList<>();
        administrativo.add("Gestio administrativa");
        administrativo.add("Administracio i finances");
        administrativo.add("Assistencia a la direccio");

        automocion = new ArrayList<>();
        automocion.add("Electromecanica de vehicles automobils");
        automocion.add("Automocio");

        mecanica = new ArrayList<>();
        mecanica.add("Mecanitzacio");
        mecanica.add("Programacio en produccio fabricacio mecanica");

        produccion = new ArrayList<>();
        produccion.add("Manteniment electromecanics");
        produccion.add("Mecatronica industrial");

        aguas = new ArrayList<>();
        aguas.add("Gestió de l aigua");

        families = new ArrayList<>();
        families.add(new Family("Informatica", informatica));
        families.add(new Family("Administratiu", administrativo));
        families.add(new Family("Automocio", automocion));
        families.add(new Family("Manteniment", mecanica));
        families.add(new Family("Fabricacio Mecanica", produccion));
        families.add(new Family("Aigues", aguas));

    }

}