package com.example.AppTurismo.dao;

import com.example.AppTurismo.DataBaseHelper;
import com.example.AppTurismo.model.Ruta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    private DataBaseHelper db;

    public RutaDAO(DataBaseHelper db) {
        this.db = db;
    }

    public List<Ruta> listarRutas() {
        List<Ruta> rutas = new ArrayList<>();
        try (Connection con = db.connectToDatabase()) {
            String sql = "SELECT * FROM rutas";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Ruta ruta = new Ruta();
                ruta.setId(rs.getInt("id"));
                ruta.setNombre(rs.getString("nombre"));
                ruta.setDescripcion(rs.getString("descripcion"));
                ruta.setImagen(rs.getBytes("imagen"));
                ruta.setPrecio(rs.getDouble("precio"));
                ruta.setDificultad(rs.getInt("dificultad"));
                rutas.add(ruta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rutas;
    }
}
