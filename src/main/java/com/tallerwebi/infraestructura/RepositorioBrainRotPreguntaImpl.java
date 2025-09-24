package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.BrainRotPregunta;
import com.tallerwebi.dominio.RepositorioBrainRotPregunta;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository("repositorioBrainRotPregunta")
public class RepositorioBrainRotPreguntaImpl implements RepositorioBrainRotPregunta {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioBrainRotPreguntaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(BrainRotPregunta pregunta) {
        sessionFactory.getCurrentSession().save(pregunta);
    }

    @Override
    public BrainRotPregunta buscarPorId(Long id) {
        return (BrainRotPregunta) sessionFactory.getCurrentSession().get(BrainRotPregunta.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPregunta> buscarTodas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPregunta.class)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPregunta> buscarPorCategoria(String categoria) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPregunta.class)
                .add(Restrictions.eq("categoria", categoria))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPregunta> buscarPorDificultad(String dificultad) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPregunta.class)
                .add(Restrictions.eq("dificultad", dificultad))
                .list();
    }

    @Override
    public BrainRotPregunta buscarAleatoria() {
        List<BrainRotPregunta> todasLasPreguntas = buscarTodas();
        if (todasLasPreguntas.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int indiceAleatorio = random.nextInt(todasLasPreguntas.size());
        return todasLasPreguntas.get(indiceAleatorio);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPregunta> buscarAleatoriasLimitadas(Integer limite) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPregunta.class)
                .setMaxResults(limite)
                .list();
    }

    @Override
    public Long contarPreguntas() {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(*) FROM BrainRotPregunta")
                .uniqueResult();
    }

    @Override
    public void eliminar(BrainRotPregunta pregunta) {
        sessionFactory.getCurrentSession().delete(pregunta);
    }

    @Override
    public void modificar(BrainRotPregunta pregunta) {
        sessionFactory.getCurrentSession().update(pregunta);
    }
}
