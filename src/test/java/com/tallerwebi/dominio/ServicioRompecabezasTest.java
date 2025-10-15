package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import com.tallerwebi.dominio.excepcion.PiezaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.RompecabezaNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioRompecabezasTest {

    /*
    * el metodo consultar rompecabezas del usuario deberia recibir un id de usuario y devolver un array de rompecabezas
    * el metodo consultar rompecabeza deberia recibir un id de rompecabeza y devolver un rompecabeza
    * al resolver el rompecabeza debe aumentar rompecabeza nivel del usuario en uno y retornar Victoria
    * al resolver el rompecabeza y no poder aumentar rompecabeza nivel del usuario en uno retorna un NoSeHaModificadoRompecabezaNivelException
    *
    * */

    private List<Rompecabeza> rompecabezasMock;
    private List<List<List<String>>> matriz;
    private Rompecabeza rompecabezaMock;
    private Integer idRompecabeza;
    private Integer nivelActualUsuario;
    private Long nivelNuevo;
    private String idPiezaAMover;
    private ServicioRompecabezas servicioRompecabezas;
    private RepositorioRompecabeza repositorioRompecabezaMock;
    private static final String imagenBlanco = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAwQYFqEAAAAASUVORK5CYII=";

    @BeforeEach
    public void init() {

        matriz = new ArrayList<>();
        rompecabezasMock = new ArrayList<>();
        rompecabezaMock = mock(Rompecabeza.class);
        rompecabezasMock.add(rompecabezaMock);
        repositorioRompecabezaMock = mock(RepositorioRompecabeza.class);
        servicioRompecabezas = new ServicioRompecabezasImpl(repositorioRompecabezaMock);

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


        when(repositorioRompecabezaMock.buscarRompecabezas(1)).thenReturn((ArrayList<Rompecabeza>) rompecabezasMock);
        when(repositorioRompecabezaMock.buscarUltimoNivelId()).thenReturn(3L);
        when(repositorioRompecabezaMock.buscarRompecabezas(2)).thenReturn(null);
        when(repositorioRompecabezaMock.buscarRompecabeza(1L)).thenReturn(rompecabezaMock);
        when(repositorioRompecabezaMock.buscarRompecabeza(2L)).thenReturn(null);
    }

    @Test
    public void sePodraMoverUnapiezaEnPosicionUnoUnoDentroDeUnaMatrizSiLaPiezaDeArribaEnPosicionCeroUnoEstaVacia(){

        givenTengoUnaMatrizYUnIdDeUnaPiezaAMover(0, 1, "img_1-1");

        List<List<List<String>>> matrizActualizada = whenComprueboSiPuedoMoverLaPieza();

        thenReciboUnaMatrizConElMovimientoRealizado(matrizActualizada, 0, 1);

    }

    private void givenTengoUnaMatrizYUnIdDeUnaPiezaAMover(int x, int y, String img) {

        matriz.get(x).get(y).set(1, imagenBlanco);
        idPiezaAMover = img;

    }

    private List<List<List<String>>> whenComprueboSiPuedoMoverLaPieza() {

        return servicioRompecabezas.moverPieza(matriz, idPiezaAMover);

    }

    private void thenReciboUnaMatrizConElMovimientoRealizado( List<List<List<String>>> matrizActualizada, int x, int y) {

        assertThat(matrizActualizada.get(x).get(y).get(0), equalToIgnoringCase(idPiezaAMover));
        assertThat(matrizActualizada.get(1).get(1).get(1), equalToIgnoringCase(imagenBlanco));

    }

    @Test
    public void sePodraMoverUnapiezaEnPosicionUnoUnoDentroDeUnaMatrizSiLaPiezaDeLaIzquierdaEnPosicionUnoCeroEstaVacia(){

        givenTengoUnaMatrizYUnIdDeUnaPiezaAMover(1, 0, "img_1-1");

        List<List<List<String>>> matrizActualizada = whenComprueboSiPuedoMoverLaPieza();

        thenReciboUnaMatrizConElMovimientoRealizado(matrizActualizada, 1, 0);

    }

    @Test
    public void sePodraMoverUnapiezaEnPosicionUnoUnoDentroDeUnaMatrizSiLaPiezaDeLaDerechaEnPosicionUnoDosEstaVacia(){

        givenTengoUnaMatrizYUnIdDeUnaPiezaAMover(1, 2, "img_1-1");

        List<List<List<String>>> matrizActualizada = whenComprueboSiPuedoMoverLaPieza();

        thenReciboUnaMatrizConElMovimientoRealizado(matrizActualizada, 1, 2);

    }

    @Test
    public void sePodraMoverUnapiezaEnPosicionUnoUnoDentroDeUnaMatrizSiLaPiezaDeAbajoEnPosicionDosUnoEstaVacia(){

        givenTengoUnaMatrizYUnIdDeUnaPiezaAMover(2, 1, "img_1-1");

        List<List<List<String>>> matrizActualizada = whenComprueboSiPuedoMoverLaPieza();

        thenReciboUnaMatrizConElMovimientoRealizado(matrizActualizada, 2, 1);

    }

    @Test
    public void siNoEncuentraLaPiezaConElIdEnviadoDevuelveUnPiezaNoEncontradaException(){

        givenTengoUnaMatrizYUnIdDeUnaPiezaAMover(2, 1, "img_3-4");

        assertThrows(
                PiezaNoEncontradaException.class,
                ()  -> whenComprueboSiPuedoMoverLaPieza()
        );

    }

    @Test
    public void siElRompecabezaEstaResueltoDevuelveTrue() {

        givenTengoUnaMatrizResuelta();

        boolean gano = whenComprueboSiEstaResueltoElRompecabezas();

        thenReciboTrue(gano);

    }

    private void givenTengoUnaMatrizResuelta() {
    }

    private boolean whenComprueboSiEstaResueltoElRompecabezas() {

        return servicioRompecabezas.comprobarVictoria(matriz);

    }

    private void thenReciboTrue(boolean gano) {

        assertTrue(gano);

    }

    @Test
    public void siElRompecabezaNoEstaResueltoDevuelveFalse() {

        givenTengoUnaMatrizNoResuelta();

        boolean gano = whenComprueboSiEstaResueltoElRompecabezas();

        thenReciboFalse(gano);

    }

    private void givenTengoUnaMatrizNoResuelta() {

        matriz.get(0).get(1).set(0, "img_1-2");
        matriz.get(1).get(2).set(0, "img_0-1");

    }

    private void thenReciboFalse(boolean gano) {

        assertFalse(gano);

    }

    @Test
    public void alConsultarRompecabezasDelUsuarioEnviandoIdDeUsuarioIgualAUnoDevuelveUnArrayDeRompecabezas(){

        Long id = givenTengoUnIdUsuario();

        List<Rompecabeza> rompecabezas = whenBuscoRompecabezasDelUsuario(1);

        thenReciboUnArrayDeRompecabezas(rompecabezas);


    }

    private Long givenTengoUnIdUsuario() {
        return 1L;
    }

    private List<Rompecabeza> whenBuscoRompecabezasDelUsuario(Integer rompecabezaNivel) {
        return servicioRompecabezas.consultarRompecabezasDelUsuario(rompecabezaNivel);
    }

    private void thenReciboUnArrayDeRompecabezas(List<Rompecabeza> rompecabezas) {
        assertThat(rompecabezas.toString(), equalToIgnoringCase(rompecabezasMock.toString()));
    }

    @Test
    public void alConsultarRompecabezasDelUsuarioEnviandoIdDeUsuarioIgualAUnoDevuelveUnaExceptionNivelesNoEncontrados(){

        Long id = givenTengoUnIdUsuario();

        assertThrows(
                NivelesNoEncontradosException.class,
                ()  -> whenBuscoRompecabezasDelUsuario(2)
        );

    }

    @Test
    public void alConsultarUnRompecabezaEnviandoIdDeRompezabezaDeberiaDevolverUnRompecabeza(){

        givenTengoUnIdRompecabeza();

        Rompecabeza rompecabeza = whenBuscoRompecabezaConId(1L);

        thenReciboUnRompecabeza(rompecabeza);

    }

    private void givenTengoUnIdRompecabeza() {
    }

    private Rompecabeza whenBuscoRompecabezaConId(Long id) {
        return servicioRompecabezas.consultarRompecabeza(id);
    }

    private void thenReciboUnRompecabeza(Rompecabeza rompecabeza) {
        assertThat(rompecabeza.toString(), equalToIgnoringCase(rompecabezaMock.toString()));
    }

    @Test
    public void alConsultarUnRompecabezaEnviandoIdDeRompezabezaDeberiaDevolverUnaExceptionRompecabezaNoEncontrado(){

        givenTengoUnIdRompecabeza();

        assertThrows(
                RompecabezaNoEncontradoException.class,
                ()  -> whenBuscoRompecabezaConId(2L)
        );

    }

    @Test
    public void buscarUltimoNivelId(){

        givenTengoRompecabezas();

        Long nivelIdObtenido = whenBuscoUltimoNivelId();

        thenObtengoUnNivelId(nivelIdObtenido, 3L);

    }

    private void givenTengoRompecabezas() {
    }

    private Long whenBuscoUltimoNivelId() {

        return servicioRompecabezas.buscarUltimoNivelId();

    }

    private void thenObtengoUnNivelId(Long nivelIdObtenido, Long nivelIdEsperado) {

        assertThat(nivelIdObtenido, equalTo(nivelIdEsperado));

    }

}
