package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Imagen;
import com.tallerwebi.dominio.RepositorioImagen;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioImagenTest {

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RepositorioImagen repositorioImagen;

    @Autowired
    private SessionFactory sessionFactory;

    @AfterEach
    public void limpiarBaseDeDatos() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "TRUNCATE TABLE IMAGEN RESTART IDENTITY;";
        session.createNativeQuery(sql).executeUpdate();
    }

    @Test
    @Transactional
    @Rollback
    public void queAlPedirImagenesPorTipoDevuelveSoloLasDeEseTipo() {
        givenTengoUnaImagen("Taza", "/img/taza.png", "crear");
        givenTengoUnaImagen("Pato", "/img/pato.png", "crear");
        givenTengoUnaImagen("Logo", "/img/logo.png", "a");

        List<Imagen> imagenesObtenidas = whenBuscoImagenesPorTipo("crear");

        thenObtengoUnaListaCon(2, imagenesObtenidas);
        assertThat(imagenesObtenidas.get(0).getNombre(), equalTo("Taza"));
        assertThat(imagenesObtenidas.get(1).getNombre(), equalTo("Pato"));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlPedirImagenesPorTipoInexistenteDevuelveListaVacia() {
        givenTengoUnaImagen("Taza", "/img/taza.png", "crear");
        givenTengoUnaImagen("Logo", "/img/logo.png", "ui");

        List<Imagen> imagenesObtenidas = whenBuscoImagenesPorTipo("tipo-que-no-existe");

        thenObtengoUnaListaCon(0, imagenesObtenidas);
        assertThat(imagenesObtenidas.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlPedirImagenesPorIdsDevuelveSoloLasDeEsosIds() {
        Imagen img1 = givenTengoUnaImagen("Taza", "/img/taza.png", "crear"); // ID 1
        Imagen img2 = givenTengoUnaImagen("Pato", "/img/pato.png", "crear"); // ID 2
        Imagen img3 = givenTengoUnaImagen("Logo", "/img/logo.png", "ui");    // ID 3

        List<Integer> idsBuscados = List.of(img1.getId(), img3.getId()); // Buscamos ID 1 y 3

        List<Imagen> imagenesObtenidas = whenBuscoImagenesPorIds(idsBuscados);

        thenObtengoUnaListaCon(2, imagenesObtenidas);
        assertThat(imagenesObtenidas.get(0).getNombre(), equalTo("Taza"));
        assertThat(imagenesObtenidas.get(1).getNombre(), equalTo("Logo"));
    }



    private Imagen givenTengoUnaImagen(String nombre, String url, String tipo) {
        Imagen imagen = new Imagen();
        imagen.setNombre(nombre);
        imagen.setUrl(url);
        imagen.setTipo(tipo);
        sessionFactory.getCurrentSession().save(imagen);
        return imagen;
    }

    private List<Imagen> whenBuscoImagenesPorTipo(String tipo) {
        return repositorioImagen.getImagenesDeUnTipo(tipo);
    }

    private List<Imagen> whenBuscoImagenesPorIds(List<Integer> ids) {
        return repositorioImagen.getImagenesPorId(ids);
    }

    private void thenObtengoUnaListaCon(int cantidadEsperada, List<Imagen> listaObtenida) {
        assertThat(listaObtenida, notNullValue());
        assertThat(listaObtenida.size(), equalTo(cantidadEsperada));
    }
}