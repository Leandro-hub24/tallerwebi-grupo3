package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PartidaLlenaException;
import com.tallerwebi.dominio.excepcion.PartidaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorPartida {

    private ServicioPartida servicioPartida;
    private ServicioRompecabezas servicioRompecabezas;

    @Autowired
    public ControladorPartida(ServicioPartida servicioPartida, ServicioRompecabezas servicioRompecabezas) {
        this.servicioPartida = servicioPartida;
        this.servicioRompecabezas = servicioRompecabezas;
    }


    @RequestMapping(value = "/rompecabezas/lobby", method = RequestMethod.GET)
    public ModelAndView irAlLobby(HttpServletRequest request) {

        if(request.getSession().getAttribute("id") == null){
           return redirectLogin();
        }

        ModelAndView mav = new ModelAndView("lobbyRompecabezas");
        mav.addObject("partidas", servicioPartida.getPartidasAbiertas());
        return mav;
    }


    @RequestMapping(value = "/partida", method = RequestMethod.POST)
    public ModelAndView crearPartida(String nombrePartida, HttpServletRequest request) {

        if(request.getSession().getAttribute("id") == null){
            return redirectLogin();
        }

        Long creador = (Long) request.getSession().getAttribute("id");
        String username = (String) request.getSession().getAttribute("username");
        Partida partida = servicioPartida.crearPartida(nombrePartida, creador.intValue(), username);

        return new ModelAndView("redirect:/partida/" + partida.getId());
    }


    @RequestMapping(value = "/partida/{idPartida}", method = RequestMethod.GET)
    public ModelAndView unirseAPartidaHTTP(
            @PathVariable("idPartida") String idPartida, HttpServletRequest request) {


        if(request.getSession().getAttribute("id") == null){
            return redirectLogin();
        }

        Long jugador = (Long) request.getSession().getAttribute("id");
        String username = (String) request.getSession().getAttribute("username");
        try {

            Partida partida = servicioPartida.unirJugador(idPartida, jugador.intValue(), username);

            if(partida.getEstado().equals("TERMINADA")){
                return new ModelAndView("redirect:/rompecabezas/lobby");
            }

            Rompecabeza rompecabeza = servicioRompecabezas.consultarRompecabeza(1L);
            request.getSession().setAttribute("partidaId", partida.getId());
            ModelAndView mav = new ModelAndView("rompecabezasMultijugador");
            mav.addObject("partida", partida);
            mav.addObject("idPartida", partida.getId());
            mav.addObject("rompecabeza", rompecabeza);
            mav.addObject("estadoInicial", partida.getEstado());
            mav.addObject("miUsuarioId", jugador.intValue());
            mav.addObject("miNombreUsuario", username);
            mav.addObject("tiempo", partida.getFechaInicio());

            return mav;

        } catch (PartidaLlenaException | PartidaNoEncontradaException e) {
            ModelAndView mav = new ModelAndView("redirect:/rompecabezas/lobby");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }


    @RequestMapping(value = "/partida/finalizar", method = RequestMethod.POST)
    @ResponseBody
    public void finalizarPartida(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        String partidaId = (String) request.getSession().getAttribute("partidaId");
        servicioPartida.terminarPartida(partidaId, usuarioId.intValue());

    }


    private ModelAndView redirectLogin() {
        ModelMap model = new ModelMap();
        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);
    }


}
