package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import com.tallerwebi.dominio.excepcion.RompecabezaNoEncontradoException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service("servicioRompecabezas")
@Transactional
public class ServicioRompecabezasImpl implements ServicioRompecabezas {

    private RepositorioRompecabeza repositorioRompecabeza;

    @Autowired
    public ServicioRompecabezasImpl (RepositorioRompecabeza repositorioRompecabeza) {this.repositorioRompecabeza = repositorioRompecabeza;};


    @Override
    public ArrayList<Rompecabeza> consultarRompecabezasDelUsuario(Long idUsuario) {
        ArrayList<Rompecabeza> rompecabezas = repositorioRompecabeza.buscarRompecabezas(idUsuario);
        if(rompecabezas == null){
            throw new NivelesNoEncontradosException("Niveles no encontrados");
        }
        return rompecabezas;

    }

    @Override
    public Rompecabeza consultarRompecabeza(Long idRompecabeza) {
        Rompecabeza rompecabeza = repositorioRompecabeza.buscarRompecabeza(idRompecabeza);
        if(rompecabeza == null){
            throw new RompecabezaNoEncontradoException("Rompecabeza no encontrado");
        }
        return rompecabeza;
    }
}
