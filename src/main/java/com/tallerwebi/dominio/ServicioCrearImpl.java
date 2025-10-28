package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.RepositorioImagenImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.stream.Collectors;

@Service("ServicioCrear")
@Transactional
public class ServicioCrearImpl implements ServicioCrear {


    ServicioImagen servicioImagen;
    RepositorioCrear repositorioCrear;

    @Autowired
    public ServicioCrearImpl (RepositorioCrear repositorioCrear, ServicioImagen servicioImagen) {
        this.repositorioCrear = repositorioCrear;
        this.servicioImagen = servicioImagen;
    }
    @Override
    public BrainrotCreado crearBrainrot(String estilo, List<Integer> imagenes) throws FaltaSeleccionarImagenParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePudoCrearBrainrotException {
        if (imagenes == null || imagenes.isEmpty()) {
            throw new FaltaSeleccionarImagenParaCrearBrainrotException("Se necesita al menos una imagen para crear un brainrot");
        }
        if (imagenes.size() > 4) {
            throw new NoSePuedeCrearUnBrainrotConMasDe4ImagenesException("No se puede crear un brainrot con mas de 4 imagenes");
        }
        if (estilo == null || estilo.isEmpty()) {
            throw new FaltaSeleccionarEstiloParaCrearBrainrotException("Se necesita un estilo para crear un brainrot");
        }
        BrainrotCreado brainrot;
        try {
            brainrot = generarBrainrotConIA(estilo, imagenes);
        } catch (NoSePudoCrearBrainrotException | NoSeEncontraronImagenesException e) {
            throw new NoSePudoCrearBrainrotException("No se pudo crear el brainrot");
        }
        repositorioCrear.guardarBrainrotAUsuario();
        return brainrot;

    }

    public BrainrotCreado generarBrainrotConIA(String estilo, List<Integer> imagenesId) throws NoSePudoCrearBrainrotException, NoSeEncontraronImagenesException {
        String prompt = generarPrompt(estilo, imagenesId);
        llamarAPI(prompt);
        System.out.println(prompt);

        return null;
    }



// ...

    private BrainrotCreado llamarAPI(String prompt) throws NoSePudoCrearBrainrotException {
        BrainrotCreado brainrot = new BrainrotCreado();
        if (brainrot == null){
            throw new NoSePudoCrearBrainrotException("No se pudo crear el brainrot");
        }
        return brainrot;
    }

    private String generarPrompt(String estilo, List<Integer> imagenesId) throws NoSeEncontraronImagenesException {
        List<Imagen> seleccionadas = servicioImagen.getImagenesPorId(imagenesId);
        List<String> nombres = seleccionadas.stream()
                .map(Imagen::getNombre)
                .collect(Collectors.toList());

        String prompt = "Crea una imagen de un personaje al estilo brainrot italiano en formato 1:1 y 3D muy realista, con resolución de 600x400. " +
                "El personaje presenta una combinación de elementos de: " + String.join(", ", nombres) + ". " +
                "El estilo general del arte debe ser " + estilo + ". " +
                "El fondo es un campo.";
        return prompt;
    }

}
