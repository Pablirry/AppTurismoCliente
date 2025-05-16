package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
