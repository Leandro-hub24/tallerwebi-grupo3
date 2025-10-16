package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilJugador;
import com.tallerwebi.presentacion.DtoPerfilJugador.DatosPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class ControladorPerfilJugador {
    private final ServicioPerfilJugador servicioPerfilJugador;

    @Autowired
    public ControladorPerfilJugador(ServicioPerfilJugador servicioPerfilJugador){
        this.servicioPerfilJugador = servicioPerfilJugador;
    }

    @RequestMapping("/perfilJugador")
    public ModelAndView irAPerfilJugador(){
        ModelMap model = new ModelMap();
        model.put("mensaje","Perfil del jugador");
        return new ModelAndView("vista-perfil-jugador", model);
    }
    @RequestMapping("/estadisticas")
    public ModelAndView irAEstadisticas(){
        List<DatosPerfil> perfiles = servicioPerfilJugador.mostrarPerfilDeJugador();
        ModelMap model = new ModelMap();
        model.put("modelPerfil", perfiles);
        return new ModelAndView("vista-estadisticas", model);
    }
}
