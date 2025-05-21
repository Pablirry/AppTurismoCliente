package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Restaurante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {
    private GestorJDBC db;

    public RestauranteDAO(GestorJDBC db) {
        this.db = db;
    }

    public List<Restaurante> listarRestaurantes() {
        List<Restaurante> restaurantes = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM restaurantes";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Restaurante restaurante = new Restaurante();
                restaurante.setId(rs.getInt("id"));
                restaurante.setNombre(rs.getString("nombre"));
                restaurante.setUbicacion(rs.getString("ubicacion"));
                restaurante.setImagen(rs.getBytes("imagen"));
                restaurante.setEspecialidad(rs.getString("especialidad"));
                restaurantes.add(restaurante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantes;
    }
    public Restaurante obtenerRestaurantePorId(int idRestaurante) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM restaurantes WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Restaurante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getBytes("imagen"),
                    rs.getString("especialidad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}