package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.tallerwebi.dominio.ServicioPartidaVersus;

import java.util.*;

@Controller
public class ControladorVersus {

    private ServicioVersus servicioVersus;
    private ServicioNivelVersus servicioNivelVersus;
    private ServicioUsuario servicioUsuario;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ServicioPartidaVersus servicioPartidaVersus;

    @Autowired
    public ControladorVersus(ServicioVersus servicioVersus, ServicioNivelVersus servicioNivelVersus, ServicioUsuario servicioUsuario) {
        this.servicioVersus = servicioVersus;
        this.servicioNivelVersus = servicioNivelVersus;
        this.servicioUsuario = servicioUsuario;
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

    @GetMapping("/versusMultijugador")
    public ModelAndView mostrarVersusMultijugador(
            @RequestParam Long partidaId,
            HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Integer preguntaActual = (Integer) request.getSession().getAttribute("PREGUNTA_ACTUAL");
        List<Brainrot> todosLosBrainrots = (List<Brainrot>) request.getSession().getAttribute("BRAINROTS_MULTIJUGADOR");

        if (todosLosBrainrots == null || preguntaActual == null) {
            todosLosBrainrots = servicioVersus.obtenerTodos();
            request.getSession().setAttribute("MODO_MULTIJUGADOR", true);
            request.getSession().setAttribute("PARTIDA_ID", partidaId);
            request.getSession().setAttribute("PREGUNTA_ACTUAL", 0);
            request.getSession().setAttribute("PUNTOS_ACUMULADOS", 0);
            request.getSession().setAttribute("BRAINROTS_MULTIJUGADOR", todosLosBrainrots);
            preguntaActual = 0;
        }

        if (todosLosBrainrots == null || todosLosBrainrots.isEmpty()) {
            return new ModelAndView("redirect:/home");
        }

        if (preguntaActual >= todosLosBrainrots.size()) {
            return new ModelAndView("redirect:/resumen-nivel");
        }

        Brainrot brainrotActual = todosLosBrainrots.get(preguntaActual);
        List<String> opciones = servicioVersus.obtenerOpcionesAleatoriasParaPreguntaMultijugador(
                brainrotActual.getImagenPersonaje()
        );

        ModelMap model = new ModelMap();
        Boolean esMultijugador = (Boolean) request.getSession().getAttribute("MODO_MULTIJUGADOR");
        if (esMultijugador != null && esMultijugador) {
            Long partidaIdSesion = (Long) request.getSession().getAttribute("PARTIDA_ID");
            PartidaVersus partida = servicioPartidaVersus.buscarPorId(partidaIdSesion);

            if (partida != null) {
                Integer misPuntos = usuario.getId().equals(partida.getJugador1Id()) ?
                        partida.getPuntosJugador1() : partida.getPuntosJugador2();
                Integer puntosRival = usuario.getId().equals(partida.getJugador1Id()) ?
                        partida.getPuntosJugador2() : partida.getPuntosJugador1();

                model.put("puntosRival", puntosRival);

                request.getSession().setAttribute("PUNTOS_ACUMULADOS", misPuntos);
            }
        }
        model.put("imagen", "/img/versus/" + brainrotActual.getImagenFondo() + ".png");
        model.put("opciones", opciones);
        model.put("imagenActual", brainrotActual.getImagenPersonaje());
        model.put("imagenActualCompleta", brainrotActual.getImagenCompleta());
        model.put("audio", brainrotActual.getAudio());
        model.put("imagenshadow", "/img/versus/" + brainrotActual.getImagenSombra() + ".png");
        model.put("esMultijugador", true);
        model.put("partidaId", partidaId);

        return new ModelAndView("versus", model);
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
            model.put("esMultijugador", request.getSession().getAttribute("MODO_MULTIJUGADOR"));
            model.put("partidaId", request.getSession().getAttribute("PARTIDA_ID"));
            return new ModelAndView("resultado-versus", model);
        } else {
            boolean esCorrecto = servicioVersus.verificarRespuesta(respuesta, imagenActual);

            if (esCorrecto) {
                Integer puntosAcumulados = (Integer) request.getSession().getAttribute("PUNTOS_ACUMULADOS");
                request.getSession().setAttribute("PUNTOS_ACUMULADOS", puntosAcumulados + puntos);
            }
            Boolean esMultijugador = (Boolean) request.getSession().getAttribute("MODO_MULTIJUGADOR");
            if (esMultijugador != null && esMultijugador && esCorrecto) {
                Long partidaId = (Long) request.getSession().getAttribute("PARTIDA_ID");

                PartidaVersus partida = servicioPartidaVersus.actualizarPuntos(partidaId, usuario.getId(), puntos);

                if (partida != null) {

                    if (partida.getPuntosJugador1() >= 30 || partida.getPuntosJugador2() >= 30) {
                        partida.setEstado("FINALIZADA");

                        MensajeVersus mensajeFinal = new MensajeVersus();
                        mensajeFinal.setTipo("PARTIDA_FINALIZADA");
                        mensajeFinal.setPartidaId(partida.getId());
                        mensajeFinal.setPuntosJugador1(partida.getPuntosJugador1());
                        mensajeFinal.setPuntosJugador2(partida.getPuntosJugador2());
                        mensajeFinal.setJugador1Id(partida.getJugador1Id());
                        mensajeFinal.setJugador2Id(partida.getJugador2Id());

                        messagingTemplate.convertAndSend("/topic/partida/" + partida.getId(), mensajeFinal);

                        return new ModelAndView("redirect:/resultado-final-versus?partidaId=" + partida.getId());
                    } else {
                        MensajeVersus mensaje = new MensajeVersus();
                        mensaje.setTipo("PUNTOS_ACTUALIZADOS");
                        mensaje.setPartidaId(partida.getId());
                        mensaje.setPuntosJugador1(partida.getPuntosJugador1());
                        mensaje.setPuntosJugador2(partida.getPuntosJugador2());
                        mensaje.setJugador1Id(partida.getJugador1Id());
                        mensaje.setJugador2Id(partida.getJugador2Id());

                        messagingTemplate.convertAndSend("/topic/partida/" + partida.getId(), mensaje);
                    }
                }
            }

            model.put("esTimeOut", false);
            model.put("esCorrecto", esCorrecto);
            model.put("respuestaCorrecta", imagenActual);
            model.put("imagen", "/img/versus/" + imagenActualCompleta + ".png");
            model.put("puntos", esCorrecto ? puntos : 0);
            model.put("nivelActual", nivelActual);
            model.put("esMultijugador", request.getSession().getAttribute("MODO_MULTIJUGADOR"));
            model.put("partidaId", request.getSession().getAttribute("PARTIDA_ID"));
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

                Usuario usuarioDB = servicioUsuario.buscarUsuarioPorId(usuario.getId());
                servicioNivelVersus.actualizarNivelVersus(usuarioDB, nuevoNivel);
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

    @GetMapping("/versus/lobby")
    public ModelAndView mostrarLobby(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();
        model.put("usuarioId", usuario.getId());
        return new ModelAndView("lobby-versus", model);
    }

    @GetMapping("/resultado-final-versus")
    public ModelAndView mostrarResultadoFinal(@RequestParam Long partidaId, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        
        PartidaVersus partida = servicioPartidaVersus.buscarPorId(partidaId);
        
        boolean ganaste = false;
        Integer misPuntos = 0;
        Integer puntosRival = 0;
        
        if (partida != null) {
            if (usuario.getId().equals(partida.getJugador1Id())) {
                misPuntos = partida.getPuntosJugador1();
                puntosRival = partida.getPuntosJugador2();
                ganaste = partida.getPuntosJugador1() >= 30;
            } else {
                misPuntos = partida.getPuntosJugador2();
                puntosRival = partida.getPuntosJugador1();
                ganaste = partida.getPuntosJugador2() >= 30;
            }
        }
        
        ModelMap model = new ModelMap();
        model.put("ganaste", ganaste);
        model.put("misPuntos", misPuntos);
        model.put("puntosRival", puntosRival);
        
        request.getSession().removeAttribute("MODO_MULTIJUGADOR");
        request.getSession().removeAttribute("PARTIDA_ID");
        request.getSession().removeAttribute("PUNTOS_ACUMULADOS");
        request.getSession().removeAttribute("BRAINROTS_MULTIJUGADOR");
        request.getSession().removeAttribute("PREGUNTA_ACTUAL");
        
        return new ModelAndView("resultado-final-versus", model);
    }

}