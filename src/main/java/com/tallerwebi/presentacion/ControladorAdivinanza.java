package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorAdivinanza {


//    @RequestMapping("/Adivinanza")
//    public ModelAndView irAAdivinanza() {
//
//        ModelMap modelo = new ModelMap();
//        modelo.put("Adivinanza", new Adivinanza());
//        return new ModelAndView("Adivinanza", modelo);
//    }
//

    @RequestMapping(path = "/Opcion-correcta", method = RequestMethod.GET)
    public ModelAndView irAOpcionCorrecta() {
        return new ModelAndView("Opcion-correcta");
    }

    @RequestMapping(path = "/Opcion-incorrecta", method = RequestMethod.GET)
    public ModelAndView irAOpcionIncorrecta() {
        return new ModelAndView("Opcion-incorrecta");
    }



}

