package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.NivelJuego;
import com.tallerwebi.dominio.RepositorioNivelJuego;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioNivelJuegoImpl implements RepositorioNivelJuego {


    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioNivelJuegoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public NivelJuego buscarNivelJuegoPorIdUsuario(Long usuarioId) {
        return (NivelJuego) sessionFactory.getCurrentSession()
                .createCriteria(NivelJuego.class)
                .createAlias("usuario", "u")
                .add(Restrictions.eq("u.id", usuarioId))
                .uniqueResult();
    }

    @Override
    public Long modificarNivelJuego(Long usuarioId) {
        NivelJuego nivelJuego = (NivelJuego) sessionFactory.getCurrentSession()
                .createCriteria(NivelJuego.class)
                .createAlias("usuario", "u")
                .add(Restrictions.eq("u.id", usuarioId))
                .uniqueResult();

        nivelJuego.setNivel(nivelJuego.getNivel() + 1);
        sessionFactory.getCurrentSession().update(nivelJuego);
    return nivelJuego.getNivel();
    }
}
