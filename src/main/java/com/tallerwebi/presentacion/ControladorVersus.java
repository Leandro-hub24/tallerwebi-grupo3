package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ControladorVersus {

    // Mapa de imágenes y sus nombres (sin extensión)
    private final Map<String, String> imagenes = new LinkedHashMap<>() {{
        put("bellerina-cappuccina", "Ballerina Capuccina");
        put("bombardino-crocodilo", "Bombardiro Cocodrilo");
        put("brrbrrr-patapi", "Brr Brr Patapi");
        put("capiballero-cocosini", "Capiballero Cocosini");
        put("capuccino-assasno", "Capuccino Assasino");
        put("chimpancini-bananini", "Chimpancini Bananini");
        put("liriririlarila", "LiririLarila");
        put("los-tralaleritos", "Los Tralaleritos");
        put("saturno-saturnita", "Saturno Saturnita");
        put("tralalero", "Tralalero Tralala");
        put("tuntuntunsahur", "Tuntuntun Sahur");

    }};

    @GetMapping("/versus")
    public ModelAndView mostrarVersus() {
        // Seleccionar una imagen aleatoria
        List<String> nombresArchivos = new ArrayList<>(imagenes.keySet());
        String nombreArchivo = nombresArchivos.get(new Random().nextInt(nombresArchivos.size()));
        String respuestaCorrecta = imagenes.get(nombreArchivo);

        // Crear opciones de respuesta (mezcladas)
        List<String> opciones = new ArrayList<>(imagenes.values());
        Collections.shuffle(opciones);

        ModelMap model = new ModelMap();
        model.put("imagen", "/img/versus/" + nombreArchivo + ".png");
        model.put("opciones", opciones.subList(0, 5)); // Tomar solo 3 opciones
        model.put("imagenActual", nombreArchivo);
        return new ModelAndView("versus", model);
    }

    @PostMapping("/versus/verificar")
    public ModelAndView verificarRespuesta(
            @RequestParam String respuesta,
            @RequestParam String imagenActual) {

        boolean esCorrecto = respuesta.equals(imagenes.get(imagenActual));
        ModelAndView model = new ModelAndView("resultado-versus");
        model.addObject("esCorrecto", esCorrecto);
        model.addObject("respuestaCorrecta", imagenes.get(imagenActual));
        model.addObject("imagen", "/img/versus/" + imagenActual + ".png");
        return model;
    }


}