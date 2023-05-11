package com.mygdx.ipop_game.models;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;

public class Totem {
    private float x,y,textX;
    private int id, height,width;
    private Texture image;
    private String family,ocupacio;
    private Rectangle textBox;
    private Sound sound;
    private Boolean correctTotem;
    private GlyphLayout glyphLayout;
    //Constructor
    public Totem() {
    }

    public Totem(int id, float x, float y, int height, int width, Texture image, String family, String ocupacio, Rectangle textBox, GlyphLayout glyphLayout, float textX, Sound sound, Boolean correctTotem) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.image = image;
        this.family = family;
        this.ocupacio = ocupacio;
        this.textBox = textBox;
        this.glyphLayout = glyphLayout;
        this.textX = textX;
        this.sound = sound;
        this.correctTotem = correctTotem;
    }

    //Setters i Getters

    public float getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getOcupacio() {
        return ocupacio;
    }
    public void setOcupacio(String ocupacio) {
        this.ocupacio = ocupacio;
    }

    public Rectangle getTextBox() {
        return textBox;
    }

    public void setTextBox(Rectangle textBox) {
        this.textBox = textBox;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }
    public void setGlyphLayout(GlyphLayout glyphLayout) {
        this.glyphLayout = glyphLayout;
    }

    public float getTextX() {
        return textX;
    }

    public void setTextX(float textX) {
        this.textX = textX;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Boolean getCorrectTotem() {
        return correctTotem;
    }

    public void setCorrectTotem(Boolean correctTotem) {
        this.correctTotem = correctTotem;
    }

    //ToString

    @Override
    public String toString() {
        return "Totem{" +
                "x=" + x +
                ", y=" + y +
                ", textX=" + textX +
                ", height=" + height +
                ", width=" + width +
                ", image=" + image +
                ", family='" + family + '\'' +
                ", ocupacio='" + ocupacio + '\'' +
                ", textBox=" + textBox +
                ", sound=" + sound +
                ", correctTotem=" + correctTotem +
                ", glyphLayout=" + glyphLayout +
                '}';
    }
}