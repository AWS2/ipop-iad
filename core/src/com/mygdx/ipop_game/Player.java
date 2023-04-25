package com.mygdx.ipop_game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    public static int[] scale = {3, 3};
    public static int[] transform = {100, 100};
    public static int speed = 2;
    public static Texture sprite;
    public static Animation<TextureRegion> player_left, player_right, player_down, player_front;
}