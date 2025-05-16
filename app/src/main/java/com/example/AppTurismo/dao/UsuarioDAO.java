package com.example.AppTurismo.dao;

import com.example.AppTurismo.GestorJDBC;
import com.example.AppTurismo.model.Usuario;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    private GestorJDBC db;

    public UsuarioDAO(GestorJDBC db) {
        this.db = db;
    }

    public boolean registrarUsuario(String nombre, String correo, String contrasena) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO usuarios (nombre, correo, contrasena, tipo) VALUES (?, ?, ?, 'cliente')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contrasena);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
