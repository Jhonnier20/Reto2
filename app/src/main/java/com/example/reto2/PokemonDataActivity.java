package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPokemonDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dropBtn = binding.dropBtn;
        pokImg = binding.pokImg;
        new Thread(
                ()->{
                    setImage();
                }
        ).start();
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


    private void loadData() {
        nameTV.setText(pokemon.getName());
        typeTV.setText(pokemon.getType());
        defenseTV.setText(""+pokemon.getDefense());
        attackTV.setText(""+pokemon.getAttack());
        speedTV.setText(""+pokemon.getSpeed());
        lifeTV.setText(""+pokemon.getLife());
    }

    private void setImage() {
        try {
            URL imageUrl = new URL(pokemon.getPhoto());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            img = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runOnUiThread(
                ()->{
                    pokImg.setImageBitmap(img);
                }
        );
    }

}