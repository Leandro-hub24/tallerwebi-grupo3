package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ControladorVersusTest {

    private ControladorVersus controladorVersus;

    @BeforeEach
    public void init() {
        controladorVersus = new ControladorVersus();
    }

    @Test
    public void queMuestraLaPantallaDeVersus() {
        // Ejecución
        ModelAndView modelAndView = controladorVersus.mostrarVersus();

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("versus"));
        assertThat(modelAndView.getModel().get("imagen"), is(notNullValue()));

        List<?> opciones = (List<?>) modelAndView.getModel().get("opciones");
        assertThat(opciones, hasSize(4));

        // Verificar que la imagen existe en la ruta
        String rutaImagen = (String) modelAndView.getModel().get("imagen");
        assertThat(rutaImagen, startsWith("/img/versus/"));
        assertThat(rutaImagen, endsWith(".png"));
    }

    @Test
    public void queVerificaRespuestaCorrecta() {
        // Configuración
        String nombreArchivo = "Tralalero tralala";
        String respuestaCorrecta = "Tralalero tralala";
        String imagenActualCompleta = "tralalerocom";

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
        // Configuración
        String nombreArchivo = "Tralalero tralala";
        String respuestaIncorrecta = "Respuesta Incorrecta";
        String imagenActualCompleta = "tralalerocom";

        // Ejecución
        ModelAndView modelAndView = controladorVersus.verificarRespuesta(respuestaIncorrecta, imagenActualCompleta, nombreArchivo);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("resultado-versus"));
        assertThat(modelAndView.getModel().get("esCorrecto"), is(false));
    }
}