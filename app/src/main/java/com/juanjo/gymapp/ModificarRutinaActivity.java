package com.juanjo.gymapp;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.recyclerview.widget.DividerItemDecoration;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import android.widget.EditText;

public class ModificarRutinaActivity extends AppCompatActivity {

    private List<Rutina> listaRutinas = new ArrayList<>();
    private Spinner spinnerRutinas;
    private RecyclerView recyclerEjercicios;
    private RutinaAdapter adapterEjercicios;
    private Button btnAgregarEjercicio;

    private Button btnGuardarCambios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_rutina);

        spinnerRutinas = findViewById(R.id.spinnerRutinas);
        recyclerEjercicios = findViewById(R.id.recyclerEjercicios);
        btnAgregarEjercicio = findViewById(R.id.btnAgregarEjercicio);
        btnAgregarEjercicio.setOnClickListener(v -> mostrarDialogoAgregarEjercicio());

        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnGuardarCambios.setOnClickListener(v -> guardarCambiosRutina());

        recyclerEjercicios.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider));

        recyclerEjercicios.addItemDecoration(divider);

        int idAlumno = getIntent().getIntExtra("idAlumno", -1);

        if (idAlumno == -1) {
            Toast.makeText(this, "Error: no se recibió el alumno", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarRutinasAlumno(idAlumno);
        cargarEjerciciosDisponibles();

        spinnerRutinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mostrarEjerciciosDeRutina(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cargarRutinasAlumno(int idAlumno) {
        String url = Config.BASE_URL + "getRutinasAlumno_AP.php?idAlumno=" + idAlumno;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (!response.getBoolean("success")) {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray data = response.getJSONArray("data");
                        listaRutinas.clear();
                        List<String> nombres = new ArrayList<>();

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject rut = data.getJSONObject(i);

                            int idRutina = rut.getInt("id_rutina");
                            String nombre = rut.getString("nombre");

                            JSONArray ejerciciosJson = rut.getJSONArray("ejercicios");
                            List<RutinaEjercicio> ejercicios = new ArrayList<>();

                            for (int j = 0; j < ejerciciosJson.length(); j++) {
                                JSONObject ej = ejerciciosJson.getJSONObject(j);

                                ejercicios.add(new RutinaEjercicio(
                                        ej.getInt("id_ejercicio"),
                                        ej.getInt("series"),
                                        ej.getInt("repeticiones"),
                                        ej.getDouble("carga"),
                                        ej.getString("nombre")
                                ));
                            }

                            listaRutinas.add(new Rutina(idRutina, nombre, ejercicios));
                            nombres.add(nombre);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_item, nombres);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerRutinas.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error cargando rutinas", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void mostrarEjerciciosDeRutina(int index) {
        if (index < 0 || index >= listaRutinas.size()) return;

        List<RutinaEjercicio> ejercicios = listaRutinas.get(index).getEjercicios();

        adapterEjercicios = new RutinaAdapter(ejercicios);
        recyclerEjercicios.setAdapter(adapterEjercicios);

        adapterEjercicios.setOnItemClickListener((ejercicio, position) -> {
            mostrarDialogoEditarEjercicio(ejercicio, position);
        });
    }
        private void mostrarDialogoEditarEjercicio(RutinaEjercicio ejercicio, int position) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_editar_ejercicio, null);

            EditText editSeriesDialog = view.findViewById(R.id.editSeriesDialog);
            EditText editRepsDialog = view.findViewById(R.id.editRepsDialog);
            EditText editCargaDialog = view.findViewById(R.id.editCargaDialog);

            // Cargar valores actuales
            editSeriesDialog.setText(String.valueOf(ejercicio.getSeries()));
            editRepsDialog.setText(String.valueOf(ejercicio.getRepeticiones()));
            editCargaDialog.setText(String.valueOf(ejercicio.getCarga()));

            builder.setView(view);
            builder.setTitle(ejercicio.getNombre());

            builder.setPositiveButton("Guardar", (dialog, which) -> {
                try {
                    int nuevasSeries = Integer.parseInt(editSeriesDialog.getText().toString());
                    int nuevasReps = Integer.parseInt(editRepsDialog.getText().toString());
                    double nuevaCarga = Double.parseDouble(editCargaDialog.getText().toString());

                    ejercicio.setSeries(nuevasSeries);
                    ejercicio.setRepeticiones(nuevasReps);
                    ejercicio.setCarga(nuevaCarga);

                    adapterEjercicios.notifyItemChanged(position);

                } catch (Exception e) {
                    Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
                }
            });


            builder.setNeutralButton("Eliminar", (dialog, which) -> {
                listaRutinas.get(spinnerRutinas.getSelectedItemPosition())
                        .getEjercicios()
                        .remove(position);

                adapterEjercicios.notifyItemRemoved(position);
            });
            builder.setNegativeButton("Cancelar", null);

            builder.show();
        }

    private void mostrarDialogoAgregarEjercicio() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_agregar_ejercicio, null);

        Spinner spinnerEjerciciosDialog = view.findViewById(R.id.spinnerEjerciciosDialog);
        EditText editSeriesNuevo = view.findViewById(R.id.editSeriesNuevo);
        EditText editRepsNuevo = view.findViewById(R.id.editRepsNuevo);
        EditText editCargaNuevo = view.findViewById(R.id.editCargaNuevo);

        // Cargar ejercicios desde tu lista global o desde backend
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Config.listaNombresEjercicios); // si ya los tenés cargados
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEjerciciosDialog.setAdapter(adapter);

        builder.setView(view);
        builder.setTitle("Agregar ejercicio");

        builder.setPositiveButton("Agregar", (dialog, which) -> {
            try {
                int series = Integer.parseInt(editSeriesNuevo.getText().toString());
                int reps = Integer.parseInt(editRepsNuevo.getText().toString());
                double carga = Double.parseDouble(editCargaNuevo.getText().toString());

                int index = spinnerEjerciciosDialog.getSelectedItemPosition();
                String nombre = Config.listaNombresEjercicios.get(index);
                int idEjercicio = Config.listaIdsEjercicios.get(index);

                RutinaEjercicio nuevo = new RutinaEjercicio(idEjercicio, series, reps, carga, nombre);

                listaRutinas.get(spinnerRutinas.getSelectedItemPosition())
                        .getEjercicios()
                        .add(nuevo);

                adapterEjercicios.notifyItemInserted(
                        listaRutinas.get(spinnerRutinas.getSelectedItemPosition())
                                .getEjercicios().size() - 1
                );

            } catch (Exception e) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void cargarEjerciciosDisponibles() {
        String url = Config.BASE_URL + "getEjercicios_AP.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Config.listaNombresEjercicios.clear();
                        Config.listaIdsEjercicios.clear();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject ej = response.getJSONObject(i);

                            Config.listaIdsEjercicios.add(ej.getInt("id_ejercicio"));
                            Config.listaNombresEjercicios.add(ej.getString("nombre"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error cargando ejercicios", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void guardarCambiosRutina() {
        int indexRutina = spinnerRutinas.getSelectedItemPosition();
        if (indexRutina < 0) return;

        Rutina rutina = listaRutinas.get(indexRutina);

        try {
            JSONObject json = new JSONObject();
            json.put("id_rutina", rutina.getIdRutina());

            JSONArray ejerciciosArray = new JSONArray();

            for (RutinaEjercicio ej : rutina.getEjercicios()) {
                JSONObject obj = new JSONObject();
                obj.put("id_ejercicio", ej.getIdEjercicio());
                obj.put("series", ej.getSeries());
                obj.put("repeticiones", ej.getRepeticiones());
                obj.put("carga", ej.getCarga());
                ejerciciosArray.put(obj);
            }

            json.put("ejercicios", ejerciciosArray);

            String url = Config.BASE_URL + "actualizarRutina_AP.php";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,
                    response -> {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(this, "Rutina actualizada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(this, "Error guardando cambios", Toast.LENGTH_SHORT).show()
            );

            Volley.newRequestQueue(this).add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}