package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioRompecabezas {

    List<Rompecabeza> consultarRompecabezasDelUsuario(Integer rompecabezaNivel);
    Rompecabeza consultarRompecabeza(Long idRompecabeza);
    List<List<List<String>>> moverPieza(List<List<List<String>>> matrizActual, String idPiezaAMover);
    boolean comprobarVictoria(List<List<List<String>>> matrizActual);
    Long buscarUltimoNivelId();
}
