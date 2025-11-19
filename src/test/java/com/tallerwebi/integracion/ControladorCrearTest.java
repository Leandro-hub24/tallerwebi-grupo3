package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.ControladorCrear;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ControladorCrearTest {

    ServicioCrear servicioCrear = mock();
    ServicioImagen servicioImagen = mock();
    ControladorCrear controladorCrear = new ControladorCrear(servicioCrear, servicioImagen );
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpSession mockSession = mock(HttpSession.class);


    @Test
    void siEnviaMasDe4ImagenesMuestraError() throws NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePudoCrearBrainrotException, FaltaSeleccionarFondoParaCrearBrainrotException {
        givenExisteUsuario();
        List<Integer> imagenes = List.of(1, 2, 3, 4, 5);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ModelAndView mav = whenUsuarioEnvia5ImagenesYEstilo(imagenes, "monstruoso", "playa", redirectAttributes);
        verify(redirectAttributes).addFlashAttribute(eq("error"), anyString());
        thenMuestraError(mav);
    }

    private ModelAndView whenUsuarioEnvia5ImagenesYEstilo(List<Integer> imagenes, String estilo, String fondo, RedirectAttributes redirectAttributes) throws NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePudoCrearBrainrotException, FaltaSeleccionarFondoParaCrearBrainrotException {
        when(servicioCrear.crearBrainrot(estilo, imagenes, fondo)).thenThrow(new NoSePuedeCrearUnBrainrotConMasDe4ImagenesException("error"));
        return EnviaImagenesYEstilo(imagenes, estilo, fondo, redirectAttributes);
    }

    private ModelAndView EnviaImagenesYEstilo(List<Integer> imagenes, String estilo, String fondo, RedirectAttributes redirectAttributes) {
        when(mockRequest.getSession()).thenReturn(mockSession);
        ModelAndView mav = controladorCrear.crearBrainrot(estilo, imagenes, fondo, mockRequest, redirectAttributes);
        return mav;
    }

    private void thenMuestraError(ModelAndView mav) {
        assertEquals("redirect:/crear", mav.getViewName());
    }

    @Test
    void siEnvia2ImagenesYEstiloCreaBrainrot() throws NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, FaltaSeleccionarFondoParaCrearBrainrotException, NoSePudoCrearBrainrotException {
        givenExisteUsuario();
        List<Integer> imagenes = List.of(1, 2);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        BrainrotCreado brainrotCreado = new BrainrotCreado();
        when(servicioCrear.crearBrainrot(anyString(), anyList(), anyString()))
                .thenReturn(brainrotCreado);
        ModelAndView mav = EnviaImagenesYEstilo(imagenes, "monstruoso", "playa", redirectAttributes);
        thenCreaBrainrotYLoMuestra(mav, redirectAttributes);
    }

    private void thenCreaBrainrotYLoMuestra(ModelAndView mav, RedirectAttributes redirectAttributes) {
        assertEquals("redirect:/crear", mav.getViewName());
        verify(redirectAttributes).addFlashAttribute(eq("brainrotCreado"), any(BrainrotCreado.class));
    }


    @Test
    void alEntrarACrearBrainrotLogueadoDevuelveVistaCrear()  {
        givenExisteUsuario();
        ModelAndView mav = whenUsuarioEntraAcrear();
        assertEquals("crear", mav.getViewName());
    }

    private void givenExisteUsuario() {
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("id")).thenReturn(1L);
    }

    private ModelAndView whenUsuarioEntraAcrear() {
        return controladorCrear.irACrear(mockRequest);
    }

    private void thenVuelveALogin() {
    }
}