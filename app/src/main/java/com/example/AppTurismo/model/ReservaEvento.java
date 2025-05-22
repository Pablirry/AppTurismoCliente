package com.example.AppTurismo.model;

import java.util.Date;

public class ReservaEvento {
    private int id;
    private int idUsuario;
    private int idEvento;
    private Date fecha;
    private boolean confirmada;

    public ReservaEvento(int id, int idUsuario, int idEvento, Date fecha, boolean confirmada) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.fecha = fecha;
        this.confirmada = confirmada;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public boolean isConfirmada() { return confirmada; }
    public void setConfirmada(boolean confirmada) { this.confirmada = confirmada; }
}