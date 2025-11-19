package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    public void opcionIngresada(PuntosJuego nuevosPuntos, Usuario usuario, int cantidadIntentos, double cantidadTiempoEnSegundos) {
        NivelJuego nivelJuego = repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(usuario.getId(), "AdivinanzaRandom");

        if (nivelJuego == null) {
            nivelJuego = new NivelJuego();
            nivelJuego.setNombre("AdivinanzaRandom");
            nivelJuego.setUsuario(usuario);
        }
        if (nuevosPuntos.getPuntos() == 10) {
            calcularPuntos(nuevosPuntos,cantidadIntentos,cantidadTiempoEnSegundos);
        }
        nuevosPuntos.setNivelJuego(nivelJuego);


         repositorioNivelJuego.guardarNivelJuego(nivelJuego);
         repositorioPuntosJuego.agregarPuntos(nuevosPuntos);
//        Integer puntajeActual = usuario.getPuntajeAdivinanza();
//        usuario.setPuntajeAdivinanza(puntajeActual + 1);
//        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void calcularPuntos(PuntosJuego puntos, int cantidadIntentos, double cantidadTiempoEnSegundos) {
        double nuevosPuntos = (puntos.getPuntos() - (2*cantidadIntentos)) * (cantidadTiempoEnSegundos/10);

        puntos.setPuntos((int) nuevosPuntos);

    }

    @Transactional
    @Override
    public boolean verificarSiEsCorrecto(String nombreImagen, String transcripcion,
                                         int cantidadIntentos, HttpSession session,
                                         int cantidadIntentos1, PuntosJuego puntos,
                                         Usuario usuario, double tiempo
    ) {
        if( transcripcion.equalsIgnoreCase(nombreImagen)){
            puntos.setPuntos(10);
            calcularPuntos(puntos, cantidadIntentos, tiempo);
            this.opcionIngresada(puntos, usuario,cantidadIntentos,tiempo);
            return true;

        }
        else{
//            cantidadIntentos++;
//            session.setAttribute("intentosFallidos", cantidadIntentos);
            return false;
        }
    }
    @Transactional
    @Override
    public void siFallo3IntentosSetPuntos0(int cantidadIntentos, PuntosJuego puntos, Usuario usuario, double tiempo) {
        if (cantidadIntentos >=3){

            puntos.setPuntos(0);
            this.opcionIngresada(puntos, usuario,cantidadIntentos,tiempo);
        }
    }

    @Override
    public void SiLaRespuestaNoEsCorrectaAniadirIntento(boolean esCorrecto, int cantidadIntentos, HttpSession session) {
            cantidadIntentos++;
            session.setAttribute("intentosFallidos", cantidadIntentos);
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
