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
    public NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId, String juego) {
        NivelJuego nivelJuego = repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(usuarioId, juego);
        if (nivelJuego != null) {
            return nivelJuego;
        } else {
            nivelJuego = new NivelJuego();
            nivelJuego.setNivel(0L);
            return nivelJuego;
        }
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

    @Override
    public NivelJuego guardarNivelJuego(Usuario usuario, String juego, Integer idRompecabeza) {
        NivelJuego nivelJuego = new NivelJuego();
        nivelJuego.setNivel(Long.valueOf(idRompecabeza));
        nivelJuego.setUsuario(usuario);
        nivelJuego.setNombre(juego);
        return repositorioNivelJuego.guardarNivelJuego(nivelJuego);
    }
    @Override
    public void actualizarNivelVersus(Long usuarioId, Integer nuevoNivel) {
        NivelJuego nivelJuego = repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(usuarioId, "Versus");

        if (nivelJuego == null) {
            nivelJuego = new NivelJuego();
            nivelJuego.setNombre("Versus");
            nivelJuego.setNivel(Long.valueOf(nuevoNivel));
            nivelJuego.setUsuario(new Usuario());
            nivelJuego.getUsuario().setId(usuarioId);
            repositorioNivelJuego.guardarNivelJuego(nivelJuego);
        } else {
            nivelJuego.setNivel(Long.valueOf(nuevoNivel));
            repositorioNivelJuego.actualizarNivelJuego(nivelJuego);
        }
    }
}
