package com.juanjo.gymapp;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText txtDni;
    Button btnLogin;

    String URL_LOGIN = "http://10.0.2.2/api/login.php";  // <<--- CAMBIAR AQUÍ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDni = findViewById(R.id.txtDni);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });
    }

    private void validarLogin() {
        String dni = txtDni.getText().toString().trim();

        if (dni.isEmpty()) {
            Toast.makeText(this, "Ingrese el DNI", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = URL_LOGIN + "?dni=" + dni;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        String rol = json.getString("rol");

                        if (rol.equals("Profesor")) {
                            //startActivity(new Intent(this, ProfesorActivity.class));
                            Intent intent = new Intent(this, AlumnoActivity.class);
                            intent.putExtra("dni", dni);
                            startActivity(intent);


                        } else if (rol.equals("Alumno")) {
                            //startActivity(new Intent(this, AlumnoActivity.class));
                            Intent intent = new Intent(this, AlumnoActivity.class);
                            intent.putExtra("dni", dni);
                            startActivity(intent);

                        } else {
                            Toast.makeText(this, "DNI incorrecto", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "Error en JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}
