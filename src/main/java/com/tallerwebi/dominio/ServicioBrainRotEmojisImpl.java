package com.tallerwebi.dominio;

import com.tallerwebi.dominio.DTOs.BrainRotEmojis;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("servicioBrainRotEmojis")
@Transactional
public class ServicioBrainRotEmojisImpl implements ServicioBrainRotEmojis {
    private final RepositorioBrainRotEmojis repositorioBrainRotEmojis;

    public ServicioBrainRotEmojisImpl(RepositorioBrainRotEmojis repositorioBrainRotEmojis) {
        this.repositorioBrainRotEmojis = repositorioBrainRotEmojis;
    }

    @Override
    public List<BrainRotEmojis> listAll() {
        return repositorioBrainRotEmojis.findAll();
    }

    @Override
    public Optional<BrainRotEmojis> find(Integer id) {
        return repositorioBrainRotEmojis.findById(id);
    }
}
