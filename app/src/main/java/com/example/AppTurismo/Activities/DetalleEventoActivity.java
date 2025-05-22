package com.example.AppTurismo.Activities;

import android.app.DatePickerDialog;
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
import com.example.AppTurismo.adapter.ValoracionEventoAdapter;
import com.example.AppTurismo.dao.EventoDAO;
import com.example.AppTurismo.dao.HistorialDAO;
import com.example.AppTurismo.dao.ReservaDAO;
import com.example.AppTurismo.dao.ValoracionEventoDAO;
import com.example.AppTurismo.model.Evento;
import com.example.AppTurismo.model.ValoracionEvento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetalleEventoActivity extends AppCompatActivity {
    private int eventoId, usuarioId;
    private GestorJDBC dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);

        eventoId = getIntent().getIntExtra("eventoId", -1);
        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();

        ImageView imgEvento = findViewById(R.id.imgDetalleEvento);
        TextView tvNombre = findViewById(R.id.tvNombreDetalleEvento);
        TextView tvTipo = findViewById(R.id.tvTipoDetalleEvento);
        TextView tvDescripcion = findViewById(R.id.tvDescripcionDetalleEvento);
        TextView tvUbicacion = findViewById(R.id.tvUbicacionDetalleEvento);
        TextView tvPrecio = findViewById(R.id.tvPrecioDetalleEvento);
        Button btnReservar = findViewById(R.id.btnReservar);
        Button btnValorar = findViewById(R.id.btnValorar);
        RecyclerView recyclerViewValoraciones = findViewById(R.id.recyclerViewValoracionesEvento);
        recyclerViewValoraciones.setLayoutManager(new LinearLayoutManager(this));

        new Thread(() -> {
            ValoracionEventoDAO valoracionDAO = new ValoracionEventoDAO(dbHelper);
            List<ValoracionEvento> valoraciones = valoracionDAO.obtenerValoracionesPorEvento(eventoId);
            List<String> nombresUsuarios = new ArrayList<>();
            for (ValoracionEvento v : valoraciones) {
                nombresUsuarios.add(valoracionDAO.obtenerNombreUsuarioPorId(v.getIdUsuario()));
            }
            runOnUiThread(() -> {
                ValoracionEventoAdapter adapter = new ValoracionEventoAdapter(valoraciones, nombresUsuarios);
                recyclerViewValoraciones.setAdapter(adapter);
            });
        }).start();

        new Thread(() -> {
            EventoDAO eventoDAO = new EventoDAO(dbHelper);
            Evento evento = eventoDAO.obtenerEventoPorId(eventoId);

            runOnUiThread(() -> {
                if (evento != null) {
                    tvNombre.setText(evento.getNombre());
                    tvTipo.setText(evento.getTipo());
                    tvDescripcion.setText(evento.getDescripcion());
                    tvUbicacion.setText(evento.getUbicacion());
                    tvPrecio.setText("Precio: " + evento.getPrecio());

                    if (evento.getImagen() != null && evento.getImagen().length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(evento.getImagen(), 0, evento.getImagen().length);
                        imgEvento.setImageBitmap(bitmap);
                    } else {
                        imgEvento.setImageResource(R.drawable.main_menu_bg);
                    }
                }
            });
        }).start();

        btnReservar.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    Date fechaSeleccionada = selectedDate.getTime();

                    new Thread(() -> {
                        ReservaDAO reservaDAO = new ReservaDAO(dbHelper);
                        boolean reservado = reservaDAO.reservarEvento(usuarioId, eventoId, fechaSeleccionada);
                        if (reservado) {
                            new HistorialDAO(dbHelper).registrarAccion(usuarioId, "Ha reservado el evento con id " + eventoId + " para el " + dayOfMonth + "/" + (month+1) + "/" + year);
                        }
                        runOnUiThread(() -> {
                            if (reservado) {
                                Toast.makeText(this, "¡Reserva realizada!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error al reservar.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).start();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        btnValorar.setOnClickListener(v -> {
            Intent intent = new Intent(this, ValorarEventoActivity.class);
            intent.putExtra("eventoId", eventoId);
            intent.putExtra("usuarioId", usuarioId);
            startActivity(intent);
        });
    }
}