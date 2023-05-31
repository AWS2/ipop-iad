package com.mygdx.ipop_game.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Player {
    public static String player_alias= "";
    public String players_alias = "";
    public static String player_ocupation= "";
    public String players_ocupation= "";
    public static int player_character = 0;
    public int players_character = 0;
    public static int totemsLeft = 5;

    public static int[] scale = {128, 128};
    public static int[] transform = {1050, 350};
    public int[] players_transform = {1050,350};

    public static int speed = 2;
    public static Texture sprite;
    public Texture players_sprite;

    public static ArrayList<Animation<TextureRegion>> player_show_room, player_left, player_right, player_up, player_down;
    public ArrayList<Animation<TextureRegion>> players_show_room, players_left, players_right, players_up, players_down;

    public Player(String player_alias, int player_character, int[] position) {
        this.players_alias = player_alias;
        this.players_character = player_character;
        this.players_transform = position;
    }

}