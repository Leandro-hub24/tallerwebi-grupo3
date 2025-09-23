package com.tallerwebi.dominio;

import java.util.ArrayList;

public interface RepositorioRompecabeza {

    Rompecabeza buscarRompecabeza(Long idRompecabeza);
    ArrayList<Rompecabeza> buscarRompecabezas(Long idUsuario);

}
