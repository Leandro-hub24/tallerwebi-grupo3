package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.ServicioRompecabezas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/rompecabezas")
    public ModelAndView irARompecabezasMain(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (request.getSession().getAttribute("id") != null) {
            return new ModelAndView("rompecabezas");
        }

        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);

    }


    @RequestMapping("/rompecabezas/niveles")
    public ModelAndView irARompecabezasNiveles(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (request.getSession().getAttribute("id") != null) {

            ArrayList<Rompecabeza> niveles = servicioRompecabezas.consultarRompecabezasDelUsuario((Long) request.getSession().getAttribute("id"));
            model.put("niveles", niveles);
            return new ModelAndView("rompecabezas", model);

        }

        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);






    }

    @RequestMapping("/rompecabezas/${id_rompecabeza}")
    public ModelAndView irARompecabezasJuego(@PathVariable Long id_rompecabeza, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (request.getSession().getAttribute("id") != null) {
            Rompecabeza nivel = servicioRompecabezas.consultarRompecabeza(id_rompecabeza);
            model.put("nivel", nivel);
            return new ModelAndView("rompecabezas", model);
        }

        model.put("error", "Inicie sesión para jugar");
        return new ModelAndView("redirect:/login", model);



    }



}
