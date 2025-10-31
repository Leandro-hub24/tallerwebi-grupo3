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

    // Inyectamos el "cartero" de WebSocket
    @Autowired
    private SimpMessagingTemplate template;

    // K: idPartida, V: Partida
    private static Map<String, Partida> partidasAbiertas = new ConcurrentHashMap<>();

    // (Recomendado) Mover las partidas llenas a otro mapa
    private static Map<String, Partida> partidasEnCurso = new ConcurrentHashMap<>();

    private static Map<String, Partida> partidasTerminadas = new ConcurrentHashMap<>();

    public Collection<Partida> getPartidasAbiertas() {
        return partidasAbiertas.values();
    }

    /**
     * Lógica principal para unir un jugador.
     */
    public Partida unirJugador(String idPartida, Integer jugador)
            throws PartidaNoEncontradaException, PartidaLlenaException {

        // 1. Buscar en partidas abiertas
        Partida partida = partidasAbiertas.get(idPartida);

        if (partida != null) {
            // Caso A: El creador (J1) está recargando su página
            if (partida.getJugador1().equals(jugador)) {
                return partida;
            }

            // Caso B: El slot J2 está libre, ¡unimos al jugador!
            if (partida.getJugador2() == null) {
                partida.setJugador2(jugador);
                partida.setEstado("EN_CURSO"); // <-- ¡CAMBIO DE ESTADO!

                // Mover la partida
                partidasAbiertas.remove(idPartida);
                partidasEnCurso.put(idPartida, partida);

                // Notificar al LOBBY que esta partida se llenó
                template.convertAndSend("/topic/lobby/removida", partida);

                // Notificar A AMBOS JUGADORES (J1 ya está esperando)
                // que el estado cambió y el juego puede empezar.
                String destinoPartida = "/topic/partida/" + idPartida;
                template.convertAndSend(destinoPartida, partida); // Enviamos la partida completa

                return partida;
            }

            // Caso C: Alguien se unió justo antes (race condition)
            throw new PartidaLlenaException("La partida se llenó.");

        }

        // 2. Si no estaba abierta, buscar en "en curso"
        partida = partidasEnCurso.get(idPartida);
        if (partida != null) {
            // Caso D: Es J1 o J2 recargando la página de un juego en curso
            if (partida.getJugador1().equals(jugador) ||
                    (partida.getJugador2() != null && partida.getJugador2().equals(jugador))) {
                return partida; // Dejarlo re-entrar
            }
            // Caso E: Es un espectador intentando unirse a una partida llena
            throw new PartidaLlenaException("La partida ya está en curso.");
        }

        // 3. ¡AÑADIR ESTO! Buscar en partidas terminadas
        partida = partidasTerminadas.get(idPartida);
        if (partida != null) {
            // Es un jugador recargando una partida terminada
            if (partida.getJugador1().equals(jugador) || partida.getJugador2().equals(jugador)) {
                // Le devolvemos la partida. El JS de la vista leerá
                // el estado "TERMINADA" y mostrará la pantalla correcta.
                return partida;
            }
            throw new PartidaLlenaException("La partida ya terminó.");
        }

        // 3. Si no está en ningún lado
        throw new PartidaNoEncontradaException("La partida no existe.");
    }

    // ... (resto de métodos: crearPartida, getPartidasAbiertas, etc.) ...

    // Modifica 'crearPartida' para que el creador sea el jugador 1
    public Partida crearPartida(String nombrePartida, Integer creadorId) {
        String idPartida = UUID.randomUUID().toString().substring(0, 8);;
        Partida nuevaPartida = new Partida(idPartida, nombrePartida);
        nuevaPartida.setJugador1(creadorId); // Asignar creador

        partidasAbiertas.put(idPartida, nuevaPartida);

        // Notificar al lobby que se creó
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
