package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/adivinanza-por-voz")
public class ControladorAdivinanzaVoz {

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
        put("tralalero", "Tralalelo Tralala");
        put("tuntuntunsahur", "Tuntuntun Sahur");
    }};

    // GET: muestra la vista con la imagen y formulario
    @GetMapping("")
    public ModelAndView mostrarVoz() {
        List<String> nombresArchivos = new ArrayList<>(imagenes.keySet());
        String nombreArchivo = nombresArchivos.get(new Random().nextInt(nombresArchivos.size()));
        String respuestaCorrecta = imagenes.get(nombreArchivo);

        ModelMap model = new ModelMap();
        model.put("imagen", "/img/versus/" + nombreArchivo + ".png");
        model.put("imagenActual", nombreArchivo);

        return new ModelAndView("adivinanza-por-voz", model);

    }

    // POST: recibe la transcripción y evalúa si es correcta
    @PostMapping("/verificar2")
    public ModelAndView verificarPorVoz(
            @RequestParam String transcripcion,
            @RequestParam String imagenActual,
            @RequestParam(required = false) String aliasDetectado){

        String respuestaCorrecta = imagenes.get(imagenActual);
        boolean esCorrecto = transcripcion.equalsIgnoreCase(respuestaCorrecta);

        System.out.println("🧠 Transcripción recibida: " + transcripcion);
        System.out.println("🎯 Alias detectado: " + aliasDetectado);
        System.out.println("🖼️ Imagen actual: " + imagenActual);
        ModelAndView model = new ModelAndView("verificar2");
        model.addObject("esCorrecto", esCorrecto);
        model.addObject("respuestaCorrecta", respuestaCorrecta);
        model.addObject("transcripcion", transcripcion);
        model.addObject("imagen", "/img/versus/" + imagenActual + ".png");
        model.addObject("aliasDetectado", aliasDetectado); // si lo querés mostrar
        return model;

    }
}