package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPuntosJuegoTest {

    private ServicioPuntosJuego servicioPuntosJuego;
    private RepositorioPuntosJuego repositorioPuntosjuegoMock;
    private NivelJuego nivelJuegoMock;
    private PuntosJuego puntosJuegoMock;
    private PuntosJuego puntosJuegoMock1;
    private List<PuntosJuego> puntosJuegoList;
    private String juego;

    @BeforeEach
    public void init() {

        juego = "Rompecabezas";
        repositorioPuntosjuegoMock = mock(RepositorioPuntosJuego.class);
        nivelJuegoMock = mock(NivelJuego.class);
        puntosJuegoMock = mock(PuntosJuego.class);
        puntosJuegoMock1 = mock(PuntosJuego.class);
        puntosJuegoList = List.of(puntosJuegoMock, puntosJuegoMock);
        servicioPuntosJuego = new ServicioPuntosJuegoImpl(repositorioPuntosjuegoMock);
        when(repositorioPuntosjuegoMock.buscarPuntosJuegoConMejorTiempoPorIdUsuario(1L, 1L, juego)).thenReturn(puntosJuegoList);
        when(puntosJuegoMock.getInicioPartida()).thenReturn(Instant.now());
        when(puntosJuegoMock.getFinPartida()).thenReturn(Instant.now().plusSeconds(60));
        when(puntosJuegoMock1.getInicioPartida()).thenReturn(Instant.now());
        when(puntosJuegoMock1.getFinPartida()).thenReturn(Instant.now().plusSeconds(90));
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

    @Test
    void buscoUnPuntosJuegoConMejorTiempo(){

        NivelJuego nivelJuego = givenTengoUnNivelJuego();
        Long usuarioId = givenTengoUnUsuarioId();

        Integer tiempoObtenido = whenBuscoPuntosJuegoConMejorTiempo(1L, usuarioId, juego);

        thenObtengoUnPuntosJuego(tiempoObtenido, 60);

    }

    private Long givenTengoUnUsuarioId() {
        return 1L;
    }

    private Integer whenBuscoPuntosJuegoConMejorTiempo(Long idRompecabeza, Long usuarioId, String juego) {
        return servicioPuntosJuego.buscarPuntosJuegoConMejorTiempo(idRompecabeza, usuarioId, juego);
    }

    private void thenObtengoUnPuntosJuego(Integer tiempoObtenido, Integer tiempoEsperado) {
        assertThat(tiempoObtenido, equalTo(tiempoEsperado));
    }

    @Test
    void buscoUnPuntosJuegoConMejorTiempoYNoEncuentro(){

        NivelJuego nivelJuego = givenTengoUnNivelJuego();
        Long usuarioId = givenTengoUnUsuarioId();
        givenNoTengoPuntosJuego();

        Integer tiempoObtenido = whenBuscoPuntosJuegoConMejorTiempo(1L, usuarioId, juego);

        thenObtengoUnPuntosJuego(tiempoObtenido, 0);

    }

    private void givenNoTengoPuntosJuego() {
        when(repositorioPuntosjuegoMock.buscarPuntosJuegoConMejorTiempoPorIdUsuario(1L, 1L, juego)).thenReturn(List.of());
    }

    @Test
    void siGanoUnaPartidaMultijugadorGanoVeintePuntos() {

        NivelJuego nivelJuego = givenTengoUnNivelJuego();

        whenGuardoLosPuntos(nivelJuego);

        thenLosPuntosSeGuardaron();

    }

    private void whenGuardoLosPuntos(NivelJuego nivelJuego) {
        servicioPuntosJuego.guardarPuntosJuegoRompecabezaMultijugador(nivelJuego);
    }
}
