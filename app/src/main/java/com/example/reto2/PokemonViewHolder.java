package com.example.reto2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.model.Pokemon;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    private Pokemon pokemon;

    private TextView namePokTVRow;
    private ImageView pokImgRow;

    public OnViewPokemonListener listener;

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);

        namePokTVRow = itemView.findViewById(R.id.namePokTVRow);
        pokImgRow = itemView.findViewById(R.id.pokImgRow);

        pokImgRow.setOnClickListener(
                v -> {
                    listener.onViewPokemon(pokemon);
                }
        );
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public TextView getNamePokTVRow() {
        return namePokTVRow;
    }

    public void setNamePokTVRow(TextView namePokTVRow) {
        this.namePokTVRow = namePokTVRow;
    }

    public ImageView getPokImgRow() {
        return pokImgRow;
    }

    public void setPokImgRow(ImageView pokImgRow) {
        this.pokImgRow = pokImgRow;
    }


    public void setListener(OnViewPokemonListener listener) {
        this.listener = listener;
    }

    public interface OnViewPokemonListener {
        void onViewPokemon(Pokemon p);
    }
}
