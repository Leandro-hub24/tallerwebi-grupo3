package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ServicioAdivinanzaImplTest {


    Usuario usuario = new Usuario();

    RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    ServicioAdivinanzaImpl servicio = new ServicioAdivinanzaImpl(repositorioUsuario);




    @Test
    void debeSumarUnoAlPuntajeYGuardarEnDbSiLaOpcionEsCorrecta() {

        usuario.setPuntajeAdivinanza(2);

        servicio.opcionCorrecta(usuario);

        assertEquals(3, usuario.getPuntajeAdivinanza()); // Verifica lógica de negocio
        Mockito.verify(repositorioUsuario).modificar(usuario); // Verifica persistencia
    }

    @Test
    void siLaOpcionEsIncorrectaSeLeReduceElPuntajeA1() {
        usuario.setPuntajeAdivinanza(5);

        servicio.opcionIncorrecta(usuario);

        assertEquals(4, usuario.getPuntajeAdivinanza()); // Verifica lógica de negocio
        Mockito.verify(repositorioUsuario).modificar(usuario); // Verifica persistencia
    }
}
