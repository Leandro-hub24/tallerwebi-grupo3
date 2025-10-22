package com.tallerwebi.dominio;

public interface RepositorioNivelJuego {
    NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId, String juego);
    Long modificarNivelJuego(Long idUsuario);
    NivelJuego guardarNivelJuego(NivelJuego nivelJuego);
    NivelJuego buscarNivelJuegoPoridUsuarioYIdRompecabeza(Long idUsuario, String juego,Long idRompecabeza);
}
