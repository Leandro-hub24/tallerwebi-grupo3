package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface ServicioPartida {

        Partida crearPartida(String nombrePartida, Integer usuario1Id, String username, String juego);
        Collection<Partida> getPartidasAbiertas();
        Partida unirJugador(String partidaId, Integer usuario2Id, String username, String juego) throws PartidaNoEncontradaException, PartidaLlenaException;
        void terminarPartida(String partidaId, Integer usuarioId, String juego);
        void registrarIntento(String idPartida, Integer usuarioId);

}
