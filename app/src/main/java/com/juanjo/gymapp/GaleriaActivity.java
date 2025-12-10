package com.juanjo.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.net.Uri;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GaleriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        // Referencias a los 7 botones
        ImageButton btnVideo1 = findViewById(R.id.btnVideo1);
        ImageButton btnVideo2 = findViewById(R.id.btnVideo2);
        ImageButton btnVideo3 = findViewById(R.id.btnVideo3);
        ImageButton btnVideo4 = findViewById(R.id.btnVideo4);
        ImageButton btnVideo5 = findViewById(R.id.btnVideo5);
        ImageButton btnVideo6 = findViewById(R.id.btnVideo6);
        ImageButton btnVideo7 = findViewById(R.id.btnVideo7);

        // Asignar listeners con URLs distintas
        btnVideo1.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video1.mp4"));
        btnVideo2.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video2.mp4"));
        btnVideo3.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video3.mp4"));
        btnVideo4.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video4.mp4"));
        btnVideo5.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video5.mp4"));
        btnVideo6.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video6.mp4"));
        btnVideo7.setOnClickListener(v -> abrirVideo(Config.MEDIA_URL + "video7.mp4"));
    }

    // Método para abrir el video


    private void abrirVideo(String urlVideo) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("VIDEO_URL", urlVideo);
        startActivity(intent);
    }
}