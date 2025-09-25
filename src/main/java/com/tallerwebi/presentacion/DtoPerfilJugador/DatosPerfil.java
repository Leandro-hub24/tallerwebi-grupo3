package com.tallerwebi.presentacion.DtoPerfilJugador;

public class DatosPerfil {
    private String NombreJugador;
    private Integer PuntosAcumulados;
    private Integer CantidadRompecabezasCompletados;
    private Integer CantidadBrainRotCreados;
    private Integer CantidadBrainrotAdivinados ;
    private Integer JuegosGanadosEnModoVersus;

    public DatosPerfil(){}

    public DatosPerfil(String nombreJugador, Integer puntosAcumulados, Integer cantidadRompecabezasCompletados,
            Integer cantidadBrainRotCreados, Integer cantidadBrainrotAdivinados, Integer juegosGanadosEnModoVersus) {
        NombreJugador = nombreJugador;
        PuntosAcumulados = puntosAcumulados;
        CantidadRompecabezasCompletados = cantidadRompecabezasCompletados;
        CantidadBrainRotCreados = cantidadBrainRotCreados;
        CantidadBrainrotAdivinados = cantidadBrainrotAdivinados;
        JuegosGanadosEnModoVersus = juegosGanadosEnModoVersus;

    }

    public String getNombreJugador() {
        return NombreJugador;
    }
    public void setNombreJugador(String nombreJugador) {
        NombreJugador = nombreJugador;
    }
    public Integer getPuntosAcumulados() {
        return PuntosAcumulados;
    }
    public void setPuntosAcumulados(Integer puntosAcumulados) {
    PuntosAcumulados = puntosAcumulados;}
    public Integer getCantidadRompecabezasCompletados() {
        return CantidadRompecabezasCompletados;
    }
    public void setCantidadRompecabezasCompletados(Integer cantidadRompecabezasCompletados) {
        CantidadRompecabezasCompletados = cantidadRompecabezasCompletados;
    }
    public Integer getCantidadBrainRotCreados() {
        return CantidadBrainRotCreados;
    }
    public void setCantidadBrainRotCreados(Integer cantidadBrainRotCreados) {
        CantidadBrainRotCreados = cantidadBrainRotCreados;
        }
    public Integer getCantidadBrainrotAdivinados() {
        return CantidadBrainrotAdivinados;
    }
    public void setCantidadBrainrotAdivinados(Integer cantidadBrainrotAdivinados) {
        CantidadBrainrotAdivinados = cantidadBrainrotAdivinados;
    }
    public Integer getJuegosGanadosEnModoVersus() {
        return JuegosGanadosEnModoVersus;
    }
    public void setJuegosGanadosEnModoVersus(Integer juegosGanadosEnModoVersus) {
        JuegosGanadosEnModoVersus = juegosGanadosEnModoVersus;
    }

}
