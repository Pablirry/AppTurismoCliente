package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ValoracionRestauranteDAO {
    private GestorJDBC db;

    public ValoracionRestauranteDAO(GestorJDBC db) {
        this.db = db;
    }

    public boolean valorarRestaurante(int idUsuario, int idRestaurante, int puntuacion, String comentario) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO valoraciones_restaurantes (id_usuario, id_restaurante, puntuacion, comentario) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRestaurante);
            stmt.setInt(3, puntuacion);
            stmt.setString(4, comentario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}