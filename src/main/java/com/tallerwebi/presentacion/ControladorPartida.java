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
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.*;

import static javax.swing.UIManager.put;

@Controller
public class ControladorPartida {

    private final String juego;
    private ServicioPartida servicioPartida;
    private ServicioRompecabezas servicioRompecabezas;
    private ServicioNivelJuego servicioNivelJuego;
    private ServicioPuntosJuego servicioPuntosJuego;
    private ServicioUsuario servicioUsuario;

    private final Map<String, String> imagenes = new LinkedHashMap<>() {{
//        put("bellerina-cappuccina", "Ballerina Capuccina");
//        put("bombardino-crocodilo", "Bombardiro Cocodrilo");
        put("Brr brr patapi", "Brr Brr Patapi");
//        put("capiballero-cocosini", "Capiballero Cocosini");
//        put("capuccino-assasno", "Capuccino Assasino");
        put("Chimpancini bananini", "Chimpancini Bananini");
//        put("liriririlarila", "LiririLarila");
//        put("los-tralaleritos", "Los Tralaleritos");
//        put("saturno-saturnita", "Saturno Saturnita");
        put("Tralalero tralala", "Tralalelo Tralala");
        put("Tung tung tung Sahur", "Tuntuntun Sahur");
    }};

    @Autowired
    public ControladorPartida(ServicioPartida servicioPartida, ServicioRompecabezas servicioRompecabezas,
                              ServicioNivelJuego servicioNivelJuego, ServicioPuntosJuego servicioPuntosJuego,
                              ServicioUsuario servicioUsuario) {

        this.servicioPartida = servicioPartida;
        this.servicioRompecabezas = servicioRompecabezas;
        this.servicioNivelJuego = servicioNivelJuego;
        this.servicioPuntosJuego = servicioPuntosJuego;
        this.servicioUsuario = servicioUsuario;
        this.juego = "Rompecabezas";
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

    @RequestMapping(value = "/adivinanzaPorVoz/lobby", method = RequestMethod.GET)
    public ModelAndView irAlLobbyAdivinanza(HttpServletRequest request) {

        if(request.getSession().getAttribute("id") == null){
            return redirectLogin();
        }

        ModelAndView mav = new ModelAndView("lobbyAdivinanza");
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
        Partida partida = servicioPartida.crearPartida(nombrePartida, creador.intValue(), username, "rompecabezas");

        return new ModelAndView("redirect:/partida/" + partida.getId());
    }

    @RequestMapping(value = "/adivinanza/partida", method = RequestMethod.POST)
    public ModelAndView crearPartidaAdivinanza(String nombrePartida, HttpServletRequest request) {

        if(request.getSession().getAttribute("id") == null){
            return redirectLogin();
        }

        Long creador = (Long) request.getSession().getAttribute("id");
        String username = (String) request.getSession().getAttribute("username");
        Partida partida = servicioPartida.crearPartida(nombrePartida, creador.intValue(), username, "adivinanza");

        return new ModelAndView("redirect:/adivinanza/partida/" + partida.getId());
    }

    @RequestMapping(value = "/partida/{idPartida}", method = RequestMethod.GET)
    public ModelAndView unirseAPartida(
            @PathVariable("idPartida") String idPartida, HttpServletRequest request) {


        if(request.getSession().getAttribute("id") == null){
            return redirectLogin();
        }

        Long jugador = (Long) request.getSession().getAttribute("id");
        String username = (String) request.getSession().getAttribute("username");
        try {

            Partida partida = servicioPartida.unirJugador(idPartida, jugador.intValue(), username, "rompecabezas");

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

    @RequestMapping(value = "/adivinanza/partida/{idPartida}", method = RequestMethod.GET)
    public ModelAndView unirseAPartidaAdivinanza(
            @PathVariable("idPartida") String idPartida, HttpServletRequest request, HttpSession session) {

        List<String> nombresArchivos = new ArrayList<>(imagenes.keySet());
        String nombreArchivo = nombresArchivos.get(new Random().nextInt(nombresArchivos.size()));
        Integer intentos = (Integer) session.getAttribute("intentosFallidos");
        if (intentos == null) intentos = 0;



        if(request.getSession().getAttribute("id") == null){
            return redirectLogin();
        }

        Long jugador = (Long) request.getSession().getAttribute("id");
        String username = (String) request.getSession().getAttribute("username");
        try {

            Partida partida = servicioPartida.unirJugador(idPartida, jugador.intValue(), username, "adivinanza");

            if(partida.getEstado().equals("TERMINADA")){
                return new ModelAndView("redirect:/rompecabezas/lobby");
            }


            request.getSession().setAttribute("partidaId", partida.getId());
            ModelAndView mav = new ModelAndView("adivinanza-por-voz-multijugador.html");
            mav.addObject("partida", partida);
            mav.addObject("idPartida", partida.getId());

            mav.addObject("estadoInicial", partida.getEstado());
            mav.addObject("miUsuarioId", jugador.intValue());
            mav.addObject("miNombreUsuario", username);

            mav.addObject("imagenActual", nombreArchivo);
            mav.addObject("intentos", intentos);


            return mav;

        } catch (PartidaLlenaException | PartidaNoEncontradaException e) {
            ModelAndView mav = new ModelAndView("redirect:/adivinanza/lobby");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }


    @RequestMapping(value = "/partida/finalizar", method = RequestMethod.POST)
    @ResponseBody
    public void finalizarPartida(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        String partidaId = (String) request.getSession().getAttribute("partidaId");
        servicioPartida.terminarPartida(partidaId, usuarioId.intValue(), "rompecabezas");
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        NivelJuego nivelJuego = servicioNivelJuego.guardarNivelJuego(usuario, juego, 1);
        servicioPuntosJuego.guardarPuntosJuegoRompecabezaMultijugador(nivelJuego);

    }

    @RequestMapping(value = "/partidaAdivinanza/finalizar", method = RequestMethod.POST)
    @ResponseBody
    public void finalizarPartidaAdivinanza(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        String partidaId = (String) request.getSession().getAttribute("partidaId");
        servicioPartida.terminarPartida(partidaId, usuarioId.intValue(), "adivinanza");

    }


    private ModelAndView redirectLogin() {
        ModelMap model = new ModelMap();
        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);
    }


}
