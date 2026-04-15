package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find two buttons using findViewById
        Button btnCreate = findViewById(R.id.btnCreateAdvert);
        Button btnLostFound = findViewById(R.id.btnLostandFound);

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAdvert.class);
            startActivity(intent);
        });

        btnLostFound.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdvertList.class);
            startActivity(intent);
        });
    }
}