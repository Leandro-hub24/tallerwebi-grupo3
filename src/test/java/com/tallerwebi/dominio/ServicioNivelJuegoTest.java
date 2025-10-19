package com.tallerwebi.dominio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioNivelJuegoTest {

    private RepositorioNivelJuego repositorioNivelJuegoMock;
    private NivelJuego nivelJuegoMock;
    private ServicioNivelJuego servicioNivelJuego;

    @BeforeEach
    public void init() {

        repositorioNivelJuegoMock = mock(RepositorioNivelJuego.class);
        nivelJuegoMock = mock(NivelJuego.class);
        servicioNivelJuego = new ServicioNivelJuegoImpl(repositorioNivelJuegoMock);
        when(repositorioNivelJuegoMock.buscarNivelJuegoPorIdUsuario(1L, "Rompecabeza")).thenReturn(nivelJuegoMock);
        when(repositorioNivelJuegoMock.modificarNivelJuego(1L)).thenReturn(3L);
        when(nivelJuegoMock.getNivel()).thenReturn(2L);
    }

    @Test
    public void buscoNivelJuegoConIdUsuario(){

        givenTengoUnUsuario();

        NivelJuego nivelJuegoObtenido = whenBuscoNivelJuego(1L);

        thenObtengoUnNivelJuego(2L, nivelJuegoObtenido);

    }

    private void givenTengoUnUsuario() {
    }

    private NivelJuego whenBuscoNivelJuego(Long usuarioId) {

        return servicioNivelJuego.buscarNivelJuegoPorIdUsuario(usuarioId, "Rompecabeza");

    }

    private void thenObtengoUnNivelJuego(Long nivelJuegoEsperado, NivelJuego nivelJuegoObtenido) {

        assertThat(nivelJuegoEsperado, equalTo(nivelJuegoObtenido.getNivel()));

    }

    @Test
    public void modificoNivelJuegoConIdUsuarioSiNivelRompecabezaEsIgualANivelJuegoYSiNivelNuevoEsMenorOIgualAUltimoNivel(){

        givenTengoUnUsuario();

        Integer nivelJuegoObtenido = whenModificoNivelJuego(1L, 2, 2, 4L);

        thenObtengoUnNivelJuegoNuevo(3, nivelJuegoObtenido);

    }

    private Integer whenModificoNivelJuego(Long usuarioId, Integer idRompezabeza, Integer nivelActualUsuario, Long ultimoNivel) {

        return servicioNivelJuego.actualizarNivelJuego(usuarioId,  idRompezabeza, nivelActualUsuario, ultimoNivel);

    }

    private void thenObtengoUnNivelJuegoNuevo(Integer nivelNuevoEsperado, Integer nivelJuegoObtenido) {

    assertThat(nivelNuevoEsperado, equalTo(nivelJuegoObtenido));

    }

    @Test
    public void retornoNullSiNivelRompecabezaEsIgualANivelJuegoPeroSuNivelNuevoEsMayorAUltimoNivel(){

        givenTengoUnUsuario();

        Integer nivelJuegoObtenido = whenModificoNivelJuego(1L, 2, 2, 2L);

        thenObtengoUnNivelJuegoNuevo(null, nivelJuegoObtenido);

    }

    @Test
    public void retornoNullSiNivelRompecabezaEsDistintoANivelJuego(){

        givenTengoUnUsuario();

        Integer nivelJuegoObtenido = whenModificoNivelJuego(1L, 1, 2, 2L);

        thenObtengoUnNivelJuegoNuevo(null, nivelJuegoObtenido);

    }
}
