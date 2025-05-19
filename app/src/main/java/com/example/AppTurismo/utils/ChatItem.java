package com.example.AppTurismo.utils;

import java.sql.Timestamp;

public class ChatItem {
    public static final int TIPO_USUARIO = 1;
    public static final int TIPO_ADMIN = 2;

    private int tipo;
    private String texto;
    private Timestamp fecha;

    public ChatItem(int tipo, String texto, Timestamp fecha) {
        this.tipo = tipo;
        this.texto = texto;
        this.fecha = fecha;
    }

    public int getTipo() { return tipo; }
    public String getTexto() { return texto; }
    public Timestamp getFecha() { return fecha; }
}