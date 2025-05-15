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
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        dbHelper = new DataBaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String contrasena = etContrasena.getText().toString();

            new Thread(() -> {
                UsuarioDAO usuarioDAO = new UsuarioDAO(dbHelper);
                Usuario usuario = usuarioDAO.login(correo, contrasena);
                runOnUiThread(() -> {
                    if (usuario != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("usuarioId", usuario.getId());
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