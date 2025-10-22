package com.tallerwebi.dominio;

import java.time.Instant;

public interface ServicioPuntosJuego {

    void guardarPuntosJuegoRompecabeza(NivelJuego nivelJuego, Instant inicioPartida, Instant finPartida);

    Integer buscarPuntosJuegoConMejorTiempo(Long idRompecabeza, Long usuarioId, String juego);
}
