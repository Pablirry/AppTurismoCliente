package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ReservaDAO {

    private GestorJDBC db;

    public ReservaDAO(GestorJDBC db) {
        this.db = db;
    }

    public boolean reservarRuta(int idUsuario, int idRuta, Date fecha) {
        try (Connection con = db.getConnection()) {
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

    public boolean aceptarReserva(int idReserva) {
        try (Connection con = db.getConnection()) {
            String sql = "UPDATE reservas SET confirmada = true WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    }

