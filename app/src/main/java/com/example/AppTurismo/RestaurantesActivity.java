package com.example.AppTurismo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.AppTurismo.adapter.RestauranteAdapter;
import com.example.AppTurismo.dao.RestauranteDAO;
import com.example.AppTurismo.model.Restaurante;
import java.util.List;

public class RestaurantesActivity extends AppCompatActivity {
    private int usuarioId;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();
        ListView listViewRestaurantes = findViewById(R.id.listViewRestaurantes);

        new Thread(() -> {
            RestauranteDAO restauranteDAO = new RestauranteDAO(dbHelper);
            List<Restaurante> restaurantes = restauranteDAO.listarRestaurantes();
            runOnUiThread(() -> {
                RestauranteAdapter adapter = new RestauranteAdapter(this, restaurantes);
                listViewRestaurantes.setAdapter(adapter);

                listViewRestaurantes.setOnItemClickListener((parent, view, position, id) -> {
                    Restaurante restauranteSeleccionado = restaurantes.get(position);
                    Intent intent = new Intent(this, DetalleRestauranteActivity.class);
                    intent.putExtra("restauranteId", restauranteSeleccionado.getId());
                    intent.putExtra("usuarioId", usuarioId);
                    startActivity(intent);
                });
            });

        }).start();


    }

}