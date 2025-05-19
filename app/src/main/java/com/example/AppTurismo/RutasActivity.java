package com.example.AppTurismo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AppTurismo.adapter.RutaAdapter;
import com.example.AppTurismo.dao.RutaDAO;
import com.example.AppTurismo.model.Ruta;
import java.util.List;

public class RutasActivity extends AppCompatActivity {
    private int usuarioId;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();
        ListView listViewRutas = findViewById(R.id.listViewRutas);

        new Thread(() -> {
            try {
                RutaDAO rutaDAO = new RutaDAO(dbHelper);
                List<Ruta> rutas = rutaDAO.listarRutas();
                runOnUiThread(() -> {
                    RutaAdapter adapter = new RutaAdapter(this, rutas);
                    listViewRutas.setAdapter(adapter);

                    listViewRutas.setOnItemClickListener((parent, view, position, id) -> {
                        Ruta rutaSeleccionada = rutas.get(position);
                        Intent intent = new Intent(this, DetalleRutaActivity.class);
                        intent.putExtra("rutaId", rutaSeleccionada.getId());
                        intent.putExtra("usuarioId", usuarioId);
                        startActivity(intent);
                    });
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Error al cargar rutas", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}