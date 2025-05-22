package com.example.AppTurismo.Activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.R;
import com.example.AppTurismo.dao.ValoracionEventoDAO;

public class ValorarEventoActivity extends AppCompatActivity {
    private int eventoId, usuarioId;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valorar_evento);

        eventoId = getIntent().getIntExtra("eventoId", -1);
        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();

        RatingBar ratingBar = findViewById(R.id.ratingBarEvento);
        EditText etComentario = findViewById(R.id.etComentarioEvento);
        Button btnEnviar = findViewById(R.id.btnEnviarValoracionEvento);

        btnEnviar.setOnClickListener(v -> {
            int puntuacion = (int) ratingBar.getRating();
            String comentario = etComentario.getText().toString();

            new Thread(() -> {
                ValoracionEventoDAO valoracionDAO = new ValoracionEventoDAO(dbHelper);
                boolean valorado = valoracionDAO.valorarEvento(usuarioId, eventoId, puntuacion, comentario);

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