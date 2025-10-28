package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.RepositorioCrearImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;

public class ServicioCrearTest {

    ServicioImagen servicioImagen = mock(ServicioImagenImpl.class);
    RepositorioCrear repositorioCrear = mock(RepositorioCrearImpl.class);

    ServicioCrear servicioCrear = new ServicioCrearImpl(repositorioCrear, servicioImagen );
    private Imagen imagen;

    @BeforeEach
    public void setUp() {
        imagen = new Imagen();
        imagen.setId(1);
        imagen.setNombre("imagen");
        imagen.setTipo("crear");
        imagen.setUrl("a");
    }
    @Test
    public void siEnviaCrearDevuelveUnBrainrot() throws FaltaSeleccionarImagenParaCrearBrainrotException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, NoSePudoCrearBrainrotException, NoSeEncontraronImagenesException {
        givenUsuarioExiste();
        List<Integer> imagenes = List.of(1);
        Mockito.when(servicioImagen.getImagenesPorId(anyList())).thenReturn(List.of(imagen));

        BrainrotCreado brainrot = whenUsuarioCreaConImagenesSeleccionadasYEstilo(imagenes, "monstruoso");

    //    thenDevuelveUnBrainrot(brainrot);
    }

    private void thenDevuelveUnBrainrot(BrainrotCreado brainrot) {
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
