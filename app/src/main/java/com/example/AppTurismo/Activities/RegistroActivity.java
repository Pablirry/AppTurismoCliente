package com.example.AppTurismo.Activities;

                import android.content.Intent;
                import android.os.Bundle;
                import android.widget.Button;
                import android.widget.EditText;
                import android.widget.Toast;
                import androidx.appcompat.app.AppCompatActivity;

                import com.example.AppTurismo.GestorJDBC;
                import com.example.AppTurismo.R;
                import com.example.AppTurismo.dao.UsuarioDAO;
                import com.example.AppTurismo.dao.HistorialDAO;

                import java.sql.Connection;
                import java.sql.PreparedStatement;
                import java.sql.ResultSet;

                public class RegistroActivity extends AppCompatActivity {
                    private EditText etNombre, etCorreo, etContrasena;
                    private Button btnRegistrar;
                    private GestorJDBC dbHelper;

                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_registro);

                        etNombre = findViewById(R.id.etNombre);
                        etCorreo = findViewById(R.id.etCorreo);
                        etContrasena = findViewById(R.id.etContrasena);
                        btnRegistrar = findViewById(R.id.btnRegistrar);
                        dbHelper = GestorJDBC.getInstance();

                        btnRegistrar.setOnClickListener(v -> {
                            String nombre = etNombre.getText().toString();
                            String correo = etCorreo.getText().toString();
                            String contrasena = etContrasena.getText().toString();

                            new Thread(() -> {
                                UsuarioDAO usuarioDAO = new UsuarioDAO(dbHelper);
                                boolean registrado = false;
                                Exception error = null;
                                int usuarioId = -1;
                                try {
                                    registrado = usuarioDAO.registrarUsuario(nombre, correo, contrasena);
                                    if (registrado) {
                                        try (Connection con = dbHelper.getConnection()) {
                                            String sql = "SELECT id FROM usuarios WHERE correo = ?";
                                            PreparedStatement ps = con.prepareStatement(sql);
                                            ps.setString(1, correo);
                                            ResultSet rs = ps.executeQuery();
                                            if (rs.next()) {
                                                usuarioId = rs.getInt("id");
                                            }
                                            rs.close();
                                            ps.close();
                                        }
                                        if (usuarioId != -1) {
                                            new HistorialDAO(dbHelper).registrarAccion(usuarioId, "Se ha registrado");
                                        }
                                    }
                                } catch (Exception e) {
                                    error = e;
                                }
                                int finalUsuarioId = usuarioId;
                                boolean finalRegistrado = registrado;
                                Exception finalError = error;
                                runOnUiThread(() -> {
                                    if (finalRegistrado) {
                                        Toast.makeText(this, "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String msg = "Error al registrar usuario";
                                        if (finalError != null) msg += ": " + finalError.getMessage();
                                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }).start();
                        });
                    }
                }