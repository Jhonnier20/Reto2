package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reto2.databinding.ActivityPokemonDataBinding;
import com.example.reto2.model.Pokemon;
import com.example.reto2.model.Trainer;
import com.google.firebase.firestore.FirebaseFirestore;

public class PokemonDataActivity extends AppCompatActivity {

    private ActivityPokemonDataBinding binding;

    private Button dropBtn;
    private ImageView pokImg;
    private TextView nameTV;
    private TextView typeTV;
    private TextView defenseTV;
    private TextView attackTV;
    private TextView speedTV;
    private TextView lifeTV;

    private Pokemon pokemon;
    private Trainer trainer;

    private OnDeletePokemonListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPokemonDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dropBtn = binding.dropBtn;
        pokImg = binding.pokImg;
        nameTV = binding.nameTV;
        typeTV = binding.typeTV;
        defenseTV = binding.defenseTV;
        attackTV = binding.attackTV;
        speedTV = binding.speedTV;
        lifeTV = binding.lifeTV;

        pokemon = (Pokemon) getIntent().getExtras().get("pokemon");
        trainer = (Trainer) getIntent().getExtras().get("trainer");

        loadData();

        dropBtn.setOnClickListener( v -> {
            FirebaseFirestore.getInstance().collection("users").document(trainer.getId()).collection("pokemones").document(pokemon.getId()).delete();
            finish();
        });

    }

    private void dropPokemon(View view) {
        listener.onDeletePokemon(pokemon);
        finish();
    }

    private void loadData() {
        nameTV.setText(pokemon.getName());
        typeTV.setText(pokemon.getType());
        defenseTV.setText(""+pokemon.getDefense());
        attackTV.setText(""+pokemon.getAttack());
        speedTV.setText(""+pokemon.getSpeed());
        lifeTV.setText(""+pokemon.getLife());
    }


    public void setListener(OnDeletePokemonListener listener) {
        this.listener = listener;
    }

    public interface OnDeletePokemonListener {
        void onDeletePokemon(Pokemon p);
    }

}