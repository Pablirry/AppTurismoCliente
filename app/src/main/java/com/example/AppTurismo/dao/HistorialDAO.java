package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Historial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialDAO {
    private GestorJDBC db;

    public HistorialDAO(GestorJDBC db) {
        this.db = db;
    }

    public boolean registrarAccion(int idUsuario, String accion) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO historial (id_usuario, accion) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setString(2, accion);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Historial> obtenerHistorialPorUsuario(int idUsuario) {
        List<Historial> historial = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM historial WHERE id_usuario = ? ORDER BY fecha DESC";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Historial h = new Historial();
                h.setId(rs.getInt("id"));
                h.setIdUsuario(rs.getInt("id_usuario"));
                h.setAccion(rs.getString("accion"));
                h.setFecha(rs.getTimestamp("fecha"));
                historial.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }
}