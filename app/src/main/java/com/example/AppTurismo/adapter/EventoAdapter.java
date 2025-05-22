package com.example.AppTurismo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.R;
import com.example.AppTurismo.model.Evento;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {
    private List<Evento> eventos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Evento evento);
    }

    public EventoAdapter(List<Evento> eventos, OnItemClickListener listener) {
        this.eventos = eventos;
        this.listener = listener;
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.tvNombre.setText(evento.getNombre());
        holder.tvTipo.setText(evento.getTipo());
        holder.tvUbicacion.setText(evento.getUbicacion());
        holder.tvDescripcion.setText(evento.getDescripcion());
        holder.tvPrecio.setText("Precio: " + evento.getPrecio());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(evento));
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTipo, tvDescripcion, tvPrecio, tvUbicacion;

        EventoViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreEvento);
            tvTipo = itemView.findViewById(R.id.tvTipoEvento);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacionEvento);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionEvento);
            tvPrecio = itemView.findViewById(R.id.tvPrecioEvento);
        }
    }
}