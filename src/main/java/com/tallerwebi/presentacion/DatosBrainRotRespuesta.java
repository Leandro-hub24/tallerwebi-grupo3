package com.tallerwebi.presentacion;

public class DatosBrainRotRespuesta {
    
    private String respuestaSeleccionada; // A, B, C o D

    public DatosBrainRotRespuesta() {
    }

    public DatosBrainRotRespuesta(String respuestaSeleccionada) {
        this.respuestaSeleccionada = respuestaSeleccionada;
    }

    public String getRespuestaSeleccionada() {
        return respuestaSeleccionada;
    }

    public void setRespuestaSeleccionada(String respuestaSeleccionada) {
        this.respuestaSeleccionada = respuestaSeleccionada;
    }
}
