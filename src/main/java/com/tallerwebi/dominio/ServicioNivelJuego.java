package com.tallerwebi.dominio;

public interface ServicioNivelJuego {
    NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId);
    Integer actualizarNivelJuego(Long idUsuario, Integer idRompecabeza, Integer nivelActualUsuario, Long ultimoNivel);
}
