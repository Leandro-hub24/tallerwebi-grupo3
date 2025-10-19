package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import com.tallerwebi.dominio.excepcion.PiezaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class ControladorRompecabezas {

    private ServicioRompecabezas servicioRompecabezas;
    private ServicioNivelJuego servicioNivelJuego;
    private ServicioUsuario servicioUsuario;
    private ServicioPuntosJuego servicioPuntosJuego;
    private String juego;

    @Autowired
    public ControladorRompecabezas(ServicioRompecabezas servicioRompecabezas, ServicioNivelJuego servicioNivelJuego,
                                   ServicioUsuario servicioUsuario, ServicioPuntosJuego servicioPuntosJuego) {
        this.servicioRompecabezas = servicioRompecabezas;
        this.servicioNivelJuego = servicioNivelJuego;
        this.servicioUsuario = servicioUsuario;
        this.servicioPuntosJuego = servicioPuntosJuego;
        this.juego = "Rompecabezas";
    }

    @RequestMapping(value = "/rompecabezas", method = RequestMethod.GET)
    public ModelAndView irARompecabezasMain(HttpServletRequest request) {

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        String url;
        if (idUsuario != null) {

            NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario, juego);
            Integer rompecabezaNivel = nivelJuego.getNivel().intValue();
            if (rompecabezaNivel < 10) {
                url = "redirect:/rompecabezas/" + (rompecabezaNivel + 1);
            } else {
                url = "redirect:/rompecabezas/" + rompecabezaNivel;
            }


            return new ModelAndView(url);
        }

        return irALogin();

    }

    @RequestMapping(value = "/rompecabezas", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelMap moverFichas(@RequestBody RompecabezasRequest requestRompecabeza, HttpServletRequest request) {


        List<List<List<String>>> matrizActual = requestRompecabeza.getMatriz();
        String idPiezaAMover = requestRompecabeza.getIdPieza();
        Integer idRompecabeza = requestRompecabeza.getIdRompecabeza();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ModelMap model = new ModelMap();
        List<List<List<String>>> matrizConCambios;
        Date inicioPartida = new Date();
        Date finPartida = new Date();


        try {
            matrizConCambios = servicioRompecabezas.moverPieza(matrizActual, idPiezaAMover);
            model.put("matrizConCambios", matrizConCambios);
            boolean gano = servicioRompecabezas.comprobarVictoria(matrizConCambios);
            if (gano) {
                Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
                NivelJuego nivelJuego = servicioNivelJuego.guardarNivelJuego(usuario, juego, idRompecabeza);
                servicioPuntosJuego.guardarPuntosJuegoRompecabeza(nivelJuego, inicioPartida, finPartida);

                model.put("nivelNuevo", nivelJuego.getNivel().intValue() + 1 );
                model.put("mensaje", "Victoria");

            } else {
                model.put("mensaje", "No resuelto");
            }

        } catch (PiezaNoEncontradaException p){
            model.put("error", p.getMessage());
            model.put("matrizConCambios", matrizActual);
        }

        return model;
    }

    @RequestMapping(value = "/rompecabezas/niveles", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelMap irARompecabezasNiveles(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario, juego);
        Integer rompecabezaNivel = nivelJuego.getNivel().intValue() + 1;


        try {
            List<Rompecabeza> niveles = servicioRompecabezas.consultarRompecabezasDelUsuario(rompecabezaNivel);
            model.put("niveles", niveles);

        } catch (NivelesNoEncontradosException e) {
            model.put("error", e.getMessage());

        }
        return model;
    }

    @RequestMapping(value = "/rompecabezas/{idRompecabeza}", method =  RequestMethod.GET)
    public ModelAndView irARompecabezasJuego(@PathVariable Long idRompecabeza, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario, juego);
        Integer rompecabezaNivel = nivelJuego.getNivel().intValue() + 1;

        if (idUsuario != null) {
            if ( idRompecabeza <= rompecabezaNivel && idRompecabeza <= 10) {
                try {
                    Rompecabeza rompecabeza = servicioRompecabezas.consultarRompecabeza(idRompecabeza);
                    model.put("rompecabeza", rompecabeza);
                } catch (NivelesNoEncontradosException e) {
                    model.put("error", e.getMessage());
                    return new ModelAndView("redirect:/rompecabezas");
                }

                return new ModelAndView("rompecabezas", model);
            }
            return new ModelAndView("redirect:/rompecabezas");
        }

        return irALogin();


    }

    private ModelAndView irALogin() {
        ModelMap model = new ModelMap();
        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);
    }


}
