package com.tallerwebi.dominio;

import javax.transaction.Transactional;

public interface ServicioAdivinanza {




    @Transactional
    void opcionIngresada(PuntosJuego nuevosPuntos, Usuario usuario, int cantidadIntentos, double cantidadTiempoEnSegundos);

    void calcularPuntos(PuntosJuego puntos, int cantidadIntentos, double cantidadTiempoEnSegundos);

//    void opcionIncorrecta(Usuario usuario);
}
