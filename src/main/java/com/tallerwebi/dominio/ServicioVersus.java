package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioVersus {
    List<Brainrot> obtenerTodosPorNivel(Integer nivel);
    List<String> obtenerOpcionesAleatoriasParaPregunta(Integer nivel, String imagenCorrecta);
    boolean verificarRespuesta(String respuesta, String respuestaCorrecta);
    boolean esTimeOut(String timeout);
    List<Brainrot> obtenerTodos();
    List<String> obtenerOpcionesAleatoriasParaPreguntaMultijugador(String imagenCorrecta);
}
