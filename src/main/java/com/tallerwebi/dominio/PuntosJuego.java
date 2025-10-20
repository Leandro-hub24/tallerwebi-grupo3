package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
public class PuntosJuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer puntos;
    private Instant inicioPartida;
    private Instant finPartida;
    @ManyToOne
    @JoinColumn(name = "nivelJuego_id")
    private NivelJuego nivelJuego;


}
