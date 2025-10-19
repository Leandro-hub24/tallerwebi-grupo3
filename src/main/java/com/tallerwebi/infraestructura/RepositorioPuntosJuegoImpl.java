package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PuntosJuego;
import com.tallerwebi.dominio.RepositorioPuntosJuego;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPuntosJuegoImpl implements RepositorioPuntosJuego {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPuntosJuegoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Long agregarPuntos(PuntosJuego puntosJuego) {
        return (Long) sessionFactory.getCurrentSession().save(puntosJuego);
    }


}
