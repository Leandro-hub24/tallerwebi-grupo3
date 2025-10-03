package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ControladorAdivinanzaPorVozTest {

    @Test
    public void queVerificaRespuestaCorrecta() {
        ControladorAdivinanzaVoz controlador = new ControladorAdivinanzaVoz();
        // Configuración
        String nombreArchivo = "Tuntuntun Sahur";
        String textoReconocido = "Tuntuntun Sahur";

        // Ejecución
        ModelAndView mav = controlador.verificarPorVoz(textoReconocido, nombreArchivo, "alias");

        // Verificación
        assertThat(mav.getViewName(), equalTo("verificar2"));
   }
}
