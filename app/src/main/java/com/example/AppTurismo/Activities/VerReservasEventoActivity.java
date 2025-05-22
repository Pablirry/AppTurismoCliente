package com.example.AppTurismo.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.R;
import com.example.AppTurismo.adapter.ReservaEventoAdapter;
import com.example.AppTurismo.dao.ReservaEventoDAO;
import com.example.AppTurismo.model.ReservaEvento;
import java.util.List;

public class VerReservasEventoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reservas_evento);

        int usuarioId = getIntent().getIntExtra("usuarioId", -1);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReservasEvento);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread(() -> {
            List<ReservaEvento> reservas = new ReservaEventoDAO(GestorJDBC.getInstance()).obtenerReservasPorUsuario(usuarioId);
            runOnUiThread(() -> recyclerView.setAdapter(new ReservaEventoAdapter(reservas)));
        }).start();
    }
}