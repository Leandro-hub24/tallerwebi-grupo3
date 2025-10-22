package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.NivelJuego;
import com.tallerwebi.dominio.RepositorioNivelJuego;
import com.tallerwebi.dominio.Usuario;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})

public class RepositorioNivelJuegoTest {

    private static final String juego = "Rompecabeza";

    @Autowired
    private RepositorioNivelJuego repositorioNivelJuego;

    @Autowired
    private SessionFactory sessionFactory;

    @AfterEach
    public void limpiarBaseDeDatos() {
        Session session = sessionFactory.getCurrentSession();


        String sqlQuery = "TRUNCATE TABLE NivelJuego RESTART IDENTITY;";
        String sqlQuery1 = "TRUNCATE TABLE Usuario RESTART IDENTITY;";// Para H2 o PostgreSQL
        // String sqlQuery = "TRUNCATE TABLE NivelJuego;"; // Para MySQL / MariaDB

        session.createNativeQuery(sqlQuery).executeUpdate();
        session.createNativeQuery(sqlQuery1).executeUpdate();
    }

    @Test
    @Transactional
    @Rollback
    public void buscarNivelJuegoDeRompecabezaConIdUsuario() {

        Usuario usuario = givenTengoUnUsuario();
        NivelJuego nivelJuego = givenTengoUnNivelJuego(usuario);

        NivelJuego nivelJuegoObtenido = whenBuscoNivelJuego(1L);

        thenObtengoUnNivelJuego(nivelJuegoObtenido, 1L);

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
        nivelJuego.setNombre("Rompecabeza");
        nivelJuego.setNivel(1L);
        nivelJuego.setUsuario(usuario);
        sessionFactory.getCurrentSession().save(nivelJuego);
        return nivelJuego;

    }

    private NivelJuego whenBuscoNivelJuego(Long usuarioId) {
        return repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(usuarioId, juego);
    }

    private void thenObtengoUnNivelJuego(NivelJuego nivelJuegoObtenido, Long nivelJuegoEsperado) {
        assertThat(nivelJuegoObtenido.getNivel(), equalTo(nivelJuegoEsperado));
    }

    @Test
    @Transactional
    @Rollback
    public void modificarNivelJuegoDeRompecabezaIncrementandoEnUno() {

        Usuario usuario = givenTengoUnUsuario();
        NivelJuego nivelJuego = givenTengoUnNivelJuego(usuario);

        Long nivelJuegoObtenido = whenModificoNivelJuego(1L);

        thenSeIncrementaEnUnoElNivelJuego(2L, nivelJuegoObtenido);

    }

    private Long whenModificoNivelJuego(Long idUsuario) {

        return repositorioNivelJuego.modificarNivelJuego(idUsuario);

    }

    private void thenSeIncrementaEnUnoElNivelJuego(Long nivelJuegoEsperado, Long nivelJuegoObtenido) {

        assertThat(nivelJuegoEsperado, equalTo(nivelJuegoObtenido));

    }

    @Test
    @Transactional
    @Rollback
    public void guardarNivelJuego() {

        Usuario usuario = givenTengoUnUsuario();
        NivelJuego nivelJuego = givenTengoUnNivelJuegoNuevo(usuario);

        NivelJuego nivelJuegoAgregado = whenAgregoNivelJuego(nivelJuego);

        thenNivelJuegoAgregado(nivelJuegoAgregado);

    }

    private NivelJuego givenTengoUnNivelJuegoNuevo(Usuario usuario) {
        NivelJuego nivelJuego = new NivelJuego();
        nivelJuego.setNombre("Rompecabeza");
        nivelJuego.setNivel(1L);
        nivelJuego.setUsuario(usuario);
        return nivelJuego;
    }

    private NivelJuego whenAgregoNivelJuego(NivelJuego nivelJuego){
        return repositorioNivelJuego.guardarNivelJuego(nivelJuego);
    }

    private void thenNivelJuegoAgregado(NivelJuego nivelJuegoAgregado) {
        assertThat(nivelJuegoAgregado.getNivel(), equalTo(1L));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarNivelJuegoDeRompecabezaConIdUsuarioYIdRompecabeza() {

        Usuario usuario = givenTengoUnUsuario();
        NivelJuego nivelJuego = givenTengoUnNivelJuego(usuario);
        Long idRompecabeza = givenTengoUnIdRompecabeza();

        NivelJuego nivelJuegoObtenido = whenBuscoNivelJuegoConIdUsuarioYIdRompecabeza(1L, idRompecabeza);

        thenObtengoUnNivelJuego(nivelJuegoObtenido, 1L);

    }

    private Long givenTengoUnIdRompecabeza() {
        return 1L;
    }

    private NivelJuego whenBuscoNivelJuegoConIdUsuarioYIdRompecabeza(long usuarioId, Long idRompecabeza) {
        return repositorioNivelJuego.buscarNivelJuegoPoridUsuarioYIdRompecabeza(usuarioId, juego,  idRompecabeza);
    }



}
