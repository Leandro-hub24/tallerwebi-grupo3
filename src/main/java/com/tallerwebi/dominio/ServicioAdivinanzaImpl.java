package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ServicioAdivinanzaImpl implements ServicioAdivinanza {

    private RepositorioNivelJuego repositorioNivelJuego;
    private RepositorioPuntosJuego repositorioPuntosJuego;

    @Autowired
    public ServicioAdivinanzaImpl(
                                  RepositorioNivelJuego repositorioNivelJuego,
                                  RepositorioPuntosJuego repositorioPuntosJuego) {

        this.repositorioNivelJuego = repositorioNivelJuego;
        this.repositorioPuntosJuego = repositorioPuntosJuego;

    }



    @Transactional
    @Override
    public void opcionIngresada(PuntosJuego nuevosPuntos, Usuario usuario) {
        NivelJuego nivelJuego = repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(usuario.getId(), "AdivinanzaRandom");

        if (nivelJuego == null) {
            nivelJuego = new NivelJuego();
            nivelJuego.setNombre("AdivinanzaRandom");
            nivelJuego.setUsuario(usuario);
        }
        nuevosPuntos.setNivelJuego(nivelJuego);


         repositorioNivelJuego.guardarNivelJuego(nivelJuego);
         repositorioPuntosJuego.agregarPuntos(nuevosPuntos);
//        Integer puntajeActual = usuario.getPuntajeAdivinanza();
//        usuario.setPuntajeAdivinanza(puntajeActual + 1);
//        repositorioUsuario.modificar(usuario);
    }

//    @Transactional
//    @Override
//    public void opcionIncorrecta(Usuario usuario) {
//        Integer puntajeActual = usuario.getPuntajeAdivinanza();
//        if (puntajeActual == null) {
//            puntajeActual = 0;
//        }
//
//        if (puntajeActual > 0) {
//            usuario.setPuntajeAdivinanza(puntajeActual - 1);
//        } else {
//            usuario.setPuntajeAdivinanza(0); // para asegurar que no quede null
//        }
//
//        repositorioUsuario.modificar(usuario);
//    }
}
