package com.example.AppTurismo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.AppTurismo.dao.UsuarioDAO;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNombre, etCorreo, etContrasena;
    private Button btnRegistrar;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        dbHelper = new DataBaseHelper(this);

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String correo = etCorreo.getText().toString();
            String contrasena = etContrasena.getText().toString();

            new Thread(() -> {
                UsuarioDAO usuarioDAO = new UsuarioDAO(dbHelper);
                boolean registrado = usuarioDAO.registrarUsuario(nombre, correo, contrasena);
                runOnUiThread(() -> {
                    if (registrado) {
                        Toast.makeText(this, "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });
    }
}