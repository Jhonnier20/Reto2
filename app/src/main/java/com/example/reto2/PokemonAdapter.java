package com.example.reto2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.model.Pokemon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder> implements PokemonViewHolder.OnViewPokemonListener {

    private ArrayList<Pokemon> pokemons;

    private OnPokemonNewViewListener listener;
    private OnShowImage imgListener;

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

        holder.getNamePokTVRow().setText(pokemon.getName());
        holder.setPokemon(pokemon);
        //Uri uri =Uri.parse();
        new Thread(
                ()->{
                    imgListener.onShowImage(downloadImg(pokemon.getPhoto()),holder.getPokImgRow());
                }
        ).start();

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    private Bitmap downloadImg (String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return imagen;
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

    public void setImgListener(OnShowImage imgListener) {
        this.imgListener = imgListener;
    }

    public void deletePokemon() {
        notifyItemRangeRemoved(0,pokemons.size());
        pokemons.clear();
    }

    public interface OnPokemonNewViewListener {
        void onPokemonNewView(Pokemon pokemon);
    }
    public interface OnShowImage{
        void onShowImage(Bitmap img, ImageView imgRow);
    }
}
