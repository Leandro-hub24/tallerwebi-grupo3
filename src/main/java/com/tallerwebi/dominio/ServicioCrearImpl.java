package com.tallerwebi.dominio;

import com.google.genai.Pager;
import com.google.genai.types.Model;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.genai.Client;
import com.google.genai.types.GenerateImagesResponse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("ServicioCrear")
@Transactional
public class ServicioCrearImpl implements ServicioCrear {

    ServicioImagen servicioImagen;
    RepositorioCrear repositorioCrear;

    /*@Value("${upload.dir}")*/
    private String UPLOAD_DIR;
    /*@Value("${google.api.key}")*/
    private String GOOGLE_API_KEY;

    @Autowired
    public ServicioCrearImpl (RepositorioCrear repositorioCrear, ServicioImagen servicioImagen) {
        this.repositorioCrear = repositorioCrear;
        this.servicioImagen = servicioImagen;
    }

    @Override
    public BrainrotCreado crearBrainrot(String estilo, List<Integer> imagenes, String fondo) throws FaltaSeleccionarImagenParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePudoCrearBrainrotException, FaltaSeleccionarFondoParaCrearBrainrotException {

        if (imagenes == null || imagenes.isEmpty()) {
            throw new FaltaSeleccionarImagenParaCrearBrainrotException("Se necesita al menos una imagen para crear un brainrot");
        }
        if (imagenes.size() > 4) {
            throw new NoSePuedeCrearUnBrainrotConMasDe4ImagenesException("No se puede crear un brainrot con mas de 4 imagenes");
        }
        if (estilo == null || estilo.isEmpty()) {
            throw new FaltaSeleccionarEstiloParaCrearBrainrotException("Se necesita un estilo para crear un brainrot");
        }
        if (fondo == null || fondo.isEmpty()) {
            throw new FaltaSeleccionarFondoParaCrearBrainrotException("Se necesita un estilo para crear un brainrot");
        }

        BrainrotCreado brainrot;
        try {
            brainrot = generarBrainrotConIA(estilo, imagenes, fondo);
        } catch (NoSePudoCrearBrainrotException | NoSeEncontraronImagenesException e) {
            throw new NoSePudoCrearBrainrotException("No se pudo crear el brainrot: " + e.getMessage());
        }

        repositorioCrear.guardarBrainrotAUsuario();

        return brainrot;
    }

    public BrainrotCreado generarBrainrotConIA(String estilo, List<Integer> imagenesId, String fondo) throws NoSePudoCrearBrainrotException, NoSeEncontraronImagenesException {
        String prompt = generarPrompt(estilo, imagenesId, fondo);
        return llamarAPI(prompt);
    }


    private BrainrotCreado llamarAPI(String prompt) throws NoSePudoCrearBrainrotException {
        try {
            //crear el cliente
            Client client = Client.builder()
                    .apiKey(GOOGLE_API_KEY)
                    .build();

            //generar la imagen
            GenerateImagesResponse response = client.models.generateImages(
                    "imagen-4.0-generate-001",
                    prompt,
                    null);

            //extraer bytes
            byte[] imageBytes = response.generatedImages().get().get(0).image().get().imageBytes().get();

            //guardar
            String filename = UUID.randomUUID() + ".png";
            Path directoryPath = Paths.get(UPLOAD_DIR);
            Path filePath = directoryPath.resolve(filename);
            Files.createDirectories(directoryPath);

            //pegar al archivo la imagen
            Files.write(filePath, imageBytes);


            Imagen nuevaImagen = new Imagen();
            nuevaImagen.setNombre("Brainrot generado: " + prompt.substring(0, Math.min(prompt.length(), 50)) + "...");
            nuevaImagen.setUrl("/gen-img/" + filename);
            nuevaImagen.setTipo("brainrotCreado");

            BrainrotCreado brainrot = new BrainrotCreado();
            brainrot.setImagen(nuevaImagen);

            return brainrot;

        } catch (Exception e) {
            e.printStackTrace();
            throw new NoSePudoCrearBrainrotException("Error al llamar a la API de GenAI: " + e.getMessage());
        }
    }

    private String generarPrompt(String estilo, List<Integer> imagenesId, String fondo) throws NoSeEncontraronImagenesException {
        List<Imagen> seleccionadas = servicioImagen.getImagenesPorId(imagenesId);
        List<String> nombres = seleccionadas.stream()
                .map(Imagen::getNombre)
                .collect(Collectors.toList());

        String prompt = "Crea una imagen de un personaje al estilo brainrot italiano, en formato 1:1 y 3D muy realista. " +
                "El personaje presenta una combinación de elementos de: " + String.join(", ", nombres) + ". " +
                "El estilo general del arte debe ser " + estilo + ". " +
                "El fondo es un " + fondo;
        return prompt;
    }
}