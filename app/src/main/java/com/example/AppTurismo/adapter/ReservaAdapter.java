package com.example.AppTurismo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.R;
import com.example.AppTurismo.model.Reserva;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {
    private List<Reserva> reservas;

    public ReservaAdapter(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

   @Override
    public void onBindViewHolder(ReservaViewHolder holder, int position) {
        Reserva r = reservas.get(position);
        holder.txtRuta.setText("Ruta ID: " + r.getIdRuta());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.txtFecha.setText(r.getFecha() != null ? sdf.format(r.getFecha()) : "");

        if (r.isConfirmada()) {
            holder.txtConfirmada.setText("Estado: Confirmada");
            holder.txtConfirmada.setTextColor(0xFF4CAF50); // Verde
            holder.btnProcederPago.setVisibility(View.VISIBLE);
            holder.btnProcederPago.setOnClickListener(v -> {
                Toast.makeText(holder.itemView.getContext(), "Ir a pago de la reserva " + r.getId(), Toast.LENGTH_SHORT).show();
            });
        } else {
            holder.txtConfirmada.setText("Estado: Pendiente");
            holder.txtConfirmada.setTextColor(0xFFF44336); // Rojo
            holder.btnProcederPago.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView txtRuta, txtConfirmada, txtFecha;
        Button btnProcederPago;
        ReservaViewHolder(View itemView) {
            super(itemView);
            txtRuta = itemView.findViewById(R.id.txtRuta);
            txtConfirmada = itemView.findViewById(R.id.txtConfirmada);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            btnProcederPago = itemView.findViewById(R.id.btnProcederPago);
        }
    }
}