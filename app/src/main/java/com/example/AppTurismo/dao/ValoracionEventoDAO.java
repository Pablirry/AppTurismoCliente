package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.ValoracionEvento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ValoracionEventoDAO {
    private final GestorJDBC dbHelper;

    public ValoracionEventoDAO(GestorJDBC dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean valorarEvento(int usuarioId, int eventoId, int puntuacion, String comentario) {
        try {
            Connection conn = dbHelper.getConnection();
            String query = "INSERT INTO valoraciones_eventos (id_usuario, id_evento, puntuacion, comentario) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, usuarioId);
            ps.setInt(2, eventoId);
            ps.setInt(3, puntuacion);
            ps.setString(4, comentario);
            int rows = ps.executeUpdate();
            ps.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ValoracionEvento> obtenerValoracionesPorEvento(int eventoId) {
        List<ValoracionEvento> lista = new ArrayList<>();
        String sql = "SELECT * FROM valoraciones_eventos WHERE id_evento = ?";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ValoracionEvento v = new ValoracionEvento(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_evento"),
                        rs.getInt("puntuacion"),
                        rs.getString("comentario")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String obtenerNombreUsuarioPorId(int usuarioId) {
        String nombre = "Usuario";
        String sql = "SELECT nombre FROM usuarios WHERE id = ?";
        try (Connection conn = dbHelper.getConnection();
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