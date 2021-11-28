package com.example.reto2.pokedexApi;

public class ApiPokemon {

    private String name;
    private Sprite sprites;
    private Stat[] stats;
    private TypePokemon[] types;

    public ApiPokemon() {
    }

    public ApiPokemon(String name, Sprite sprites, Stat[] stats) {
        this.name = name;
        this.sprites = sprites;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stat[] getStats() {
        return stats;
    }

    public void setStats(Stat[] stats) {
        this.stats = stats;
    }

    public TypePokemon[] getTypes() {
        return types;
    }

    public void setTypes(TypePokemon[] types) {
        this.types = types;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }
}
