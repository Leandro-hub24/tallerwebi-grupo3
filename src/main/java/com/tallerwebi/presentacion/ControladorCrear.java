package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.BrainrotCreado;
import com.tallerwebi.dominio.Imagen;
import com.tallerwebi.dominio.ServicioCrear;
import com.tallerwebi.dominio.ServicioImagen;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
public class ControladorCrear {

    ServicioCrear servicioCrear;
    ServicioImagen servicioImagen;
    @Autowired
    public ControladorCrear(ServicioCrear servicioCrear, ServicioImagen servicioImagen) {
        this.servicioCrear = servicioCrear;
        this.servicioImagen = servicioImagen;
    }

    @RequestMapping("/crear")
    public ModelAndView irACrear() {
        ModelMap modelo = new ModelMap();

        List<Imagen> imagenesDisponibles = List.of();
        try {
            imagenesDisponibles = servicioImagen.getImagenesPorTipo("crear");
        } catch (NoSeEncontraronImagenesDelTipoEspecificadoException e) {
            modelo.addAttribute("error", "No se pudieron obtener las imagenes");
        }

        modelo.addAttribute("imagenesDisponibles", imagenesDisponibles);
        return new ModelAndView("crear", modelo);
    }

    @PostMapping("/crear")
    public ModelAndView crearBrainrot(@RequestParam String estilo,
                                      @RequestParam List<Integer> imagenes,
                                      RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/crear");

        try {

            BrainrotCreado brainrotCreado = servicioCrear.crearBrainrot(estilo, imagenes);
            redirectAttributes.addFlashAttribute("brainrotCreado", brainrotCreado);
            mav.addObject("brainrotCreado", brainrotCreado);
        } catch (NoSePuedeCrearUnBrainrotConMasDe4ImagenesException e) {
            redirectAttributes.addFlashAttribute("error", "No se puede crear un brainrot con más de 4 imágenes");
            mav.addObject("error", "No se puede crear un brainrot con más de 4 imágenes");
        } catch (FaltaSeleccionarImagenParaCrearBrainrotException e) {
            redirectAttributes.addFlashAttribute("error", "Se necesita al menos una imagen para crear un Brainrot");
            mav.addObject("error", "Se necesita al menos una imagen para crear un Brainrot");
        } catch (FaltaSeleccionarEstiloParaCrearBrainrotException e) {
            redirectAttributes.addFlashAttribute("error", "Debes seleccionar un estilo para crear un Brainrot");
            mav.addObject("error", "Debes seleccionar un estilo para crear un Brainrot");
        } catch (NoSePudoCrearBrainrotException e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo crear un Brainrot");
            mav.addObject("error", "No se pudo crear un Brainrot");
        }

        return mav;
    }


}
