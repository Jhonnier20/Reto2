package com.example.reto2.model;

public class Pokemon {

    private String id;
    private String name;
    private String type;
    private String photo;
    private long dateCatch;
    private int attack;
    private int defense;
    private int speed;
    private int life;

    public Pokemon() {
    }

    public Pokemon(String id, String name, String type, String photo, long dateCatch, int attack, int defense, int speed, int life) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photo = photo;
        this.dateCatch = dateCatch;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.life = life;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getDateCatch() {
        return dateCatch;
    }

    public void setDateCatch(long dateCatch) {
        this.dateCatch = dateCatch;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
