package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Brainrot;
import com.tallerwebi.dominio.RepositorioVersus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("repositorioVersus")
public class RepositorioVersusImpl implements RepositorioVersus {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioVersusImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Brainrot> obtenerTodosPorNivel(Integer nivel) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Brainrot.class)
                .add(org.hibernate.criterion.Restrictions.eq("nivel", nivel))
                .list(); //esto es este codigo sql pero con criteria SELECT * FROM Brainrot WHERE nivel = 1 lo devuelve como lista,
    }
}
