package com.example.AppTurismo.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.AppTurismo.model.Restaurante;
import com.example.AppTurismo.R;
import java.util.List;

public class RestauranteAdapter extends ArrayAdapter<Restaurante> {
    public RestauranteAdapter(Context context, List<Restaurante> restaurantes) {
        super(context, 0, restaurantes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurante restaurante = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_restaurante, parent, false);
        }
        ImageView imgRestaurante = convertView.findViewById(R.id.imgRestaurante);
        TextView txtNombre = convertView.findViewById(R.id.txtNombreRestaurante);
        TextView txtUbicacion = convertView.findViewById(R.id.txtUbicacionRestaurante);

        txtNombre.setText(restaurante.getNombre());
        txtUbicacion.setText(restaurante.getUbicacion());
        if (restaurante.getImagen() != null && restaurante.getImagen().length > 0) {
            imgRestaurante.setImageBitmap(BitmapFactory.decodeByteArray(restaurante.getImagen(), 0, restaurante.getImagen().length));
        } else {
            imgRestaurante.setImageResource(R.drawable.ic_launcher_background);
        }
        return convertView;
    }
}