package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.NivelJuego;
import com.tallerwebi.dominio.PuntosJuego;
import com.tallerwebi.dominio.RepositorioPuntosJuego;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<PuntosJuego> buscarPuntosJuegoConMejorTiempoPorIdUsuario(Long usuarioId, Long idRompecabeza, String juego) {
        return (List<PuntosJuego>)  sessionFactory.getCurrentSession()
                .createCriteria(PuntosJuego.class)
                .createAlias("nivelJuego", "nj")
                .createAlias("nj.usuario", "u" )
                .add(Restrictions.eq("nj.nivel", idRompecabeza))
                .add(Restrictions.eq("nj.nombre", juego))
                .add(Restrictions.eq("u.id", usuarioId))
                .list();

    }


}
