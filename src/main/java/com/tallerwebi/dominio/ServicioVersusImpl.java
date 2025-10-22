package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("servicioVersus")
@Transactional
public class ServicioVersusImpl implements ServicioVersus{
    private final RepositorioVersus repositorioVersus;

    @Autowired
    public ServicioVersusImpl(RepositorioVersus repositorioVersus){
        this.repositorioVersus = repositorioVersus;
    }

    @Override
    public Brainrot obtenerBrainrotAleatorio() {
        return repositorioVersus.obtenerAleatorio();
    }

    @Override
    public List<String> obtenerOpcionesAleatorias() {
        List<Brainrot> brainrots = repositorioVersus.obtenerTodos();
        List<String> opciones = new ArrayList<>();
        for (Brainrot brainrot : brainrots) {
            opciones.add(brainrot.getImagenPersonaje());//[Tralalero tralala, Tung tung tung Sahur, ...]
        }
        Collections.shuffle(opciones);
        return opciones.subList(0, 4);
    }

    @Override
    public boolean verificarRespuesta(String respuesta, String respuestaCorrecta) {
        return respuesta.equals(respuestaCorrecta);
    }
}
