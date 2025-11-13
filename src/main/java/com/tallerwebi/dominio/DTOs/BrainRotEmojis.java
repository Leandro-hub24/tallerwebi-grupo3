package com.tallerwebi.dominio.DTOs;

public class BrainRotEmojis {
    private Integer id;
    private String imagen;
    private String audio;
    private String emojis;
    private String nombre;

    public BrainRotEmojis() {}
    public BrainRotEmojis(Integer id, String imagen, String audio, String emojis, String nombre) {
        this.id = id;
        this.imagen = imagen;
        this.audio = audio;
        this.emojis = emojis;
        this.nombre = nombre;
    }

    public Integer getId() { return id; }
    public String getImagen() { return imagen; }
    public String getAudio() { return audio; }
    public String getEmojis() { return emojis; }
    public String getNombre() { return nombre; }

    public void setId(Integer id) { this.id = id; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setAudio(String audio) { this.audio = audio; }
    public void setEmojis(String emojis) { this.emojis = emojis; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
