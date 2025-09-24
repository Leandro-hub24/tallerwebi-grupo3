package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.BrainRotPartida;
import com.tallerwebi.dominio.BrainRotRespuesta;
import com.tallerwebi.dominio.RepositorioBrainRotRespuesta;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioBrainRotRespuesta")
public class RepositorioBrainRotRespuestaImpl implements RepositorioBrainRotRespuesta {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioBrainRotRespuestaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(BrainRotRespuesta respuesta) {
        sessionFactory.getCurrentSession().save(respuesta);
    }

    @Override
    public BrainRotRespuesta buscarPorId(Long id) {
        return (BrainRotRespuesta) sessionFactory.getCurrentSession().get(BrainRotRespuesta.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotRespuesta> buscarTodas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotRespuesta.class)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotRespuesta> buscarPorPartida(BrainRotPartida partida) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotRespuesta.class)
                .add(Restrictions.eq("partida", partida))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotRespuesta> buscarPorUsuario(Usuario usuario) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT r FROM BrainRotRespuesta r WHERE r.partida.usuario = :usuario")
                .setParameter("usuario", usuario)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotRespuesta> buscarRespuestasCorrectasPorUsuario(Usuario usuario) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT r FROM BrainRotRespuesta r WHERE r.partida.usuario = :usuario AND r.usuarioCorrecta = true")
                .setParameter("usuario", usuario)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotRespuesta> buscarRespuestasIncorrectasPorUsuario(Usuario usuario) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT r FROM BrainRotRespuesta r WHERE r.partida.usuario = :usuario AND r.usuarioCorrecta = false")
                .setParameter("usuario", usuario)
                .list();
    }

    @Override
    public Long contarRespuestasCorrectasPorUsuario(Usuario usuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(*) FROM BrainRotRespuesta r WHERE r.partida.usuario = :usuario AND r.usuarioCorrecta = true")
                .setParameter("usuario", usuario)
                .uniqueResult();
    }

    @Override
    public Long contarRespuestasTotalesPorUsuario(Usuario usuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(*) FROM BrainRotRespuesta r WHERE r.partida.usuario = :usuario")
                .setParameter("usuario", usuario)
                .uniqueResult();
    }

    @Override
    public Double calcularPorcentajeAciertoPorUsuario(Usuario usuario) {
        Long correctas = contarRespuestasCorrectasPorUsuario(usuario);
        Long totales = contarRespuestasTotalesPorUsuario(usuario);
        
        if (totales == 0) {
            return 0.0;
        }
        
        return (correctas.doubleValue() / totales.doubleValue()) * 100.0;
    }

    @Override
    public void eliminar(BrainRotRespuesta respuesta) {
        sessionFactory.getCurrentSession().delete(respuesta);
    }

    @Override
    public void modificar(BrainRotRespuesta respuesta) {
        sessionFactory.getCurrentSession().update(respuesta);
    }
}
