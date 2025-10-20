package com.tallerwebi.dominio;

public interface ServicioNivelJuego {
    NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId, String juego);
    Integer actualizarNivelJuego(Long idUsuario, Integer idRompecabeza, Integer nivelActualUsuario, Long ultimoNivel);
    NivelJuego guardarNivelJuego(Usuario usuario, String juego, Integer idRompecabeza);
}
