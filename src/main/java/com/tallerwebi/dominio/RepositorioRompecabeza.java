package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public interface RepositorioRompecabeza {

    Rompecabeza buscarRompecabeza(Long idRompecabeza);
    List<Rompecabeza> buscarRompecabezas(Integer rompecabezaNivel);
    Integer modificarRompecabezaNivel(Long idUsuario);
    Long buscarUltimoNivelId();

}
