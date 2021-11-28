package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reto2.databinding.ActivityMainBinding;
import com.example.reto2.model.Trainer;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Button loginBtn;
    private EditText usernameET;

    private Trainer trainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usernameET = binding.usernameET;
        loginBtn = binding.loginBtn;

        loginBtn.setOnClickListener(this::login);

    }

    private void login(View view) {
        if(usernameET.getText().toString() == null || usernameET.getText().toString().equals("")) {
            Toast.makeText(this, "No ha ingresado ningun usuario!", Toast.LENGTH_LONG).show();

        } else {

            trainer = new Trainer(UUID.randomUUID().toString(), usernameET.getText().toString());

            Query query = FirebaseFirestore.getInstance().collection("trainers").whereEqualTo("name", trainer.getName());
            query.get().addOnCompleteListener(
                    task -> {
                        if(task.getResult().size() == 0) {
                            FirebaseFirestore.getInstance().collection("trainers").document(trainer.getId()).set(trainer);

                        } else {
                            for (DocumentSnapshot doc: task.getResult()) {
                                Trainer trainerNew = doc.toObject(Trainer.class);
                                trainer.setId(trainerNew.getId());
                                trainer.setName(trainerNew.getName());
                                break;
                            }
                        }
                        Intent intent = new Intent(this, PokemonsActivity.class);
                        intent.putExtra("trainer", trainer);
                        startActivity(intent);
                    }
            );

        }

    }
}