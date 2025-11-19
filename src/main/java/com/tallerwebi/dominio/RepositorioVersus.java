package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioVersus {
    List<Brainrot> obtenerTodosPorNivel(Integer nivel);
    List<Brainrot> obtenerTodos();
    List<String> obtenerTodasLasOpciones();
}
