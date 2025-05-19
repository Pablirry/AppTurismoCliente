package com.example.AppTurismo.Activities;

        import android.os.Bundle;
        import android.widget.*;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.AppTurismo.GestorJDBC;
        import com.example.AppTurismo.R;
        import com.example.AppTurismo.dao.ValoracionRestauranteDAO;

        public class ValorarRestauranteActivity extends AppCompatActivity {
            private int restauranteId, usuarioId;
            private GestorJDBC dbHelper;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_valorar_restaurante);

                restauranteId = getIntent().getIntExtra("restauranteId", -1);
                usuarioId = getIntent().getIntExtra("usuarioId", -1);
                dbHelper = GestorJDBC.getInstance();

                RatingBar ratingBar = findViewById(R.id.ratingBarRestaurante);
                EditText etComentario = findViewById(R.id.etComentarioRestaurante);
                Button btnEnviar = findViewById(R.id.btnEnviarValoracionRestaurante);

                btnEnviar.setOnClickListener(v -> {
                    int puntuacion = (int) ratingBar.getRating();
                    String comentario = etComentario.getText().toString();

                    new Thread(() -> {
                        ValoracionRestauranteDAO valoracionDAO = new ValoracionRestauranteDAO(dbHelper);
                        boolean valorado = valoracionDAO.valorarRestaurante(usuarioId, restauranteId, puntuacion, comentario);
                        if (valorado) {
                            new com.example.AppTurismo.dao.HistorialDAO(dbHelper)
                                .registrarAccion(usuarioId,
                                    "Ha valorado el restaurante con id " + restauranteId + " con " + puntuacion + " estrellas");
                        }
                        runOnUiThread(() -> {
                            if (valorado) {
                                Toast.makeText(this, "¡Gracias por tu valoración!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Error al valorar.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).start();
                });
            }
        }