package com.juanjo.gymapp;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ModificarRutinaActivity extends AppCompatActivity {

    private int idAlumno;
    private Spinner spinnerRutinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_rutina);

        spinnerRutinas = findViewById(R.id.spinnerRutinas);

        idAlumno = getIntent().getIntExtra("idAlumno", -1);

        if (idAlumno == -1) {
            Toast.makeText(this, "Error: no se recibió el alumno", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Después agregamos la carga de rutinas
    }
}