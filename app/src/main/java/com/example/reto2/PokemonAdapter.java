package com.example.reto2;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder> implements PokemonViewHolder.OnViewPokemonListener {

    private ArrayList<Pokemon> pokemons;

    private OnPokemonNewViewListener listener;

    public PokemonAdapter() {
        pokemons = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemonrow, parent, false);
        PokemonViewHolder holder = new PokemonViewHolder(view);
        holder.setListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.setPokemon(pokemon);
        holder.getNamePokTVRow().setText(pokemon.getName());
        //Uri uri =Uri.parse();
        //holder.getPokImgRow().setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
        notifyItemInserted(pokemons.size()-1);
    }

    @Override
    public void onViewPokemon(Pokemon p) {
        listener.onPokemonNewView(p);
    }


    public void setListener(OnPokemonNewViewListener listener) {
        this.listener = listener;
    }

    public void deletePokemon(Pokemon p) {
        int i = pokemons.indexOf(p);
        pokemons.remove(i);
    }

    public interface OnPokemonNewViewListener {
        void onPokemonNewView(Pokemon pokemon);
    }
}
