package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.MensajeVersus;
import com.tallerwebi.dominio.PartidaVersus;
import com.tallerwebi.dominio.ServicioPartidaVersus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class ControladorPartidasVersus {

    private final SimpMessagingTemplate messagingTemplate;
    private final ServicioPartidaVersus servicioPartida;

    @Autowired
    public ControladorPartidasVersus(SimpMessagingTemplate messagingTemplate,
                                      ServicioPartidaVersus servicioPartida) {
        this.messagingTemplate = messagingTemplate;
        this.servicioPartida = servicioPartida;
    }

    @MessageMapping("/crear-partida")
    public void crearPartida(@Payload MensajeVersus mensaje) {
        PartidaVersus nuevaPartida = servicioPartida.crearPartida(mensaje.getUsuarioId());

        MensajeVersus respuesta = new MensajeVersus();
        respuesta.setTipo("PARTIDA_CREADA");
        respuesta.setPartidaId(nuevaPartida.getId());
        respuesta.setUsuarioId(mensaje.getUsuarioId());

        messagingTemplate.convertAndSend("/topic/lobby", respuesta);
    }

    @MessageMapping("/unirse-partida")
    public void unirseAPartida(@Payload MensajeVersus mensaje) {
        PartidaVersus partida = servicioPartida.unirseAPartida(mensaje.getPartidaId(), mensaje.getUsuarioId());

        if (partida != null && partida.getEstado().equals("EN_JUEGO")) {
            MensajeVersus respuesta = new MensajeVersus();
            respuesta.setTipo("PARTIDA_INICIADA");
            respuesta.setPartidaId(partida.getId());

            messagingTemplate.convertAndSend("/topic/partida/" + partida.getId(), respuesta);

            messagingTemplate.convertAndSend("/topic/lobby", respuesta);

            MensajeVersus lobbyUpdate = new MensajeVersus();
            lobbyUpdate.setTipo("PARTIDA_NO_DISPONIBLE");
            lobbyUpdate.setPartidaId(partida.getId());
            messagingTemplate.convertAndSend("/topic/lobby", lobbyUpdate);
        }
    }

    @MessageMapping("/listar-partidas")
    public void listarPartidas() {
        List<PartidaVersus> partidasDisponibles = servicioPartida.obtenerPartidasDisponibles();

        MensajeVersus respuesta = new MensajeVersus();
        respuesta.setTipo("LISTA_PARTIDAS");
        respuesta.setPartidas(partidasDisponibles);

        messagingTemplate.convertAndSend("/topic/lobby", respuesta);
    }

    @MessageMapping("/actualizar-puntos")
    public void actualizarPuntos(@Payload MensajeVersus mensaje) {
        PartidaVersus partida = servicioPartida.actualizarPuntos(
                mensaje.getPartidaId(),
                mensaje.getUsuarioId(),
                mensaje.getPuntos()
        );

        if (partida != null) {
            MensajeVersus respuesta = new MensajeVersus();
            respuesta.setTipo("PUNTOS_ACTUALIZADOS");
            respuesta.setPartidaId(partida.getId());
            respuesta.setPuntosJugador1(partida.getPuntosJugador1());
            respuesta.setPuntosJugador2(partida.getPuntosJugador2());
            respuesta.setJugador1Id(partida.getJugador1Id());
            respuesta.setJugador2Id(partida.getJugador2Id());

            messagingTemplate.convertAndSend("/topic/partida/" + partida.getId(), respuesta);
        }
    }
}