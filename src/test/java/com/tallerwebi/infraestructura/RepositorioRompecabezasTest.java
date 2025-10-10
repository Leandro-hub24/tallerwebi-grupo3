package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioRompecabeza;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.Rompecabeza;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

    @ExtendWith(SpringExtension.class)
    @WebAppConfiguration
    @ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
    public class RepositorioRompecabezasTest {


        @Autowired
        private RepositorioRompecabeza repositorioRompecabeza;

        @Autowired
        private SessionFactory sessionFactory;

        @AfterEach
        public void limpiarBaseDeDatos() {
            Session session = sessionFactory.getCurrentSession();


            String sqlQuery = "TRUNCATE TABLE Rompecabeza RESTART IDENTITY;"; // Para H2 o PostgreSQL
            // String sqlQuery = "TRUNCATE TABLE Rompecabeza;"; // Para MySQL / MariaDB

            session.createNativeQuery(sqlQuery).executeUpdate();
        }

        @Test
        @Transactional
        @Rollback
        public void buscarRompecabezaPorId() {

            Rompecabeza rompecabeza1 = givenTengoUnRompecabeza("Bombardino", "/img/bombardino.png");

            Rompecabeza rompecabezaObtenido = whenBuscoRompecabezaPorIdUno(1L);

            thenObtengoUnRompecabeza(1L, rompecabezaObtenido);


        }

        private Rompecabeza givenTengoUnRompecabeza(String nombre, String urlImg) {

            Rompecabeza rompecabeza = new Rompecabeza();
            rompecabeza.setNombre(nombre);
            rompecabeza.setUrlImg(urlImg);
            sessionFactory.getCurrentSession().save(rompecabeza);
            return rompecabeza;
        }

        private Rompecabeza whenBuscoRompecabezaPorIdUno(long id) {

            return repositorioRompecabeza.buscarRompecabeza(id);

        }

        private void thenObtengoUnRompecabeza(Long idEsperado, Rompecabeza rompecabezaObtenido) {

            assertThat(rompecabezaObtenido.getId(), equalTo(idEsperado));

        }

        @Test
        @Transactional
        @Rollback
        public void buscarUltimoNivelId() {

            Rompecabeza rompecabeza1 = givenTengoUnRompecabeza("Bombardino", "/img/bombardino.png");
            Rompecabeza rompecabeza2 = givenTengoUnRompecabeza("Bombar", "/img/bombardino.png");
            Rompecabeza rompecabeza3 = givenTengoUnRompecabeza("Bombardi", "/img/bombardino.png");
            Rompecabeza rompecabeza4 = givenTengoUnRompecabeza("Bomb", "/img/bombardino.png");

            Long rompecabezaIdObtenido = whenBuscoUltimoRompecabezaId();

            thenObtengoUnIdRompecabeza(4L, rompecabezaIdObtenido);


        }

        private Long whenBuscoUltimoRompecabezaId() {
            return repositorioRompecabeza.buscarUltimoNivelId();
        }

        private void thenObtengoUnIdRompecabeza(long idEsperado, Long rompecabezaIdObtenido) {

            assertThat(rompecabezaIdObtenido, equalTo(idEsperado));

        }

        @Test
        @Transactional
        @Rollback
        public void buscarRompecabezasPorRompecabezaNivel() {

            Rompecabeza rompecabeza1 = givenTengoUnRompecabeza("Bombardino", "/img/bombardino.png");
            Rompecabeza rompecabeza2 = givenTengoUnRompecabeza("Bombar", "/img/bombardino.png");
            Rompecabeza rompecabeza3 = givenTengoUnRompecabeza("Bombardi", "/img/bombardino.png");
            Rompecabeza rompecabeza4 = givenTengoUnRompecabeza("Bomb", "/img/bombardino.png");

            List<Rompecabeza> rompecabezasObtenidos = whenBuscoRompecabezasPorRompecabezaNivel(3);

            thenObtengoUnListRompecabeza(3, rompecabezasObtenidos);


        }

        private List<Rompecabeza> whenBuscoRompecabezasPorRompecabezaNivel(Integer rompecabezaNivel) {

            return repositorioRompecabeza.buscarRompecabezas(rompecabezaNivel);

        }

        private void thenObtengoUnListRompecabeza(Integer cantNivelesEsperado, List<Rompecabeza> rompecabezasObtenidos) {
            assertThat(rompecabezasObtenidos.size(), equalTo(cantNivelesEsperado));
        }

    }
