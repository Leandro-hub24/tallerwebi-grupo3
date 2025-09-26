package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorAdivinanza {


    @RequestMapping("/Adivinanza")
    public ModelAndView irAAdivinanza() {

        ModelMap modelo = new ModelMap();
        return new ModelAndView("adivinanza", modelo);
    }


    @RequestMapping(path = "/Opcion-correcta", method = RequestMethod.GET)
    public ModelAndView irAOpcionCorrecta() {
        return new ModelAndView("Opcion-correcta");
    }

    @RequestMapping(path = "/Opcion-incorrecta", method = RequestMethod.GET)
    public ModelAndView irAOpcionIncorrecta() {
        return new ModelAndView("Opcion-incorrecta");
    }



}

