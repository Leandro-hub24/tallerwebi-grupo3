package com.tallerwebi.dominio;

import java.util.ArrayList;

public interface ServicioRompecabezas {

    ArrayList<Rompecabeza> consultarRompecabezasDelUsuario(Long idUsuario);
    Rompecabeza consultarRompecabeza(Long idRompecabeza);

}
