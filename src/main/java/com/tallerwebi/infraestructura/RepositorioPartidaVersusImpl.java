package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.PartidaVersus;
import com.tallerwebi.dominio.RepositorioPartidaVersus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.hibernate.criterion.Restrictions;

@Repository
public class RepositorioPartidaVersusImpl implements RepositorioPartidaVersus {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidaVersusImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(PartidaVersus partida) {
        sessionFactory.getCurrentSession().save(partida);
    }

    @Override
    public PartidaVersus buscarPorId(Long id) {
        return (PartidaVersus) sessionFactory.getCurrentSession()
                .createCriteria(PartidaVersus.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public List<PartidaVersus> buscarPorEstado(String estado) {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidaVersus.class)
                .add(Restrictions.eq("estado", estado))
                .list();
    }

    @Override
    public void actualizar(PartidaVersus partida) {
        sessionFactory.getCurrentSession().update(partida);
    }

    @Override
    public List<PartidaVersus> buscarPorJugador1(Long jugador1Id) {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidaVersus.class)
                .add(Restrictions.eq("jugador1Id", jugador1Id))
                .list();
    }
}
