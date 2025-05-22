package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.ReservaEvento;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaEventoDAO {
    private GestorJDBC dbHelper;

    public ReservaEventoDAO(GestorJDBC dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean reservarEvento(int usuarioId, int eventoId, Date fecha) {
        String sql = "INSERT INTO reservas_eventos (usuario_id, evento_id, fecha_reserva, confirmada) VALUES (?, ?, ?, ?)";
        try (Connection con = dbHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, eventoId);
            ps.setTimestamp(3, new Timestamp(fecha.getTime()));
            ps.setBoolean(4, false);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ReservaEvento> obtenerReservasPorUsuario(int usuarioId) {
        List<ReservaEvento> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas_eventos WHERE usuario_id = ?";
        try (Connection con = dbHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservaEvento r = new ReservaEvento(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("evento_id"),
                    rs.getTimestamp("fecha_reserva"),
                    rs.getBoolean("confirmada")
                );
                reservas.add(r);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservas;
    }

}