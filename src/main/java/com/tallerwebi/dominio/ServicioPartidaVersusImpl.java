package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPartidaVersus")
@Transactional
public class ServicioPartidaVersusImpl implements ServicioPartidaVersus {

    private RepositorioPartidaVersus repositorioPartidaVersus;

    @Autowired
    public ServicioPartidaVersusImpl(RepositorioPartidaVersus repositorioPartidaVersus) {
        this.repositorioPartidaVersus = repositorioPartidaVersus;
    }

    @Override
    public PartidaVersus crearPartida(Long usuarioId) {
        List<PartidaVersus> partidasExistentes = repositorioPartidaVersus.buscarPorJugador1(usuarioId);
        for (PartidaVersus partida : partidasExistentes) {
            if ("ESPERANDO".equals(partida.getEstado()) || "EN_JUEGO".equals(partida.getEstado())) {
                return null;
            }
        }
        PartidaVersus nuevaPartida = new PartidaVersus(usuarioId);
        repositorioPartidaVersus.guardar(nuevaPartida);
        return nuevaPartida;
    }

    @Override
    public List<PartidaVersus> obtenerPartidasDisponibles() {
        return repositorioPartidaVersus.buscarPorEstado("ESPERANDO");
    }

    @Override
    public PartidaVersus unirseAPartida(Long partidaId, Long usuarioId) {
        PartidaVersus partida = repositorioPartidaVersus.buscarPorId(partidaId);

        if (partida != null && partida.getJugador2Id() == null) {
            partida.setJugador2Id(usuarioId);
            partida.setEstado("EN_JUEGO");
            repositorioPartidaVersus.actualizar(partida);
        }

        return partida;
    }
    @Override
    public PartidaVersus buscarPorId(Long id) {
        return repositorioPartidaVersus.buscarPorId(id);
    }

    @Override
    public PartidaVersus actualizarPuntos(Long partidaId, Long usuarioId, Integer puntos) {
        PartidaVersus partida = repositorioPartidaVersus.buscarPorId(partidaId);

        if (partida != null) {
            if (usuarioId.equals(partida.getJugador1Id())) {
                partida.setPuntosJugador1(partida.getPuntosJugador1() + puntos);
            } else if (usuarioId.equals(partida.getJugador2Id())) {
                partida.setPuntosJugador2(partida.getPuntosJugador2() + puntos);
            }
            repositorioPartidaVersus.actualizar(partida);
        }

        return partida;
    }
}
