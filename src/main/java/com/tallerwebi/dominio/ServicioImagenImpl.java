package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoSeEncontraronImagenesDelTipoCrearException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioImagenImpl implements ServicioImagen {
    RepositorioImagen repositorioImagen;

    @Autowired
    public ServicioImagenImpl(RepositorioImagen repositorioImagen) {
        this.repositorioImagen = repositorioImagen;
    }


    @Override
    public List<Imagen> getImagenesCrear() throws NoSeEncontraronImagenesDelTipoCrearException {
        List<Imagen> imagenes = repositorioImagen.getImagenesDeUnTipo("crear");
        if (imagenes.isEmpty()) {
            throw new NoSeEncontraronImagenesDelTipoCrearException("No se encontraron imagenes del tipo crear");
        }
        return imagenes;
    }
}
