package com.mygdx.ipop_game.models;

import java.util.ArrayList;

public class Ocupacio {
    private String nameCicle;
    private ArrayList<String> ocupacions = new ArrayList<>();

    //Setters

    public String getNameCicle() {
        return nameCicle;
    }

    public void setNameCicle(String nameCicle) {
        this.nameCicle = nameCicle;
    }

    public ArrayList<String> getOcupacions() {
        return ocupacions;
    }

    public void setOcupacions(ArrayList<String> ocupacions) {
        this.ocupacions = ocupacions;
    }
}
