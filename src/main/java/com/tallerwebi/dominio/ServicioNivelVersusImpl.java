package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioNivelVersus")
@Transactional
public class ServicioNivelVersusImpl implements ServicioNivelVersus {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioNivelVersusImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void actualizarNivelVersus(Usuario usuario, Integer nuevoNivel) {
        usuario.setVersusNivel(nuevoNivel);
        repositorioUsuario.modificar(usuario);
    }
}