package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service("servicioPuntosJuego")
@Transactional
public class ServicioPuntosJuegoImpl implements ServicioPuntosJuego{

    private RepositorioPuntosJuego repositorioPuntosJuego;

    @Autowired
    public ServicioPuntosJuegoImpl(RepositorioPuntosJuego repositorioPuntosJuego){
        this.repositorioPuntosJuego = repositorioPuntosJuego;
    }

    @Override
    public void guardarPuntosJuegoRompecabeza(NivelJuego nivelJuego, Instant inicioPartida, Instant finPartida) {

        PuntosJuego puntosJuego = new PuntosJuego();
        puntosJuego.setInicioPartida(inicioPartida);
        puntosJuego.setFinPartida(finPartida);
        puntosJuego.setNivelJuego(nivelJuego);

        Duration duration = Duration.between(inicioPartida, finPartida);
        long tiempoEnSegundos = duration.getSeconds();

        if ( tiempoEnSegundos <= 60) {
            puntosJuego.setPuntos(10);
            repositorioPuntosJuego.agregarPuntos(puntosJuego);
        }

        if (tiempoEnSegundos <= 180 && tiempoEnSegundos > 60) {
            puntosJuego.setPuntos(5);
            repositorioPuntosJuego.agregarPuntos(puntosJuego);
        }

        if (tiempoEnSegundos <= 300 && tiempoEnSegundos > 180) {
            puntosJuego.setPuntos(2);
            repositorioPuntosJuego.agregarPuntos(puntosJuego);
        }

        if (tiempoEnSegundos > 300) {
            puntosJuego.setPuntos(1);
            repositorioPuntosJuego.agregarPuntos(puntosJuego);
        }
    }
}
