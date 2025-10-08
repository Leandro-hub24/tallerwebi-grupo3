package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ServicioAdivinanza {
    private  RepositorioUsuario  repositorioUsuario;

    @Autowired
    public ServicioAdivinanza(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;

    }

    @Transactional
    public void opcionCorrecta(Usuario usuario) {
        usuario.setPuntajeAdivinanza(usuario.getPuntajeAdivinanza() + 1);

        repositorioUsuario.modificar(usuario);
    }

    @Transactional
    public void opcionIncorrecta(Usuario usuario) {
        if (usuario.getPuntajeAdivinanza() > 0 ){
            usuario.setPuntajeAdivinanza(usuario.getPuntajeAdivinanza() - 1);
        }

        repositorioUsuario.modificar(usuario);
    }
}
