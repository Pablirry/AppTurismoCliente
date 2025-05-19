package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Reserva> obtenerReservasPorUsuario(int idUsuario) {
        List<Reserva> reservas = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM reservas WHERE id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setIdUsuario(rs.getInt("id_usuario"));
                r.setIdRuta(rs.getInt("id_ruta"));
                r.setFecha(rs.getTimestamp("fecha"));
                r.setConfirmada(rs.getBoolean("confirmada"));
                reservas.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
    }

