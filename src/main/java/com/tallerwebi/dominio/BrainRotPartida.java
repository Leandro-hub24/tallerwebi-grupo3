package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BrainRotPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    private Integer puntajeUsuario;
    private Integer puntajeMaquina;
    private String estado; // JUGANDO, TERMINADA
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer preguntaActual; // Número de pregunta actual (1-10)
    private Integer totalPreguntas;
    private String ganador; // USUARIO, MAQUINA, EMPATE
    private Integer racha; // Racha actual de respuestas correctas del usuario
    private Integer mejorRacha; // Mejor racha en esta partida

    // Constructor vacío (requerido por JPA)
    public BrainRotPartida() {
        this.puntajeUsuario = 0;
        this.puntajeMaquina = 0;
        this.estado = "JUGANDO";
        this.fechaInicio = LocalDateTime.now();
        this.preguntaActual = 1;
        this.totalPreguntas = 10;
        this.racha = 0;
        this.mejorRacha = 0;
    }

    // Constructor con usuario
    public BrainRotPartida(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getPuntajeUsuario() {
        return puntajeUsuario;
    }

    public void setPuntajeUsuario(Integer puntajeUsuario) {
        this.puntajeUsuario = puntajeUsuario;
    }

    public Integer getPuntajeMaquina() {
        return puntajeMaquina;
    }

    public void setPuntajeMaquina(Integer puntajeMaquina) {
        this.puntajeMaquina = puntajeMaquina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getPreguntaActual() {
        return preguntaActual;
    }

    public void setPreguntaActual(Integer preguntaActual) {
        this.preguntaActual = preguntaActual;
    }

    public Integer getTotalPreguntas() {
        return totalPreguntas;
    }

    public void setTotalPreguntas(Integer totalPreguntas) {
        this.totalPreguntas = totalPreguntas;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public Integer getRacha() {
        return racha;
    }

    public void setRacha(Integer racha) {
        this.racha = racha;
    }

    public Integer getMejorRacha() {
        return mejorRacha;
    }

    public void setMejorRacha(Integer mejorRacha) {
        this.mejorRacha = mejorRacha;
    }

    // Métodos específicos para el juego de brain rot
    public void respuestaCorrecta() {
        this.puntajeUsuario++;
        this.racha++;
        if (this.racha > this.mejorRacha) {
            this.mejorRacha = this.racha;
        }
    }

    public void respuestaIncorrecta() {
        this.racha = 0; // Se reinicia la racha
    }

    public void sumarPuntoMaquina() {
        this.puntajeMaquina++;
    }

    public void avanzarPregunta() {
        this.preguntaActual++;
    }

    public boolean estaTerminada() {
        return this.preguntaActual > this.totalPreguntas;
    }

    public void terminarPartida() {
        this.estado = "TERMINADA";
        this.fechaFin = LocalDateTime.now();
        
        // Determinar ganador
        if (this.puntajeUsuario > this.puntajeMaquina) {
            this.ganador = "USUARIO";
        } else if (this.puntajeMaquina > this.puntajeUsuario) {
            this.ganador = "MAQUINA";
        } else {
            this.ganador = "EMPATE";
        }
    }

    public String getEstadoVisual() {
        if ("TERMINADA".equals(this.estado)) {
            switch (this.ganador) {
                case "USUARIO": return "¡Ganaste! 🎉";
                case "MAQUINA": return "Perdiste 😢";
                case "EMPATE": return "¡Empate! 🤝";
                default: return "Partida terminada";
            }
        }
        return "Jugando... 🎮";
    }
}
