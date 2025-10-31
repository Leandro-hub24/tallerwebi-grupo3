package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface ServicioPartida {

        Partida crearPartida(String nombrePartida, Integer usuario1Id);
        Collection<Partida> getPartidasAbiertas();
        Partida unirJugador(String partidaId, Integer usuario2Id) throws PartidaNoEncontradaException, PartidaLlenaException;
        void terminarPartida(String partidaId, Integer usuarioId);
}
