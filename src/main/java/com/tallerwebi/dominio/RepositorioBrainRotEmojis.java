package com.tallerwebi.dominio;

import com.tallerwebi.dominio.DTOs.BrainRotEmojis;

import java.util.List;
import java.util.Optional;

public interface RepositorioBrainRotEmojis {

    List<BrainRotEmojis> findAll();
    Optional<BrainRotEmojis> findById(Integer id);
}
