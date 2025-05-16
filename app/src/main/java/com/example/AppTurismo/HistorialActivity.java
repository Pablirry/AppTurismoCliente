package com.example.AppTurismo;

    import android.os.Bundle;
    import android.widget.ListView;
    import androidx.appcompat.app.AppCompatActivity;
    import com.example.AppTurismo.dao.HistorialDAO;
    import com.example.AppTurismo.model.Historial;
    import java.util.List;
    import android.widget.ArrayAdapter;

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
                    ArrayAdapter<Historial> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historial);
                    listViewHistorial.setAdapter(adapter);
                });
            }).start();
        }
    }