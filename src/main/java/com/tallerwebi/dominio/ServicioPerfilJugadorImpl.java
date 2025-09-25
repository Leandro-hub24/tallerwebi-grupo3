package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DtoPerfilJugador.DatosPerfil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPerfilJugador")
@Transactional
public class ServicioPerfilJugadorImpl implements ServicioPerfilJugador {

    @Override
    public List<DatosPerfil> mostrarPerfilDeJugador() {
        return null;
    }
}
