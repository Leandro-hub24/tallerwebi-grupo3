package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service("servicioBrainRot")
@Transactional
public class ServicioBrainRotImpl implements ServicioBrainRot {

    private RepositorioBrainRotPregunta repositorioPregunta;
    private RepositorioBrainRotPartida repositorioPartida;
    private RepositorioBrainRotRespuesta repositorioRespuesta;

    @Autowired
    public ServicioBrainRotImpl(RepositorioBrainRotPregunta repositorioPregunta,
                               RepositorioBrainRotPartida repositorioPartida,
                               RepositorioBrainRotRespuesta repositorioRespuesta) {
        this.repositorioPregunta = repositorioPregunta;
        this.repositorioPartida = repositorioPartida;
        this.repositorioRespuesta = repositorioRespuesta;
    }

    @Override
    public BrainRotPartida iniciarNuevaPartida(Usuario usuario) {
        // Verificar si ya tiene una partida activa
        BrainRotPartida partidaActiva = repositorioPartida.buscarPartidaActivaPorUsuario(usuario);
        if (partidaActiva != null) {
            // Si ya tiene una partida activa, la devolvemos
            return partidaActiva;
        }

        // Crear nueva partida
        BrainRotPartida nuevaPartida = new BrainRotPartida(usuario);
        repositorioPartida.guardar(nuevaPartida);
        return nuevaPartida;
    }

    @Override
    public BrainRotPartida obtenerPartidaActiva(Usuario usuario) {
        return repositorioPartida.buscarPartidaActivaPorUsuario(usuario);
    }

    @Override
    public void terminarPartida(BrainRotPartida partida) {
        partida.terminarPartida();
        repositorioPartida.modificar(partida);
    }

    @Override
    public BrainRotPregunta obtenerSiguientePregunta(BrainRotPartida partida) {
        // Si no hay preguntas cargadas, sembrar una pregunta de respaldo para evitar null
        Long total = repositorioPregunta.contarPreguntas();
        if (total == null || total == 0) {
            BrainRotPregunta seed = new BrainRotPregunta(
                    "/resources/img/imgversus/tralalero.png",
                    "Cantante Tra",
                    "Tralalero",
                    "Músico Lala",
                    "Artista Tralala",
                    "B",
                    "MEME",
                    "FACIL",
                    "Pregunta temporal de respaldo"
            );
            repositorioPregunta.guardar(seed);
        }
        // Obtener una pregunta aleatoria
        return repositorioPregunta.buscarAleatoria();
    }

    @Override
    public List<BrainRotPregunta> obtenerPreguntasAleatorias(Integer cantidad) {
        return repositorioPregunta.buscarAleatoriasLimitadas(cantidad);
    }

    @Override
    public BrainRotPregunta buscarPreguntaPorId(Long id) {
        return repositorioPregunta.buscarPorId(id);
    }

    @Override
    public BrainRotRespuesta procesarRespuesta(BrainRotPartida partida, BrainRotPregunta pregunta, String respuestaUsuario) {
        // Generar respuesta de la máquina
        String respuestaMaquina = generarRespuestaMaquina(pregunta);
        
        // Crear objeto respuesta
        BrainRotRespuesta respuesta = new BrainRotRespuesta(partida, pregunta, respuestaUsuario, respuestaMaquina);
        
        // Guardar la respuesta
        repositorioRespuesta.guardar(respuesta);
        
        // Actualizar puntajes de la partida
        actualizarPuntajes(partida, respuesta);
        
        // Avanzar a la siguiente pregunta
        partida.avanzarPregunta();
        
        // Verificar si la partida terminó
        if (esPartidaTerminada(partida)) {
            terminarPartida(partida);
        } else {
            repositorioPartida.modificar(partida);
        }
        
        return respuesta;
    }

    @Override
    public String generarRespuestaMaquina(BrainRotPregunta pregunta) {
        Random random = new Random();
        
        // Lógica simple de IA: la máquina tiene diferentes niveles de "inteligencia"
        // según la dificultad de la pregunta
        
        double probabilidadAcierto;
        switch (pregunta.getDificultad()) {
            case "FACIL":
                probabilidadAcierto = 0.7; // 70% de probabilidad de acertar
                break;
            case "MEDIO":
                probabilidadAcierto = 0.5; // 50% de probabilidad de acertar
                break;
            case "DIFICIL":
                probabilidadAcierto = 0.3; // 30% de probabilidad de acertar
                break;
            default:
                probabilidadAcierto = 0.5;
        }
        
        // Decidir si la máquina acierta o no
        if (random.nextDouble() < probabilidadAcierto) {
            // La máquina acierta: devolver la respuesta correcta
            return pregunta.getRespuestaCorrecta();
        } else {
            // La máquina falla: devolver una respuesta incorrecta aleatoria
            String[] opciones = {"A", "B", "C", "D"};
            String respuestaCorrecta = pregunta.getRespuestaCorrecta();
            
            // Filtrar la respuesta correcta para que no la elija
            String respuestaIncorrecta;
            do {
                respuestaIncorrecta = opciones[random.nextInt(opciones.length)];
            } while (respuestaIncorrecta.equals(respuestaCorrecta));
            
            return respuestaIncorrecta;
        }
    }

    @Override
    public void actualizarPuntajes(BrainRotPartida partida, BrainRotRespuesta respuesta) {
        // Actualizar puntaje del usuario
        if (respuesta.getUsuarioCorrecta()) {
            partida.respuestaCorrecta(); // Suma punto y actualiza racha
        } else {
            partida.respuestaIncorrecta(); // Reinicia racha
        }
        
        // Actualizar puntaje de la máquina
        if (respuesta.getMaquinaCorrecta()) {
            partida.sumarPuntoMaquina();
        }
    }

    @Override
    public boolean esPartidaTerminada(BrainRotPartida partida) {
        return partida.estaTerminada();
    }

    @Override
    public String determinarGanador(BrainRotPartida partida) {
        if (partida.getPuntajeUsuario() > partida.getPuntajeMaquina()) {
            return "USUARIO";
        } else if (partida.getPuntajeMaquina() > partida.getPuntajeUsuario()) {
            return "MAQUINA";
        } else {
            return "EMPATE";
        }
    }

    @Override
    public Long obtenerVictoriasPorUsuario(Usuario usuario) {
        return repositorioPartida.contarVictoriasPorUsuario(usuario);
    }

    @Override
    public Double obtenerPorcentajeAcierto(Usuario usuario) {
        return repositorioRespuesta.calcularPorcentajeAciertoPorUsuario(usuario);
    }

    @Override
    public Integer obtenerMejorRacha(Usuario usuario) {
        List<BrainRotPartida> partidas = repositorioPartida.buscarPorUsuario(usuario);
        Integer mejorRacha = 0;
        
        for (BrainRotPartida partida : partidas) {
            if (partida.getMejorRacha() > mejorRacha) {
                mejorRacha = partida.getMejorRacha();
            }
        }
        
        return mejorRacha;
    }
}
