package com.juanjo.gymapp;

public class RutinaEjercicio {

    private int idEjercicio;
    private int series;
    private int repeticiones;
    private double carga;
    private String nombre;

    public RutinaEjercicio(int idEjercicio, int series, int repeticiones, double carga, String nombre) {
        this.idEjercicio = idEjercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.carga = carga;
        this.nombre = nombre;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public int getSeries() {
        return series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public double getCarga() {
        return carga;
    }

    public String getNombre() {
        return nombre;
    }
    public void setSeries(int series) {
        this.series = series;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public void setCarga(double carga) {
        this.carga = carga;
    }
}