package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Ruta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RutaDAO {

    private GestorJDBC db;

    public RutaDAO(GestorJDBC db) {
        this.db = db;
    }

    public List<Ruta> listarRutas() {
        List<Ruta> rutas = new ArrayList<>();
        try (Connection con = db.getConnection();
                PreparedStatement ps = con
                        .prepareStatement("SELECT id, nombre, descripcion, precio, dificultad, imagen FROM rutas")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ruta ruta = new Ruta();
                ruta.setId(rs.getInt("id"));
                ruta.setNombre(rs.getString("nombre"));
                ruta.setDescripcion(rs.getString("descripcion"));
                ruta.setPrecio(rs.getDouble("precio"));
                ruta.setDificultad(rs.getInt("dificultad"));
                ruta.setImagen(rs.getBytes("imagen"));
                rutas.add(ruta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rutas;
    }

    public Ruta obtenerRutaPorId(int id) {
        Ruta ruta = null;
        try (Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT id, nombre, descripcion, precio, dificultad, imagen FROM rutas WHERE id = ?")) {
            System.out.println("Buscando ruta con id: " + id);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ruta = new Ruta();
                ruta.setId(rs.getInt("id"));
                ruta.setNombre(rs.getString("nombre"));
                ruta.setDescripcion(rs.getString("descripcion"));
                ruta.setPrecio(rs.getDouble("precio"));
                ruta.setDificultad(rs.getInt("dificultad"));
                ruta.setImagen(rs.getBytes("imagen"));
                System.out.println("Ruta encontrada: " + ruta.getNombre());
            } else {
                System.out.println("No se encontró la ruta con id: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ruta;
    }
}
