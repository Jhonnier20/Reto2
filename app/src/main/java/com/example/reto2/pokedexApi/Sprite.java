package com.example.reto2.pokedexApi;

public class Sprite {
    private String front_default;

    public Sprite() {
    }

    public Sprite(String front_default) {
        this.front_default = front_default;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }
}
