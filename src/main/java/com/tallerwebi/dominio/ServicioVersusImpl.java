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

    @Override
    public List<Brainrot> obtenerTodos(){
        List<Brainrot> todosDesordenadosParaMultijugador = repositorioVersus.obtenerTodos();
        Collections.shuffle(todosDesordenadosParaMultijugador);
        return todosDesordenadosParaMultijugador;
    }

    @Override
    public List<String> obtenerOpcionesAleatoriasParaPreguntaMultijugador(String imagenCorrecta) {
        List<String> todasLasOpciones = repositorioVersus.obtenerTodasLasOpciones();

        List<String> opcionesFinal = new ArrayList<>();

        opcionesFinal.add(imagenCorrecta);

        Collections.shuffle(todasLasOpciones);
        for (String opcion : todasLasOpciones) {
            if (!opcion.equals(imagenCorrecta) && opcionesFinal.size() < 4) {
                opcionesFinal.add(opcion);
            }
        }
        Collections.shuffle(opcionesFinal);

        return opcionesFinal;
    }
}
