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

    public IPOP() {

    }

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

    static ArrayList<String> families = new ArrayList<>();
    static ArrayList<Texture> imgFamilies = new ArrayList<>();
    static ArrayList<String> familiaInformatica = new ArrayList<>();
    static ArrayList<String> familiaAdministratiu = new ArrayList<>();
    static ArrayList<String> familiaAutomocio = new ArrayList<>();
    static ArrayList<String> familiaProduccio = new ArrayList<>();
    static ArrayList<String> familiaMecanica = new ArrayList<>();
    static ArrayList<String>  familiaAigua = new ArrayList<>();

    static ArrayList<Texture> familia_imagenes = new ArrayList<>();

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

        families.add("Informatica");
        families.add("Administratiu");
        families.add("Automocio");
        families.add("Manteniment i serveis a la produccio");
        families.add("Fabricacio Mecanica");
        families.add("Aigues");

        familiaInformatica.add("Sistemes microinformatics i xarxes");
        familiaInformatica.add("Administracio de sistemes informatics en xarxa");
        familiaInformatica.add("Desenvolupament d aplicacions multiplataforma");
        familiaInformatica.add("Desenvolupament d aplicacions web");

        familiaAdministratiu.add("Gestio administrativa");
        familiaAdministratiu.add("Administracio i finances");
        familiaAdministratiu.add("Assistencia a la direccio");

        familiaAutomocio.add("Electromecanica de vehicles automobils");
        familiaAutomocio.add("Automocio");

        familiaMecanica.add("Mecanitzacio");
        familiaMecanica.add("Programacio en produccio fabricacio mecanica");

        familiaProduccio.add("Manteniment electromecanics");
        familiaProduccio.add("Mecatronica industrial");

        familiaAigua.add("Gestió de l aigua");

    }

}