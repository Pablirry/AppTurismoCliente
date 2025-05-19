package com.example.AppTurismo;

        import android.content.Intent;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.widget.*;
        import androidx.appcompat.app.AppCompatActivity;
        import com.example.AppTurismo.dao.RestauranteDAO;
        import com.example.AppTurismo.model.Restaurante;

        public class DetalleRestauranteActivity extends AppCompatActivity {
            private int restauranteId, usuarioId;
            private GestorJDBC dbHelper;
            private Restaurante restaurante;

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

                new Thread(() -> {
                    RestauranteDAO restauranteDAO = new RestauranteDAO(dbHelper);
                    Restaurante restaurante = null;
                    for (Restaurante r : restauranteDAO.listarRestaurantes()) {
                        if (r.getId() == restauranteId) {
                            restaurante = r;
                            break;
                        }
                    }
                    Restaurante finalRestaurante = restaurante;
                    runOnUiThread(() -> {
                        if (finalRestaurante != null) {
                            txtNombre.setText(finalRestaurante.getNombre());
                            txtUbicacion.setText(finalRestaurante.getUbicacion());
                            if (finalRestaurante.getImagen() != null) {
                                imgRestaurante.setImageBitmap(BitmapFactory.decodeByteArray(finalRestaurante.getImagen(), 0, finalRestaurante.getImagen().length));
                            }
                        }
                    });
                }).start();

                btnValorar.setOnClickListener(v -> {
                    Intent intent = new Intent(this,ValorarRestauranteActivity.class);
                    intent.putExtra("restauranteId", restauranteId);
                    intent.putExtra("usuarioId", usuarioId);
                    startActivity(intent);
                });
            }
        }