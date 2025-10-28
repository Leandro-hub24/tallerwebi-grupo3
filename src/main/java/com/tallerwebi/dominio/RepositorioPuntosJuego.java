package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPuntosJuego {
    Long agregarPuntos(PuntosJuego puntosJuego);

    List<PuntosJuego> buscarPuntosJuegoConMejorTiempoPorIdUsuario(Long usuarioId, Long idRompecabeza, String juego);
}
