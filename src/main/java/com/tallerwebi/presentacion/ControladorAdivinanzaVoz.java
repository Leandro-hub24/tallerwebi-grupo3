package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PuntosJuego;
import com.tallerwebi.dominio.ServicioAdivinanza;
import com.tallerwebi.dominio.ServicioAdivinanzaImpl;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/adivinanza-por-voz")
public class ControladorAdivinanzaVoz {
    private final ServicioAdivinanza servicio;


    // Mapa de imágenes y sus nombres (sin extensión)
    private final Map<String, String> imagenes = new LinkedHashMap<>() {{
//        put("bellerina-cappuccina", "Ballerina Capuccina");
//        put("bombardino-crocodilo", "Bombardiro Cocodrilo");
        put("Brr brr patapi", "Brr Brr Patapi");
//        put("capiballero-cocosini", "Capiballero Cocosini");
//        put("capuccino-assasno", "Capuccino Assasino");
        put("Chimpancini bananini", "Chimpancini Bananini");
//        put("liriririlarila", "LiririLarila");
//        put("los-tralaleritos", "Los Tralaleritos");
//        put("saturno-saturnita", "Saturno Saturnita");
        put("Tralalero tralala", "Tralalelo Tralala");
        put("Tung tung tung Sahur", "Tuntuntun Sahur");
    }};

    @Autowired
    public ControladorAdivinanzaVoz(ServicioAdivinanza servicio) {
        this.servicio = servicio;
    }


    // GET: muestra la vista con la imagen y formulario
    @GetMapping("")
    public ModelAndView mostrarVoz(HttpServletRequest request, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        List<String> nombresArchivos = new ArrayList<>(imagenes.keySet());
        String nombreArchivo = nombresArchivos.get(new Random().nextInt(nombresArchivos.size()));

        Integer intentos = (Integer) session.getAttribute("intentosFallidos");
        if (intentos == null) intentos = 0;

        ModelMap model = new ModelMap();
        model.addAttribute("intentos", intentos);
        model.put("imagenActual", nombreArchivo);


        return new ModelAndView("adivinanza-por-voz", model);

    }

    // POST: recibe la transcripción y evalúa si es correcta
    @PostMapping("/verificar2")
    public ModelAndView verificarPorVoz(
            @RequestParam String transcripcion,
            @RequestParam String imagenActual,
            @RequestParam(required = false) String aliasDetectado,
            HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
//        if (usuario == null) {
//            return new ModelAndView("redirect:/login");
//        }
        PuntosJuego puntos = new PuntosJuego();
        String respuestaCorrecta = imagenes.get(imagenActual);
        boolean esCorrecto = transcripcion.equalsIgnoreCase(respuestaCorrecta);
        Integer intentos = (Integer) session.getAttribute("intentosFallidos");
        if (intentos == null) intentos = 0;
        if (esCorrecto){
            puntos.setPuntos(10);
            servicio.opcionIngresada(puntos, usuario);

        }else{
            puntos.setPuntos(0);
            servicio.opcionIngresada(puntos, usuario);
            intentos++;
            session.setAttribute("intentosFallidos", intentos);

        }

        //  Solo mostrar la vista "verificar2" si es el 3er intento (fallido o no)
        if (intentos >= 3 || esCorrecto) {
            session.setAttribute("intentosFallidos", 0);
            ModelAndView model = new ModelAndView("verificar2");
            model.addObject("esCorrecto", esCorrecto);
            model.addObject("respuestaCorrecta", respuestaCorrecta);
            model.addObject("transcripcion", transcripcion);
            model.addObject("imagen", "/img/versus/" + imagenActual + ".png");
            model.addObject("imagenActual", imagenActual);
            model.addObject("aliasDetectado", aliasDetectado);
            model.addObject("intentos", intentos);
            return model;
        }

        //  Si es un intento < 3, redirigir a la pantalla de adivinanza para seguir jugando
        return new ModelAndView("redirect:/adivinanza-por-voz"); // ajustá el endpoint si es diferente
    }
}