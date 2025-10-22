package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Brainrot;
import com.tallerwebi.dominio.RepositorioVersus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Random;
import java.util.List;

@Repository("repositorioVersus")
public class RepositorioVersusImpl implements RepositorioVersus {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioVersusImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Brainrot> obtenerTodos() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Brainrot.class).list();
    }
    @Override
    public Brainrot obtenerAleatorio(){
        final Session session = sessionFactory.getCurrentSession();
        List<Brainrot> todos = obtenerTodos();
        return todos.get(new Random().nextInt(todos.size()));
    }
}
