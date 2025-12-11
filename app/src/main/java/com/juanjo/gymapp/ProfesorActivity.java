package com.juanjo.gymapp;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;

import java.util.*;

public class ProfesorActivity extends AppCompatActivity {

    private Spinner spinnerAlumnos, spinnerEjercicios;
    private EditText editNombreRutina, editSeries, editRepeticiones, editCarga;
    private Button btnAgregarEjercicio, btnGuardarRutina, btnCrearEjercicio, btnModificarRutina;
    private RecyclerView recyclerRutina;

    private RequestQueue requestQueue;
    private List<Alumno> listaAlumnos = new ArrayList<>();
    private List<Ejercicio> listaEjercicios = new ArrayList<>();
    private List<RutinaEjercicio> rutinaEjercicios = new ArrayList<>();
    // Lista de IDs de alumnos que corresponde al Spinner
    private List<Integer> alumnosIds = new ArrayList<>();
    private RutinaAdapter rutinaAdapter;
    //private int idAlumnoSeleccionado;

    private int idRutinaSeleccionada;

    private int idAlumnoSeleccionado = -1;
    private int idEjercicioSeleccionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        spinnerAlumnos = findViewById(R.id.spinnerAlumnos);
        spinnerEjercicios = findViewById(R.id.spinnerEjercicios);
        editNombreRutina = findViewById(R.id.editNombreRutina);
        editSeries = findViewById(R.id.editSeries);
        editRepeticiones = findViewById(R.id.editRepeticiones);
        editCarga = findViewById(R.id.editCarga);
        btnAgregarEjercicio = findViewById(R.id.btnAgregarEjercicio);
        btnGuardarRutina = findViewById(R.id.btnGuardarRutina);
        btnCrearEjercicio = findViewById(R.id.btnCrearEjercicio);
        btnModificarRutina = findViewById(R.id.btnModificarRutina);
        recyclerRutina = findViewById(R.id.recyclerRutina);

        requestQueue = Volley.newRequestQueue(this);

        // Configurar RecyclerView
        recyclerRutina.setLayoutManager(new LinearLayoutManager(this));
        rutinaAdapter = new RutinaAdapter(rutinaEjercicios);
        recyclerRutina.setAdapter(rutinaAdapter);

        cargarAlumnos();
        cargarEjercicios();

        spinnerAlumnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idAlumnoSeleccionado = listaAlumnos.get(position).getId();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerEjercicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEjercicioSeleccionado = listaEjercicios.get(position).getId();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnCrearEjercicio.setOnClickListener(v -> {
            Intent intent = new Intent(ProfesorActivity.this, CrearEjercicioActivity.class);
            startActivity(intent);
        });

        btnAgregarEjercicio.setOnClickListener(v -> agregarEjercicio());
        btnGuardarRutina.setOnClickListener(v -> guardarRutina());


        btnModificarRutina.setOnClickListener(v -> {
            int pos = spinnerAlumnos.getSelectedItemPosition();

            if (pos >= 0 && pos < listaAlumnos.size()) {
                int idAlumno = listaAlumnos.get(pos).getId();

                Intent intent = new Intent(ProfesorActivity.this, ModificarRutinaActivity.class);
                intent.putExtra("idAlumno", idAlumno);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Selecciona un alumno válido", Toast.LENGTH_SHORT).show();
            }
        });






    }

    private void cargarAlumnos() {
        String url = Config.BASE_URL + "getAlumnos_AP.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaAlumnos.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id");
                            String nombre = obj.getString("nombre");
                            listaAlumnos.add(new Alumno(id, nombre));
                            nombres.add(nombre);
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, nombres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAlumnos.setAdapter(adapter);
                },
                error -> Toast.makeText(this, "Error cargando alumnos", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }

    private void cargarEjercicios() {
        String url = Config.BASE_URL + "getEjercicios_AP.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaEjercicios.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int id = obj.getInt("id_ejercicio");
                            String nombre = obj.getString("nombre");
                            listaEjercicios.add(new Ejercicio(id, nombre));
                            nombres.add(nombre);
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, nombres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEjercicios.setAdapter(adapter);
                },
                error -> Toast.makeText(this, "Error cargando ejercicios", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }

    private void agregarEjercicio() {
        try {
            int series = Integer.parseInt(editSeries.getText().toString());
            int repeticiones = Integer.parseInt(editRepeticiones.getText().toString());
            double carga = Double.parseDouble(editCarga.getText().toString());
            String nombreEjercicio = spinnerEjercicios.getSelectedItem().toString();

            rutinaEjercicios.add(new RutinaEjercicio(idEjercicioSeleccionado, series, repeticiones, carga, nombreEjercicio));
            rutinaAdapter.notifyDataSetChanged();

            editSeries.setText("");
            editRepeticiones.setText("");
            editCarga.setText("");
        } catch (Exception e) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarRutina() {
        String nombreRutina = editNombreRutina.getText().toString();
        if (nombreRutina.isEmpty() || idAlumnoSeleccionado == -1 || rutinaEjercicios.isEmpty()) {
            Toast.makeText(this, "Completa todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject rutinaJson = new JSONObject();
        try {
            rutinaJson.put("nombreRutina", nombreRutina);
            rutinaJson.put("idAlumno", idAlumnoSeleccionado);

            JSONArray ejerciciosArray = new JSONArray();
            for (RutinaEjercicio re : rutinaEjercicios) {
                JSONObject ejObj = new JSONObject();
                ejObj.put("idEjercicio", re.getIdEjercicio());
                ejObj.put("series", re.getSeries());
                ejObj.put("repeticiones", re.getRepeticiones());
                ejObj.put("carga", re.getCarga());
                ejerciciosArray.put(ejObj);
            }
            rutinaJson.put("ejercicios", ejerciciosArray);

        } catch (JSONException e) { e.printStackTrace(); }

        String url = Config.BASE_URL + "crearRutina_AP.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, rutinaJson,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        if (success) {
                            rutinaEjercicios.clear();
                            rutinaAdapter.notifyDataSetChanged();
                            editNombreRutina.setText("");
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Toast.makeText(this, "Error guardando rutina", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }
}