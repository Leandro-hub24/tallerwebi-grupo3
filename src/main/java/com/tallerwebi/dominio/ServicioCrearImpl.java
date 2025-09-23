package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FaltaSeleccionarEstiloParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarImagenParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePuedeCrearUnBrainrotConMasDe4ImagenesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ServiceCrear")
@Transactional
public class ServicioCrearImpl implements ServicioCrear {

    RepositorioCrear repositorioCrear;

    @Autowired
    public ServicioCrearImpl (RepositorioCrear repositorioCrear) {
        this.repositorioCrear = repositorioCrear;
    }
    @Override
    public byte[] crearBrainrot(String estilo, List<Integer> imagenes) throws FaltaSeleccionarImagenParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarEstiloParaCrearBrainrotException {
        if (imagenes == null || imagenes.isEmpty()) {
            throw new FaltaSeleccionarImagenParaCrearBrainrotException("Se necesita al menos una imagen para crear un brainrot");
        }
        if (imagenes.size()>4){
            throw new NoSePuedeCrearUnBrainrotConMasDe4ImagenesException("No se puede crear un brainrot con mas de 4 imagenes");
        }
        if (estilo == null || estilo.isEmpty()) {
            throw new FaltaSeleccionarEstiloParaCrearBrainrotException("Se necesita un estilo para crear un brainrot");
        }
        repositorioCrear.guardarBrainrotAUsuario();
        return "IMAGE_FAKE".getBytes();

    }
}
