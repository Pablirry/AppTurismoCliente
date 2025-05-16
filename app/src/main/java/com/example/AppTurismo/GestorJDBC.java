package com.example.AppTurismo;

import static claves.ClavesJDBC.PASSWORD;
import static claves.ClavesJDBC.URI;
import static claves.ClavesJDBC.USER;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorJDBC extends Service {

    // singleton
    private static GestorJDBC gestor;

    // atributos
    private Connection conn;
    private boolean abierto;

    // Constructor privado para singleton
    private GestorJDBC() {
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

}