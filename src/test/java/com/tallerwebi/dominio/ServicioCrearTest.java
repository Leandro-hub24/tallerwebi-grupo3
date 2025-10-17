package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FaltaSeleccionarEstiloParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarImagenParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePuedeCrearUnBrainrotConMasDe4ImagenesException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicioCrearTest {

    RepositorioCrear repositorioFake = new RepositorioCrear() {
        @Override
        public void guardarBrainrotAUsuario() {}

    };
    ServicioCrear servicioCrear = new ServicioCrearImpl(repositorioFake);


    @Test
    public void siEnviaCrearLeDevuelveUnaImagen() throws FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException {
        givenUsuarioExiste();
        List<Integer> imagenes = List.of(1, 2, 3);
        byte[] imagenDevuelta = whenUsuarioCreaConImagenesSeleccionadasYEstilo(imagenes, "monstruoso");
        thenDevuelveUnaImagen(imagenDevuelta);
    }

    private void thenDevuelveUnaImagen(byte[] imagenDevuelta) {
        assertTrue(imagenDevuelta.length > 0);
    }

    private byte[] whenUsuarioCreaConImagenesSeleccionadasYEstilo(List<Integer> imagenes, String estilo) throws FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException {
       byte[] imagenDevuelta = servicioCrear.crearBrainrot(estilo, imagenes);
       return imagenDevuelta;
    }

    private void givenUsuarioExiste() {
    }

    @Test
    public void siEnviaCrearConMasDe4ImagenesObtengoUnaExcepcion() {
        givenUsuarioExiste();
        List<Integer> imagenes = List.of(1, 2, 3, 4, 5);
       assertThrows(NoSePuedeCrearUnBrainrotConMasDe4ImagenesException.class, ()-> servicioCrear.crearBrainrot("monstruoso", imagenes));
    }

    @Test
    public void siEnviaCrearSinImagenesObtengoUnaExcepcion() {
        givenUsuarioExiste();
        List<Integer> imagenes = List.of();
        assertThrows(FaltaSeleccionarImagenParaCrearBrainrotException.class, ()-> servicioCrear.crearBrainrot("monstruoso", imagenes));
    }


    @Test
    public void siFaltaSeleccionarEstiloFalla() {
        givenUsuarioExiste();
        List<Integer> imagenes = List.of(1, 2);
        assertThrows(FaltaSeleccionarEstiloParaCrearBrainrotException.class, ()-> servicioCrear.crearBrainrot("", imagenes));
    }


}
