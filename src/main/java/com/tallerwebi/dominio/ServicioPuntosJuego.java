package com.tallerwebi.dominio;

import java.time.Instant;
import java.util.Date;

public interface ServicioPuntosJuego {

    void guardarPuntosJuegoRompecabeza(NivelJuego nivelJuego, Instant inicioPartida, Instant finPartida);
}
