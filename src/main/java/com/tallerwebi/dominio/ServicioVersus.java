package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioVersus {
    Brainrot obtenerBrainrotAleatorio();
    List<String> obtenerOpcionesAleatorias();
    boolean verificarRespuesta(String respuesta, String respuestaCorrecta);
}
