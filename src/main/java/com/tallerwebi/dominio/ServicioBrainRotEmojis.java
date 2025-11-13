package com.tallerwebi.dominio;

import com.tallerwebi.dominio.DTOs.BrainRotEmojis;

import java.util.List;
import java.util.Optional;

public interface ServicioBrainRotEmojis {
    List<BrainRotEmojis> listAll();
    Optional<BrainRotEmojis> find(Integer id);
}
