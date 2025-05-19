package com.example.AppTurismo.Activities;

            import android.os.Bundle;
            import androidx.appcompat.app.AppCompatActivity;
            import androidx.recyclerview.widget.LinearLayoutManager;
            import androidx.recyclerview.widget.RecyclerView;
            import com.example.AppTurismo.GestorJDBC;
            import com.example.AppTurismo.R;
            import com.example.AppTurismo.adapter.ReservaAdapter;
            import com.example.AppTurismo.dao.ReservaDAO;
            import com.example.AppTurismo.model.Reserva;
            import java.util.List;

            public class VerReservasActivity extends AppCompatActivity {
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_ver_reservas);

                    int usuarioId = getIntent().getIntExtra("usuarioId", -1);
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewReservas);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));

                    new Thread(() -> {
                        List<Reserva> reservas = new ReservaDAO(GestorJDBC.getInstance()).obtenerReservasPorUsuario(usuarioId);
                        runOnUiThread(() -> recyclerView.setAdapter(new ReservaAdapter(reservas)));
                    }).start();
                }
            }