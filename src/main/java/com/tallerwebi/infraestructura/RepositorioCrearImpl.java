package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioCrear;
import com.tallerwebi.presentacion.ImagenCrear;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("RepositorioCrear")
public class RepositorioCrearImpl implements RepositorioCrear {
    @Override
    public void guardarBrainrotAUsuario() {

    }

    private final List<ImagenCrear> imagenesDisponibles = List.of(
            new ImagenCrear(1, "Imagen 1", "/img/TralaleroTralala.jpg"),
            new ImagenCrear(2, "Imagen 2", "/img/TralaleroTralala.jpg"),
            new ImagenCrear(3, "Imagen 3", "/img/TralaleroTralala.jpg"),
            new ImagenCrear(4, "Imagen 4", "/img/TralaleroTralala.jpg"),
            new ImagenCrear(5, "Imagen 5", "/img/TralaleroTralala.jpg")
    );

    @Override
    public List<ImagenCrear> getImagenesCrear() {
        return imagenesDisponibles;
    }

    @Override
    public List<ImagenCrear> buscarPorIds(List<Integer> idImagenesSeleccionadas) {
        return imagenesDisponibles.stream()
                .filter(img -> idImagenesSeleccionadas.contains(img.getId()))
                .collect(Collectors.toList());
    }
}
