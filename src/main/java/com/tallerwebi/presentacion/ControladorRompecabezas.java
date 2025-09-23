package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.ServicioRompecabezas;
import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class ControladorRompecabezas {

    private ServicioRompecabezas servicioRompecabezas;

    @Autowired
    public ControladorRompecabezas(ServicioRompecabezas servicioRompecabezas) {
        this.servicioRompecabezas = servicioRompecabezas;
    }

    //Lleva a Rompecabezas
    @RequestMapping(value = "/rompecabezas", method = RequestMethod.GET)
    public ModelAndView irARompecabezasMain(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (request.getSession().getAttribute("id") != null) {
            model.addAttribute("rompecabezaNivel", request.getSession().getAttribute("rompecabezaNivel"));
            return new ModelAndView("rompecabezas", model);
        }

        return irALogin();

    }


    @RequestMapping(value = "/rompecabezas/niveles", method = RequestMethod.GET)
    public ModelAndView irARompecabezasNiveles(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (request.getSession().getAttribute("id") != null) {

            try {
                ArrayList<Rompecabeza> niveles = servicioRompecabezas.consultarRompecabezasDelUsuario((Long) request.getSession().getAttribute("id"));
                model.put("niveles", niveles);
            } catch (NivelesNoEncontradosException e) {
                model.put("error", e.getMessage());
            }

            return new ModelAndView("rompecabezas", model);

        }

        return irALogin();

    }

    @RequestMapping(value = "/rompecabezas/${id_rompecabeza}", method =  RequestMethod.GET)
    public ModelAndView irARompecabezasJuego(@PathVariable Long id_rompecabeza, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (request.getSession().getAttribute("id") != null) {
            try {
                Rompecabeza nivel = servicioRompecabezas.consultarRompecabeza(id_rompecabeza);
                model.put("nivel", nivel);
            } catch (NivelesNoEncontradosException e) {
                model.put("error", e.getMessage());
            }

            return new ModelAndView("rompecabezas", model);
        }

        return irALogin();


    }

    private ModelAndView irALogin() {
        ModelMap model = new ModelMap();
        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);
    }


}
