package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartidaVersus {

    void guardar(PartidaVersus partida);
    PartidaVersus buscarPorId(Long id);
    List<PartidaVersus> buscarPorEstado(String estado);
    void actualizar(PartidaVersus partida);
    List<PartidaVersus> buscarPorJugador1(Long jugador1Id);
}