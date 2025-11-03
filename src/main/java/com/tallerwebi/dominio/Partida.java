package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Partida {
    private String id;
    private String nombre;
    private Integer jugador1;
    private Integer jugador2;
    private String estado;
    private Integer ganador;

    public Partida() {
    }

    public Partida(String idPartida, String nombrePartida) {
        this.id = idPartida;
        this.nombre = nombrePartida;
        this.estado = "ESPERANDO_OPONENTE";
    }
}
