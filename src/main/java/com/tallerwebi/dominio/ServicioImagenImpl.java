package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoSeEncontraronImagenesDelTipoEspecificadoException;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronImagenesException;
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
    public List<Imagen> getImagenesPorTipo(String tipo) throws NoSeEncontraronImagenesDelTipoEspecificadoException {
        List<Imagen> imagenes = repositorioImagen.getImagenesDeUnTipo(tipo);
        if (imagenes.isEmpty()) {
            throw new NoSeEncontraronImagenesDelTipoEspecificadoException("No se encontraron imagenes del tipo " + tipo);
        }
        return imagenes;
    }

    @Override
    public List<Imagen> getImagenesPorId(List<Integer> ids) throws NoSeEncontraronImagenesException {
        List<Imagen> imagenes = repositorioImagen.getImagenesPorId(ids);
        if (imagenes.isEmpty()) {
            throw new NoSeEncontraronImagenesException("No se encontraron imagenes con estos ids" + ids);
        }
        return imagenes;
    }

}
