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
        Integer puntajeActual = usuario.getPuntajeAdivinanza();
        if (puntajeActual == null) {
            puntajeActual = 0;
        }
        usuario.setPuntajeAdivinanza(puntajeActual + 1);
        repositorioUsuario.modificar(usuario);
    }

    @Transactional
    public void opcionIncorrecta(Usuario usuario) {
        Integer puntajeActual = usuario.getPuntajeAdivinanza();
        if (puntajeActual == null) {
            puntajeActual = 0;
        }

        if (puntajeActual > 0) {
            usuario.setPuntajeAdivinanza(puntajeActual - 1);
        } else {
            usuario.setPuntajeAdivinanza(0); // para asegurar que no quede null
        }

        repositorioUsuario.modificar(usuario);
    }
}
