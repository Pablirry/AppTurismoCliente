package com.example.AppTurismo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import com.example.AppTurismo.model.Historial;
import com.example.AppTurismo.R;
import java.util.List;

public class HistorialAdapter extends ArrayAdapter<Historial> {
    private Context context;
    private List<Historial> historialList;

    public HistorialAdapter(Context context, List<Historial> historialList) {
        super(context, R.layout.item_historial, historialList);
        this.context = context;
        this.historialList = historialList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false);
        }

        Historial historial = historialList.get(position);

        TextView txtAccion = convertView.findViewById(R.id.txtAccion);
        TextView txtFecha = convertView.findViewById(R.id.txtFecha);

        txtAccion.setText(historial.getAccion());
        txtFecha.setText(historial.getFecha().toString());

        return convertView;
    }
}