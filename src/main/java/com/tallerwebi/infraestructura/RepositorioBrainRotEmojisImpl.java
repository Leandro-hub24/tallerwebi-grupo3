package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DTOs.BrainRotEmojis;
import com.tallerwebi.dominio.RepositorioBrainRotEmojis;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("repositorioBrainRotEmojis")
public class RepositorioBrainRotEmojisImpl implements RepositorioBrainRotEmojis {

    private final List<BrainRotEmojis> data = new ArrayList<>();

    public RepositorioBrainRotEmojisImpl() {
        data.add(new BrainRotEmojis(1, "/spring/imgBrainrot/brr-brr-patapi.png", "/spring/audioBrainrot/brrbrrsound.mp3", "🗣️🗣️🗣️", "Brr Brr Patapi"));
        data.add(new BrainRotEmojis(2, "/spring/imgBrainrot/chimpancini-bananini.png", "/spring/audioBrainrot/chimpancinisound.mp3", "🐒🍌🍌", "Chimpancini Bananini"));
        data.add(new BrainRotEmojis(3, "/spring/imgBrainrot/tralalero-tralala.png", "/spring/audioBrainrot/tralalerosound.mp3", "👟🦈🌊", "Tralalero Tralala"));
        data.add(new BrainRotEmojis(4, "/spring/imgBrainrot/tung-tung-tung-Sahur.png", "/spring/audioBrainrot/tuntunsound.mp3", "🥁🥁🥁", "Tung Tung Tung Sahur"));
        data.add(new BrainRotEmojis(5, "/spring/imgBrainrot/cappuccino-assassino.png", "/spring/audioBrainrot/cappuccinosound.mp3", "☕🔪🩸", "Cappuccino Assassino"));
        data.add(new BrainRotEmojis(6, "/spring/imgBrainrot/la-vaca-saturno-saturnita.png", "/spring/audioBrainrot/vaca-saturno-saturnita-sound.mp3", "🐮🪐💫", "La Vaca Saturno Saturnita"));
        data.add(new BrainRotEmojis(7, "/spring/imgBrainrot/bombardino-crocodillo.png", "/spring/audioBrainrot/bombardino-crocodilo-sound.mp3", "🎺🐊🎶", "Bombardino Crocodillo"));
    }

    @Override
    public List<BrainRotEmojis> findAll()
    {
        return List.copyOf(data);
    }

    @Override
    public Optional<BrainRotEmojis> findById(Integer id) {
        return data.stream().filter(b -> b
                .getId().equals(id)).findFirst();
    }
}
