package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DtoPerfilJugador.DatosPerfil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioPerfilJugador")
@Transactional
public class ServicioPerfilJugadorImpl implements ServicioPerfilJugador {

    @Override
    public List<DatosPerfil> mostrarPerfilDeJugador() {
       //implementacion de la logica para obtener los datos del perfil del jugador
       /*
        private String NombreJugador;
    private Integer PuntosAcumulados;
    private Integer CantidadRompecabezasCompletados;
    private Integer CantidadBrainRotCreados;
    private Integer CantidadBrainrotAdivinados ;
    private Integer JuegosGanadosEnModoVersus;
    private Integer tiempoDejuego; // en segundos
       * */

        List<DatosPerfil> datosPerfil = new ArrayList<>();
        //crear datos de ejemplo

        DatosPerfil p1 = new DatosPerfil("Juan", 1500,
                10,
                5, 20,
                3, 3600);
        datosPerfil.add(p1);
        DatosPerfil p2 = new DatosPerfil("Maria", 2000,
                15,
                7, 25,
                5, 5400);
        datosPerfil.add(p2);

        return datosPerfil;
    }
}
