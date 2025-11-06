package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public void setPartidasEnCurso(String idPartida, Partida partida) {
        partidasEnCurso.put(idPartida, partida);
    }

    public void setPartidasAbiertas(String idPartida, Partida partida) {
        partidasAbiertas.put(idPartida, partida);
    }

    public Partida crearPartida(String nombrePartida, Integer creadorId, String username,String juego) {
        String idPartida = UUID.randomUUID().toString().substring(0, 8);
        Partida nuevaPartida = new Partida(idPartida, nombrePartida);
        nuevaPartida.setJugador1Id(creadorId);
        nuevaPartida.setJugador1Nombre(username);

        partidasAbiertas.put(idPartida, nuevaPartida);
        if (juego == "rompecabezas"){
            template.convertAndSend("/topic/lobby/nueva", nuevaPartida);

        } else if (juego == "adivinanza"){
            template.convertAndSend("/topic/lobbyAdivinanza/nueva", nuevaPartida);
        }
//        template.convertAndSend("/topic/lobby/nueva", nuevaPartida);
        return nuevaPartida;
    }



    public Partida unirJugador(String idPartida, Integer jugador, String username, String juego)
            throws PartidaNoEncontradaException, PartidaLlenaException {


        Partida partida = partidasAbiertas.get(idPartida);

        if (partida != null) {

            if (partida.getJugador1Id().equals(jugador)) {
                return partida;
            }

            if (partida.getJugador2Id() == null) {
                partida.setJugador2Id(jugador);
                partida.setJugador2Nombre(username);
                partida.setEstado("EN_CURSO");
                partida.setFechaInicio(Instant.now());
                partidasAbiertas.remove(idPartida);
                partidasEnCurso.put(idPartida, partida);
                if ( juego == "rompecabezas"){
                    template.convertAndSend("/topic/lobby/removida", partida);
                    String destinoPartida = "/topic/partida/" + idPartida;
                    template.convertAndSend(destinoPartida, partida);

                }  else if (juego == "adivinanza"){
                    template.convertAndSend("/topic/lobbyAdivinanza/removida", partida);
                    String destinoPartida = "/topic/partidaAdivinanza/" + idPartida;
                    template.convertAndSend(destinoPartida, partida);

                }
//                template.convertAndSend("/topic/lobby/removida", partida);
//
//
//                String destinoPartida = "/topic/partida/" + idPartida;
//                template.convertAndSend(destinoPartida, partida);
                return partida;

            }

            throw new PartidaLlenaException("La partida se llenó.");

        }

        partida = partidasEnCurso.get(idPartida);
        if (partida != null) {

            if (partida.getJugador1Id().equals(jugador) ||
                    (partida.getJugador2Id() != null && partida.getJugador2Id().equals(jugador))) {
                return partida;
            }

            throw new PartidaLlenaException("La partida ya está en curso.");
        }

        partida = partidasTerminadas.get(idPartida);

        if (partida != null) {
            if (partida.getJugador1Id().equals(jugador) || partida.getJugador2Id().equals(jugador)) {
                return partida;
            }
            throw new PartidaLlenaException("La partida ya terminó.");
        }

        throw new PartidaNoEncontradaException("La partida no existe.");
    }

    public void terminarPartida(String idPartida, Integer usuarioId, String juego) {
        Partida partida = partidasEnCurso.get(idPartida);

        if (partida != null) {
            partidasEnCurso.remove(idPartida);
            partidasTerminadas.put(idPartida, partida);
            partida.setEstado("TERMINADA");
            partida.setGanador(usuarioId);
            if ( juego == "rompecabezas"){
                String destinoPartida = "/topic/partida/" + idPartida;
                template.convertAndSend(destinoPartida, partida);

            }  else if (juego == "adivinanza"){
                String destinoPartida = "/topic/partidaAdivinanza/" + idPartida;
                template.convertAndSend(destinoPartida, partida);
            }

//            String destinoPartida = "/topic/partida/" + idPartida;
//
//            template.convertAndSend(destinoPartida, partida);

        }

    }

}
