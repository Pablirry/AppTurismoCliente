package com.example.AppTurismo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.AppTurismo.dao.ValoracionRutaDAO;
import com.example.AppTurismo.model.Ruta;
import com.example.AppTurismo.R;
import java.util.List;
import java.util.Locale;

public class RutaAdapter extends BaseAdapter {
    private Context context;
    private List<Ruta> rutas;


    public RutaAdapter(Context context, List<Ruta> rutas) {
        this.context = context;
        this.rutas = rutas;
    }

    @Override
    public int getCount() {
        return rutas.size();
    }

    @Override
    public Object getItem(int position) {
        return rutas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rutas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_ruta, parent, false);
        }

        Ruta ruta = rutas.get(position);

        TextView txtNombre = view.findViewById(R.id.txtNombreRuta);
        TextView txtDescripcion = view.findViewById(R.id.txtDescripcionRuta);
        TextView txtPrecio = view.findViewById(R.id.txtPrecioRuta);
        ImageView imgRuta = view.findViewById(R.id.imgRuta);


        txtNombre.setText(ruta.getNombre());
        txtDescripcion.setText(ruta.getDescripcion());
        txtPrecio.setText("Precio: " + ruta.getPrecio() + "€");

        if (ruta.getImagen() != null && ruta.getImagen().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(ruta.getImagen(), 0, ruta.getImagen().length);
            imgRuta.setImageBitmap(bitmap);
        } else {
            imgRuta.setImageResource(R.drawable.ic_launcher_background);
        }

        return view;
    }
}