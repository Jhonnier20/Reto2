package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.reto2.databinding.ActivityPokemonsBinding;
import com.example.reto2.model.Pokemon;
import com.example.reto2.model.Trainer;

import java.util.Date;
import java.util.UUID;

public class PokemonsActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonNewViewListener, PokemonDataActivity.OnDeletePokemonListener {

    private ActivityPokemonsBinding binding;

    private Button catchPokBtn;
    private Button searchPokBtn;
    private EditText catchET;
    private EditText searchET;
    private RecyclerView pokemonsRecycler;

    private PokemonAdapter adapter;
    private LinearLayoutManager manager;

    private Trainer trainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPokemonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        catchET = binding.catchET;
        searchET = binding.searchET;
        pokemonsRecycler = binding.pokemonsRecycler;
        catchPokBtn = binding.catchPokBtn;
        searchPokBtn = binding.searchPokBtn;

        trainer = (Trainer) getIntent().getExtras().get("trainer");

        manager = new LinearLayoutManager(this);
        pokemonsRecycler.setLayoutManager(manager);
        adapter = new PokemonAdapter();
        adapter.setListener(this);
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.setHasFixedSize(true);

        catchPokBtn.setOnClickListener(this::searchPokApi);
        searchPokBtn.setOnClickListener(this::searchPokRecycler);

    }

    private void searchPokRecycler(View view) {

    }

    private void searchPokApi(View view) {
        Pokemon pokemon = new Pokemon(
                UUID.randomUUID().toString(),
                "Picachu",
                "poke",
                "",
                new Date().getTime(),
                10,
                10,
                10,
                10
        );
        Log.e(">>>", pokemon.toString());
        adapter.addPokemon(pokemon);
    }

    @Override
    public void onPokemonNewView(Pokemon pokemon) {
        Intent intent = new Intent(this, PokemonDataActivity.class);
        intent.putExtra("pokemon", pokemon);
        intent.putExtra("trainer", trainer);
        startActivity(intent);
    }

    @Override
    public void onDeletePokemon(Pokemon p) {
        adapter.deletePokemon(p);
    }
}