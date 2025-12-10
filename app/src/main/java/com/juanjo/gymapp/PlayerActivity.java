package com.juanjo.gymapp;



import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        VideoView videoView = findViewById(R.id.videoView);

        // Recibir la URL desde GaleriaActivity
        String url = getIntent().getStringExtra("VIDEO_URL");

        videoView.setVideoURI(Uri.parse(url));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }
}