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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SessionFactory sessionFactory;


    RepositorioNivelJuego repositorioNivelJuego = mock(RepositorioNivelJuego.class);
    RepositorioPuntosJuego repositorioPuntosJuego = mock(RepositorioPuntosJuego.class);
    ServicioAdivinanzaImpl servicio = new ServicioAdivinanzaImpl(repositorioNivelJuego,repositorioPuntosJuego);

    @AfterEach
    @Transactional
    public void limpiarBaseDeDatos() {
        Session session = sessionFactory.getCurrentSession();
        session.clear();

        session.createNativeQuery("TRUNCATE TABLE PuntosJuego RESTART IDENTITY ").executeUpdate();
        session.createNativeQuery("TRUNCATE TABLE NivelJuego RESTART IDENTITY ").executeUpdate();
        session.createNativeQuery("TRUNCATE TABLE Usuario RESTART IDENTITY ").executeUpdate();
    }




    @Transactional
    @Rollback
    @Test
    void debeSumar2AlPuntajeYGuardarEnDbSiLaOpcionEsCorrecta() {

        Usuario usuario = givenUsuarioExiste();
        PuntosJuego puntos = givenPuntosInstanciadoCon(2 );
        int intentos = 0;
        double tiempo = 1;

        whenSeLlamaALaOpcionIngresada(puntos, usuario,intentos,tiempo);

        thenSeVerificaSiSeGuardoEnElRepositorioPuntosJuego();


    }




    @Transactional
    @Rollback
    @Test
    void debeCrearYGuardarNivelJuegoSiNoExiste() {
        Usuario usuario = givenUsuarioExiste();
        int intentos = 0;
        double tiempo = 1;
        PuntosJuego puntos = givenPuntosInstanciadoCon(2);
        givenNoExisteNivelJuego();

        whenSeLlamaALaOpcionIngresada(puntos, usuario,intentos, tiempo);
        thenSeVerificaQueSeCreoUnNivelJuegoEn(usuario);
        thenSeVerificaSiSeGuardoEnElRepositorioPuntosJuego();
    }
    
    @Transactional
    @Rollback
    @Test
    void siSeRecibeUnPuntajeDe10AlPasarPorElMetodoCalcularPuntajeSeConvierteEn30(){
        Usuario usuario = givenUsuarioExiste();
        PuntosJuego puntos = givenPuntosInstanciadoCon(10);
        int cantidadIntentos = 0;
        double tiempoEnSegundos = 30;
        thenSeCalculanLosPuntos(puntos, cantidadIntentos, tiempoEnSegundos);
        assertEquals( 30, puntos.getPuntos());

    }

    private void thenSeCalculanLosPuntos(PuntosJuego puntos, int cantidadIntentos, double tiempoEnSegundos) {
        servicio.calcularPuntos(puntos, cantidadIntentos, tiempoEnSegundos);
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


    private PuntosJuego givenPuntosInstanciadoCon(Integer num ) {
        PuntosJuego puntos = new PuntosJuego();

        puntos.setPuntos(num);
        sessionFactory.getCurrentSession().save(puntos);
        return puntos;
    }

    private void whenSeLlamaALaOpcionIngresada(PuntosJuego puntos, Usuario usuario, int intentos, double tiempo) {
        servicio.opcionIngresada(puntos, usuario, intentos, tiempo);
    }

    private void thenSeVerificaSiSeGuardoEnElRepositorioPuntosJuego() {
        // verify verifica si x metodo fue llamado
        // argThat es parecido a equals, verifica que PuntosJuego p tenga esos valores
        Mockito.verify(repositorioPuntosJuego).agregarPuntos(Mockito.argThat(p ->
                p.getPuntos() == 2 &&
                        p.getNivelJuego() != null &&
                        "AdivinanzaRandom".equals(p.getNivelJuego().getNombre())
        ));
    }

}
