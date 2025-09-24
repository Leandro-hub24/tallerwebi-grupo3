package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BrainRotRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "partida_id")
    private BrainRotPartida partida;
    
    @ManyToOne
    @JoinColumn(name = "pregunta_id")
    private BrainRotPregunta pregunta;
    
    private String respuestaUsuario; // A, B, C o D
    private String respuestaMaquina; // A, B, C o D
    private Boolean usuarioCorrecta;
    private Boolean maquinaCorrecta;
    private LocalDateTime fechaRespuesta;
    private Integer tiempoRespuestaSegundos;
    private String colorFeedback; // "verde" o "rojo" para el feedback visual

    // Constructor vacío (requerido por JPA)
    public BrainRotRespuesta() {
        this.fechaRespuesta = LocalDateTime.now();
    }

    // Constructor con parámetros básicos
    public BrainRotRespuesta(BrainRotPartida partida, BrainRotPregunta pregunta, String respuestaUsuario, String respuestaMaquina) {
        this();
        this.partida = partida;
        this.pregunta = pregunta;
        this.respuestaUsuario = respuestaUsuario;
        this.respuestaMaquina = respuestaMaquina;
        
        // Evaluar automáticamente si las respuestas son correctas
        this.usuarioCorrecta = pregunta.esRespuestaCorrecta(respuestaUsuario);
        this.maquinaCorrecta = pregunta.esRespuestaCorrecta(respuestaMaquina);
        
        // Establecer color de feedback
        this.colorFeedback = this.usuarioCorrecta ? "verde" : "rojo";
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrainRotPartida getPartida() {
        return partida;
    }

    public void setPartida(BrainRotPartida partida) {
        this.partida = partida;
    }

    public BrainRotPregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(BrainRotPregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(String respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
        // Recalcular si es correcta cuando se cambia la respuesta
        if (this.pregunta != null) {
            this.usuarioCorrecta = this.pregunta.esRespuestaCorrecta(respuestaUsuario);
            this.colorFeedback = this.usuarioCorrecta ? "verde" : "rojo";
        }
    }

    public String getRespuestaMaquina() {
        return respuestaMaquina;
    }

    public void setRespuestaMaquina(String respuestaMaquina) {
        this.respuestaMaquina = respuestaMaquina;
        // Recalcular si es correcta cuando se cambia la respuesta
        if (this.pregunta != null) {
            this.maquinaCorrecta = this.pregunta.esRespuestaCorrecta(respuestaMaquina);
        }
    }

    public Boolean getUsuarioCorrecta() {
        return usuarioCorrecta;
    }

    public void setUsuarioCorrecta(Boolean usuarioCorrecta) {
        this.usuarioCorrecta = usuarioCorrecta;
    }

    public Boolean getMaquinaCorrecta() {
        return maquinaCorrecta;
    }

    public void setMaquinaCorrecta(Boolean maquinaCorrecta) {
        this.maquinaCorrecta = maquinaCorrecta;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Integer getTiempoRespuestaSegundos() {
        return tiempoRespuestaSegundos;
    }

    public void setTiempoRespuestaSegundos(Integer tiempoRespuestaSegundos) {
        this.tiempoRespuestaSegundos = tiempoRespuestaSegundos;
    }

    public String getColorFeedback() {
        return colorFeedback;
    }

    public void setColorFeedback(String colorFeedback) {
        this.colorFeedback = colorFeedback;
    }

    // Métodos útiles para el feedback visual
    public String getClaseCSS() {
        return this.usuarioCorrecta ? "respuesta-correcta" : "respuesta-incorrecta";
    }

    public String getEmojiResultado() {
        if (this.usuarioCorrecta && this.maquinaCorrecta) {
            return "🤝"; // Ambos correctos
        } else if (this.usuarioCorrecta && !this.maquinaCorrecta) {
            return "🎉"; // Solo usuario correcto
        } else if (!this.usuarioCorrecta && this.maquinaCorrecta) {
            return "🤖"; // Solo máquina correcta
        } else {
            return "😅"; // Ambos incorrectos
        }
    }

    public String getMensajeFeedback() {
        if (this.usuarioCorrecta) {
            return "¡Correcto! 🎉";
        } else {
            String respuestaCorrecta = this.pregunta.getOpcionPorLetra(this.pregunta.getRespuestaCorrecta());
            return "Incorrecto 😢. La respuesta era: " + respuestaCorrecta;
        }
    }

    // Métodos de comparación
    public boolean ambosCorrectos() {
        return usuarioCorrecta && maquinaCorrecta;
    }

    public boolean soloUsuarioCorrect() {
        return usuarioCorrecta && !maquinaCorrecta;
    }

    public boolean soloMaquinaCorrecta() {
        return !usuarioCorrecta && maquinaCorrecta;
    }

    public boolean ambosIncorrectos() {
        return !usuarioCorrecta && !maquinaCorrecta;
    }
}
