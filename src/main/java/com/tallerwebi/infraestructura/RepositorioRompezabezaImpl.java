package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioRompecabeza;
import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        /*return (Rompecabeza) sessionFactory.getCurrentSession().createCriteria(Rompecabeza.class)
                .add(Restrictions.eq("id", idRompecabeza))
                .uniqueResult();*/
        return null;
    }

    @Override
    public ArrayList<Rompecabeza> buscarRompecabezas(Long idUsuario) {
        /*return (ArrayList<Rompecabeza>) sessionFactory.getCurrentSession().createCriteria(Rompecabeza.class)
                .list();*/
        return null;
    }


}
