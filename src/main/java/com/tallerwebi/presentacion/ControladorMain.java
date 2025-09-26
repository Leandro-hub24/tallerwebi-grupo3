package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMain {

    //Redirige a main
    @RequestMapping("/main")
    public ModelAndView irAMain() {

        //ModelMap modelo = new ModelMap();
        //modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("main");

    }

}
