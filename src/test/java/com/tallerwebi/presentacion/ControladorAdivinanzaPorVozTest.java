package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioAdivinanza;
import com.tallerwebi.dominio.ServicioAdivinanzaImpl;
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
    private String nombreArchivo;
    private String textoReconocido;

    @BeforeEach
    void init() {
        servicioAdivinanzaMock = mock(ServicioAdivinanza.class);
        controlador = new ControladorAdivinanzaVoz(servicioAdivinanzaMock); // ✅ usás el que sí existe

        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
        nombreArchivo = "Tung tung tung Sahur";
        textoReconocido = "Respuesta equivocada";

        imagenActual = "Tung tung tung Sahur";
        transcripcionCorrecta = "Tuntuntun Sahur";
        aliasDetectado = "aliasDetectado";
    }

    @Test
    void queVerificaRespuestaCorrectaLoLlevaAVerificar2() {
        givenHayDosStringsParaComparar();
        whenSeComparanLosStrings();
        thenSeLoLlevaALaDireccionCorrecta("verificar2");
    }
    @Test
    void siSePideElGetYNoExisteElUsuarioQueLoMandeALogin() {
        givenNoexisteUsuario();
        whenSeVerificaSIHayUnUsuario();
        thenSeLoLlevaALaDireccionCorrecta("redirect:/login");
    }
    @Test
    void siExisteUsuarioEnSesionDebeMostrarVistaDeAdivinanzaDeVoz() {
        givenExisteUnUsuarioEnSesion();
        whenSeInvocaElGet();
        thenSeLoLlevaALaDireccionCorrecta("adivinanza-por-voz");
    }
    @Test
    void siSeLlegaATresIntentosFallidosDebeIrAVerificar2() {
        givenUsuarioMockEnSesionConDosFallosPrevios();
        whenSeVerificaLaRespuestaIncorrecta();
        thenSeLoLlevaALaDireccionCorrecta("verificar2");
        thenSeReiniciaIntentosFallidos();
    }

//    @Test
//    void SiElTemporizadorLlevaA0SeLoLlevaAVerificar2() {
//        givenTemporizadorLlegaACero();
//        whenSeVerificaSiElTemporizadorLlegoA0();
//        thenSeLoLlevaALaDireccionCorrecta("verificar2");
//
//    }

    private void whenSeVerificaSiElTemporizadorLlegoA0() {
    }

    private void givenTemporizadorLlegaACero() {
    }


    private void givenUsuarioMockEnSesionConDosFallosPrevios() {
        usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("intentosFallidos")).thenReturn(2);
    }
    private void whenSeVerificaLaRespuestaIncorrecta() {

        mav = controlador.verificarPorVoz(textoReconocido, nombreArchivo, "aliasDetectado", sessionMock);
    }
    private void thenSeReiniciaIntentosFallidos() {
        verify(sessionMock).setAttribute("intentosFallidos", 0);
    }

    private void givenExisteUnUsuarioEnSesion() {
        Usuario usuarioMock = mock(Usuario.class);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    }

    private void whenSeInvocaElGet() {
        mav = controlador.mostrarVoz(null, sessionMock);
    }


    private void whenSeVerificaSIHayUnUsuario() {
        mav = controlador.mostrarVoz(null, sessionMock);
    }

    private void givenNoexisteUsuario() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
    }


    private void givenHayDosStringsParaComparar() {
        // Usuario en sesión y 0 intentos previos
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(sessionMock.getAttribute("intentosFallidos")).thenReturn(0);

    }

    private void whenSeComparanLosStrings() {
        mav = controlador.verificarPorVoz(transcripcionCorrecta, imagenActual, aliasDetectado, sessionMock);
    }

    private void thenSeLoLlevaALaDireccionCorrecta(String direccion) {
        assertThat(mav.getViewName(), equalTo(direccion));

    }
}
