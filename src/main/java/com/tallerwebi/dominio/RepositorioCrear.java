package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ImagenCrear;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCrear {
     void guardarBrainrotAUsuario();

     List<ImagenCrear> getImagenesCrear();

     List<ImagenCrear> buscarPorIds(List<Integer> ids);
}
