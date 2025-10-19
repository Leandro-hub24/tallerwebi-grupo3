package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPuntosJuegoTest {

    @Autowired
    private RepositorioPuntosJuego repositorioPuntosJuego;

    @Autowired
    private SessionFactory sessionFactory;

    @AfterEach
    public void limpiarBaseDeDatos() {
        Session session = sessionFactory.getCurrentSession();


        String sqlQuery = "TRUNCATE TABLE PuntosJuego RESTART IDENTITY;";
        String sqlQuery1 = "TRUNCATE TABLE Usuario RESTART IDENTITY;";
        String sqlQuery2 = "TRUNCATE TABLE NivelJuego RESTART IDENTITY;";

        session.createNativeQuery(sqlQuery).executeUpdate();
        session.createNativeQuery(sqlQuery2).executeUpdate();
        session.createNativeQuery(sqlQuery1).executeUpdate();
    }

    @Test
    @Transactional
    @Rollback()
    public void agregarPuntosJuego() {

        Usuario usuario = givenTengoUnUsuario();
        NivelJuego nivelJuego = givenTengoUnNivelJuego(usuario);
        PuntosJuego puntosJuego = givenTengoUnPuntosJuego(nivelJuego);

        Long filaInsertada = whenAgregoPuntos(puntosJuego);

        thenLosPuntosFueronAgregados(filaInsertada);

    }

    private Usuario givenTengoUnUsuario() {

        Usuario usuario = new Usuario();
        usuario.setRol("ADMIN");
        usuario.setEmail("admin@example.com");
        usuario.setPassword("1234");
        usuario.setActivo(true);
        usuario.setRompecabezaNivel(1);
        usuario.setPuntajeAdivinanza(0);
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;

    }

    private NivelJuego givenTengoUnNivelJuego(Usuario usuario) {

        NivelJuego nivelJuego = new NivelJuego();
        nivelJuego.setNivel(1L);
        nivelJuego.setNombre("Rompecabeza");
        nivelJuego.setUsuario(usuario);
        sessionFactory.getCurrentSession().save(nivelJuego);
        return nivelJuego;

    }

    private PuntosJuego givenTengoUnPuntosJuego(NivelJuego nivelJuego) {
        PuntosJuego puntosJuego = new PuntosJuego();
        puntosJuego.setInicioPartida(new Date());
        puntosJuego.setFinPartida(new Date());
        puntosJuego.setNivelJuego(nivelJuego);
        return puntosJuego;
    }

    private Long whenAgregoPuntos(PuntosJuego puntosJuego) {
        return repositorioPuntosJuego.agregarPuntos(puntosJuego);
    }

    private void thenLosPuntosFueronAgregados(Long filaInsertada) {
        assertThat(filaInsertada, equalTo(1L));
    }
}
