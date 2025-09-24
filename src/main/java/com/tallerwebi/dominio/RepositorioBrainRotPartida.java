package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioBrainRotPartida {

    void guardar(BrainRotPartida partida);
    BrainRotPartida buscarPorId(Long id);
    List<BrainRotPartida> buscarTodas();
    List<BrainRotPartida> buscarPorUsuario(Usuario usuario);
    BrainRotPartida buscarPartidaActivaPorUsuario(Usuario usuario);
    List<BrainRotPartida> buscarPartidasTerminadasPorUsuario(Usuario usuario);
    List<BrainRotPartida> buscarPorEstado(String estado);
    Long contarPartidasPorUsuario(Usuario usuario);
    Long contarVictoriasPorUsuario(Usuario usuario);
    void eliminar(BrainRotPartida partida);
    void modificar(BrainRotPartida partida);
}
