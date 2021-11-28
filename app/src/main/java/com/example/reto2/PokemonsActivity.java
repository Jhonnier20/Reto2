package com.example.reto2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto2.databinding.ActivityPokemonsBinding;
import com.example.reto2.model.Pokemon;
import com.example.reto2.model.Trainer;
import com.example.reto2.pokedexApi.ApiPokemon;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Date;
import java.util.UUID;

public class PokemonsActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonNewViewListener, PokemonAdapter.OnShowImage {

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
        adapter.setImgListener(this);
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.setHasFixedSize(true);

        catchPokBtn.setOnClickListener(this::searchPokApi);
        searchPokBtn.setOnClickListener(this::searchPokRecycler);
        chargePokemons();
    }

    private void searchPokRecycler(View view) {
        String namePoke = searchET.getText().toString();
        String userID = trainer.getId();
        FirebaseFirestore.getInstance().collection("trainers").document(userID).collection("pokemons").whereEqualTo("name", namePoke).get().addOnCompleteListener(
                task -> {
                    if (task.getResult().size() == 0) {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "El pokemon: " + namePoke + " no lo has atrapado!", Toast.LENGTH_LONG).show();
                                }
                        );
                    } else {
                        runOnUiThread(
                                () -> {
                                    adapter.deletePokemon();
                                }
                        );
                        for (DocumentSnapshot doc : task.getResult()) {
                            Pokemon pokemon = doc.toObject(Pokemon.class);
                            adapter.addPokemon(pokemon);
                        }
                    }
                }
        );
    }
    public void chargePokemons() {
        Log.e(">>>",trainer.getId());
        FirebaseFirestore.getInstance().collection("trainers").document(trainer.getId()).collection("pokemons").orderBy("dateCatch").get().addOnCompleteListener(
                task -> {
                    for (DocumentSnapshot doc : task.getResult()) {

                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        Log.e(">>>", pokemon.toString());
                        adapter.addPokemon(pokemon);
                    }
                }
        );
    }
    private void searchPokApi(View view) {
        /**Pokemon pokemon = new Pokemon(
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
        adapter.addPokemon(pokemon);*/
        String name = catchET.getText().toString();
        catchET.setText("");
        new Thread(
                () -> {
                    HTTPSWebUtilDomi httpsWebUtilDomi = new HTTPSWebUtilDomi();
                    String json = httpsWebUtilDomi.GETrequest("https://pokeapi.co/api/v2/pokemon/" + name);
                    if (json.isEmpty() == false) {
                        Gson gson = new Gson();
                        ApiPokemon pokemonApi = gson.fromJson(json, ApiPokemon.class);
                        String type = pokemonApi.getTypes()[0].getType().getName();
                        int defense = pokemonApi.getStats()[2].getBase_stat();
                        int attack = pokemonApi.getStats()[1].getBase_stat();
                        int velocity = pokemonApi.getStats()[5].getBase_stat();
                        int life = pokemonApi.getStats()[0].getBase_stat();
                        String img = pokemonApi.getSprites().getFront_default();

                            Pokemon pokem = new Pokemon(UUID.randomUUID().toString(), pokemonApi.getName(), type, img,  new Date().getTime(), attack, defense, velocity, life);
                        addPokemon(pokem);
                    } else {
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(this, "El pokemon: " + name + " no existe", Toast.LENGTH_LONG).show();
                                }
                        );

                    }

                }
        ).start();
    }
    public void addPokemon(Pokemon pokemon) {
        FirebaseFirestore.getInstance().collection("trainers").document(trainer.getId()).collection("pokemons").document(pokemon.getId()).set(pokemon);
        runOnUiThread(
                () -> {
                    adapter.addPokemon(pokemon);
                }
        );
    }
    @Override
    public void onPokemonNewView(Pokemon pokemon) {
        Intent intent = new Intent(this, PokemonDataActivity.class);
        intent.putExtra("pokemon", pokemon);
        intent.putExtra("trainer", trainer);
        startActivity(intent);
    }


    @Override
    public void onShowImage(Bitmap img, ImageView imgRow) {
        runOnUiThread(
                () -> {
                    imgRow.setImageBitmap(img);
                }
        );
    }
}