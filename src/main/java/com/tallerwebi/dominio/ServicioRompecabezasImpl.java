package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service("servicioRompecabeza")
@Transactional
public class ServicioRompecabezasImpl implements ServicioRompecabezas {


    @Override
    public ArrayList<Rompecabeza> consultarRompecabezasDelUsuario(Long id) {
        return null;
    }

    @Override
    public Rompecabeza consultarRompecabeza(Long id) {
        return null;
    }
}
