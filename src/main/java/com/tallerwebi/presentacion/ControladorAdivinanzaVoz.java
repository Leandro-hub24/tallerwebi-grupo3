package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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
    private final ServicioPartida servicioPartida;


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
    public ControladorAdivinanzaVoz(ServicioAdivinanza servicio, ServicioPartida servicioPartida) {

        this.servicio = servicio;
        this.servicioPartida = servicioPartida;
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
            HttpSession session,
            @RequestParam int cantidadIntentos,
            @RequestParam double tiempo){
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        Integer intentos = (Integer) session.getAttribute("intentosFallidos");
        if (intentos == null) intentos = 0;
//        if (usuario == null) {
//            return new ModelAndView("redirect:/login");
//        }

        PuntosJuego puntos = new PuntosJuego();
        boolean esCorrecto = servicio.verificarSiEsCorrecto(
                imagenes.get(imagenActual), transcripcion, cantidadIntentos,session, cantidadIntentos,puntos,usuario,tiempo );

       String respuestaCorrecta = imagenes.get(imagenActual);
//        boolean esCorrecto = transcripcion.equalsIgnoreCase(respuestaCorrecta);
//        Integer intentos = (Integer) session.getAttribute("intentosFallidos");
//        if (intentos == null) intentos = 0;
        servicio.SiLaRespuestaNoEsCorrectaAniadirIntento(esCorrecto,intentos,session);
        servicio.siFallo3IntentosSetPuntos0 (cantidadIntentos,puntos,usuario,tiempo);
//        if (!esCorrecto){
//            intentos++;
//            session.setAttribute("intentosFallidos", intentos);
//
//        }


        //  Solo mostrar la vista "verificar2" si es el 3er intento fallido
        if (intentos >= 2 || esCorrecto) {
            // servicio.siEsCorrectoSetpuntos10(servicio.verificarSIEsCorrecto);
//            if (esCorrecto) {
//                puntos.setPuntos(10);
//                servicio.opcionIngresada(puntos, usuario,cantidadIntentos,tiempo);
//
//            }
//             servicio.siFallo3IntentosSetPuntos0 (cantidadIntentos,puntos,usuario,tiempo);
//            if (cantidadIntentos >=3){
//                puntos.setPuntos(0);
//                servicio.opcionIngresada(puntos, usuario,cantidadIntentos,tiempo);
//            }
            session.setAttribute("intentosFallidos", 0);
            ModelAndView model = new ModelAndView("verificar2");
            model.addObject("esCorrecto", esCorrecto);
            model.addObject("respuestaCorrecta", respuestaCorrecta);
            model.addObject("transcripcion", transcripcion);
            model.addObject("imagen", "/img/versus/" + imagenActual + ".png");
            model.addObject("imagenActual", imagenActual);
            model.addObject("aliasDetectado", aliasDetectado);
            model.addObject("intentos", cantidadIntentos);
            model.addObject("puntos", puntos.getPuntos());
            return model;
        }

        //  Si es un intento < 3, redirigir a la pantalla de adivinanza para seguir jugando
        return new ModelAndView("redirect:/adivinanza-por-voz");
    }
    // POST: recibe la transcripción y evalúa si es correcta
    @PostMapping("/verificarMultijugador")
    public ModelAndView verificarPorVozMultijugador(
            @RequestParam String transcripcion,
            @RequestParam String imagenActual,
            @RequestParam(required = false) String aliasDetectado,
            HttpSession session,
            @RequestParam int cantidadIntentos,
            @RequestParam double tiempo,
            @RequestParam String idPartida,
            @RequestParam Integer usuarioId,
            @RequestParam int jugador2){
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        Integer intentos = (Integer) session.getAttribute("intentosFallidos");
        if (intentos == null) intentos = 0;


        PuntosJuego puntos = new PuntosJuego();
        boolean esCorrecto = servicio.verificarSiEsCorrecto(
                imagenes.get(imagenActual), transcripcion, cantidadIntentos,session, cantidadIntentos,puntos,usuario,tiempo );

        String respuestaCorrecta = imagenes.get(imagenActual);

        servicio.SiLaRespuestaNoEsCorrectaAniadirIntento(esCorrecto,intentos,session);
        servicio.siFallo3IntentosSetPuntos0 (cantidadIntentos,puntos,usuario,tiempo);
        servicioPartida.registrarIntento(idPartida,usuarioId);

        //  Solo mostrar la vista "verificar2" si es el 3er intento fallido
        if (intentos >= 2 || esCorrecto) {

            session.setAttribute("intentosFallidos", 0);
            ModelAndView model = new ModelAndView("verificarMultijugador");
            model.addObject("esCorrecto", esCorrecto);
            model.addObject("respuestaCorrecta", respuestaCorrecta);
            model.addObject("transcripcion", transcripcion);
            model.addObject("imagen", "/img/versus/" + imagenActual + ".png");
            model.addObject("imagenActual", imagenActual);
            model.addObject("aliasDetectado", aliasDetectado);
            model.addObject("intentos", cantidadIntentos);
            model.addObject("puntos", puntos.getPuntos());

                servicioPartida.terminarPartida(idPartida, usuarioId, "adivinanza");



            return model;

        }

        //  Si es un intento < 3, redirigir a la pantalla de adivinanza para seguir jugando
        return new ModelAndView("redirect:/adivinanza/partida/" + idPartida);
    }
}