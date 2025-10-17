package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioCrear;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("RepositorioCrear")
public class RepositorioCrearImpl implements RepositorioCrear {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCrearImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarBrainrotAUsuario() {
    }

}
