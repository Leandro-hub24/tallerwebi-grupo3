package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.NivelJuego;
import com.tallerwebi.dominio.RepositorioRompecabeza;
import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioRompezabeza")
public class RepositorioRompezabezaImpl implements RepositorioRompecabeza {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioRompezabezaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Rompecabeza buscarRompecabeza(Long idRompecabeza) {
        return (Rompecabeza) sessionFactory.getCurrentSession()
                .createCriteria(Rompecabeza.class)
                .add(Restrictions.eq("nivel", idRompecabeza))
                .uniqueResult();
    }

    @Override
    public List<Rompecabeza> buscarRompecabezas(Integer rompecabezaNivel) {
        Long rompecabeza = Long.valueOf(rompecabezaNivel);
        return (List<Rompecabeza>) sessionFactory.getCurrentSession()
                .createCriteria(Rompecabeza.class)
                .add(Restrictions.le("nivel", rompecabeza))
                .list();
    }

    @Override
    public Long buscarUltimoNivelId() {
        /*return (Long) sessionFactory.getCurrentSession().createCriteria(Rompecabeza.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();*/

        Rompecabeza rompecabeza = (Rompecabeza) sessionFactory.getCurrentSession()
                .createCriteria(Rompecabeza.class)
                .addOrder(Order.desc("nivel"))
                .setMaxResults(1)
                .uniqueResult();

        return rompecabeza.getNivel();
    }
}
