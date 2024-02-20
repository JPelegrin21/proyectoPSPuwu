package com.example.chattt;

public class DatosUsuario {

    String nombre;
    String ultimoMensaje;

    public DatosUsuario(String nombre, String ultimoMensaje) {
        this.nombre = nombre;
        this.ultimoMensaje = ultimoMensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }
}