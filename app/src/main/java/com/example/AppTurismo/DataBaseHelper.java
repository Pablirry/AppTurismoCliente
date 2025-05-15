package com.example.AppTurismo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHelper extends Service {

    // singleton
    public static DataBaseHelper gestor;

    // atributos
    private Connection conn;
    private Statement state;
    private boolean abierto; // si está abierto lo que necesito

    /**Método que abre todo si no estaba abierto*/
    private void open(){
        // si ya estaba todo abierto, pues nada
        if (abierto) return;
        try {
            // no hace falta: compruebo que tengo el driver necesario
            //Class.forName("com.mysql.cj.jdbc.Driver");
            // arranco el atributo conn
            conn = DriverManager.getConnection(URI, USER, PASSWORD);
            // con la conexión hecha, genero un statement
            if (conn!=null){
                state = conn.createStatement();
                System.out.println("Conexion correcta");
                abierto = true; // conseguí abrir
            }
            else{
                System.out.println("Conexión incorrecta");
            }
        }

        catch (SQLException e) {
            System.err.println("Error de conexion?");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**Método para cerrar conexiones*/
    private void close(){
        if (state!=null){
            try {
                state.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        abierto = false;
    }

    private GestorJDBC(){
        abierto = false; // me sobra
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // singleton: acceso a la instancia
    public static GestorJDBC getInstance(){
        if (gestor==null) gestor = new GestorJDBC();
        return gestor;
    }


    private Connection getConnection() {
        return conn;
    }