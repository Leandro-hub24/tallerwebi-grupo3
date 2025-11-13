package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PartidaVersus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long jugador1Id;
    private Long jugador2Id;
    private String estado;
    private Integer puntosJugador1 = 0;
    private Integer puntosJugador2 = 0;
    
    public PartidaVersus() {
    }
    
    public PartidaVersus(Long jugador1Id) {
        this.jugador1Id = jugador1Id;
        this.estado = "ESPERANDO";
    }
}
