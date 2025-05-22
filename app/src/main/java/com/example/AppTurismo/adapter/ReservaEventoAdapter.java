package com.example.AppTurismo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.R;
import com.example.AppTurismo.model.ReservaEvento;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReservaEventoAdapter extends RecyclerView.Adapter<ReservaEventoAdapter.ReservaEventoViewHolder> {
    private List<ReservaEvento> reservasEvento;

    public ReservaEventoAdapter(List<ReservaEvento> reservasEvento) {
        this.reservasEvento = reservasEvento;
    }

    @Override
    public ReservaEventoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva_evento, parent, false);
        return new ReservaEventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservaEventoViewHolder holder, int position) {
        ReservaEvento r = reservasEvento.get(position);
        holder.tvNombreEvento.setText("Evento ID: " + r.getIdEvento());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.tvFechaReservaEvento.setText(r.getFecha() != null ? sdf.format(r.getFecha()) : "");

        if (r.isConfirmada()) {
            holder.tvEstadoReservaEvento.setText("Estado: Confirmada");
            holder.tvEstadoReservaEvento.setTextColor(0xFF4CAF50); // Verde
            holder.btnPagarEvento.setVisibility(View.VISIBLE);
            holder.btnPagarEvento.setOnClickListener(v -> {
                Toast.makeText(holder.itemView.getContext(), "Ir a pago de la reserva de evento " + r.getId(), Toast.LENGTH_SHORT).show();
            });
        } else {
            holder.tvEstadoReservaEvento.setText("Estado: Pendiente");
            holder.tvEstadoReservaEvento.setTextColor(0xFFF44336); // Rojo
            holder.btnPagarEvento.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reservasEvento.size();
    }

    static class ReservaEventoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreEvento, tvFechaReservaEvento, tvEstadoReservaEvento;
        Button btnPagarEvento;
        ReservaEventoViewHolder(View itemView) {
            super(itemView);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreEvento);
            tvFechaReservaEvento = itemView.findViewById(R.id.tvFechaReservaEvento);
            tvEstadoReservaEvento = itemView.findViewById(R.id.tvEstadoReservaEvento);
            btnPagarEvento = itemView.findViewById(R.id.btnPagarEvento);
        }
    }
}