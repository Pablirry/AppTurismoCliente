package com.example.AppTurismo.Activities;

            import android.content.Intent;
            import android.os.Bundle;
            import android.widget.LinearLayout;

            import androidx.appcompat.app.AppCompatActivity;

            import com.example.AppTurismo.R;

public class MainActivity extends AppCompatActivity {
                private int usuarioId;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    usuarioId = getIntent().getIntExtra("usuarioId", -1);

                    LinearLayout btnRutas = findViewById(R.id.btnRutas);
                    LinearLayout btnRestaurantes = findViewById(R.id.btnRestaurantes);
                    LinearLayout btnHistorial = findViewById(R.id.btnHistorial);
                    LinearLayout btnMensajes = findViewById(R.id.btnMensajes);

                    btnRutas.setOnClickListener(v -> {
                        Intent intent = new Intent(this, RutasActivity.class);
                        intent.putExtra("usuarioId", usuarioId);
                        startActivity(intent);
                    });

                    btnRestaurantes.setOnClickListener(v -> {
                        Intent intent = new Intent(this, RestaurantesActivity.class);
                        intent.putExtra("usuarioId", usuarioId);
                        startActivity(intent);
                    });

                    btnHistorial.setOnClickListener(v -> {
                        Intent intent = new Intent(this, HistorialActivity.class);
                        intent.putExtra("usuarioId", usuarioId);
                        startActivity(intent);
                    });

                    btnMensajes.setOnClickListener(v -> {
                        Intent intent = new Intent(this, MensajeActivity.class);
                        intent.putExtra("usuarioId", usuarioId);
                        startActivity(intent);
                    });
                }
            }