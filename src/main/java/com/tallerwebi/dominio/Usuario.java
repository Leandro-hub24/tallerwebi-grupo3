package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;

    private Integer puntajeAdivinanza;
    private Integer rompecabezaNivel;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getRompecabezaNivel() {
        return rompecabezaNivel;
    }
    public void setRompecabezaNivel(Integer rompecabezaNivel) {
        this.rompecabezaNivel = rompecabezaNivel;
    }

    public Integer getPuntajeAdivinanza() {return puntajeAdivinanza;}

    public void setPuntajeAdivinanza(Integer puntajeAdivinanza) {this.puntajeAdivinanza = puntajeAdivinanza;}

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }


}
