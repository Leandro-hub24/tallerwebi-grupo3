package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioAdivinanza;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class ControladorAdivinanzaPorVozTest {
    private ServicioAdivinanza servicioAdivinanzaMock;


    @Test
    public void queVerificaRespuestaCorrecta() {
        HttpSession session = mock(HttpSession.class);
        servicioAdivinanzaMock =  mock(ServicioAdivinanza.class);
        ControladorAdivinanzaVoz controlador = new ControladorAdivinanzaVoz(servicioAdivinanzaMock);
        // Configuración
        String nombreArchivo = "Tuntuntun Sahur";
        String textoReconocido = "Tuntuntun Sahur";

        // Ejecución
        ModelAndView mav = controlador.verificarPorVoz(textoReconocido, nombreArchivo, "alias", session);

        // Verificación
        assertThat(mav.getViewName(), equalTo("verificar2"));
   }
}
