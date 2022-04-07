package com.appgate.utils;


import java.util.HashMap;
import java.util.Map;

public class Helpers {

    public static Map<String, String> data = new HashMap<>();
    private String tituloArticulo;
    public String getTituloArticulo() {
        return tituloArticulo;
    }
    public void setTituloArticulo(String tituloArticulo) {
        this.tituloArticulo = tituloArticulo;
    }

}
