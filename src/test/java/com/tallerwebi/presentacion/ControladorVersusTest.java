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

//    @Test
//    public void queMuestraLaPantallaDeVersus() {
//        // Ejecución
//        ModelAndView modelAndView = controladorVersus.mostrarVersus();
//
//        // Verificación
//        assertThat(modelAndView.getViewName(), equalTo("versus"));
//        assertThat(modelAndView.getModel().get("imagen"), is(notNullValue()));
//
//        // Verificar que se devuelven 3 opciones
//        List<?> opciones = (List<?>) modelAndView.getModel().get("opciones");
//        assertThat(opciones, hasSize(3));
//
//        // Verificar que la imagen existe en la ruta
//        String rutaImagen = (String) modelAndView.getModel().get("imagen");
//        assertThat(rutaImagen, startsWith("/img/versus/"));
//        assertThat(rutaImagen, endsWith(".png"));
//    }

//    @Test
//    public void queVerificaRespuestaCorrecta() {
//        // Configuración
//        String nombreArchivo = "tuntuntunsahur";
//        String respuestaCorrecta = "Mago Musical";
//
//        // Ejecución
//        ModelAndView modelAndView = controladorVersus.verificarRespuesta(respuestaCorrecta, nombreArchivo);
//
//        // Verificación
//        assertThat(modelAndView.getViewName(), equalTo("resultado-versus"));
//        assertThat(modelAndView.getModel().get("esCorrecto"), is(true));
//        assertThat(modelAndView.getModel().get("respuestaCorrecta"), equalTo(respuestaCorrecta));
//        assertThat(modelAndView.getModel().get("imagen"), equalTo("/img/versus/" + nombreArchivo + ".png"));
//    }

    @Test
    public void queVerificaRespuestaIncorrecta() {
        // Configuración
        String nombreArchivo = "tuntuntunsahur";
        String respuestaIncorrecta = "Respuesta Incorrecta";

        // Ejecución
        ModelAndView modelAndView = controladorVersus.verificarRespuesta(respuestaIncorrecta, nombreArchivo);

        // Verificación
        assertThat(modelAndView.getViewName(), equalTo("resultado-versus"));
        assertThat(modelAndView.getModel().get("esCorrecto"), is(false));
    }
}