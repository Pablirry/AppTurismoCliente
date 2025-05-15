package com.example.AppTurismo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.AppTurismo.dao.RutaDAO;
import com.example.AppTurismo.model.Ruta;
import java.util.List;

public class RutasActivity extends AppCompatActivity {
    private int usuarioId;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = new DataBaseHelper(this);
        ListView listViewRutas = findViewById(R.id.listViewRutas);

        new Thread(() -> {
            try {
                RutaDAO rutaDAO = new RutaDAO(dbHelper);
                List<Ruta> rutas = rutaDAO.listarRutas();
                runOnUiThread(() -> {
                    ArrayAdapter<Ruta> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rutas);
                    listViewRutas.setAdapter(adapter);
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Error al cargar rutas", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}