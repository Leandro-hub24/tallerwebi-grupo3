package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioImagen;
import com.tallerwebi.dominio.Imagen;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository("RepositorioImagen")
public class RepositorioImagenImpl implements RepositorioImagen {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioImagenImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Imagen> getImagenesCrear() {
        return (List<Imagen>) sessionFactory.getCurrentSession()
                .createCriteria(Imagen.class)
                .add(Restrictions.eq("tipo", "crear"))
                .list();
    }
}
