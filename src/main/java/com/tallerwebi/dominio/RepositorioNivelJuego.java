package com.tallerwebi.dominio;

public interface RepositorioNivelJuego {
    NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId, String juego);

    NivelJuego buscarNivelJuegoPorNombre(String nombre);

    Long modificarNivelJuego(Long idUsuario);
    NivelJuego guardarNivelJuego(NivelJuego nivelJuego);
}
