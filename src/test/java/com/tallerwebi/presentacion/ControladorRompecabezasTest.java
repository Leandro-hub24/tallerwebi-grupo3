package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.RompecabezasRequest;
import com.tallerwebi.dominio.ServicioRompecabezas;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import com.tallerwebi.dominio.excepcion.PiezaNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorRompecabezasTest {

    /*
    * al tocar seleccionar nivel lo llevaria a una vista de niveles
    * si existe un usuario se lo lleva a rompecabezas + id
    * si no existe un usuario se lo lleva a login
    * si se ingresa un id de rompecabeza que el usuario aun no haya jugado se lo lleva al ultimos nivel jugado
    * si existe un usuario y existe un nivel valido de lo lleva a la vista rompecabezas y se envia un model con un Rompecabeza
    *
    * */
    private ArrayList <Rompecabeza> rompecabezasMock;
    private Rompecabeza rompecabezaMock;
    private ServicioRompecabezas servicioRompecabezasMock;
    private ControladorRompecabezas controladorRompecabezas;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private List<List<List<String>>> matriz;
    private RompecabezasRequest requestRompecabezaMock;

    @BeforeEach
    public void init() {

        matriz = new ArrayList<>();
        requestRompecabezaMock = mock(RompecabezasRequest.class);
        rompecabezaMock = mock(Rompecabeza.class);
        rompecabezasMock = new ArrayList<>();
        rompecabezasMock.add(rompecabezaMock);
        servicioRompecabezasMock = mock(ServicioRompecabezas.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        for (int i = 0; i < 3; i++) {
            List<List<String>> fila = new ArrayList<>();
            for (int j = 0; j < 3; j++) {

                String idPieza = "img_" + i + "-" + j;
                String img = "imagen";
                List<String> pieza = Arrays.asList(idPieza, img);
                fila.add(pieza);
            }
            matriz.add(fila);
        }

        when(requestRompecabezaMock.getIdRompecabeza()).thenReturn(2);
        when(requestRompecabezaMock.getIdPieza()).thenReturn("img_1-1");
        when(servicioRompecabezasMock.moverPieza(matriz, "img_1-1")).thenReturn(matriz);
        when(servicioRompecabezasMock.actualizarNivelEnUsuario(1L, 2, 2)).thenReturn(3);
        when(servicioRompecabezasMock.comprobarVictoria(any())).thenReturn(true);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(1L);
        controladorRompecabezas = new ControladorRompecabezas(servicioRompecabezasMock);
        when(servicioRompecabezasMock.consultarRompecabeza(1L)).thenReturn(rompecabezaMock);
        when(servicioRompecabezasMock.consultarRompecabezasDelUsuario(1)).thenReturn(rompecabezasMock);
    }

    @Test
    public void usuarioEntraYTocaSeleccionarNivelesYLoLlevaAUnaVistaDeNiveles() {

        // preparacion --> given
        givenExisteUsuario();

        // ejecucion --> when
        ModelMap model = whenUsuarioTocaSeleccionarNiveles();

        //comprobacion --> then
        thenElUsuarioEsLlevadoAUnaVistaDeNiveles(model);

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

    @Test
    public void siHayUsuarioSeLoLlevaARompecabezasMasId(){
        // preparacion --> given
        givenHayUsuario();

        // ejecucion --> when
        ModelAndView modelAndView = whenIngresoAVistaRompecabezaConUsuario();

        //comprobacion --> then
        thenSeLoLlevaAVistaRompecabezaDelUltimoRompecabezaJugado(modelAndView);
    }

    @Test
    public void siExisteUnUsuarioIdUnoYExisteUnNivelUnoValidoSeLoLlevaALaVistaRompecabezasYSeEnviaUnModelConUnRompecabeza(){

        givenExisteUsuario();

        ModelAndView modelAndView = whenComprobarUsuarioYNivelValido(1L, requestMock);

        thenSeLoLlevaAlNivelUno(modelAndView);
    }

    @Test
    public void siExisteUnUsuarioIdUnoYExisteUnNivelTresNoValidoSeLoLlevaALaVistaRompecabezasYSeEnviaUnModelConUnRompecabeza(){

        givenExisteUsuario();

        ModelAndView model = whenComprobarUsuarioYNivelValido(3L, requestMock);

        thenSeLoLlevaAlUltimoRompecabezaJugado(model);

    }

    @Test
    public void SiAlMoverUnaFichaYComprobarVictoriaConUnaMatrizResueltaDevuelveTrueSeActualizaRompecabezaNivelDeLaSesionYRetornaElNivelNuevoDistintoDeNullYElMensajeVictoria(){

    givenExisteUnaMatrizResuelta();

    ModelMap model = whenCompruebaVictoriaDevuelveTrueYUnNivelNuevo();

    thenRetornaUnModelMapConElNivelNuevoYElMensajeVictoria(model, 3, "Victoria");

    }

    @Test
    public void SiAlMoverUnaFichaYComprobarVictoriaConUnaMatrizResueltaDevuelveTrueSeActualizaRompecabezaNivelDeLaSesionPeroElUnNivelNuevoEsNullRetornaElMensajeVictoriaYElIdRompecabezaMasUno(){

        givenExisteUnaMatrizResueltaPeroNivelNuevoNull();

        ModelMap model = whenCompruebaVictoriaDevuelveTrueYUnNivelNuevo();

        thenRetornaUnModelMapConElNivelNuevoYElMensajeVictoria(model, 3, "Victoria");

    }

    @Test
    public void SiAlMoverUnaFichaYComprobarVictoriaConUnaMatrizNoResueltaDevuelveFalseRetornaElMensajeNoResuelto(){

        givenExisteUnaMatrizNoResuelta();

        ModelMap model = whenCompruebaVictoriaDevuelveFalse();

        thenRetornaUnModelMapConElMensajeNoResuelto(model);

    }

    private void givenExisteUnaMatrizNoResuelta() {
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(2);
    }

    private ModelMap whenCompruebaVictoriaDevuelveFalse() {
        when(servicioRompecabezasMock.comprobarVictoria(any())).thenReturn(false);
        return controladorRompecabezas.moverFichas(requestRompecabezaMock, requestMock);
    }

    private void thenRetornaUnModelMapConElMensajeNoResuelto(ModelMap model) {
        assertThat(model.get("mensaje").toString(), equalToIgnoringCase("No resuelto"));
    }

    private void givenExisteUnaMatrizResueltaPeroNivelNuevoNull() {
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(2);
        when(servicioRompecabezasMock.actualizarNivelEnUsuario(1L, 2, 2)).thenReturn(null);
    }

    private void givenExisteUnaMatrizResuelta() {
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(2);
    }

    private ModelMap whenCompruebaVictoriaDevuelveTrueYUnNivelNuevo() {
        return controladorRompecabezas.moverFichas(requestRompecabezaMock, requestMock);
    }

    private void thenRetornaUnModelMapConElNivelNuevoYElMensajeVictoria(ModelMap model, Integer nivelNuevo, String mensaje) {

        assertThat(model.get("nivelNuevo").toString(), equalToIgnoringCase(nivelNuevo.toString()));
        assertThat(model.get("mensaje").toString(), equalToIgnoringCase(mensaje));

    }


    private void thenSeLoLlevaAlUltimoRompecabezaJugado(ModelAndView modelAndView) {

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/rompecabezas"));

    }

    private ModelAndView whenComprobarUsuarioYNivelValido(Long rompecabezaNivel, HttpServletRequest requestMock) {
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(1);
        return controladorRompecabezas.irARompecabezasJuego(rompecabezaNivel, requestMock);

    }

    private void thenSeLoLlevaAlNivelUno(ModelAndView modelAndView) {

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("rompecabezas"));
        assertThat(modelAndView.getModel().get("rompecabeza").toString(), equalToIgnoringCase(rompecabezaMock.toString()) );

    }


    private void givenHayUsuario() {
    }

    private ModelAndView whenIngresoAVistaRompecabezaConUsuario() {
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(3);
        return controladorRompecabezas.irARompecabezasMain(requestMock);
    }

    private void thenSeLoLlevaAVistaRompecabezaDelUltimoRompecabezaJugado(ModelAndView modelAndView) {
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/rompecabezas/3"));
    }

    private void givenNoHayUsuario() {
        when(sessionMock.getAttribute("id")).thenReturn(null);
    }

    private ModelAndView whenVerificoSiExisteUsuario() {

        return controladorRompecabezas.irARompecabezasMain(requestMock);

    }

    private void thenSeLoLlevaALogin( ModelAndView modelAndView) {

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Inicie sesión para jugar"));

    }

    private ModelMap whenUsuarioTocaSeleccionarNiveles() {
        when(sessionMock.getAttribute("rompecabezaNivel")).thenReturn(1L);
        return controladorRompecabezas.irARompecabezasNiveles(requestMock);

    }

    private void thenElUsuarioEsLlevadoAUnaVistaDeNiveles( ModelMap model) {

        assertThat(model.get("niveles").toString(), equalToIgnoringCase(rompecabezasMock.toString()) );

    }

    private void givenExisteUsuario() {

    }







}
