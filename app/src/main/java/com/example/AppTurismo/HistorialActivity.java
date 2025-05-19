package com.example.AppTurismo;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.AppTurismo.dao.HistorialDAO;
import com.example.AppTurismo.model.Historial;
import com.example.AppTurismo.adapter.HistorialAdapter;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {
    private ListView listViewHistorial;
    private GestorJDBC dbHelper;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();
        listViewHistorial = findViewById(R.id.listViewHistorial);

        cargarHistorial();
    }

    private void cargarHistorial() {
        new Thread(() -> {
            HistorialDAO historialDAO = new HistorialDAO(dbHelper);
            List<Historial> historial = historialDAO.obtenerHistorialPorUsuario(usuarioId);
            runOnUiThread(() -> {
                HistorialAdapter adapter = new HistorialAdapter(this, historial);
                listViewHistorial.setAdapter(adapter);
            });
        }).start();
    }
}