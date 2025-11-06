package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.PiezaNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServicioPartidaTest {

    private Partida partida1;
    private Partida partida2;
    private String idPartida2;
    private String idPartida1;
    private Integer UsuarioId;

    @Captor
    private ArgumentCaptor<Partida> partidaCaptor = ArgumentCaptor.forClass(Partida.class);

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private ServicioPartidaImpl servicioPartida;

    @BeforeEach
    public void init() {
        partida1 = new Partida();
        idPartida1 = UUID.randomUUID().toString().substring(0, 8);
        partida1.setEstado("ESPERANDO_OPONENTE");
        partida1.setFechaInicio(Instant.now());
        partida1.setJugador1Id(1);
        partida1.setJugador1Nombre("Jugador 1");
        partida1.setId(idPartida1);

        servicioPartida.setPartidasAbiertas(idPartida1, partida1);

        partida2 = new Partida();

        idPartida2 = UUID.randomUUID().toString().substring(0, 8);
        partida2.setEstado("EN_CURSO");
        partida2.setFechaInicio(Instant.now());
        partida2.setJugador1Id(1);
        partida2.setJugador2Id(2);
        partida2.setJugador1Nombre("Jugador 1");
        partida2.setJugador2Nombre("Jugador 2");
        partida2.setId(idPartida2);

        servicioPartida.setPartidasEnCurso(idPartida2, partida2);

    }

    @Test
    public void creoUnaPartida() {

        givenTengoUnNombrePartidaYIdDelCreadorDeEsta();

        Partida partida = whenCreoUnaPartida("Partida 1", 1, "Leandro");

        thenDevuelvoLaPartidaCreada(partida);

    }

    private void givenTengoUnNombrePartidaYIdDelCreadorDeEsta() {
    }

    private Partida whenCreoUnaPartida(String nombrePartida, Integer usuarioId, String username) {
        return servicioPartida.crearPartida(nombrePartida, usuarioId, username);
    }

    private void thenDevuelvoLaPartidaCreada(Partida partida) {
        verify(simpMessagingTemplate).convertAndSend("/topic/lobby/nueva", partida);
        assertThat(partida.getNombre(), equalTo("Partida 1"));

    }

    @Test
    public void terminarUnaPartida() {

        givenTengoUnaPartidaEnCursoQueEstaPorTerminar();

        Partida partida = whenTerminoLaPartida();

        thenDevuelvoLaPartidaFinalizada(partida);
    }

    private void givenTengoUnaPartidaEnCursoQueEstaPorTerminar() {
    }

    private Partida whenTerminoLaPartida() {
        servicioPartida.terminarPartida(idPartida2, 1);
        verify(simpMessagingTemplate, times(1)).convertAndSend(
                eq("/topic/partida/" + idPartida2),
                partidaCaptor.capture()
        );
        return partidaCaptor.getValue();
    }

    private void thenDevuelvoLaPartidaFinalizada(Partida partida) {

        assertThat(partida.getEstado(), equalTo("TERMINADA"));
        assertThat(partida.getGanador(), equalTo(1));

    }

    @Test
    public void alUnirmeAUnaPartidaSiEstaNoExisteLanzarPartidaNoEncontradaException() {

        givenNoTengoUnaPartidaCreada();



        assertThrows(
                PartidaNoEncontradaException.class,
                ()  -> whenIntentoUnirmeALaPartida("sajfha", 1, "Jugador 1")
        );

    }

    private void givenNoTengoUnaPartidaCreada() {
    }

    private Partida whenIntentoUnirmeALaPartida(String idPartida, Integer idUsuario, String nombreUsuario) throws PartidaNoEncontradaException, PartidaLlenaException {

        return servicioPartida.unirJugador(idPartida, idUsuario, nombreUsuario);

    }

    @Test
    public void siAlCrearUnaPartidaYLuegoTratoDeUnirmeMeRetornLaPartida() throws PartidaNoEncontradaException, PartidaLlenaException {

        givenTengoUnaPartidaCreada();

        Partida partida = whenIntentoUnirmeALaPartida(idPartida1, 1, "Jugador 1");

        thenDevuelveLaPartida(partida, "Jugador 1");
    }

    private void givenTengoUnaPartidaCreada() {
    }

    private void thenDevuelveLaPartida(Partida partida, String nombreUsuario){

        assertThat(partida.getJugador1Nombre(), equalTo(nombreUsuario));
        assertThat(partida.getId(), equalTo(idPartida1));

    }


    @Test
    public void alUnirmeAUnaPartidaCreadaPorOtroJugadorMeRetornaLaPartida() throws PartidaNoEncontradaException, PartidaLlenaException {

        givenExiteUnaPartidaCreadaPorOtroJugador();

        Partida partida = whenIntentoUnirmeALaPartida(idPartida1, 2, "Jugador 2");

        thenDevuelveLaPartidaALaQueMeUni(partida, "Jugador 2");
    }

    private void givenExiteUnaPartidaCreadaPorOtroJugador() {
    }

    private void thenDevuelveLaPartidaALaQueMeUni(Partida partida, String nombreUsuario){

        verify(simpMessagingTemplate, times(1)).convertAndSend(
                eq("/topic/partida/" + idPartida1),
                partidaCaptor.capture()
        );
        partidaCaptor.getValue();

        assertThat(partidaCaptor.getValue().getEstado(), equalTo("EN_CURSO"));
        assertThat(partidaCaptor.getValue().getJugador2Nombre(), equalTo(nombreUsuario));
        assertThat(partidaCaptor.getValue().getId(), equalTo(idPartida1));

        assertThat(partida.getJugador2Nombre(), equalTo(nombreUsuario));
        assertThat(partida.getId(), equalTo(idPartida1));
        assertThat(partida.getEstado(), equalTo("EN_CURSO"));

    }

    @Test
    public void siIntentoUnirmeAUnaPartidaLlenaMeLanzaPartidaLlenaException() throws PartidaNoEncontradaException, PartidaLlenaException {

        givenExiteUnaPartidaLlena();

        assertThrows(
                PartidaLlenaException.class,
                ()  -> whenIntentoUnirmeALaPartida(idPartida2, 3, "Jugador 3")
        );

    }

    private void givenExiteUnaPartidaLlena() {
    }

    /*@Test
    public void  siEntroEnUnaPartidaEnCursoDeLaCualSoyParteMeRetornaLaPartida() throws PartidaNoEncontradaException, PartidaLlenaException {

        givenExisteUnaPartidaEnCursoDeLaCualSoyParte();



    }*/

}
