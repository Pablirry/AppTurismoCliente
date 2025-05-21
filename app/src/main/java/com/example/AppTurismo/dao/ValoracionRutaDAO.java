package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.ValoracionRuta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ValoracionRutaDAO {
    private GestorJDBC db;

    public ValoracionRutaDAO(GestorJDBC db) {
        this.db = db;
    }

    public boolean valorarRuta(int idUsuario, int idRuta, int puntuacion, String comentario) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO valoraciones_rutas (id_usuario, id_ruta, puntuacion, comentario) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRuta);
            stmt.setInt(3, puntuacion);
            stmt.setString(4, comentario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ValoracionRuta> obtenerValoracionesPorRuta(int idRuta) {
        List<ValoracionRuta> lista = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM valoraciones_rutas WHERE id_ruta = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idRuta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ValoracionRuta v = new ValoracionRuta(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_ruta"),
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

    public float obtenerMediaValoracionRuta(int rutaId) {
        float media = 0f;
        try (Connection con = db.getConnection()) {
            String sql = "SELECT AVG(puntuacion) as media FROM valoraciones_rutas WHERE id_ruta = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, rutaId);
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