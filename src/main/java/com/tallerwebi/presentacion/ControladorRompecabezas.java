package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.RompecabezasRequest;
import com.tallerwebi.dominio.ServicioRompecabezas;
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

    @Autowired
    public ControladorRompecabezas(ServicioRompecabezas servicioRompecabezas) {
        this.servicioRompecabezas = servicioRompecabezas;
    }

    @RequestMapping(value = "/rompecabezas", method = RequestMethod.GET)
    public ModelAndView irARompecabezasMain(HttpServletRequest request) {


        if (request.getSession().getAttribute("id") != null) {
            Integer rompecabezaNivel = (Integer) request.getSession().getAttribute("rompecabezaNivel");
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
        Integer nivelActualUsuario = (Integer) request.getSession().getAttribute("rompecabezaNivel");
        ModelMap model = new ModelMap();
        List<List<List<String>>> matrizConCambios;


        try {

            matrizConCambios = servicioRompecabezas.moverPieza(matrizActual, idPiezaAMover);
            model.put("matrizConCambios", matrizConCambios);
            boolean gano = servicioRompecabezas.comprobarVictoria(matrizConCambios);
            if (gano) {

                Integer nivelNuevo = servicioRompecabezas.actualizarNivelEnUsuario(idUsuario, idRompecabeza, nivelActualUsuario);
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

        if (request.getSession().getAttribute("rompecabezaNivel") != null) {
            Integer rompecabezaNivel = Integer.valueOf(request.getSession().getAttribute("rompecabezaNivel").toString());
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

        Integer rompecabezaNivel = (Integer) request.getSession().getAttribute("rompecabezaNivel");

        if (request.getSession().getAttribute("id") != null) {
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
