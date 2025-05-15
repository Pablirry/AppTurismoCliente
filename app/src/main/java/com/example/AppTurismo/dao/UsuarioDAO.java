package com.example.AppTurismo.dao;

import com.example.AppTurismo.DataBaseHelper;
import com.example.AppTurismo.model.Usuario;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    private DataBaseHelper db;

    public UsuarioDAO(DataBaseHelper db) {
        this.db = db;
    }

    public Usuario login(String correo, String contrasena) {
        Usuario usuario = null;
        try (Connection con = db.connectToDatabase()) {
            String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setImagenPerfil(rs.getBytes("imagen_perfil"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean registrarUsuario(String nombre, String correo, String contrasena) {
        try (Connection con = db.connectToDatabase()) {
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
