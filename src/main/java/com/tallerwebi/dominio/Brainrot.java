package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Brainrot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imagenFondo;
    private String imagenPersonaje;
    private String audio;
    private String imagenSombra;
    private String imagenCompleta;
    private Integer nivel;

    public Brainrot(String imagenFondo,String imagenPersonaje,String audio,String imagenSombra,String imagenCompleta,Integer nivel){
        this.imagenFondo = imagenFondo;
        this.imagenPersonaje = imagenPersonaje;
        this.audio = audio;
        this.imagenSombra = imagenSombra;
        this.imagenCompleta = imagenCompleta;
        this.nivel = nivel;
    }
    public Brainrot(){
    }


}
