package com.example.AppTurismo.adapter;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import androidx.recyclerview.widget.RecyclerView;
    import com.example.AppTurismo.R;
    import com.example.AppTurismo.utils.ChatItem;

    import java.sql.Timestamp;
    import java.text.SimpleDateFormat;
    import java.util.List;
    import java.util.Locale;

    public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TIPO_ENVIADO = ChatItem.TIPO_USUARIO;
        private static final int TIPO_RECIBIDO = ChatItem.TIPO_ADMIN;
        private List<ChatItem> chatItems;

        public ChatAdapter(List<ChatItem> chatItems) {
            this.chatItems = chatItems;
        }

        @Override
        public int getItemViewType(int position) {
            return chatItems.get(position).getTipo();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TIPO_ENVIADO) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje_enviado, parent, false);
                return new EnviadoViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje_recibido, parent, false);
                return new RecibidoViewHolder(view);
            }
        }

        @Override
        public int getItemCount() {
            return chatItems != null ? chatItems.size() : 0;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ChatItem item = chatItems.get(position);
            if (holder instanceof EnviadoViewHolder) {
                ((EnviadoViewHolder) holder).txtMensaje.setText(item.getTexto());
                ((EnviadoViewHolder) holder).txtNombre.setText("Tú");
                ((EnviadoViewHolder) holder).txtFecha.setText(formatFecha(item.getFecha()));
            } else if (holder instanceof RecibidoViewHolder) {
                ((RecibidoViewHolder) holder).txtMensaje.setText(item.getTexto());
                ((RecibidoViewHolder) holder).txtNombre.setText("Administrador");
                ((RecibidoViewHolder) holder).txtFecha.setText(formatFecha(item.getFecha()));
            }
        }

        private String formatFecha(Timestamp fecha) {
            if (fecha == null) return "";
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            return sdf.format(fecha);
        }

        static class EnviadoViewHolder extends RecyclerView.ViewHolder {
            TextView txtMensaje, txtNombre, txtFecha;
            EnviadoViewHolder(View itemView) {
                super(itemView);
                txtMensaje = itemView.findViewById(R.id.txtMensaje);
                txtNombre = itemView.findViewById(R.id.txtNombre);
                txtFecha = itemView.findViewById(R.id.txtFecha);
            }
        }

        static class RecibidoViewHolder extends RecyclerView.ViewHolder {
            TextView txtMensaje, txtNombre, txtFecha;
            RecibidoViewHolder(View itemView) {
                super(itemView);
                txtMensaje = itemView.findViewById(R.id.txtMensaje);
                txtNombre = itemView.findViewById(R.id.txtNombre);
                txtFecha = itemView.findViewById(R.id.txtFecha);
            }
        }

        public void setChatItems(List<ChatItem> chatItems) {
            this.chatItems = chatItems;
            notifyDataSetChanged();
        }
    }