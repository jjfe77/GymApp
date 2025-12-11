package com.juanjo.gymapp;


import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;


import com.android.volley.toolbox.JsonArrayRequest;

import java.util.List;
import java.util.ArrayList;





public class CrearEjercicioActivity extends AppCompatActivity {

    private EditText editNombre, editDescripcion;
    private Spinner spinnerGrupo;

    private Spinner spinnerEquipos;
    private Button btnGuardarEjercicio;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ejercicio);

        editNombre = findViewById(R.id.editNombreEjercicio);
        editDescripcion = findViewById(R.id.editDescripcionEjercicio);
        spinnerGrupo = findViewById(R.id.spinnerGrupoMuscular);
        btnGuardarEjercicio = findViewById(R.id.btnGuardarEjercicio);
        spinnerEquipos = findViewById(R.id.spinnerEquipos);

        requestQueue = Volley.newRequestQueue(this);

        // TODO: cargar grupos musculares desde tu endpoint si lo tenés
        // Por ahora, ejemplo estático:
        String[] grupos = {"Pecho", "Espalda", "Piernas", "Brazos", "Hombros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, grupos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupo.setAdapter(adapter);

        btnGuardarEjercicio.setOnClickListener(v -> guardarEjercicio());

        cargarEquipos();
    }

    private void guardarEjercicio() {
        String nombre = editNombre.getText().toString().trim();
        String descripcion = editDescripcion.getText().toString().trim();
        int idGrupo = spinnerGrupo.getSelectedItemPosition() + 1; // ejemplo: posición +1 como id

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingresa el nombre del ejercicio", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject ejercicioJson = new JSONObject();
        try {
            ejercicioJson.put("nombre", nombre);
            ejercicioJson.put("id_grupo", idGrupo);
            ejercicioJson.put("descripcion", descripcion);

            int idEquipoSeleccionado = spinnerEquipos.getSelectedItemPosition() + 1; // ejemplo simple

            ejercicioJson.put("id_equipo", idEquipoSeleccionado);

            // Si querés enviar equipos:
            JSONArray equiposArray = new JSONArray();
            // ejemplo vacío, podrías llenarlo con selección de equipos
            ejercicioJson.put("equipos", equiposArray);

        } catch (JSONException e) { e.printStackTrace(); }

        String url = Config.BASE_URL + "crearEjercicio_AP.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, ejercicioJson,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (success) {
                            finish(); // cerrar Activity y volver a ProfesorActivity
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Toast.makeText(this, "Error creando ejercicio", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }

    private void cargarEquipos() {
        String url = Config.BASE_URL + "getEquipos_AP.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id_equipo");
                            String nombre = obj.getString("nombre");
                            nombres.add(nombre);
                            // Podés guardar también los IDs en una lista paralela
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, nombres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEquipos.setAdapter(adapter);
                },
                error -> Toast.makeText(this, "Error cargando equipos", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }



}