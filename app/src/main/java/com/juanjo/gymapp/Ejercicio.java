package com.juanjo.gymapp;

public class Ejercicio {
    private int id;
    private String nombre;

    public Ejercicio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}