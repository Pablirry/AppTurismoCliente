package com.example.AppTurismo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorJDBC extends Service {

    // GestorJDBC.java
    private static final String URI = "jdbc:mysql://be6i3twqn0pieiochkud-mysql.services.clever-cloud.com:3306/be6i3twqn0pieiochkud";
    private static final String USER = "usrshnydk2vnkzsm";
    private static final String PASSWORD = "HJAlziOv2B3LyzsRSt2T";

    // singleton
    private static GestorJDBC gestor;

    // atributos
    private Connection conn;
    private boolean abierto;

    // Constructor privado para singleton
    GestorJDBC() {
        abierto = false;
    }

    // singleton: acceso a la instancia
    public static synchronized GestorJDBC getInstance() {
        if (gestor == null) {
            gestor = new GestorJDBC();
        }
        return gestor;
    }

    /** Método que abre la conexión si no estaba abierta */
    private void open() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(URI, USER, PASSWORD);
                abierto = true;
            } catch (Exception e) {
                e.printStackTrace();
                abierto = false;
            }
    }

    /** Método para cerrar conexiones */
    private void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        abierto = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Connection getConnection() {
        open();
        return conn;
    }

    public String login(String correo, String contrasena) throws SQLException {
        open();
        String query = "SELECT id FROM usuarios WHERE correo = ? AND contrasena = ?";
        PreparedStatement ps = getInstance().getConnection().prepareStatement(query);
        ps.setString(1, correo);
        ps.setString(2, contrasena);
        ResultSet rs = ps.executeQuery();
        String public_id = null;
        if (rs.next()) {
            public_id = rs.getString("id");
        }
        rs.close();
        ps.close();
        close();
        return public_id;
    }

    public String obtenerNombreUsuario(int usuarioId) throws SQLException {
        String nombre = null;
        String sql = "SELECT nombre FROM usuarios WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
}