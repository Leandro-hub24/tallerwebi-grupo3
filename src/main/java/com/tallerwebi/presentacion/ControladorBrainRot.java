package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorBrainRot {

    private ServicioBrainRot servicioBrainRot;

    @Autowired
    public ControladorBrainRot(ServicioBrainRot servicioBrainRot) {
        this.servicioBrainRot = servicioBrainRot;
    }

    @RequestMapping("/brain-rot")
    public ModelAndView irAMenuBrainRot(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        
        // Verificar si el usuario está logueado
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener estadísticas del usuario
        Long victorias = servicioBrainRot.obtenerVictoriasPorUsuario(usuario);
        Double porcentajeAcierto = servicioBrainRot.obtenerPorcentajeAcierto(usuario);
        Integer mejorRacha = servicioBrainRot.obtenerMejorRacha(usuario);

        modelo.put("usuario", usuario);
        modelo.put("victorias", victorias);
        modelo.put("porcentajeAcierto", String.format("%.1f", porcentajeAcierto));
        modelo.put("mejorRacha", mejorRacha);

        return new ModelAndView("brain-rot-menu", modelo);
    }

    @RequestMapping(path = "/brain-rot/nueva-partida", method = RequestMethod.POST)
    public ModelAndView iniciarNuevaPartida(HttpServletRequest request) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Iniciar nueva partida
        BrainRotPartida partida = servicioBrainRot.iniciarNuevaPartida(usuario);
        
        // Obtener primera pregunta
        BrainRotPregunta pregunta = servicioBrainRot.obtenerSiguientePregunta(partida);
        // Si no hay preguntas disponibles, volver al menú con un mensaje
        if (pregunta == null) {
            // Opcional: podríamos finalizar la partida si corresponde
            return new ModelAndView("redirect:/brain-rot");
        }
        
        // Guardar IDs en sesión para el juego
        request.getSession().setAttribute("partidaId", partida.getId());
        request.getSession().setAttribute("preguntaId", pregunta.getId());

        return new ModelAndView("redirect:/brain-rot/jugar");
    }

    @RequestMapping("/brain-rot/jugar")
    public ModelAndView mostrarJuego(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener partida activa
        BrainRotPartida partida = servicioBrainRot.obtenerPartidaActiva(usuario);
        if (partida == null) {
            return new ModelAndView("redirect:/brain-rot");
        }

        // Verificar si la partida ya terminó
        if (partida.estaTerminada()) {
            return new ModelAndView("redirect:/brain-rot/resultado");
        }

        // Obtener pregunta actual
        BrainRotPregunta pregunta = servicioBrainRot.obtenerSiguientePregunta(partida);
        if (pregunta == null) {
            // No hay pregunta para mostrar (posible falta de datos). Volver al menú.
            return new ModelAndView("redirect:/brain-rot");
        }
        // Guardar el id de la pregunta mostrada en sesión para evaluar la misma
        request.getSession().setAttribute("preguntaId", pregunta.getId());
        
        // Crear objeto para capturar la respuesta
        DatosBrainRotRespuesta datosRespuesta = new DatosBrainRotRespuesta();

        modelo.put("partida", partida);
        modelo.put("pregunta", pregunta);
        modelo.put("datosRespuesta", datosRespuesta);
        modelo.put("usuario", usuario);

        return new ModelAndView("brain-rot-juego", modelo);
    }

    @RequestMapping(path = "/brain-rot/responder", method = RequestMethod.POST)
    public ModelAndView procesarRespuesta(@ModelAttribute("datosRespuesta") DatosBrainRotRespuesta datosRespuesta,
                                        HttpServletRequest request) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener partida activa
        BrainRotPartida partida = servicioBrainRot.obtenerPartidaActiva(usuario);
        if (partida == null) {
            return new ModelAndView("redirect:/brain-rot");
        }

        // Obtener pregunta actual (desde sesión, fue guardada al mostrarla)
        Long preguntaId = (Long) request.getSession().getAttribute("preguntaId");
        if (preguntaId == null) {
            return new ModelAndView("redirect:/brain-rot/jugar");
        }

        // Buscar la misma pregunta que se mostró al usuario
        BrainRotPregunta pregunta = servicioBrainRot.buscarPreguntaPorId(preguntaId);

        // Asegurar que tomamos la opción seleccionada por el usuario
        String seleccion = datosRespuesta.getRespuestaSeleccionada();
        if (seleccion == null || seleccion.isEmpty()) {
            seleccion = request.getParameter("respuestaSeleccionada");
        }
        if (seleccion != null) {
            seleccion = seleccion.trim().toUpperCase();
        }

        // Procesar la respuesta
        BrainRotRespuesta respuesta = servicioBrainRot.procesarRespuesta(
            partida, pregunta, seleccion
        );

        // Guardar información del resultado en sesión para mostrar feedback
        request.getSession().setAttribute("ultimaRespuesta", respuesta);

        // Verificar si la partida terminó
        if (partida.estaTerminada()) {
            return new ModelAndView("redirect:/brain-rot/resultado");
        } else {
            return new ModelAndView("redirect:/brain-rot/feedback");
        }
    }

    @RequestMapping("/brain-rot/feedback")
    public ModelAndView mostrarFeedback(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener la última respuesta de la sesión
        BrainRotRespuesta ultimaRespuesta = (BrainRotRespuesta) request.getSession().getAttribute("ultimaRespuesta");
        if (ultimaRespuesta == null) {
            return new ModelAndView("redirect:/brain-rot/jugar");
        }

        // Obtener partida activa para mostrar puntajes actualizados
        BrainRotPartida partida = servicioBrainRot.obtenerPartidaActiva(usuario);

        modelo.put("respuesta", ultimaRespuesta);
        modelo.put("partida", partida);
        modelo.put("usuario", usuario);

        // Limpiar la respuesta de la sesión
        request.getSession().removeAttribute("ultimaRespuesta");

        return new ModelAndView("brain-rot-feedback", modelo);
    }

    @RequestMapping("/brain-rot/resultado")
    public ModelAndView mostrarResultado(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        // Buscar la última partida terminada del usuario
        BrainRotPartida partida = servicioBrainRot.obtenerPartidaActiva(usuario);
        if (partida == null || !partida.estaTerminada()) {
            return new ModelAndView("redirect:/brain-rot");
        }

        // Obtener estadísticas actualizadas
        Long victorias = servicioBrainRot.obtenerVictoriasPorUsuario(usuario);
        Double porcentajeAcierto = servicioBrainRot.obtenerPorcentajeAcierto(usuario);
        Integer mejorRacha = servicioBrainRot.obtenerMejorRacha(usuario);

        modelo.put("partida", partida);
        modelo.put("usuario", usuario);
        modelo.put("victorias", victorias);
        modelo.put("porcentajeAcierto", String.format("%.1f", porcentajeAcierto));
        modelo.put("mejorRacha", mejorRacha);

        return new ModelAndView("brain-rot-resultado", modelo);
    }

    @RequestMapping(path = "/brain-rot/continuar", method = RequestMethod.POST)
    public ModelAndView continuarJugando(HttpServletRequest request) {
        return new ModelAndView("redirect:/brain-rot/jugar");
    }

    @RequestMapping(path = "/brain-rot/volver-menu", method = RequestMethod.POST)
    public ModelAndView volverAlMenu(HttpServletRequest request) {
        return new ModelAndView("redirect:/brain-rot");
    }

    // Método auxiliar para obtener el usuario logueado
    private Usuario obtenerUsuarioLogueado(HttpServletRequest request) {
        // Obtener el email del usuario de la sesión (como lo hace el sistema existente)
        return (Usuario) request.getSession().getAttribute("USUARIO");
    }
}
