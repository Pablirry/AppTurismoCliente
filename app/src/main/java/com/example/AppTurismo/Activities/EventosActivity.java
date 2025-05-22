package com.example.AppTurismo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.R;
import com.example.AppTurismo.adapter.EventoAdapter;
import com.example.AppTurismo.dao.EventoDAO;
import com.example.AppTurismo.model.Evento;
import java.util.List;

public class EventosActivity extends AppCompatActivity {
    private int usuarioId;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnVerReservas = findViewById(R.id.btnVerReservas);
        btnVerReservas.setOnClickListener(v -> {
            Intent intent = new Intent(this, VerReservasEventoActivity.class);
            intent.putExtra("usuarioId", usuarioId);
            startActivity(intent);
        });

        new Thread(() -> {
            EventoDAO eventoDAO = new EventoDAO(dbHelper);
            List<Evento> eventos = eventoDAO.obtenerTodosEventos();

            runOnUiThread(() -> {
                EventoAdapter adapter = new EventoAdapter(eventos, evento -> {
                    Intent intent = new Intent(this, DetalleEventoActivity.class);
                    intent.putExtra("eventoId", evento.getId());
                    intent.putExtra("usuarioId", usuarioId);
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}