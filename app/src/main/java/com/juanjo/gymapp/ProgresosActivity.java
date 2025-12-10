package com.juanjo.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProgresosActivity extends AppCompatActivity {

    private static final String TAG = "PROGRESOS_DEBUG";
    private ListView listaProgresos;
    private int idAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresos);

        listaProgresos = findViewById(R.id.listaProgresos);

        // Recibir idAlumno desde AlumnoActivity
        idAlumno = getIntent().getIntExtra("idAlumno", -1);

        if (idAlumno == -1) {
            Toast.makeText(this, "ID Alumno inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarProgresos();
    }



    private void cargarProgresos() {
        String url = Config.BASE_URL + "ver_progreso_android.php?id_alumno=" + idAlumno;

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);

                        ArrayList<String> listaEjercicios = new ArrayList<>();
                        ArrayList<String> listaDetalles = new ArrayList<>();

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            String fecha = obj.optString("fecha", "");
                            String ejercicio = obj.optString("ejercicio", "");
                            int seriesPlan = obj.optInt("series_plan", 0);
                            int seriesReal = obj.optInt("series_real", 0);
                            int repesPlan = obj.optInt("repes_plan", 0);
                            int repesReal = obj.optInt("repes_real", 0);
                            double cargaPlan = obj.optDouble("carga_plan", 0);
                            double cargaReal = obj.optDouble("carga_real", 0);

                            listaEjercicios.add(ejercicio);
                            listaDetalles.add("Fecha: " + fecha +
                                    " | Plan: " + seriesPlan + "x" + repesPlan + " (" + cargaPlan + "kg)" +
                                    " | Real: " + seriesReal + "x" + repesReal + " (" + cargaReal + "kg)");
                        }

                        // 🔥 ORDENAR POR EJERCICIO (simple y sin errores)
                        ArrayList<Integer> indices = new ArrayList<>();
                        for (int i = 0; i < listaEjercicios.size(); i++) {
                            indices.add(i);
                        }

                        // ordenar los índices según el ejercicio
                        Collections.sort(indices, (i1, i2) ->
                                listaEjercicios.get(i1).compareToIgnoreCase(listaEjercicios.get(i2))
                        );

                        // crear listas finales ordenadas
                        ArrayList<String> listaEjerciciosOrdenada = new ArrayList<>();
                        ArrayList<String> listaDetallesOrdenada = new ArrayList<>();

                        for (int index : indices) {
                            listaEjerciciosOrdenada.add(listaEjercicios.get(index));
                            listaDetallesOrdenada.add(listaDetalles.get(index));
                        }

                        // Adaptador usando listas ordenadas
                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                                R.layout.item_progreso, R.id.txtEjercicio, listaEjerciciosOrdenada) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView txtDetalle = view.findViewById(R.id.txtDetalle);
                                txtDetalle.setText(listaDetallesOrdenada.get(position));
                                return view;
                            }
                        };

                        listaProgresos.setAdapter(adaptador);

                    } catch (Exception e) {
                        Toast.makeText(this, "Error parseando JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Error conexión progresos", Toast.LENGTH_LONG).show());

        Volley.newRequestQueue(this).add(req);
    }




}