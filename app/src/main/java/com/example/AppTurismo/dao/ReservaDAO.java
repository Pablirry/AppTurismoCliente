package com.example.AppTurismo.dao;

import com.example.AppTurismo.DataBaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ReservaDAO {

    private DataBaseHelper db;

    public ReservaDAO(DataBaseHelper db) {
        this.db = db;
    }

    public boolean reservarRuta(int idUsuario, int idRuta, Date fecha) {
        try (Connection con = db.connectToDatabase()) {
            String sql = "INSERT INTO reservas (id_usuario, id_ruta, fecha, confirmada) VALUES (?, ?, ?, false)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRuta);
            stmt.setDate(3, new java.sql.Date(fecha.getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
