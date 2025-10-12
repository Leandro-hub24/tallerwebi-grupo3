package com.tallerwebi.dominio;

public interface RepositorioNivelJuego {
    NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId);
    Long modificarNivelJuego(Long idUsuario);
}
