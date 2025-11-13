package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPartidaVersus {
    PartidaVersus crearPartida(Long usuarioId);
    List<PartidaVersus> obtenerPartidasDisponibles();
    PartidaVersus unirseAPartida(Long partidaId, Long usuarioId);
    PartidaVersus buscarPorId(Long id);
    PartidaVersus actualizarPuntos(Long partidaId, Long usuarioId, Integer puntos);
}