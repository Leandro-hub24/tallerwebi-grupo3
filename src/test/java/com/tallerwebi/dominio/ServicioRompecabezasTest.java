package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import com.tallerwebi.dominio.excepcion.RompecabezaNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioRompecabezasTest {

    /*
    * el metodo consultar rompecabezas del usuario deberia recibir un id de usuario y devolver un array de rompecabezas
    * el metodo consultar rompecabeza deberia recibir un id de rompecabeza y devolver un rompecabeza
    *
    * */

    private ArrayList<Rompecabeza> rompecabezasMock;
    private Rompecabeza rompecabezaMock;
    private ServicioRompecabezas servicioRompecabezas;
    private RepositorioRompecabeza repositorioRompecabezaMock;

    @BeforeEach
    public void init() {
        rompecabezasMock = new ArrayList<>();
        rompecabezaMock = mock(Rompecabeza.class);
        rompecabezasMock.add(rompecabezaMock);
        repositorioRompecabezaMock = mock(RepositorioRompecabeza.class);
        servicioRompecabezas = new ServicioRompecabezasImpl(repositorioRompecabezaMock);

        when(repositorioRompecabezaMock.buscarRompecabezas(1L)).thenReturn(rompecabezasMock);
        when(repositorioRompecabezaMock.buscarRompecabezas(2L)).thenReturn(null);
        when(repositorioRompecabezaMock.buscarRompecabeza(1L)).thenReturn(rompecabezaMock);
        when(repositorioRompecabezaMock.buscarRompecabeza(2L)).thenReturn(null);
    }

    @Test
    public void alConsultarRompecabezasDelUsuarioEnviandoIdDeUsuarioIgualAUnoDevuelveUnArrayDeRompecabezas(){

        Long id = givenTengoUnIdUsuario();

        ArrayList<Rompecabeza> rompecabezas = whenBuscoRompecabezasDelUsuario(1L);

        thenReciboUnArrayDeRompecabezas(rompecabezas);


    }

    @Test
    public void alConsultarRompecabezasDelUsuarioEnviandoIdDeUsuarioIgualAUnoDevuelveUnaExceptionNivelesNoEncontrados(){

        Long id = givenTengoUnIdUsuario();

        assertThrows(
                NivelesNoEncontradosException.class,
                ()  -> whenBuscoRompecabezasDelUsuario(2L)
        );

    }

    @Test
    public void alConsultarUnRompecabezaEnviandoIdDeRompezabezaDeberiaDevolverUnRompecabeza(){

        givenTengoUnIdRompecabeza();

        Rompecabeza rompecabeza = whenBuscoRompecabezaConId(1L);

        thenReciboUnRompecabeza(rompecabeza);

    }

    @Test
    public void alConsultarUnRompecabezaEnviandoIdDeRompezabezaDeberiaDevolverUnaExceptionRompecabezaNoEncontrado(){

        givenTengoUnIdRompecabeza();

        assertThrows(
                RompecabezaNoEncontradoException.class,
                ()  -> whenBuscoRompecabezaConId(2L)
        );

    }

    private void givenTengoUnIdRompecabeza() {
    }


    private Rompecabeza whenBuscoRompecabezaConId(Long id) {
        return servicioRompecabezas.consultarRompecabeza(id);
    }

    private void thenReciboUnRompecabeza(Rompecabeza rompecabeza) {
        assertThat(rompecabeza.toString(), equalToIgnoringCase(rompecabezaMock.toString()));
    }

    private Long givenTengoUnIdUsuario() {
        return 1L;
    }

    private ArrayList<Rompecabeza> whenBuscoRompecabezasDelUsuario(Long id) {
        return servicioRompecabezas.consultarRompecabezasDelUsuario(id);
    }

    private void thenReciboUnArrayDeRompecabezas(ArrayList<Rompecabeza> rompecabezas) {
        assertThat(rompecabezas.toString(), equalToIgnoringCase(rompecabezasMock.toString()));
    }





}
