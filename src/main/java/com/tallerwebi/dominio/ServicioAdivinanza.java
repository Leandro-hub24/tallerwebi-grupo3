package com.tallerwebi.dominio;

import javax.transaction.Transactional;

public interface ServicioAdivinanza {


    @Transactional
    void opcionIngresada(PuntosJuego nuevosPuntos, Usuario usuario);

//    void opcionIncorrecta(Usuario usuario);
}
