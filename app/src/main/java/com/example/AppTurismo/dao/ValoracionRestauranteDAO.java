package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.ValoracionRestaurante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<ValoracionRestaurante> obtenerValoracionesPorRestaurante(int idRestaurante) {
        List<ValoracionRestaurante> lista = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM valoraciones_restaurantes WHERE id_restaurante = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ValoracionRestaurante v = new ValoracionRestaurante(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_restaurante"),
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

    public String obtenerNombreUsuarioPorId(int idUsuario) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT nombre FROM usuarios WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Usuario";
    }

    public float obtenerMediaValoracionRestaurante(int restauranteId) {
        float media = 0f;
        try (Connection con = db.getConnection()) {
            String sql = "SELECT AVG(puntuacion) as media FROM valoraciones_restaurantes WHERE id_restaurante = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, restauranteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                media = rs.getFloat("media");
                if (rs.wasNull()) {
                    media = 0f;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return media;
    }

}