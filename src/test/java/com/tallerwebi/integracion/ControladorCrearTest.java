package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioImagen;
import com.tallerwebi.dominio.ServicioImagenImpl;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.ControladorCrear;
import com.tallerwebi.dominio.ServicioCrear;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ControladorCrearTest {

    ServicioCrear servicioCrear = mock();
    ServicioImagen servicioImagen = mock();
    ControladorCrear controladorCrear = new ControladorCrear(servicioCrear, servicioImagen );


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

    private ModelAndView EnviaImagenesYEstilo(List<Integer> imagenes, String estilo, String fondo, RedirectAttributes redirectAttributes) throws FaltaSeleccionarImagenParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarEstiloParaCrearBrainrotException {
        ModelAndView mav = controladorCrear.crearBrainrot(estilo, imagenes, fondo, redirectAttributes);
        return mav;
    }

    private void thenMuestraError(ModelAndView mav) {
        assertEquals("redirect:/crear", mav.getViewName());
        assertTrue(mav.getModel().containsKey("error"));
    }

    @Test
    void siEnvia2ImagenesYEstiloCreaBrainrot() throws NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException {
        givenExisteUsuario();
        List<Integer> imagenes = List.of(1, 2, 3, 4, 5);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ModelAndView mav = EnviaImagenesYEstilo(imagenes, "monstruoso", "playa", redirectAttributes);
        thenCreaBrainrotYLoMuestra(mav);
    }

    private void thenCreaBrainrotYLoMuestra(ModelAndView mav) {
        assertEquals("redirect:/crear", mav.getViewName());
        assertTrue(mav.getModel().containsKey("brainrotCreado"));
    }


    @Test
    void alEntrarACrearBrainrotLogueadoDevuelveVistaCrear()  {
        givenExisteUsuario();
        whenUsuarioEntraAcrear();
        thenVuelveALogin();
    }

    private void givenExisteUsuario() {
        Usuario usuario = new Usuario();
    }

    private void whenUsuarioEntraAcrear() {

    }

    private void thenVuelveALogin() {
    }
}
