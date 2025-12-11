package com.juanjo.gymapp;

import java.util.ArrayList;
import java.util.List;
public class Config {
    // Cambiá solo esta línea cuando cambie tu IP
    //public static final String IP = "192.168.100.15";//Claro
    public static final String IP = "192.168.0.217";//Fibertel
    public static final String BASE_URL = "http://" + IP + "/api/";
    public static final String MEDIA_URL = "http://" + IP + "/Media/";

    public static List<String> listaNombresEjercicios = new ArrayList<>();
    public static List<Integer> listaIdsEjercicios = new ArrayList<>();

}
