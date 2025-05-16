package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Restaurante;

import java.sql.Connection;
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
                restaurantes.add(restaurante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantes;
    }
}