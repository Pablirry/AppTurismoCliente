package com.example.AppTurismo;

        import android.os.Bundle;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import androidx.appcompat.app.AppCompatActivity;
        import com.example.AppTurismo.dao.RestauranteDAO;
        import com.example.AppTurismo.model.Restaurante;
        import java.util.List;

        public class RestaurantesActivity extends AppCompatActivity {
            private int usuarioId;
            private DataBaseHelper dbHelper;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_restaurantes);

                usuarioId = getIntent().getIntExtra("usuarioId", -1);
                dbHelper = new DataBaseHelper(this);
                ListView listViewRestaurantes = findViewById(R.id.listViewRestaurantes);

                new Thread(() -> {
                    RestauranteDAO restauranteDAO = new RestauranteDAO(dbHelper);
                    List<Restaurante> restaurantes = restauranteDAO.listarRestaurantes();
                    runOnUiThread(() -> {
                        ArrayAdapter<Restaurante> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantes);
                        listViewRestaurantes.setAdapter(adapter);
                    });
                }).start();
            }
        }