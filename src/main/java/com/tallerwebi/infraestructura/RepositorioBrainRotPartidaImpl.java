package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.BrainRotPartida;
import com.tallerwebi.dominio.RepositorioBrainRotPartida;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioBrainRotPartida")
public class RepositorioBrainRotPartidaImpl implements RepositorioBrainRotPartida {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioBrainRotPartidaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(BrainRotPartida partida) {
        sessionFactory.getCurrentSession().save(partida);
    }

    @Override
    public BrainRotPartida buscarPorId(Long id) {
        return (BrainRotPartida) sessionFactory.getCurrentSession().get(BrainRotPartida.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPartida> buscarTodas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPartida.class)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPartida> buscarPorUsuario(Usuario usuario) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPartida.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }

    @Override
    public BrainRotPartida buscarPartidaActivaPorUsuario(Usuario usuario) {
        return (BrainRotPartida) sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPartida.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("estado", "JUGANDO"))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPartida> buscarPartidasTerminadasPorUsuario(Usuario usuario) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPartida.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("estado", "TERMINADA"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BrainRotPartida> buscarPorEstado(String estado) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BrainRotPartida.class)
                .add(Restrictions.eq("estado", estado))
                .list();
    }

    @Override
    public Long contarPartidasPorUsuario(Usuario usuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(*) FROM BrainRotPartida p WHERE p.usuario = :usuario")
                .setParameter("usuario", usuario)
                .uniqueResult();
    }

    @Override
    public Long contarVictoriasPorUsuario(Usuario usuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(*) FROM BrainRotPartida p WHERE p.usuario = :usuario AND p.ganador = 'USUARIO'")
                .setParameter("usuario", usuario)
                .uniqueResult();
    }

    @Override
    public void eliminar(BrainRotPartida partida) {
        sessionFactory.getCurrentSession().delete(partida);
    }

    @Override
    public void modificar(BrainRotPartida partida) {
        sessionFactory.getCurrentSession().update(partida);
    }
}
