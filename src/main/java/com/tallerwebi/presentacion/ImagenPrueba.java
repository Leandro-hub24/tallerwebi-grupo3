package com.tallerwebi.presentacion;

public class ImagenPrueba {
    private int id;
    private String nombre;
    private String url;

    public ImagenPrueba(int id, String nombre, String url) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUrl() { return url; }
}
