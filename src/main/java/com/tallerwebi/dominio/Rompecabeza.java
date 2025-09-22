package com.tallerwebi.dominio;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rompecabeza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Long piezas;
    private Boolean fueCompletado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPiezas() {
        return piezas;
    }

    public void setPiezas(Long piezas) {
        this.piezas = piezas;
    }

    public Boolean getFueCompletado() {
        return fueCompletado;
    }

    public void setFueCompletado(Boolean fueCompletado) {
        this.fueCompletado = fueCompletado;
    }
}
