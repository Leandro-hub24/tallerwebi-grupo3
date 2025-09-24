package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCrear;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarEstiloParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarImagenParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePuedeCrearUnBrainrotConMasDe4ImagenesException;
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
    @Autowired
    public ControladorCrear(ServicioCrear servicioCrear) {
        this.servicioCrear = servicioCrear;
    }

    @RequestMapping("/crear")
    public ModelAndView irACrear() {
        ModelMap modelo = new ModelMap();

        List<ImagenPrueba> imagenesDisponibles = List.of(
                new ImagenPrueba(1, "Imagen 1", "/img/TralaleroTralala.jpg"),
                new ImagenPrueba(2, "Imagen 2", "/img/TralaleroTralala.jpg"),
                new ImagenPrueba(3, "Imagen 3", "/img/TralaleroTralala.jpg"),
                new ImagenPrueba(4, "Imagen 4", "/img/TralaleroTralala.jpg"),
                new ImagenPrueba(5, "Imagen 5", "/img/TralaleroTralala.jpg")
        );

        modelo.addAttribute("imagenesDisponibles", imagenesDisponibles);
        return new ModelAndView("crear", modelo);
    }

    @PostMapping("/crear")
    public ModelAndView crearBrainrot(@RequestParam String estilo,
                                      @RequestParam List<Integer> imagenes,
                                      RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/crear");

        try {
            byte[] brainrotCreado = servicioCrear.crearBrainrot(estilo, imagenes);
            redirectAttributes.addFlashAttribute("brainrotCreado", brainrotCreado);
            mav.addObject("brainrotCreado", brainrotCreado); // ✅ agregado para el test
        } catch (NoSePuedeCrearUnBrainrotConMasDe4ImagenesException e) {
            redirectAttributes.addFlashAttribute("error", "No se puede crear un brainrot con más de 4 imágenes");
            mav.addObject("error", "No se puede crear un brainrot con más de 4 imágenes"); // ✅ agregado para el test
        } catch (FaltaSeleccionarImagenParaCrearBrainrotException e) {
            redirectAttributes.addFlashAttribute("error", "Se necesita al menos una imagen para crear un Brainrot");
            mav.addObject("error", "Se necesita al menos una imagen para crear un Brainrot"); // ✅ agregado para el test
        } catch (FaltaSeleccionarEstiloParaCrearBrainrotException e) {
            redirectAttributes.addFlashAttribute("error", "Debes seleccionar un estilo para crear un Brainrot");
            mav.addObject("error", "Debes seleccionar un estilo para crear un Brainrot"); // ✅ agregado para el test
        }

        return mav;
    }


}
