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
import java.util.List;

@Controller
public class ControladorRompecabezas {

    private ServicioRompecabezas servicioRompecabezas;
    private ServicioNivelJuego servicioNivelJuego;

    @Autowired
    public ControladorRompecabezas(ServicioRompecabezas servicioRompecabezas, ServicioNivelJuego servicioNivelJuego) {
        this.servicioRompecabezas = servicioRompecabezas;
        this.servicioNivelJuego = servicioNivelJuego;
    }

    @RequestMapping(value = "/rompecabezas", method = RequestMethod.GET)
    public ModelAndView irARompecabezasMain(HttpServletRequest request) {

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        if (idUsuario != null) {
            NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario);
            Integer rompecabezaNivel = nivelJuego.getNivel().intValue();
            String url = "redirect:/rompecabezas/" + rompecabezaNivel;
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
        Long idUsuario = (Long) request.getSession().getAttribute("id");
        NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario);
        Integer nivelActualUsuario = nivelJuego.getNivel().intValue();
        ModelMap model = new ModelMap();
        List<List<List<String>>> matrizConCambios;


        try {

            matrizConCambios = servicioRompecabezas.moverPieza(matrizActual, idPiezaAMover);
            model.put("matrizConCambios", matrizConCambios);
            boolean gano = servicioRompecabezas.comprobarVictoria(matrizConCambios);
            if (gano) {
                Long ultimoNivel = servicioRompecabezas.buscarUltimoNivelId();
                Integer nivelNuevo = servicioNivelJuego.actualizarNivelJuego(idUsuario, idRompecabeza, nivelActualUsuario, ultimoNivel);
                if (nivelNuevo != null) {
                    request.getSession().setAttribute("rompecabezaNivel", nivelNuevo );
                    model.put("nivelNuevo", nivelNuevo);
                    model.put("mensaje", "Victoria");
                } else {
                    model.put("nivelNuevo", idRompecabeza + 1);
                    model.put("mensaje", "Victoria");
                }

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
        NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario);
        Integer rompecabezaNivel = nivelJuego.getNivel().intValue();

        if (rompecabezaNivel != null) {
            try {
                List<Rompecabeza> niveles = servicioRompecabezas.consultarRompecabezasDelUsuario(rompecabezaNivel);
                model.put("niveles", niveles);
            } catch (NivelesNoEncontradosException e) {
                model.put("error", e.getMessage());
            }

            return model;
        }

        model.put("error", "Nivel no existe");
        return model;

    }

    @RequestMapping(value = "/rompecabezas/{idRompecabeza}", method =  RequestMethod.GET)
    public ModelAndView irARompecabezasJuego(@PathVariable Long idRompecabeza, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        NivelJuego nivelJuego = servicioNivelJuego.buscarNivelJuegoPorIdUsuario(idUsuario);
        Integer rompecabezaNivel = nivelJuego.getNivel().intValue();

        if (idUsuario != null) {
            if ( idRompecabeza <= rompecabezaNivel) {
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
