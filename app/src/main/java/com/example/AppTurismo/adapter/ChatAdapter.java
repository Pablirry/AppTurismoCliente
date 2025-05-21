// Archivo: app/src/main/java/com/example/AppTurismo/adapter/ChatAdapter.java
package com.example.AppTurismo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.AppTurismo.R;
import com.example.AppTurismo.utils.ChatItem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatItem> chatItems;

    public ChatAdapter(List<ChatItem> chatItems) {
        this.chatItems = chatItems;
    }

    public void setChatItems(List<ChatItem> chatItems) {
        this.chatItems = chatItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return chatItems.get(position).getTipo();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ChatItem.TIPO_USUARIO) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mensaje_enviado, parent, false);
            return new UsuarioViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mensaje_recibido, parent, false);
            return new AdminViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatItem item = chatItems.get(position);
        String horaFormateada = formatearHora(item.getFecha());

        if (holder instanceof UsuarioViewHolder) {
            ((UsuarioViewHolder) holder).txtNombre.setText("Tú");
            ((UsuarioViewHolder) holder).txtMensaje.setText(item.getTexto());
            ((UsuarioViewHolder) holder).txtFecha.setText(horaFormateada);
        } else if (holder instanceof AdminViewHolder) {
            ((AdminViewHolder) holder).txtNombre.setText("Administrador");
            ((AdminViewHolder) holder).txtMensaje.setText(item.getTexto());
            ((AdminViewHolder) holder).txtFecha.setText(horaFormateada);
        }
    }

    @Override
    public int getItemCount() {
        return chatItems != null ? chatItems.size() : 0;
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtMensaje, txtFecha;
        UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtMensaje = itemView.findViewById(R.id.txtMensaje);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }

    static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtMensaje, txtFecha;
        AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtMensaje = itemView.findViewById(R.id.txtMensaje);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }

    private String formatearHora(Timestamp timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp.getTime());
            calendar.add(Calendar.HOUR_OF_DAY, 2);

            Date nuevaFecha = calendar.getTime();

            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            return formatoSalida.format(nuevaFecha);
        } catch (Exception e) {
            return "";
        }
    }
}