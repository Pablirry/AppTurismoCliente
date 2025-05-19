package com.example.AppTurismo.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.R;
import com.example.AppTurismo.adapter.ChatAdapter;
import com.example.AppTurismo.dao.MensajeDAO;
import com.example.AppTurismo.model.Mensaje;
import com.example.AppTurismo.utils.ChatItem;
import com.example.AppTurismo.utils.NotificacionUtils;

import java.util.ArrayList;
import java.util.List;

public class MensajeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private EditText etMensaje;
    private Button btnEnviar;
    private ChatAdapter chatAdapter;
    private int usuarioId;
    private String usuarioNombre;
    private GestorJDBC dbHelper;
    private List<ChatItem> chatItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        etMensaje = findViewById(R.id.etMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);

        NotificacionUtils.createNotificationChannel(this);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        usuarioNombre = getIntent().getStringExtra("usuarioNombre");
        dbHelper = GestorJDBC.getInstance();

        chatAdapter = new ChatAdapter(chatItems);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        cargarMensajes();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensajeTexto = etMensaje.getText().toString().trim();
                if (!mensajeTexto.isEmpty()) {
                    new Thread(() -> {
                        MensajeDAO mensajeDAO = new MensajeDAO(dbHelper);
                        boolean enviado = mensajeDAO.enviarMensaje(usuarioId, usuarioNombre, mensajeTexto);
                        if (enviado) {
                            runOnUiThread(() -> {
                                etMensaje.setText("");
                                cargarMensajes();
                            });
                        }
                    }).start();
                }
            }
        });
    }

    private void cargarMensajes() {
        new Thread(() -> {
            MensajeDAO mensajeDAO = new MensajeDAO(dbHelper);
            List<Mensaje> lista = mensajeDAO.obtenerMensajesChat(usuarioId);

            List<ChatItem> items = new ArrayList<>();
            for (Mensaje m : lista) {
                // Mensaje del usuario
                if (m.getMensaje() != null && !m.getMensaje().isEmpty() && m.getIdUsuario() == usuarioId) {
                    items.add(new ChatItem(
                            ChatItem.TIPO_USUARIO,
                            m.getMensaje(),
                            m.getFecha()
                    ));
                }
                // Respuesta del admin
                if (m.getRespuesta() != null && !m.getRespuesta().isEmpty()) {
                    items.add(new ChatItem(
                            ChatItem.TIPO_ADMIN,
                            m.getRespuesta(),
                            m.getFecha()
                    ));
                }
            }

            // Notificación si hay nueva respuesta del admin
            if (items.size() > chatItems.size() && !items.isEmpty()) {
                ChatItem ultimo = items.get(items.size() - 1);
                if (ultimo.getTipo() == ChatItem.TIPO_ADMIN) {
                    runOnUiThread(() -> NotificacionUtils.showNotification(
                            this,
                            "Respuesta del administrador",
                            ultimo.getTexto()
                    ));
                }
            }
            chatItems = items;

            runOnUiThread(() -> {
                chatAdapter.setChatItems(chatItems);
                recyclerViewChat.scrollToPosition(chatItems.size() - 1);
            });
        }).start();
    }
}