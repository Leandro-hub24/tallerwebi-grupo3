package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Brainrot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ControladorVersus {

//    private final Map<String, String> imagenes = new LinkedHashMap<>() {{
//        put("tralalerobg", "Tralalero tralala");
//        put("tuntunbg", "tuntunpj");
//        put("chimpancinibg", "chimpancinipj");
//        put("patapibg", "Brr brr patapi");
//    }};

    private ArrayList<Brainrot> brainrots = new ArrayList<>() {{
        add(new Brainrot("tralalerobg","Tralalero tralala","tralalerosound","tralaleroshadow","tralalerocom"));
        add(new Brainrot("tuntunbg","Tung tung tung Sahur","tuntunsound","tuntunshadow","tuntuncom"));
        add(new Brainrot("chimpancinibg","Chimpancini bananini","chimpancinisound","chimpancinishadow","chimpancinicom"));
        add(new Brainrot("brrbrrbg","Brr brr patapi","brrbrrsound","brrbrrshadow","brrbrrcom"));
    }};

    @GetMapping("/versus")
    public ModelAndView mostrarVersus() {

//      List<String> nombresArchivos = new ArrayList<>(imagenes.keySet()); //[brrbrr-patapibg, tralalerobg, ...]
//      List<String> nombresArchivos = new ArrayList<>();
//      for (Brainrot brainrot : brainrots) {
//          nombresArchivos.add(brainrot.getImagenFondo());
//      }

//      String nombreArchivo = nombresArchivos.get(new Random().nextInt(nombresArchivos.size())); //tralalerobg
        Brainrot brainrotActual = brainrots.get(new Random().nextInt(brainrots.size())); //El brainrot completo

    //  List<String> opciones = new ArrayList<>(brainrots.values()); //[tralaleropj, tuntunpj, ...]
        List<String> opciones = new ArrayList<>();
        for (Brainrot brainrot : brainrots) {
            opciones.add(brainrot.getImagenPersonaje());//[tralaleropj, tuntunpj, ...]
        }
        Collections.shuffle(opciones);

        ModelMap model = new ModelMap();
        model.put("imagen", "/img/versus/" + brainrotActual.getImagenFondo() + ".png");
        model.put("opciones", opciones.subList(0, 4));
        model.put("imagenActual", brainrotActual.getImagenPersonaje());
        model.put("imagenActualCompleta", brainrotActual.getImagenCompleta());
        model.put("audio", brainrotActual.getAudio());
        model.put("imagenshadow", "/img/versus/" + brainrotActual.getImagenSombra() + ".png");
        return new ModelAndView("versus", model)
//        model = {
//                "imagen": "/img/versus/tralalerobg.png",
//                "opciones": ["chimpancinipj", "tuntunpj", "brrbrrpatapipj", "tralaleropj"],
//                "imagenActual": "tralalerobg"
//        }
        ;
    }

    @PostMapping("/versus/verificar")
    public ModelAndView verificarRespuesta(
            @RequestParam String respuesta,
            @RequestParam String imagenActualCompleta,
            @RequestParam String imagenActual) {

        boolean esCorrecto = respuesta.equals(imagenActual);

        ModelAndView model = new ModelAndView("resultado-versus");
        model.addObject("esCorrecto", esCorrecto);
        model.addObject("respuestaCorrecta", imagenActual);
        model.addObject("imagen", "/img/versus/" + imagenActualCompleta + ".png");
        return model;
//        model = {
//                "esCorrecto": false,
//                "respuestaCorrecta": "tralaleropj",
//                "imagen": "/img/versus/tralalerobg.png"
//        }
    }
}