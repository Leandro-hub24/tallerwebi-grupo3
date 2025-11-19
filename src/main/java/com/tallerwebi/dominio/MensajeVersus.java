package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MensajeVersus {
    private String tipo;
    private Long partidaId;
    private Long usuarioId;
    private String respuesta;
    private List<PartidaVersus> partidas;
    private Integer puntos;
    private Integer puntosJugador1;
    private Integer puntosJugador2;
    private Long jugador1Id;
    private Long jugador2Id;
    public MensajeVersus() {
    }
}