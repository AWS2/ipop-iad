package com.mygdx.ipop_game;

import java.util.ArrayList;

public class Family {

    String name;
    ArrayList<String> ocupations;

    public Family(String name, ArrayList<String> ocupations) {
        this.name = name;
        this.ocupations = ocupations;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getOcupations() {
        return ocupations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOcupations(ArrayList<String> ocupations) {
        this.ocupations = ocupations;
    }
}
