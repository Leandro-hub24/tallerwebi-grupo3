package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.ServicioRompecabezas;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorRompecabezasTest {

    /*
    * al entrar un usuario al tocar en el boton comenzar lo llevaria al juego
    * al tocar seleccionar nivel lo llevaria a una vista de niveles
    * si existe un usuario se lo lleva a rompecabezas
    * si no existe un usuario se lo lleva a login
    * */
    private ArrayList <Rompecabeza> rompecabezasMock;
    private Rompecabeza rompecabezaMock;
    private ServicioRompecabezas servicioRompecabezasMock;
    private ControladorRompecabezas controladorRompecabezas;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){

        rompecabezaMock = mock(Rompecabeza.class);
        rompecabezasMock = new ArrayList<>();
        rompecabezasMock.add(rompecabezaMock);
        servicioRompecabezasMock = mock(ServicioRompecabezas.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        controladorRompecabezas = new ControladorRompecabezas(servicioRompecabezasMock);
        when(servicioRompecabezasMock.consultarRompecabeza(1L)).thenReturn(rompecabezaMock);
        when(servicioRompecabezasMock.consultarRompecabezasDelUsuario(1L)).thenReturn(rompecabezasMock);
    }

    @Test
    public void usuarioTocaComenzarLoLlevaAlNivelConIdUno() {

        // preparacion --> given
        givenExisteUsuario();

        // ejecucion --> when
        ModelAndView modelAndView = whenUsuarioTocaComenzar(1L);

        //comprobacion --> then
        thenElUsuarioEsLlevadoAlJuego(modelAndView);

    }

    @Test
    public void usuarioEntraYTocaSeleccionarNivelesYLoLlevaAUnaVistaDeNiveles() {

        // preparacion --> given
        givenExisteUsuario();

        // ejecucion --> when
        ModelAndView modelAndView = whenUsuarioTocaSeleccionarNiveles();

        //comprobacion --> then
        thenElUsuarioEsLlevadoAUnaVistaDeNiveles(modelAndView);

    }

    @Test
    public void siNoHayUsuarioSeLoLlevaALogin(){
        // preparacion --> given
        givenNoHayUsuario();

        // ejecucion --> when
        ModelAndView modelAndView = whenVerificoSiExisteUsuario();

        //comprobacion --> then
        thenSeLoLlevaALogin(modelAndView);
    }

    private void givenNoHayUsuario() {

    }

    private ModelAndView whenVerificoSiExisteUsuario() {
        when(sessionMock.getAttribute("id")).thenReturn(null);
        return controladorRompecabezas.irARompecabezasMain(requestMock);

    }

    private void thenSeLoLlevaALogin( ModelAndView modelAndView) {

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Inicie sesión para jugar"));

    }

    private ModelAndView whenUsuarioTocaSeleccionarNiveles() {

        return controladorRompecabezas.irARompecabezasNiveles(requestMock);

    }

    private void thenElUsuarioEsLlevadoAUnaVistaDeNiveles( ModelAndView modelAndView) {

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("rompecabezas"));
        assertThat(modelAndView.getModel().get("niveles").toString(), equalToIgnoringCase(rompecabezasMock.toString()) );

    }

    private void givenExisteUsuario() {

    }

    private ModelAndView whenUsuarioTocaComenzar(Long id) {

        return controladorRompecabezas.irARompecabezasJuego(id, requestMock);

    }

    private void thenElUsuarioEsLlevadoAlJuego(ModelAndView modelAndView) {

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("rompecabezas"));
        assertThat(modelAndView.getModel().get("nivel").toString(), equalToIgnoringCase(rompecabezaMock.toString()) );

    }



}
