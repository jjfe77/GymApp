package com.juanjo.gymapp;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class AlumnoActivity extends AppCompatActivity {

    private static final String TAG = "ALUMNO_DEBUG";

    // Views
    private TextView txtNombre;
    private Spinner spinnerRutinas;
    private ListView listaEjercicios;

    // Datos
    private String dni;
    private int idAlumno = -1;
    private JSONArray arrEjercicios = null; // guardamos el JSONArray para acceder luego
    private ArrayList<String> listaString = new ArrayList<>();
    private ArrayList<Integer> idsRutinas = new ArrayList<>();
    private ArrayList<String> nombresRutinas = new ArrayList<>();
    private int rutinaSeleccionadaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        txtNombre = findViewById(R.id.txtNombreAlumno);
        spinnerRutinas = findViewById(R.id.spinnerRutinas);
        listaEjercicios = findViewById(R.id.listaEjercicios);



        // Recibir el dni enviado desde el login
        Intent it = getIntent();
        dni = it.getStringExtra("dni");
        Log.i(TAG, "DNI recibido: " + dni);
        Toast.makeText(this, "DNI recibido: " + dni, Toast.LENGTH_SHORT).show();

        if (dni == null || dni.trim().isEmpty()) {
            Toast.makeText(this, "DNI vacío. Volver al login.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "DNI es null o vacío");
            return;
        }

        buscarAlumno();
        //dni = getIntent().getStringExtra("dni");

        obtenerIdAlumno(dni);

        Button btnMostrarProgresos = findViewById(R.id.btnMostrarProgresos);
        btnMostrarProgresos.setOnClickListener(v -> {
            Intent intent = new Intent(AlumnoActivity.this, ProgresosActivity.class);
            intent.putExtra("idAlumno", idAlumno);
            startActivity(intent);
        });






    }

    // --------------- Buscar alumno por DNI ---------------
    private void buscarAlumno() {
        String url = "http://10.0.2.2/api/buscar_usuario_usuario.php?dni=" + dni;
        //String url = "http://10.0.2.2/api/buscar_id_por_dni.php?dni=" + dni;
        Log.i(TAG, "buscarAlumno - URL: " + url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i(TAG, "Respuesta buscarAlumno: " + response);
                    try {
                        JSONObject json = new JSONObject(response);

                        // Asegurarnos de que existan las claves
                        if (json.has("id") && !json.isNull("id")) {
                            idAlumno = json.getInt("id");
                            String nombre = json.optString("nombre", "");
                            String apellido = json.optString("apellido", "");
                            txtNombre.setText("Alumno: " + nombre + " " + apellido);

                            Log.i(TAG, "idAlumno=" + idAlumno + " nombre=" + nombre + " " + apellido);
                            cargarRutinas();

                        } else {
                            Toast.makeText(this, "Alumno no encontrado (buscar_usuario)", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "buscarAlumno: JSON no contiene id o es nulo -> " + response);
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Error parseando JSON (buscarAlumno)", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Exception buscarAlumno", e);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error conexión (buscarAlumno)", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Volley error buscarAlumno: " + error.getMessage(), error);
                });

        Volley.newRequestQueue(this).add(req);
    }

    // --------------- Cargar rutinas del alumno ---------------
    private void cargarRutinas() {
        String url = "http://10.0.2.2/api/listar_rutinas_android.php?id=" + idAlumno;
        Log.i(TAG, "cargarRutinas - URL: " + url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i(TAG, "Respuesta cargarRutinas: " + response);
                    try {
                        JSONArray arr = new JSONArray(response);

                        nombresRutinas.clear();
                        idsRutinas.clear();

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);
                            idsRutinas.add(o.getInt("id_rutina"));
                            nombresRutinas.add(o.optString("nombre", "Rutina " + i));
                        }

                        ArrayAdapter<String> adapt =
                                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresRutinas);
                        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerRutinas.setAdapter(adapt);

                        spinnerRutinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                rutinaSeleccionadaId = idsRutinas.get(position);
                                Log.i(TAG, "Rutina seleccionada id = " + rutinaSeleccionadaId);
                                cargarEjercicios(rutinaSeleccionadaId);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // nada
                            }
                        });

                    } catch (Exception e) {
                        Toast.makeText(this, "Error parseando JSON (rutinas)", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Exception cargarRutinas", e);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error conexión (rutinas)", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Volley error cargarRutinas: " + error.getMessage(), error);
                });

        Volley.newRequestQueue(this).add(req);
    }

    // --------------- Cargar ejercicios de la rutina ---------------
    private void cargarEjercicios(int idRutina) {
        String url = "http://10.0.2.2/api/listar_ejercicios_android.php?id_rutina=" + idRutina;
        Log.i(TAG, "cargarEjercicios - URL: " + url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i(TAG, "Respuesta cargarEjercicios: " + response);
                    try {
                        // Guardamos el JSONArray en la variable de clase
                        arrEjercicios = new JSONArray(response);

                        listaString.clear();
                        for (int i = 0; i < arrEjercicios.length(); i++) {
                            JSONObject obj = arrEjercicios.getJSONObject(i);
                            String ejercicio = obj.optString("ejercicio", "Ejercicio " + i);
                            int series = obj.optInt("series", 0);
                            int repes = obj.optInt("repeticiones", 0);
                            double carga = obj.optDouble("carga", 0);

                            String linea = ejercicio + " - " + series + "x" + repes + " (Carga " + carga + ")";
                            listaString.add(linea);
                        }

                        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaString);
                        listaEjercicios.setAdapter(adapt);

                        // Listener para abrir dialogo y guardar progreso
                        listaEjercicios.setOnItemClickListener((parent, view, position, id) -> {
                            try {
                                if (arrEjercicios == null) {
                                    Toast.makeText(this, "Datos de ejercicios no cargados", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                JSONObject obj = arrEjercicios.getJSONObject(position);
                                int idRutinaEjercicio = obj.getInt("id_rutina_ejercicio");
                                String nombreEj = obj.optString("ejercicio", "Ejercicio");

                                mostrarDialogoGuardar(rutinaSeleccionadaId, idRutinaEjercicio, nombreEj);
                            } catch (Exception e) {
                                Log.e(TAG, "Error onItemClick ejercicios", e);
                                Toast.makeText(this, "Error leyendo ejercicio seleccionado", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        Toast.makeText(this, "Error parseando JSON (ejercicios)", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Exception cargarEjercicios", e);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error conexión (ejercicios)", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Volley error cargarEjercicios: " + error.getMessage(), error);
                });

        Volley.newRequestQueue(this).add(req);
    }

    // --------------- Dialogo para guardar progreso ---------------
    private void mostrarDialogoGuardar(int idRutina, int idRutinaEjercicio, String nombreEjercicio) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Registrar progreso");
        builder.setMessage(nombreEjercicio);

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int pad = (int) (8 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);

        final android.widget.EditText txtSeries = new android.widget.EditText(this);
        txtSeries.setHint("Series realizadas (número)");
        txtSeries.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(txtSeries);

        final android.widget.EditText txtRepes = new android.widget.EditText(this);
        txtRepes.setHint("Repeticiones realizadas (número)");
        txtRepes.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(txtRepes);

        final android.widget.EditText txtCarga = new android.widget.EditText(this);
        txtCarga.setHint("Carga utilizada (número)");
        txtCarga.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(txtCarga);

        builder.setView(layout);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String s = txtSeries.getText().toString().trim();
            String r = txtRepes.getText().toString().trim();
            String c = txtCarga.getText().toString().trim();

            if (s.isEmpty() || r.isEmpty() || c.isEmpty()) {
                Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            guardarProgreso(idRutina, idRutinaEjercicio, s, r, c);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    // --------------- Guardar progreso (POST) ---------------
    private void guardarProgreso(int idRutina, int idRutinaEjercicio, String series, String repes, String carga) {
        String url = "http://10.0.2.2/api/guardar_progreso_android.php";
        Log.i(TAG, "guardarProgreso -> " + url);

        StringRequest req = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.i(TAG, "Respuesta guardarProgreso: " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean ok = json.optBoolean("success", false);
                        if (ok) {
                            Toast.makeText(this, "Progreso guardado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error guardando (server)", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "guardarProgreso: server error -> " + response);
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error parseando respuesta guardar", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Exception guardarProgreso", e);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error de red al guardar", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Volley error guardarProgreso: " + error.getMessage(), error);
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> p = new java.util.HashMap<>();
                p.put("id_alumno", String.valueOf(idAlumno));
                p.put("id_rutina", String.valueOf(idRutina));
                p.put("id_rutina_ejercicio", String.valueOf(idRutinaEjercicio));
                p.put("series_real", series);
                p.put("repeticiones_real", repes);
                p.put("carga_real", carga);
                return p;
            }
        };



        Volley.newRequestQueue(this).add(req);
    }

    private void obtenerIdAlumno(String dniStr) {
        String url = "http://10.0.2.2/api/buscar_id_por_dni.php?dni=" + dni;

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        idAlumno = obj.getInt("id");
                    } catch (Exception e) {
                        Toast.makeText(this, "Error procesando ID", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error conectando con servidor", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(req);
    }


}
