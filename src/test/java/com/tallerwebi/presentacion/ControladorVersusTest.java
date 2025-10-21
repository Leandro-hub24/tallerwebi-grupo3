package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Brainrot;
import com.tallerwebi.dominio.ServicioVersus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorVersusTest {

    private ControladorVersus controladorVersus;
    private ServicioVersus servicioVersusMock;

    @BeforeEach
    public void init() {
        servicioVersusMock = mock(ServicioVersus.class);
        controladorVersus = new ControladorVersus(servicioVersusMock);
    }

    @Test
    public void queMuestraLaPantallaDeVersus() {
        // Preparación
        Brainrot brainrotMock = new Brainrot("tralalerobg", "Tralalero tralala", "tralalerosound", "tralaleroshadow", "tralalerocom");
        List<String> opcionesMock = Arrays.asList("Tralalero tralala", "Tung tung tung Sahur", "Chimpancini bananini", "Brr brr patapi");
        
        when(servicioVersusMock.obtenerBrainrotAleatorio()).thenReturn(brainrotMock);
        when(servicioVersusMock.obtenerOpcionesAleatorias()).thenReturn(opcionesMock);

        // Ejecución
        ModelAndView modelAndView = controladorVersus.mostrarVersus();

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("versus"));
        assertThat(modelAndView.getModel().get("imagen"), is(notNullValue()));

        List<?> opciones = (List<?>) modelAndView.getModel().get("opciones");
        assertThat(opciones, hasSize(4));

        // Verificar que se llamaron los métodos del servicio
        verify(servicioVersusMock).obtenerBrainrotAleatorio();
        verify(servicioVersusMock).obtenerOpcionesAleatorias();
    }

    @Test
    public void queVerificaRespuestaCorrecta() {
        // Preparacion
        String nombreArchivo = "Tralalero tralala";
        String respuestaCorrecta = "Tralalero tralala";
        String imagenActualCompleta = "tralalerocom";
        
        when(servicioVersusMock.verificarRespuesta(respuestaCorrecta, nombreArchivo)).thenReturn(true);

        // Ejecución
        ModelAndView modelAndView = controladorVersus.verificarRespuesta(respuestaCorrecta, imagenActualCompleta, nombreArchivo);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("resultado-versus"));
        assertThat(modelAndView.getModel().get("esCorrecto"), is(true));
        assertThat(modelAndView.getModel().get("respuestaCorrecta"), equalTo(respuestaCorrecta));
        assertThat(modelAndView.getModel().get("imagen"), equalTo("/img/versus/" + imagenActualCompleta + ".png"));
    }

    @Test
    public void queVerificaRespuestaIncorrecta() {
        // Preparacion
        String nombreArchivo = "Tralalero tralala";
        String respuestaIncorrecta = "Respuesta Incorrecta";
        String imagenActualCompleta = "tralalerocom";
        
        when(servicioVersusMock.verificarRespuesta(respuestaIncorrecta, nombreArchivo)).thenReturn(false);

        // Ejecución
        ModelAndView modelAndView = controladorVersus.verificarRespuesta(respuestaIncorrecta, imagenActualCompleta, nombreArchivo);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("resultado-versus"));
        assertThat(modelAndView.getModel().get("esCorrecto"), is(false));
    }
}