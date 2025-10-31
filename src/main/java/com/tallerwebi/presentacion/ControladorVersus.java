package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Brainrot;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNivelJuego;
import com.tallerwebi.dominio.ServicioVersus;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ControladorVersus {

    private ServicioVersus servicioVersus;
    private ServicioNivelJuego servicioNivelJuego;

    @Autowired
    public ControladorVersus(ServicioVersus servicioVersus, ServicioNivelJuego servicioNivelJuego, ServicioLogin servicioLogin) {
        this.servicioVersus = servicioVersus;
        this.servicioNivelJuego = servicioNivelJuego;
    }

    @GetMapping("/seleccionar-nivel")
    public ModelAndView mostrarSeleccionNivel(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("seleccionar-nivel", model);
    }

    @GetMapping("/versus")
    public ModelAndView mostrarVersus(
            @RequestParam(required = false, defaultValue = "1") Integer nivel,
            HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        if (nivel > usuario.getVersusNivel()) {
            return new ModelAndView("redirect:/seleccionar-nivel");
        }
        Integer nivelActual = (Integer) request.getSession().getAttribute("NIVEL_ACTUAL");
        Integer preguntaActual = (Integer) request.getSession().getAttribute("PREGUNTA_ACTUAL");
        List<Brainrot> brainrotsDelNivel = (List<Brainrot>) request.getSession().getAttribute("BRAINROTS_NIVEL");

        if (nivelActual == null || !nivelActual.equals(nivel)) {
            brainrotsDelNivel = servicioVersus.obtenerTodosPorNivel(nivel);

            request.getSession().setAttribute("NIVEL_ACTUAL", nivel);
            request.getSession().setAttribute("PREGUNTA_ACTUAL", 0);
            request.getSession().setAttribute("PUNTOS_ACUMULADOS", 0);
            request.getSession().setAttribute("BRAINROTS_NIVEL", brainrotsDelNivel);

            preguntaActual = 0;
        }

        if (brainrotsDelNivel == null || brainrotsDelNivel.isEmpty()) {
            return new ModelAndView("redirect:/seleccionar-nivel");
        }

        if (preguntaActual >= brainrotsDelNivel.size()) {
            return new ModelAndView("redirect:/resumen-nivel");
        }

        Brainrot brainrotActual = brainrotsDelNivel.get(preguntaActual);
        List<String> opciones = servicioVersus.obtenerOpcionesAleatoriasParaPregunta(
                nivel,
                brainrotActual.getImagenPersonaje()
        );

        ModelMap model = new ModelMap();
        model.put("imagen", "/img/versus/" + brainrotActual.getImagenFondo() + ".png");
        model.put("opciones", opciones);
        model.put("imagenActual", brainrotActual.getImagenPersonaje());
        model.put("imagenActualCompleta", brainrotActual.getImagenCompleta());
        model.put("audio", brainrotActual.getAudio());
        model.put("imagenshadow", "/img/versus/" + brainrotActual.getImagenSombra() + ".png");
        return new ModelAndView("versus", model);

    }

    @PostMapping("/versus/verificar")
    public ModelAndView verificarRespuesta(
            @RequestParam(required = false) String respuesta,
            @RequestParam String imagenActualCompleta,
            @RequestParam String imagenActual,
            @RequestParam(value = "timeout", required = false, defaultValue = "false") String timeout,
            @RequestParam(value = "puntos", required = false, defaultValue = "3") int puntos, HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Integer preguntaActual = (Integer) request.getSession().getAttribute("PREGUNTA_ACTUAL");

        request.getSession().setAttribute("PREGUNTA_ACTUAL", preguntaActual + 1);
        Integer nivelActual = (Integer) request.getSession().getAttribute("NIVEL_ACTUAL");
        ModelMap model = new ModelMap();
        if (servicioVersus.esTimeOut(timeout)){
            model.put("esTimeOut", true);
            model.put("esCorrecto", false);
            model.put("respuestaCorrecta", imagenActual);
            model.put("imagen", "/img/versus/" + imagenActualCompleta + ".png");
            model.put("nivelActual", nivelActual);
            return new ModelAndView("resultado-versus", model);
        } else {
            boolean esCorrecto = servicioVersus.verificarRespuesta(respuesta, imagenActual);

            if (esCorrecto) {
                Integer puntosAcumulados = (Integer) request.getSession().getAttribute("PUNTOS_ACUMULADOS");
                request.getSession().setAttribute("PUNTOS_ACUMULADOS", puntosAcumulados + puntos);
            }

            model.put("esTimeOut", false);
            model.put("esCorrecto", esCorrecto);
            model.put("respuestaCorrecta", imagenActual);
            model.put("imagen", "/img/versus/" + imagenActualCompleta + ".png");
            model.put("puntos", esCorrecto ? puntos : 0);
            model.put("nivelActual", nivelActual);
        }

        return new ModelAndView("resultado-versus", model);
    }

    @GetMapping("/resumen-nivel")
    public ModelAndView mostrarResumenNivel(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Integer nivelActual = (Integer) request.getSession().getAttribute("NIVEL_ACTUAL");
        Integer puntosAcumulados = (Integer) request.getSession().getAttribute("PUNTOS_ACUMULADOS");

        if (nivelActual == null) {
            return new ModelAndView("redirect:/seleccionar-nivel");
        }

        boolean pasaNivel = puntosAcumulados != null && puntosAcumulados >= 8;
        
        if (pasaNivel && nivelActual.equals(usuario.getVersusNivel())) {
            if (usuario.getVersusNivel() < 4) {
                Integer nuevoNivel = usuario.getVersusNivel() + 1;
                usuario.setVersusNivel(nuevoNivel);

                servicioNivelJuego.actualizarNivelVersus(usuario.getId(), nuevoNivel);
            }
        }

        ModelMap model = new ModelMap();
        if (puntosAcumulados != null) {
            model.put("puntosAcumulados", puntosAcumulados);
        } else {
            model.put("puntosAcumulados", 0);
        }
        model.put("puntosMaximos", 12);
        model.put("pasaNivel", pasaNivel);
        model.put("nivelActual", nivelActual);
        model.put("nivelSiguiente", nivelActual + 1);

        request.getSession().removeAttribute("NIVEL_ACTUAL");
        request.getSession().removeAttribute("PREGUNTA_ACTUAL");
        request.getSession().removeAttribute("PUNTOS_ACUMULADOS");
        request.getSession().removeAttribute("BRAINROTS_NIVEL");

        return new ModelAndView("resumen-nivel", model);
    }
}