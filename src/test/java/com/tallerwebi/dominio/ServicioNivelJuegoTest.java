package com.tallerwebi.dominio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioNivelJuegoTest {

    private RepositorioNivelJuego repositorioNivelJuegoMock;
    private NivelJuego nivelJuegoMock;
    private NivelJuego nivelJuego;
    private ServicioNivelJuego servicioNivelJuego;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {

        repositorioNivelJuegoMock = mock(RepositorioNivelJuego.class);
        nivelJuegoMock = mock(NivelJuego.class);
        nivelJuego = new NivelJuego();
        nivelJuego.setNivel(1L);
        nivelJuego.setUsuario(usuarioMock);
        nivelJuego.setNombre("Rompecabezas");
        usuarioMock = mock(Usuario.class);
        servicioNivelJuego = new ServicioNivelJuegoImpl(repositorioNivelJuegoMock);
        when(repositorioNivelJuegoMock.buscarNivelJuegoPorIdUsuario(1L, "Rompecabeza")).thenReturn(nivelJuegoMock);
        when(repositorioNivelJuegoMock.modificarNivelJuego(1L)).thenReturn(3L);
        when(repositorioNivelJuegoMock.guardarNivelJuego(any(NivelJuego.class))).thenReturn(nivelJuego);
        when(nivelJuegoMock.getNivel()).thenReturn(2L);
    }

    @Test
    public void buscoNivelJuegoConIdUsuarioYObtengoUnNivelJuego() {

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
    public void buscoNivelJuegoConIdUsuarioYObtengoUnNivelJuegoNull(){

        givenTengoUnUsuario();

        NivelJuego nivelJuegoObtenido = whenBuscoUnNivelJuego(1L);

        thenObtengoUnNivelJuego(0L, nivelJuegoObtenido);

    }

    private NivelJuego whenBuscoUnNivelJuego(long usuarioId) {

        when(repositorioNivelJuegoMock.buscarNivelJuegoPorIdUsuario(1L, "Rompecabezas")).thenReturn(null);
        return servicioNivelJuego.buscarNivelJuegoPorIdUsuario(usuarioId, "Rompecabezas");
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

    @Test
    public void alGuardarUnNivelJuegoRetornoElNivelJuegoGuardado(){

        givenTengoUnUsuario();

        NivelJuego nivelJuegoObtenido = whenGuardoUnNivelJuego();

        thenReciboUnNivelJuego(nivelJuegoObtenido, nivelJuego);

    }

    private NivelJuego whenGuardoUnNivelJuego() {
        return servicioNivelJuego.guardarNivelJuego(usuarioMock, "Rompecabezas", 1);
    }

    private void thenReciboUnNivelJuego(NivelJuego nivelJuegoObtenido, NivelJuego nivelJuegoEsperado) {

        assertThat(nivelJuegoObtenido, equalTo(nivelJuegoEsperado));

    }
}
