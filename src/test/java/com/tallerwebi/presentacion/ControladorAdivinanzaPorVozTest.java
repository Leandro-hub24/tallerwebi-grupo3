package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioAdivinanza;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorAdivinanzaPorVozTest {
    private ControladorAdivinanzaVoz controlador;
    private ServicioAdivinanza servicioAdivinanzaMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    private String imagenActual;
    private String transcripcionCorrecta;
    private String aliasDetectado;
    private ModelAndView mav;

    @BeforeEach
    void init() {
        servicioAdivinanzaMock = mock(ServicioAdivinanza.class);
        controlador = new ControladorAdivinanzaVoz(servicioAdivinanzaMock);

        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);

        imagenActual = "Tung tung tung Sahur"; // Clave del mapa
        transcripcionCorrecta = "Tuntuntun Sahur"; // Valor del mapa
        aliasDetectado = "aliasDetectado";
    }

    @Test
    void queVerificaRespuestaCorrectaLoLlevaAVerificar2() {
        givenHayDosStringsParaComparar();
        whenSeComparanLosStrings();
        thenSeLoLlevaALaDireccionCorrecta();
    }

    private void givenHayDosStringsParaComparar() {
        // Usuario en sesión y 0 intentos previos
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("intentosFallidos")).thenReturn(0);
    }

    private void whenSeComparanLosStrings() {
        mav = controlador.verificarPorVoz(transcripcionCorrecta, imagenActual, aliasDetectado, sessionMock);
    }

    private void thenSeLoLlevaALaDireccionCorrecta() {
        assertThat(mav.getViewName(), equalTo("verificar2"));
        assertThat(mav.getModel().get("esCorrecto"), equalTo(true));
        assertThat(mav.getModel().get("respuestaCorrecta"), equalTo("Tuntuntun Sahur"));
        assertThat(mav.getModel().get("transcripcion"), equalTo(transcripcionCorrecta));
        assertThat(mav.getModel().get("imagen"), equalTo("/img/versus/" + imagenActual + ".png"));
        assertThat(mav.getModel().get("imagenActual"), equalTo(imagenActual));
        assertThat(mav.getModel().get("aliasDetectado"), equalTo(aliasDetectado));

        // Verifica que se llamó a opcionCorrecta y se reseteó el contador
        verify(servicioAdivinanzaMock).opcionCorrecta(usuarioMock);
        verify(sessionMock).setAttribute("intentosFallidos", 0);
    }
}
