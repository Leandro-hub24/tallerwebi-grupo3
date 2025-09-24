package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioBrainRotPregunta {

    void guardar(BrainRotPregunta pregunta);
    BrainRotPregunta buscarPorId(Long id);
    List<BrainRotPregunta> buscarTodas();
    List<BrainRotPregunta> buscarPorCategoria(String categoria);
    List<BrainRotPregunta> buscarPorDificultad(String dificultad);
    BrainRotPregunta buscarAleatoria();
    List<BrainRotPregunta> buscarAleatoriasLimitadas(Integer limite);
    Long contarPreguntas();
    void eliminar(BrainRotPregunta pregunta);
    void modificar(BrainRotPregunta pregunta);
}
