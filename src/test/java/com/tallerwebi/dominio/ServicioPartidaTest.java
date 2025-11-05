package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicioPartidaTest {

    private ArgumentCaptor<Partida> partidaCaptor = ArgumentCaptor.forClass(Partida.class);

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private ServicioPartidaImpl servicioPartida;

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

}
