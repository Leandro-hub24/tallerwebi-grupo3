package com.tallerwebi.presentacion.DtoPerfilJugador;

public class DatosPerfil {
    private String NombreJugador;
    private Integer PuntosAcumulados;
    private Integer CantidadRompecabezasCompletados;
    private Integer CantidadBrainRotCreados;
    private Integer CantidadBrainrotAdivinados ;
    private Integer JuegosGanadosEnModoVersus;
    private Integer tiempoDejuego; // en segundos
/*contructor por defecto*/
    public DatosPerfil(){}
/*constructor con parametrizado*/
    public DatosPerfil(String nombreJugador, Integer puntosAcumulados, Integer cantidadRompecabezasCompletados,
            Integer cantidadBrainRotCreados, Integer cantidadBrainrotAdivinados, Integer juegosGanadosEnModoVersus
            ,Integer tiempoDejuego) {
        this.NombreJugador = nombreJugador;
        this.PuntosAcumulados = puntosAcumulados;
        this.CantidadRompecabezasCompletados = cantidadRompecabezasCompletados;
        this.CantidadBrainRotCreados = cantidadBrainRotCreados;
        this.CantidadBrainrotAdivinados = cantidadBrainrotAdivinados;
        this.JuegosGanadosEnModoVersus = juegosGanadosEnModoVersus;
        this.tiempoDejuego = 0;

    }

    public String getNombreJugador() {
        return NombreJugador;
    }
    public void setNombreJugador(String nombreJugador) {
        this.NombreJugador = nombreJugador;
    }
    public Integer getPuntosAcumulados() {
        return PuntosAcumulados;
    }
    public void setPuntosAcumulados(Integer puntosAcumulados) {
    this.PuntosAcumulados = puntosAcumulados;}
    public Integer getCantidadRompecabezasCompletados() {
        return CantidadRompecabezasCompletados;
    }
    public void setCantidadRompecabezasCompletados(Integer cantidadRompecabezasCompletados) {
        this.CantidadRompecabezasCompletados = cantidadRompecabezasCompletados;
    }
    public Integer getCantidadBrainRotCreados() {
        return CantidadBrainRotCreados;
    }
    public void setCantidadBrainRotCreados(Integer cantidadBrainRotCreados) {
        this.CantidadBrainRotCreados = cantidadBrainRotCreados;
        }
    public Integer getCantidadBrainrotAdivinados() {
        return CantidadBrainrotAdivinados;
    }
    public void setCantidadBrainrotAdivinados(Integer cantidadBrainrotAdivinados) {
        this.CantidadBrainrotAdivinados = cantidadBrainrotAdivinados;
    }
    public Integer getJuegosGanadosEnModoVersus() {
        return JuegosGanadosEnModoVersus;
    }
    public void setJuegosGanadosEnModoVersus(Integer juegosGanadosEnModoVersus) {
        this.JuegosGanadosEnModoVersus = juegosGanadosEnModoVersus;
    }
    public Integer getTiempoDejuego() {
        return tiempoDejuego;
    }
    public void setTiempoDejuego(Integer tiempoDejuego) {
        this.tiempoDejuego = tiempoDejuego;
    }
}
