package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FaltaSeleccionarEstiloParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarImagenParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePudoCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePuedeCrearUnBrainrotConMasDe4ImagenesException;
import com.tallerwebi.infraestructura.RepositorioImagenImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioCrearTest {

    RepositorioCrear repositorioFake = new RepositorioCrear() {
        @Override
        public void guardarBrainrotAUsuario() {}

    };
    SessionFactory sessionFactory;

    ServicioImagen servicioImagen;
    ServicioCrear servicioCrear = new ServicioCrearImpl(repositorioFake, servicioImagen );


    @Test
    public void siEnviaCrearLeDevuelveUnaImagen() throws FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, NoSePudoCrearBrainrotException {
        givenUsuarioExiste();
        List<Integer> imagenes = List.of(1, 2, 3);
        BrainrotCreado brainrot = whenUsuarioCreaConImagenesSeleccionadasYEstilo(imagenes, "monstruoso");
        thenDevuelveUnaImagen(brainrot);
    }

    private void thenDevuelveUnaImagen(BrainrotCreado brainrot) {
        assertNotNull(brainrot);
    }

    private BrainrotCreado whenUsuarioCreaConImagenesSeleccionadasYEstilo(List<Integer> imagenes, String estilo) throws FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, NoSePudoCrearBrainrotException {
       BrainrotCreado brainrot = servicioCrear.crearBrainrot(estilo, imagenes);
       return brainrot;
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
