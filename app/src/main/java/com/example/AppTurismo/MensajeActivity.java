package com.example.AppTurismo;

            import android.os.Bundle;
            import android.widget.Button;
            import android.widget.EditText;
            import android.widget.Toast;
            import androidx.appcompat.app.AppCompatActivity;
            import com.example.AppTurismo.dao.MensajeDAO;

            public class MensajeActivity extends AppCompatActivity {
                private EditText etMensaje;
                private Button btnEnviar;
                private DataBaseHelper dbHelper;
                private int usuarioId;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_mensaje);

                    usuarioId = getIntent().getIntExtra("usuarioId", -1);
                    dbHelper = new DataBaseHelper(this);
                    etMensaje = findViewById(R.id.etMensaje);
                    btnEnviar = findViewById(R.id.btnEnviar);

                    btnEnviar.setOnClickListener(v -> {
                        String mensaje = etMensaje.getText().toString();
                        new Thread(() -> {
                            MensajeDAO mensajeDAO = new MensajeDAO(dbHelper);
                            boolean enviado = mensajeDAO.enviarMensaje(usuarioId, "Usuario", mensaje);
                            runOnUiThread(() -> {
                                if (enviado) {
                                    Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                                    etMensaje.setText("");
                                } else {
                                    Toast.makeText(this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }).start();
                    });
                }
            }