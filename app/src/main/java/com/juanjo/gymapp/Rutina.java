package com.juanjo.gymapp;

import java.util.List;

public class Rutina {

    private int idRutina;
    private String nombre;
    private List<RutinaEjercicio> ejercicios;

    public Rutina(int idRutina, String nombre, List<RutinaEjercicio> ejercicios) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.ejercicios = ejercicios;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public String getNombre() {
        return nombre;
    }

    public List<RutinaEjercicio> getEjercicios() {
        return ejercicios;
    }
}