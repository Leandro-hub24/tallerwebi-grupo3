package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Partida {
    private String id;
    private String nombre;
    private Integer jugador1Id;
    private String jugador1Nombre;
    private Integer jugador2Id;
    private String jugador2Nombre;
    private String estado;
    private Integer ganador;
    private Instant fechaInicio;
    private Map<Integer, Integer> intentosPorJugador = new HashMap<>();

    public Partida() {
    }

    public Partida(String idPartida, String nombrePartida) {
        this.id = idPartida;
        this.nombre = nombrePartida;
        this.estado = "ESPERANDO_OPONENTE";
    }

}
