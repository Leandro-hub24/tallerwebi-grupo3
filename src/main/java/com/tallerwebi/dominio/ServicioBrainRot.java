package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioBrainRot {

    // Gestión de partidas
    BrainRotPartida iniciarNuevaPartida(Usuario usuario);
    BrainRotPartida obtenerPartidaActiva(Usuario usuario);
    void terminarPartida(BrainRotPartida partida);
    
    // Gestión de preguntas
    BrainRotPregunta obtenerSiguientePregunta(BrainRotPartida partida);
    List<BrainRotPregunta> obtenerPreguntasAleatorias(Integer cantidad);
    BrainRotPregunta buscarPreguntaPorId(Long id);
    
    // Procesamiento de respuestas
    BrainRotRespuesta procesarRespuesta(BrainRotPartida partida, BrainRotPregunta pregunta, String respuestaUsuario);
    String generarRespuestaMaquina(BrainRotPregunta pregunta);
    
    // Lógica del juego
    void actualizarPuntajes(BrainRotPartida partida, BrainRotRespuesta respuesta);
    boolean esPartidaTerminada(BrainRotPartida partida);
    String determinarGanador(BrainRotPartida partida);
    
    // Estadísticas
    Long obtenerVictoriasPorUsuario(Usuario usuario);
    Double obtenerPorcentajeAcierto(Usuario usuario);
    Integer obtenerMejorRacha(Usuario usuario);
}
