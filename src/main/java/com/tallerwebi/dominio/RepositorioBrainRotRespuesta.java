package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioBrainRotRespuesta {

    void guardar(BrainRotRespuesta respuesta);
    BrainRotRespuesta buscarPorId(Long id);
    List<BrainRotRespuesta> buscarTodas();
    List<BrainRotRespuesta> buscarPorPartida(BrainRotPartida partida);
    List<BrainRotRespuesta> buscarPorUsuario(Usuario usuario);
    List<BrainRotRespuesta> buscarRespuestasCorrectasPorUsuario(Usuario usuario);
    List<BrainRotRespuesta> buscarRespuestasIncorrectasPorUsuario(Usuario usuario);
    Long contarRespuestasCorrectasPorUsuario(Usuario usuario);
    Long contarRespuestasTotalesPorUsuario(Usuario usuario);
    Double calcularPorcentajeAciertoPorUsuario(Usuario usuario);
    void eliminar(BrainRotRespuesta respuesta);
    void modificar(BrainRotRespuesta respuesta);
}
