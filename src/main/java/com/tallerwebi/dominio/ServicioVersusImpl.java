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
    public List<Brainrot> obtenerTodosPorNivel(Integer nivel) {
        List<Brainrot> brainrots = repositorioVersus.obtenerTodosPorNivel(nivel);

        Collections.shuffle(brainrots);

        return brainrots;
    }

    @Override
    public List<String> obtenerOpcionesAleatoriasParaPregunta(Integer nivel, String imagenCorrecta) {
        List<Brainrot> brainrotsDelNivel = repositorioVersus.obtenerTodosPorNivel(nivel);

        List<String> todasLasOpciones = new ArrayList<>();
        for (Brainrot br : brainrotsDelNivel) {
            todasLasOpciones.add(br.getImagenPersonaje());
        }

        Collections.shuffle(todasLasOpciones);
        return todasLasOpciones; //por ahora funciona poque hay solo 4 opciones si en el futuro agrego mas opciones hacer alguna validacion
    }

    @Override
    public boolean verificarRespuesta(String respuesta, String respuestaCorrecta) {
        return respuesta.equals(respuestaCorrecta);
    }

    @Override
    public boolean esTimeOut(String timeout){
        return "true".equals(timeout);
    }
}
