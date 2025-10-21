package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Brainrot;
import com.tallerwebi.dominio.ServicioVersus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ControladorVersus {

    private ServicioVersus servicioVersus;

    @Autowired
    public ControladorVersus(ServicioVersus servicioVersus) {
        this.servicioVersus = servicioVersus;
    }

    @GetMapping("/versus")
    public ModelAndView mostrarVersus() {

        Brainrot brainrotActual = servicioVersus.obtenerBrainrotAleatorio();//El brainrot completo
        List<String> opciones = servicioVersus.obtenerOpcionesAleatorias();//[Tralalero tralala, Tung tung tung Sahur, ...]

        ModelMap model = new ModelMap();
        model.put("imagen", "/img/versus/" + brainrotActual.getImagenFondo() + ".png");
        model.put("opciones", opciones);
        model.put("imagenActual", brainrotActual.getImagenPersonaje());
        model.put("imagenActualCompleta", brainrotActual.getImagenCompleta());
        model.put("audio", brainrotActual.getAudio());
        model.put("imagenshadow", "/img/versus/" + brainrotActual.getImagenSombra() + ".png");
        return new ModelAndView("versus", model)
//        model = {
//                "imagen": "/img/versus/tralalerobg.png",
//                "opciones": ["Tralalero tralala", "Tung tung tung Sahur", "Chimpancini bananini", "Brr brr patapi"],
//                "imagenActual": "Tralalero tralala",
//                "imagenActualCompleta": "tralalerocom",
//                "audio": "tralalerosound",
//                "imagenshadow": "/img/versus/tralaleroshadow.png"
//        }
        ;
    }

    @PostMapping("/versus/verificar")
    public ModelAndView verificarRespuesta(
            @RequestParam String respuesta,
            @RequestParam String imagenActualCompleta,
            @RequestParam String imagenActual) {

        boolean esCorrecto = servicioVersus.verificarRespuesta(respuesta, imagenActual);

        ModelMap model = new ModelMap();
        model.put("esCorrecto", esCorrecto);
        model.put("respuestaCorrecta", imagenActual);
        model.put("imagen", "/img/versus/" + imagenActualCompleta + ".png");
        return new ModelAndView("resultado-versus", model);
//        model = {
//                "esCorrecto": true/false,
//                "respuestaCorrecta": "Tralalero tralala",
//                "imagen": "/img/versus/tralalerocom.png"
//        }
    }
}