package com.example.reto2.pokedexApi;

public class Stat {

    private int base_stat;
    private int effort;
    private StatType stat;

    public Stat() {
    }

    public Stat(int base_stat, int effort, StatType stat) {
        this.base_stat = base_stat;
        this.effort = effort;
        this.stat = stat;
    }

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public StatType getStat() {
        return stat;
    }

    public void setStat(StatType stat) {
        this.stat = stat;
    }
}
