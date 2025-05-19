package com.example.AppTurismo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.AppTurismo.dao.RutaDAO;
import com.example.AppTurismo.dao.ReservaDAO;
import com.example.AppTurismo.model.Ruta;

import java.util.Calendar;
import java.util.Date;

public class DetalleRutaActivity extends AppCompatActivity {
    private int rutaId, usuarioId;
    private GestorJDBC dbHelper;
    private Ruta ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ruta);

        rutaId = getIntent().getIntExtra("rutaId", -1);
        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        dbHelper = GestorJDBC.getInstance();

        ImageView imgRuta = findViewById(R.id.imgDetalleRuta);
        TextView txtNombre = findViewById(R.id.txtDetalleNombre);
        TextView txtDescripcion = findViewById(R.id.txtDetalleDescripcion);
        TextView txtPrecio = findViewById(R.id.txtDetallePrecio);
        RatingBar ratingBarDificultad = findViewById(R.id.ratingBarDificultad);
        Button btnReservar = findViewById(R.id.btnReservar);
        Button btnValorar = findViewById(R.id.btnValorar);

        new Thread(() -> {
            RutaDAO rutaDAO = new RutaDAO(GestorJDBC.getInstance());
            Ruta ruta = rutaDAO.obtenerRutaPorId(rutaId);
            runOnUiThread(() -> {
                if (ruta != null) {
                    txtNombre.setText(ruta.getNombre());
                    txtDescripcion.setText(ruta.getDescripcion());
                    txtPrecio.setText("Precio: " + String.format("%.2f", ruta.getPrecio()) + "€");
                    ratingBarDificultad.setRating(ruta.getDificultad());
                    if (ruta.getImagen() != null && ruta.getImagen().length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(ruta.getImagen(), 0, ruta.getImagen().length);
                        imgRuta.setImageBitmap(bitmap);
                    } else {
                        imgRuta.setImageResource(R.drawable.main_menu_bg);
                    }
                } else {
                    txtNombre.setText("Ruta no encontrada");
                    txtDescripcion.setText("");
                    txtPrecio.setText("");
                    ratingBarDificultad.setRating(0);
                    imgRuta.setImageResource(R.drawable.main_menu_bg);
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

                        new Thread(() -> {
                            ReservaDAO reservaDAO = new ReservaDAO(dbHelper);
                            boolean reservado = reservaDAO.reservarRuta(usuarioId, rutaId, selectedDate.getTime());
                            if (reservado) {
                                new com.example.AppTurismo.dao.HistorialDAO(dbHelper)
                                        .registrarAccion(usuarioId, "Ha reservado la ruta con id " + rutaId + " para el " +
                                                dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                            runOnUiThread(() -> {
                                if (reservado) {
                                    Toast.makeText(this, "Reserva realizada. Esperando confirmación.", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, ValorarRutaActivity.class);
            intent.putExtra("rutaId", rutaId);
            intent.putExtra("usuarioId", usuarioId);
            startActivity(intent);
        });

    }
}