package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPuntosJuegoTest {

    private ServicioPuntosJuego servicioPuntosJuego;
    private RepositorioPuntosJuego repositorioPuntosjuegoMock;
    private NivelJuego nivelJuegoMock;

    @BeforeEach
    public void init() {

        repositorioPuntosjuegoMock = mock(RepositorioPuntosJuego.class);
        nivelJuegoMock = mock(NivelJuego.class);
        servicioPuntosJuego = new ServicioPuntosJuegoImpl(repositorioPuntosjuegoMock);

    }

    @Test
    void siElTiempoDeResolucionEsMenorOIgualASesentaSegundosGuardaDiezPuntosGanados() {

        NivelJuego nivelJuego = givenTengoUnNivelJuego();
        Instant inicioTime = givenTengoUnTiempoDeInicio();
        Instant finTime = givenTengoUnTiempoDeFin(45L);

        whenComprueboLosPuntosYLosGuardo(nivelJuego, inicioTime, finTime);

        thenLosPuntosSeGuardaron();

    }

    private NivelJuego givenTengoUnNivelJuego() {
        return nivelJuegoMock;
    }

    private Instant givenTengoUnTiempoDeInicio() {
        return Instant.now();
    }

    private Instant givenTengoUnTiempoDeFin(Long segundos) {
        return Instant.now().plusSeconds(segundos);
    }

    private void whenComprueboLosPuntosYLosGuardo(NivelJuego nivelJuego, Instant inicioTime, Instant finTime) {
        servicioPuntosJuego.guardarPuntosJuegoRompecabeza(nivelJuego, inicioTime, finTime);
    }

    private void thenLosPuntosSeGuardaron() {
    }

    @Test
    void siElTiempoDeResolucionEsMayorASesentaSegundosYMenorOIgualACientoOchentaGuarda5PuntosGanados() {

        NivelJuego nivelJuego = givenTengoUnNivelJuego();
        Instant inicioTime = givenTengoUnTiempoDeInicio();
        Instant finTime = givenTengoUnTiempoDeFin(120L);

        whenComprueboLosPuntosYLosGuardo(nivelJuego, inicioTime, finTime);

        thenLosPuntosSeGuardaron();

    }

    @Test
    void siElTiempoDeResolucionEsMayorACientoOchentaSegundosYMenorOIgualATrescientosGuarda2PuntosGanados() {

        NivelJuego nivelJuego =  givenTengoUnNivelJuego();
        Instant inicioTime = givenTengoUnTiempoDeInicio();
        Instant finTime = givenTengoUnTiempoDeFin(240L);

        whenComprueboLosPuntosYLosGuardo(nivelJuego, inicioTime, finTime);

        thenLosPuntosSeGuardaron();

    }

    @Test
    void siElTiempoDeResolucionEsMayorATrescientosGuarda1PuntosGanados() {

        NivelJuego nivelJuego =  givenTengoUnNivelJuego();
        Instant inicioTime = givenTengoUnTiempoDeInicio();
        Instant finTime = givenTengoUnTiempoDeFin(320L);

        whenComprueboLosPuntosYLosGuardo(nivelJuego, inicioTime, finTime);

        thenLosPuntosSeGuardaron();

    }
}
