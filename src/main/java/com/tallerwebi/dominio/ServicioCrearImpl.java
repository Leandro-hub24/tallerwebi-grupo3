package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FaltaSeleccionarEstiloParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarImagenParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePuedeCrearUnBrainrotConMasDe4ImagenesException;
import com.tallerwebi.presentacion.ImagenCrear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.stream.Collectors;

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
        generarBrainrotConIA(estilo, imagenes);
        return "IMAGE_FAKE".getBytes();

    }

    public void generarBrainrotConIA(String estilo, List<Integer> imagenesId){
        String prompt = generarPrompt(estilo, imagenesId);
        llamarAPI(prompt);
    }



// ...

    private String llamarAPI(String prompt)  {
        return prompt;
    }

    private String generarPrompt(String estilo, List<Integer> imagenesId) {
        List<ImagenCrear> seleccionadas = repositorioCrear.buscarPorIds(imagenesId);

        List<String> nombres = seleccionadas.stream()
                .map(ImagenCrear::getNombre)
                .collect(Collectors.toList());

        String prompt = "Crea una imagen de un personaje al estilo brainrot italiano en formato 1:1 y 3D muy realista, con resolución de 600x400. " +
                "El personaje presenta una combinación de elementos de: " + String.join(", ", nombres) + ". " +
                "El estilo general del arte debe ser " + estilo + ". " +
                "El fondo es un campo.";
        return prompt;
    }

    @Override
    public List<ImagenCrear> getImagenesCrear() {
        return repositorioCrear.getImagenesCrear();
    }
}
