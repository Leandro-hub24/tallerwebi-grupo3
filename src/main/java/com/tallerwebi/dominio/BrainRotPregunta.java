package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BrainRotPregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlImagen;           // URL o path de la imagen del brain rot
    private String opcionA;             // Primera opción de respuesta
    private String opcionB;             // Segunda opción de respuesta  
    private String opcionC;             // Tercera opción de respuesta
    private String opcionD;             // Cuarta opción de respuesta
    private String respuestaCorrecta;   // A, B, C o D
    private String categoria;           // MEME, VIRAL, TIKTOK, YOUTUBE, etc.
    private String dificultad;          // FACIL, MEDIO, DIFICIL
    private String descripcionImagen;   // Descripción alternativa para accesibilidad

    // Constructor vacío (requerido por JPA)
    public BrainRotPregunta() {
    }

    // Constructor completo
    public BrainRotPregunta(String urlImagen, String opcionA, String opcionB, String opcionC, String opcionD, 
                           String respuestaCorrecta, String categoria, String dificultad, String descripcionImagen) {
        this.urlImagen = urlImagen;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.opcionD = opcionD;
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
        this.dificultad = dificultad;
        this.descripcionImagen = descripcionImagen;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getDescripcionImagen() {
        return descripcionImagen;
    }

    public void setDescripcionImagen(String descripcionImagen) {
        this.descripcionImagen = descripcionImagen;
    }

    // Métodos útiles para el juego
    public boolean esRespuestaCorrecta(String respuesta) {
        return this.respuestaCorrecta.equalsIgnoreCase(respuesta);
    }

    public String getOpcionPorLetra(String letra) {
        switch (letra.toUpperCase()) {
            case "A": return this.opcionA;
            case "B": return this.opcionB;
            case "C": return this.opcionC;
            case "D": return this.opcionD;
            default: return null;
        }
    }

    public String getColorRespuesta(String respuestaUsuario) {
        return esRespuestaCorrecta(respuestaUsuario) ? "verde" : "rojo";
    }
}
