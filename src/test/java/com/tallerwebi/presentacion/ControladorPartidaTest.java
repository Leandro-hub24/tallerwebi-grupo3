package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPartidaTest {

    private ServicioPartida servicioPartidaMock;
    private ServicioRompecabezas servicioRompecabezasMock;
    private ServicioNivelJuego servicioNivelJuegoMock;
    private ServicioUsuario servicioUsuarioMock;
    private ServicioPuntosJuego servicioPuntosJuegoMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private ControladorPartida controladorPartida;
    private NivelJuego nivelJuegoMock;
    private Usuario usuarioMock;
    private Rompecabeza rompecabezaMock;
    private Partida partidaMock;
    private Collection<Partida> partidaCollection;

    @BeforeEach
    public void init() throws PartidaNoEncontradaException, PartidaLlenaException {

        usuarioMock = mock(Usuario.class);
        partidaMock = mock(Partida.class);
        rompecabezaMock = mock(Rompecabeza.class);
        nivelJuegoMock = mock(NivelJuego.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        partidaCollection = List.of(partidaMock);

        servicioPartidaMock = mock(ServicioPartida.class);
        servicioRompecabezasMock = mock(ServicioRompecabezas.class);
        servicioNivelJuegoMock = mock(ServicioNivelJuego.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioPuntosJuegoMock = mock(ServicioPuntosJuego.class);


        when(servicioPartidaMock.crearPartida("Partida 1", 1, "Jugador 1", "rompecabezas")).thenReturn(partidaMock);
        when(partidaMock.getId()).thenReturn("12345");
        when(partidaMock.getEstado()).thenReturn("ESPERANDO_OPONENTE");
        when(servicioPartidaMock.getPartidasAbiertas()).thenReturn(partidaCollection);
        when(servicioPartidaMock.unirJugador("12345", 1, "Jugador 1", "rompecabezas")).thenReturn(partidaMock);


        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(1L);
        when(sessionMock.getAttribute("username")).thenReturn("Jugador 1");

        controladorPartida = new ControladorPartida(servicioPartidaMock, servicioRompecabezasMock, servicioNivelJuegoMock, servicioPuntosJuegoMock, servicioUsuarioMock);
        when(servicioRompecabezasMock.consultarRompecabeza(1L)).thenReturn(rompecabezaMock);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuarioMock);
        when(servicioNivelJuegoMock.guardarNivelJuego(usuarioMock, "Rompecabezas", 1)).thenReturn(nivelJuegoMock);

    }


    @Test
    public void siEntroAPartidaMultijugadorMeLlevaAlLobby(){

        givenExisteUnUsuario();

        ModelAndView mav = whenSeleccionoPartidaMultijugador();

        thenMeLlevaAlLobby(mav);

    }

    private void givenExisteUnUsuario() {
    }

    private ModelAndView whenSeleccionoPartidaMultijugador() {
        return controladorPartida.irAlLobby(requestMock);
    }

    private void thenMeLlevaAlLobby(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("lobbyRompecabezas"));
        assertThat(mav.getModel().get("partidas"), equalTo(partidaCollection));

    }

    @Test
    public void siNoExiteUsuarioSeLoLlevaALogin() {

        givenNoExisteUnUsuario();

        ModelAndView mav = whenEntroEnPartidaMultijugador();

        thenMeLlevaAlLogin(mav);

    }

    private void givenNoExisteUnUsuario() {
        when(sessionMock.getAttribute("id")).thenReturn(null);
    }

    private ModelAndView whenEntroEnPartidaMultijugador() {

        return controladorPartida.irAlLobby(requestMock);
    }

    private void thenMeLlevaAlLogin(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/login"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase("Inicie sesión para jugar"));
    }

    @Test
    public void alCrearUnaPartidaConUnNombreMeLlevaAEsta(){

        givenExisteUnUsuario();

        ModelAndView mav = whenCreoLaPartida();

        thenMeLlevaALaPartida(mav);
    }

    private ModelAndView whenCreoLaPartida() {
        return controladorPartida.crearPartida("Partida 1", requestMock);
    }

    private void thenMeLlevaALaPartida(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/partida/12345"));
    }

    @Test
    public void alUnirmeAUnaPartidaQueNoEstaLlenaMeLlevaAEsta(){

        givenExisteUnUsuario();
        givenExisteUnaPartidaAbierta();

        ModelAndView mav = whenMeUnoAUnaPartida();

        thenMeLlevaAEsaPartida(mav);

    }

    private void givenExisteUnaPartidaAbierta() {
    }

    private ModelAndView whenMeUnoAUnaPartida() {
        return controladorPartida.unirseAPartida("12345", requestMock);
    }

    private void thenMeLlevaAEsaPartida(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("rompecabezasMultijugador"));
    }

    @Test
    public void alUnirmeAUnaPartidaQueEstaTerminadaMeLlevaAlLobby(){

        givenExisteUnUsuario();
        givenExisteUnaPertidaTerminada();

        ModelAndView mav = whenMeUnoAUnaPartida();

        thenMeRedirigeAlLobby(mav);

    }

    private void givenExisteUnaPertidaTerminada() {
        when(partidaMock.getEstado()).thenReturn("TERMINADA");
    }

    private void thenMeRedirigeAlLobby(ModelAndView mav) {

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/rompecabezas/lobby"));
    }

    @Test
    public void alGanarUnaPartidaEstaFinalizaYMeGuardaPuntos(){

        givenExisteUnUsuario();
        givenExisteUnaPartida();

        whenFinalizaUnaPartida();

        thenMeGuardaPuntos();

    }

    private void givenExisteUnaPartida() {
    }

    private void whenFinalizaUnaPartida() {
        controladorPartida.finalizarPartida(requestMock);
    }

    private void thenMeGuardaPuntos() {
    }


}

