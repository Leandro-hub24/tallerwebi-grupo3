package com.tallerwebi.dominio;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RepositorioImagen {
    List<Imagen>  getImagenesDeUnTipo(String tipo);
    List<Imagen>  getImagenesPorId(List<Integer> ids);
}
