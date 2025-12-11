package com.juanjo.gymapp;

public class RutinaEjercicio {
    private int idEjercicio;
    private int series;
    private int repeticiones;
    private double carga;

    public RutinaEjercicio(int idEjercicio, int series, int repeticiones, double carga) {
        this.idEjercicio = idEjercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.carga = carga;
    }

    public int getIdEjercicio() { return idEjercicio; }
    public int getSeries() { return series; }
    public int getRepeticiones() { return repeticiones; }
    public double getCarga() { return carga; }
}