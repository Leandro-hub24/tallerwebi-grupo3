package com.tallerwebi.dominio;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

public interface ServicioAdivinanza {




    @Transactional
    void opcionIngresada(PuntosJuego nuevosPuntos, Usuario usuario, int cantidadIntentos, double cantidadTiempoEnSegundos);

    void calcularPuntos(PuntosJuego puntos, int cantidadIntentos, double cantidadTiempoEnSegundos);

    boolean verificarSiEsCorrecto(String nombreImagen, String transcripcion, int cantidadIntentos, HttpSession session, int cantidadIntentos1, PuntosJuego puntos, Usuario usuario, double tiempo);

    void siFallo3IntentosSetPuntos0(int cantidadIntentos, PuntosJuego puntos, Usuario usuario, double tiempo);

    void SiLaRespuestaNoEsCorrectaAniadirIntento(boolean esCorrecto, int cantidadIntentos, HttpSession session);

//    void opcionIncorrecta(Usuario usuario);
}
