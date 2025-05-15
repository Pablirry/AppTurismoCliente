package com.example.AppTurismo.model;

import java.sql.Timestamp;

public class Historial {
    private int id;
    private int idUsuario;
    private String accion;
    private Timestamp fecha;

    public Historial() {}

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}