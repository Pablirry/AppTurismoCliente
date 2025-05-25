package com.example.AppTurismo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AppTurismo.R;
import com.example.AppTurismo.model.Evento;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private Context context;
    private List<Evento> eventoList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Evento evento);
    }

    public EventoAdapter(Context context, List<Evento> eventoList, OnItemClickListener listener) {
        this.context = context;
        this.eventoList = eventoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventoList.get(position);
        holder.tvNombreEvento.setText(evento.getNombre());
        holder.tvDescripcionEvento.setText(evento.getDescripcion());
        holder.tvUbicacionEvento.setText(evento.getUbicacion());
        holder.tvTipoEvento.setText(evento.getTipo());
        holder.tvPrecioEvento.setText("Precio: $" + evento.getPrecio());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(evento);
        });
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEvento;
        TextView tvNombreEvento, tvDescripcionEvento, tvUbicacionEvento, tvTipoEvento, tvPrecioEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEvento = itemView.findViewById(R.id.imgEvento);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreEvento);
            tvDescripcionEvento = itemView.findViewById(R.id.tvDescripcionEvento);
            tvUbicacionEvento = itemView.findViewById(R.id.tvUbicacionEvento);
            tvTipoEvento = itemView.findViewById(R.id.tvTipoEvento);
            tvPrecioEvento = itemView.findViewById(R.id.tvPrecioEvento);
        }
    }
}