package com.tallerwebi.dominio;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ServicioAdivinanzaImplTest {

    @Autowired
    private SessionFactory sessionFactory;


    RepositorioNivelJuego repositorioNivelJuego = mock(RepositorioNivelJuego.class);
    RepositorioPuntosJuego repositorioPuntosJuego = mock(RepositorioPuntosJuego.class);
    ServicioAdivinanzaImpl servicio = new ServicioAdivinanzaImpl(repositorioNivelJuego,repositorioPuntosJuego);

    @AfterEach
    @Transactional
    public void limpiarBaseDeDatos() {
        Session session = sessionFactory.getCurrentSession();
        session.clear(); // <- esto es clave para evitar el error de Hibernate

        session.createNativeQuery("TRUNCATE TABLE PuntosJuego RESTART IDENTITY ").executeUpdate();
        session.createNativeQuery("TRUNCATE TABLE NivelJuego RESTART IDENTITY ").executeUpdate();
        session.createNativeQuery("TRUNCATE TABLE Usuario RESTART IDENTITY ").executeUpdate();
    }




    @Transactional
    @Rollback
    @Test
    void debeSumar2AlPuntajeYGuardarEnDbSiLaOpcionEsCorrecta() {

        Usuario usuario = givenUsuarioExiste();
        PuntosJuego puntos = givenPuntosInstanciadoCon2( );


        whenSeLlamaALaOpcionIngresada(puntos, usuario);

        thenSeVerificaSiSeGuardoEnElRepositorioPuntosJuego();


    }




    @Transactional
    @Rollback
    @Test
    void debeCrearYGuardarNivelJuegoSiNoExiste() {
        Usuario usuario = givenUsuarioExiste();

        PuntosJuego puntos = givenPuntosInstanciadoCon2();
        givenNoExisteNivelJuego();

        whenSeLlamaALaOpcionIngresada(puntos, usuario);
        thenSeVerificaQueSeCreoUnNivelJuegoEn(usuario);
        thenSeVerificaSiSeGuardoEnElRepositorioPuntosJuego();
    }

    private void thenSeVerificaQueSeCreoUnNivelJuegoEn(Usuario usuario) {
        Mockito.verify(repositorioNivelJuego).guardarNivelJuego(Mockito.argThat(n ->
                "AdivinanzaRandom".equals(n.getNombre()) &&
                        n.getUsuario().equals(usuario)
        ));
    }

    private void givenNoExisteNivelJuego() {
        Mockito.when(repositorioNivelJuego.buscarNivelJuegoPorIdUsuario(1L, "AdivinanzaRandom"))
                .thenReturn(null);

    }


//    @Test
//    void siLaOpcionEsIncorrectaSeLeReduceElPuntajeA1() {
//        usuario.setPuntajeAdivinanza(5);
//
//        servicio.opcionIncorrecta(usuario);
//
//        assertEquals(4, usuario.getPuntajeAdivinanza()); // Verifica lógica de negocio
//        Mockito.verify(repositorioUsuario).modificar(usuario); // Verifica persistencia
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void siTengoUnUsuarioYCuandoCompleteUnNivelNuncaCompletadoAntesSeGeneraUnNivelJuego() {
//        Usuario usuario = givenTengoUnUsuarioQueCompletoUnNivel("luis@ejemplo.com", "pwd1");
//
//
//        whenSeVerificaSiNoExisteNivelJuegoEnDB("AdivinanzaRandom");
//
//        thenSeGeneraUnNuevoNivelJuego("AdivinanzaRandom");
//    }


    private Usuario givenUsuarioExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@usuario.com");
        usuario.setPassword("password");

        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }


    private PuntosJuego givenPuntosInstanciadoCon2( ) {
        PuntosJuego puntos = new PuntosJuego();

        puntos.setPuntos(2);
        sessionFactory.getCurrentSession().save(puntos);
        return puntos;
    }

    private void whenSeLlamaALaOpcionIngresada(PuntosJuego puntos, Usuario usuario) {
        servicio.opcionIngresada(puntos, usuario);
    }

    private void thenSeVerificaSiSeGuardoEnElRepositorioPuntosJuego() {
        Mockito.verify(repositorioPuntosJuego).agregarPuntos(Mockito.argThat(p ->
                p.getPuntos() == 2 &&
                        p.getNivelJuego() != null &&
                        "AdivinanzaRandom".equals(p.getNivelJuego().getNombre())
        ));
    }

}
