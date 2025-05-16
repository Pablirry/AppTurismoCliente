package com.example.AppTurismo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.AppTurismo.dao.UsuarioDAO;
import com.example.AppTurismo.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText etCorreo, etContrasena;
    private Button btnLogin, btnRegistro;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        dbHelper = GestorJDBC.getInstance();

        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String contrasena = etContrasena.getText().toString();

            new Thread(() -> {
                String publicId = null;
                try {
                    publicId = GestorJDBC.getInstance().login(correo, contrasena);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String finalPublicId = publicId;
                runOnUiThread(() -> {
                    if (finalPublicId != null) {
                        // Login exitoso, puedes guardar el publicId o pasar a la siguiente actividad
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("usuarioId", finalPublicId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        btnRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }
}