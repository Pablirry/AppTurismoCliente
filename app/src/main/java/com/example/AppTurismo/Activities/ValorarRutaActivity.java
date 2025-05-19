package com.example.AppTurismo.Activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.R;
import com.example.AppTurismo.dao.ValoracionRutaDAO;

public class ValorarRutaActivity extends AppCompatActivity {
    private int rutaId, usuarioId;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valorar_ruta);

        rutaId = getIntent().getIntExtra("rutaId", -1);
        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText etComentario = findViewById(R.id.etComentario);
        Button btnEnviar = findViewById(R.id.btnEnviarValoracion);

       
        btnEnviar.setOnClickListener(v -> {
            int puntuacion = (int) ratingBar.getRating();
            String comentario = etComentario.getText().toString();

            new Thread(() -> {
                ValoracionRutaDAO valoracionDAO = new ValoracionRutaDAO(dbHelper);
                boolean valorado = valoracionDAO.valorarRuta(usuarioId, rutaId, puntuacion, comentario);
                if (valorado) {
                    // Registrar acción: valoración realizada
                    new com.example.AppTurismo.dao.HistorialDAO(dbHelper)
                            .registrarAccion(usuarioId,
                                    "Ha valorado la ruta con id " + rutaId + " con " + puntuacion + " estrellas");
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