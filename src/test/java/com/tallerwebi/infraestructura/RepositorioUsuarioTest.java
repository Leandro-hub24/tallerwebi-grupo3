package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.RepositorioUsuario;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioUsuarioTest {

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private SessionFactory sessionFactory;

    @AfterEach
    public void limpiarBaseDeDatos() {
        Session session = sessionFactory.getCurrentSession();

        String sql = "TRUNCATE TABLE usuario RESTART IDENTITY;";
        session.createNativeQuery(sql).executeUpdate();
    }

    @Test
    @Transactional
    @Rollback
    public void guardarYBuscarUsuarioPorEmailYPassword() {
        Usuario usuario = givenTengoUnUsuario("juan@ejemplo.com", "secreto");

        Usuario buscado = whenBuscoUsuarioPorEmailYPassword("juan@ejemplo.com", "secreto");

        thenObtengoElUsuario(usuario, buscado);
    }

    @Test
    @Transactional
    @Rollback
    public void buscarUsuarioPorEmailInexistenteDevuelveNull() {
        Usuario buscado = whenBuscoUsuarioPorEmail("noexiste@correo.com");

        thenNoObtengoUsuario(buscado);
    }

    @Test
    @Transactional
    @Rollback
    public void buscarUsuarioPorEmailExistente() {
        Usuario usuario = givenTengoUnUsuario("ana@ejemplo.com", "clave");

        Usuario buscado = whenBuscoUsuarioPorEmail("ana@ejemplo.com");

        thenObtengoElUsuario(usuario, buscado);
    }

    @Test
    @Transactional
    @Rollback
    public void modificarUsuarioYaGuardado() {
        Usuario usuario = givenTengoUnUsuario("luis@ejemplo.com", "pwd1");


        usuario.setPassword("nuevopwd");
        whenModificoUsuario(usuario);

        Usuario buscado = whenBuscoUsuarioPorEmailYPassword("luis@ejemplo.com", "nuevopwd");

        thenObtengoElUsuario(usuario, buscado);
    }



    private Usuario givenTengoUnUsuario(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

    private Usuario whenBuscoUsuarioPorEmailYPassword(String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    private Usuario whenBuscoUsuarioPorEmail(String email) {
        return repositorioUsuario.buscar(email);
    }

    private void whenModificoUsuario(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    private void thenObtengoElUsuario(Usuario esperado, Usuario obtenido) {
        assertThat(obtenido, notNullValue());
        assertThat(obtenido.getId(), equalTo(esperado.getId()));
        assertThat(obtenido.getEmail(), equalTo(esperado.getEmail()));
        assertThat(obtenido.getPassword(), equalTo(esperado.getPassword()));
    }

    private void thenNoObtengoUsuario(Usuario obtenido) {
        assertThat(obtenido, nullValue());
    }
}