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

    /*private void cargarProgresos() {
        String url = "http://10.0.2.2/api/ver_progreso_android.php?id_alumno=" + idAlumno;
        Log.i(TAG, "URL: " + url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        ArrayList<String> lista = new ArrayList<>();

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

                            String linea = fecha + " - " + ejercicio +
                                    " | Plan: " + seriesPlan + "x" + repesPlan + " (" + cargaPlan + "kg)" +
                                    " | Real: " + seriesReal + "x" + repesReal + " (" + cargaReal + "kg)";
                            lista.add(linea);
                        }

                        Collections.sort(lista, (a, b) -> {
                            String ejercicioA = a.split(" - ")[1].split("\\|")[0].trim();
                            String ejercicioB = b.split(" - ")[1].split("\\|")[0].trim();
                            return ejercicioA.compareToIgnoreCase(ejercicioB);
                        });


                        ArrayAdapter<String> adapt = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, lista);
                        listaProgresos.setAdapter(adapt);

                    } catch (Exception e) {
                        Toast.makeText(this, "Error parseando JSON", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Exception cargarProgresos", e);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error conexión progresos", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Volley error cargarProgresos: " + error.getMessage(), error);
                });

        Volley.newRequestQueue(this).add(req);
    }*/
    private void cargarProgresos() {
        String url = "http://10.0.2.2/api/ver_progreso_android.php?id_alumno=" + idAlumno;

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

                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                                R.layout.item_progreso, R.id.txtEjercicio, listaEjercicios) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView txtDetalle = view.findViewById(R.id.txtDetalle);
                                txtDetalle.setText(listaDetalles.get(position));
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