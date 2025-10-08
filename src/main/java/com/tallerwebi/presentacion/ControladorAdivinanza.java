package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller

public class ControladorAdivinanza {



    @RequestMapping("/adivinanza")
    public ModelAndView irAAdivinanza() {

        ModelMap modelo = new ModelMap();
        return new ModelAndView("adivinanza", modelo);
    }


    @RequestMapping(path = "/opcion-correcta", method = RequestMethod.GET)
    public ModelAndView irAOpcionCorrecta() {
        return new ModelAndView("Opcion-correcta");
    }

    @RequestMapping(path = "/opcion-incorrecta", method = RequestMethod.GET)
    public ModelAndView irAOpcionIncorrecta() {
        return new ModelAndView("Opcion-incorrecta");
    }





}

