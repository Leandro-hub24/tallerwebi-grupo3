package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioNivelVersus;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioVersus;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Brainrot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorVersusTest {
    private ControladorVersus controlador;
    private ServicioVersus servicioVersusMock;
    private ServicioNivelVersus servicioNivelVersusMock;
    private ServicioUsuario servicioUsuarioMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioVersusMock = mock(ServicioVersus.class);
        servicioNivelVersusMock = mock(ServicioNivelVersus.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controlador = new ControladorVersus(
                servicioVersusMock,
                servicioNivelVersusMock,
                servicioUsuarioMock
        );
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
    }

    @Test
    public void testUsuarioNoLogueadoRedirigeALogin() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
        ModelAndView mav = controlador.mostrarVersus(1, requestMock);
        assertThat(mav.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void testUsuarioLogueadoMuestraVistaVersus() {
        Usuario usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getVersusNivel()).thenReturn(1);

        when(sessionMock.getAttribute("NIVEL_ACTUAL")).thenReturn(1);
        when(sessionMock.getAttribute("PREGUNTA_ACTUAL")).thenReturn(0);

        Brainrot brainrotMock = mock(Brainrot.class);
        when(brainrotMock.getImagenPersonaje()).thenReturn("personaje");
        when(brainrotMock.getImagenFondo()).thenReturn("fondo");
        when(brainrotMock.getImagenCompleta()).thenReturn("completa");
        when(brainrotMock.getAudio()).thenReturn("audio");
        when(brainrotMock.getImagenSombra()).thenReturn("sombra");
        List<Brainrot> brainrots = new ArrayList<>();
        brainrots.add(brainrotMock);
        when(sessionMock.getAttribute("BRAINROTS_NIVEL")).thenReturn(brainrots);

        when(servicioVersusMock.obtenerOpcionesAleatoriasParaPregunta(anyInt(), anyString()))
                .thenReturn(Arrays.asList("personaje", "otro1", "otro2", "otro3"));

        ModelAndView mav = controlador.mostrarVersus(1, requestMock);
        assertThat(mav.getViewName(), equalTo("versus"));
    }

    @Test
    public void testRedirigeSiNivelBloqueado() {
        Usuario usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getVersusNivel()).thenReturn(1);

        ModelAndView mav = controlador.mostrarVersus(2, requestMock);
        assertThat(mav.getViewName(), equalTo("redirect:/seleccionar-nivel"));
    }

    @Test
    public void testReiniciaProgresoAlCambiarDeNivel() {
        Usuario usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getVersusNivel()).thenReturn(2);

        when(sessionMock.getAttribute("NIVEL_ACTUAL")).thenReturn(1);
        when(sessionMock.getAttribute("PREGUNTA_ACTUAL")).thenReturn(3);
        when(sessionMock.getAttribute("PUNTOS_ACUMULADOS")).thenReturn(8);
        when(sessionMock.getAttribute("BRAINROTS_NIVEL")).thenReturn(null);

        Brainrot brainrotMock = mock(Brainrot.class);
        when(brainrotMock.getImagenPersonaje()).thenReturn("personaje");
        when(brainrotMock.getImagenFondo()).thenReturn("fondo");
        when(brainrotMock.getImagenCompleta()).thenReturn("completa");
        when(brainrotMock.getAudio()).thenReturn("audio");
        when(brainrotMock.getImagenSombra()).thenReturn("sombra");
        List<Brainrot> brainrots = new ArrayList<>();
        brainrots.add(brainrotMock);
        when(servicioVersusMock.obtenerTodosPorNivel(2)).thenReturn(brainrots);
        when(servicioVersusMock.obtenerOpcionesAleatoriasParaPregunta(anyInt(), anyString()))
                .thenReturn(Arrays.asList("personaje", "otro1", "otro2", "otro3"));

        ModelAndView mav = controlador.mostrarVersus(2, requestMock);
        assertThat(mav.getViewName(), equalTo("versus"));
        verify(sessionMock).setAttribute("PREGUNTA_ACTUAL", 0);
        verify(sessionMock).setAttribute("PUNTOS_ACUMULADOS", 0);

    }

    @Test
    public void testRedirigeAResumenSiTerminaPreguntas() {
        Usuario usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getVersusNivel()).thenReturn(1);

        when(sessionMock.getAttribute("NIVEL_ACTUAL")).thenReturn(1);
        Brainrot brainrotMock = mock(Brainrot.class);
        List<Brainrot> brainrots = new ArrayList<>();
        brainrots.add(brainrotMock);
        when(sessionMock.getAttribute("BRAINROTS_NIVEL")).thenReturn(brainrots);

        when(sessionMock.getAttribute("PREGUNTA_ACTUAL")).thenReturn(brainrots.size());

        ModelAndView mav = controlador.mostrarVersus(1, requestMock);
        assertThat(mav.getViewName(), equalTo("redirect:/resumen-nivel"));
    }

    @Test
    public void testRedirigeASeleccionarNivelSiNoHayBrainrots() {
        Usuario usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getVersusNivel()).thenReturn(1);

        when(sessionMock.getAttribute("NIVEL_ACTUAL")).thenReturn(1);
        when(sessionMock.getAttribute("PREGUNTA_ACTUAL")).thenReturn(0);
        when(sessionMock.getAttribute("BRAINROTS_NIVEL")).thenReturn(new ArrayList<>());

        ModelAndView mav = controlador.mostrarVersus(1, requestMock);
        assertThat(mav.getViewName(), equalTo("redirect:/seleccionar-nivel"));
    }
}