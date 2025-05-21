package com.example.AppTurismo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.R;
import com.example.AppTurismo.model.ValoracionRestaurante;
import java.util.List;

public class ValoracionRestauranteAdapter extends RecyclerView.Adapter<ValoracionRestauranteAdapter.ViewHolder> {
    private List<ValoracionRestaurante> valoraciones;
    private List<String> nombresUsuarios;

    public ValoracionRestauranteAdapter(List<ValoracionRestaurante> valoraciones, List<String> nombresUsuarios) {
        this.valoraciones = valoraciones;
        this.nombresUsuarios = nombresUsuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_valoracion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ValoracionRestaurante valoracion = valoraciones.get(position);
        holder.tvUsuario.setText(nombresUsuarios.get(position));
        holder.ratingBar.setRating(valoracion.getPuntuacion());
        holder.tvComentario.setText(valoracion.getComentario());
    }

    @Override
    public int getItemCount() {
        return valoraciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsuario, tvComentario;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvUsuarioValoracion);
            ratingBar = itemView.findViewById(R.id.ratingBarValoracion);
            tvComentario = itemView.findViewById(R.id.tvComentarioValoracion);
        }
    }
}