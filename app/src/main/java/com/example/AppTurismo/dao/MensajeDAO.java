package com.example.AppTurismo.dao;

import com.example.AppTurismo.DataBaseHelper;
import com.example.AppTurismo.model.Mensaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MensajeDAO {
    private DataBaseHelper db;

    public MensajeDAO(DataBaseHelper db) {
        this.db = db;
    }

    public boolean enviarMensaje(int idUsuario, String usuarioNombre, String mensaje) {
        try (Connection con = db.connectToDatabase()) {
            String sql = "INSERT INTO mensajes (id_usuario, usuarioNombre, mensaje) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setString(2, usuarioNombre);
            stmt.setString(3, mensaje);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Mensaje> obtenerMensajesPorUsuario(int idUsuario) {
        List<Mensaje> mensajes = new ArrayList<>();
        try (Connection con = db.connectToDatabase()) {
            String sql = "SELECT * FROM mensajes WHERE id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mensaje m = new Mensaje();
                m.setId(rs.getInt("id"));
                m.setIdUsuario(rs.getInt("id_usuario"));
                m.setUsuarioNombre(rs.getString("usuarioNombre"));
                m.setMensaje(rs.getString("mensaje"));
                m.setRespuesta(rs.getString("respuesta"));
                m.setFecha(rs.getTimestamp("fecha"));
                mensajes.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mensajes;
    }
}