package com.tallerwebi.dominio;

public class Brainrot {
    private String imagenFondo;
    private String imagenPersonaje;
    private String audio;
    private String imagenSombra;
    private String imagenCompleta;

    public Brainrot(String imagenFondo,String imagenPersonaje,String audio,String imagenSombra,String imagenCompleta){
        this.imagenFondo = imagenFondo;
        this.imagenPersonaje = imagenPersonaje;
        this.audio = audio;
        this.imagenSombra = imagenSombra;
        this.imagenCompleta = imagenCompleta;
    }

    public String getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(String imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public String getImagenPersonaje() {
        return imagenPersonaje;
    }

    public void setImagenPersonaje(String imagenPersonaje) {
        this.imagenPersonaje = imagenPersonaje;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImagenSombra() {
        return imagenSombra;
    }

    public void setImagenSombra(String imagenSombra) {
        this.imagenSombra = imagenSombra;
    }

    public String getImagenCompleta() {
        return imagenCompleta;
    }

    public void setImagenCompleta(String imagenCompleta) {
        this.imagenCompleta = imagenCompleta;
    }
}
