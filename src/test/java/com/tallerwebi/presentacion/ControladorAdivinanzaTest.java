package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorAdivinanzaTest {
    ControladorAdivinanza controlador = new ControladorAdivinanza();
    @Test
    public void debeRetornarVistaOpcionCorrecta() {
        // Preparación

        givenHayControladores();

        // Ejecución
        ModelAndView mav = whenElUsuarioElijeOpcionCorrecta();

        // Verificación

        thenLoLLevaALaVistaCorrecta(mav,"Opcion-Correcta");
    }

    @Test
    public void debeRetornarVistaOpcionIncorrecta() {
        // Preparación

        givenHayControladores();

        // Ejecución
        ModelAndView mav = whenElUsuarioElijeOpcionIncorrecta();

        // Verificación

        thenLoLLevaALaVistaCorrecta(mav, "Opcion-Incorrecta");
    }

    private void thenLoLLevaALaVistaCorrecta(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase(mensaje));
    }


    private ModelAndView whenElUsuarioElijeOpcionIncorrecta() {
        return controlador.irAOpcionIncorrecta();
    }



    private ModelAndView whenElUsuarioElijeOpcionCorrecta() {

        return controlador.irAOpcionCorrecta();
    }

    private void givenHayControladores() {

    }



}
