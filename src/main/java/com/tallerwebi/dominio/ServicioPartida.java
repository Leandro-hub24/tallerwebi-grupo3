package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface ServicioPartida {

        Partida crearPartida(String nombrePartida, Integer usuario1Id, String username, String juego);
        void setPartidasEnCurso(String idPartida, Partida partida);
        void setPartidasAbiertas(String idPartida, Partida partida);
        void setPartidasTerminadas(String idPartida, Partida partida);
        Collection<Partida> getPartidasAbiertas();
        Partida unirJugador(String partidaId, Integer usuario2Id, String username, String juego) throws PartidaNoEncontradaException, PartidaLlenaException;
        void terminarPartida(String partidaId, Integer usuarioId, String juego);

}
