package com.example.AppTurismo.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.R;
import com.example.AppTurismo.adapter.ValoracionRestauranteAdapter;
import com.example.AppTurismo.adapter.ValoracionRutaAdapter;
import com.example.AppTurismo.dao.RestauranteDAO;
import com.example.AppTurismo.dao.ValoracionRestauranteDAO;
import com.example.AppTurismo.model.Restaurante;
import com.example.AppTurismo.model.ValoracionRestaurante;

import java.util.ArrayList;
import java.util.List;

public class DetalleRestauranteActivity extends AppCompatActivity {
    private int restauranteId, usuarioId;
    private GestorJDBC dbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_restaurante);

        restauranteId = getIntent().getIntExtra("restauranteId", -1);
        usuarioId = getIntent().getIntExtra("usuarioId", -1);

        dbHelper = GestorJDBC.getInstance();

        ImageView imgRestaurante = findViewById(R.id.imgDetalleRestaurante);
        TextView txtNombre = findViewById(R.id.txtDetalleNombreRestaurante);
        TextView txtUbicacion = findViewById(R.id.txtDetalleUbicacionRestaurante);
        Button btnValorar = findViewById(R.id.btnValorarRestaurante);
        RecyclerView recyclerViewValoraciones = findViewById(R.id.recyclerViewValoraciones);
        TextView txtEspecialidad = findViewById(R.id.txtDetalleEspecialidadRestaurante);

        recyclerViewValoraciones.setLayoutManager(new LinearLayoutManager(this));

        new Thread(() -> {
            RestauranteDAO restauranteDAO = new RestauranteDAO(dbHelper);
            Restaurante restaurante = restauranteDAO.obtenerRestaurantePorId(restauranteId);

            ValoracionRestauranteDAO valoracionDAO = new ValoracionRestauranteDAO(dbHelper);
            List<ValoracionRestaurante> valoraciones = valoracionDAO.obtenerValoracionesPorRestaurante(restauranteId);
            List<String> nombresUsuarios = new ArrayList<>();
            for (ValoracionRestaurante v : valoraciones) {
                nombresUsuarios.add(valoracionDAO.obtenerNombreUsuarioPorId(v.getIdUsuario()));
            }

            runOnUiThread(() -> {
                if (restaurante != null) {
                    txtNombre.setText(restaurante.getNombre());
                    txtEspecialidad.setText(restaurante.getEspecialidad());
                    txtUbicacion.setText(restaurante.getUbicacion());
                    if (restaurante.getImagen() != null && restaurante.getImagen().length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(restaurante.getImagen(), 0, restaurante.getImagen().length);
                        imgRestaurante.setImageBitmap(bitmap);
                    } else {
                        imgRestaurante.setImageResource(R.drawable.main_menu_bg);
                    }
                } else {
                    txtNombre.setText("Restaurante no encontrado");
                    txtUbicacion.setText("");
                    imgRestaurante.setImageResource(R.drawable.main_menu_bg);
                }
                ValoracionRestauranteAdapter adapter = new ValoracionRestauranteAdapter(valoraciones, nombresUsuarios);
                recyclerViewValoraciones.setAdapter(adapter);
            });
        }).start();

        btnValorar.setOnClickListener(v -> {
            Intent intent = new Intent(this, ValorarRestauranteActivity.class);
            intent.putExtra("restauranteId", restauranteId);
            intent.putExtra("usuarioId", usuarioId);
            startActivity(intent);
        });
    }
}