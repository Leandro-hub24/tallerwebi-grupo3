package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service("servicioPartida")
public class ServicioPartidaImpl implements ServicioPartida {

    @Autowired
    private SimpMessagingTemplate template;

    private static Map<String, Partida> partidasAbiertas = new ConcurrentHashMap<>();
    private static Map<String, Partida> partidasEnCurso = new ConcurrentHashMap<>();
    private static Map<String, Partida> partidasTerminadas = new ConcurrentHashMap<>();

    public Collection<Partida> getPartidasAbiertas() {
        return partidasAbiertas.values();
    }

    public Partida unirJugador(String idPartida, Integer jugador)
            throws PartidaNoEncontradaException, PartidaLlenaException {


        Partida partida = partidasAbiertas.get(idPartida);

        if (partida != null) {

            if (partida.getJugador1().equals(jugador)) {
                return partida;
            }

            if (partida.getJugador2() == null) {
                partida.setJugador2(jugador);
                partida.setEstado("EN_CURSO");

                partidasAbiertas.remove(idPartida);
                partidasEnCurso.put(idPartida, partida);

                template.convertAndSend("/topic/lobby/removida", partida);


                String destinoPartida = "/topic/partida/" + idPartida;
                template.convertAndSend(destinoPartida, partida);

                return partida;
            }

            throw new PartidaLlenaException("La partida se llenó.");

        }

        partida = partidasEnCurso.get(idPartida);
        if (partida != null) {

            if (partida.getJugador1().equals(jugador) ||
                    (partida.getJugador2() != null && partida.getJugador2().equals(jugador))) {
                return partida;
            }

            throw new PartidaLlenaException("La partida ya está en curso.");
        }

        partida = partidasTerminadas.get(idPartida);

        if (partida != null) {
            if (partida.getJugador1().equals(jugador) || partida.getJugador2().equals(jugador)) {
                return partida;
            }
            throw new PartidaLlenaException("La partida ya terminó.");
        }

        throw new PartidaNoEncontradaException("La partida no existe.");
    }


    public Partida crearPartida(String nombrePartida, Integer creadorId) {
        String idPartida = UUID.randomUUID().toString().substring(0, 8);
        Partida nuevaPartida = new Partida(idPartida, nombrePartida);
        nuevaPartida.setJugador1(creadorId);

        partidasAbiertas.put(idPartida, nuevaPartida);

        template.convertAndSend("/topic/lobby/nueva", nuevaPartida);
        return nuevaPartida;
    }

    public void terminarPartida(String idPartida, Integer usuarioId) {
        Partida partida = partidasEnCurso.get(idPartida);

        if (partida != null) {
            partidasEnCurso.remove(idPartida);
            partidasTerminadas.put(idPartida, partida);
            partida.setEstado("TERMINADA");
            partida.setGanador(usuarioId);
            String destinoPartida = "/topic/partida/" + idPartida;

            template.convertAndSend(destinoPartida, partida);

        }

    }

}
