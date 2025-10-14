package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioNivelJuego")
@Transactional
public class ServicioNivelJuegoImpl implements ServicioNivelJuego {

    private RepositorioNivelJuego repositorioNivelJuego;

    @Autowired
    public ServicioNivelJuegoImpl(RepositorioNivelJuego repositorioNivelJuegoMock) {
        this.repositorioNivelJuego = repositorioNivelJuegoMock;
    }

    @Override
    public NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId) {
        return repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(usuarioId);
    }

    @Override
    public Integer actualizarNivelJuego(Long idUsuario, Integer idRompecabeza, Integer nivelActualUsuario, Long ultimoNivel) {
        if(idRompecabeza == nivelActualUsuario){
            Integer nivelNuevo = idRompecabeza + 1;

            if (nivelNuevo <= ultimoNivel) {
                return repositorioNivelJuego.modificarNivelJuego(idUsuario).intValue();
            }
        }

        return null;
    }
}
