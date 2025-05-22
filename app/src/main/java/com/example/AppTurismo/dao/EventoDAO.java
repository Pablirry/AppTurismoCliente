package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Evento;
import java.sql.*;
import java.util.*;

public class EventoDAO {
    private GestorJDBC dbHelper;

    public EventoDAO(GestorJDBC dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Evento obtenerEventoPorId(int id) {
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM eventos WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Evento(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("ubicacion"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBytes("imagen")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<Evento> obtenerTodosEventos() {
        List<Evento> lista = new ArrayList<>();
        try (Connection conn = dbHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM eventos")) {
            while (rs.next()) {
                lista.add(new Evento(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("ubicacion"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBytes("imagen")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}