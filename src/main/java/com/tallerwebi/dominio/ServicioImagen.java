package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoSeEncontraronImagenesDelTipoCrearException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioImagen {
    List<Imagen> getImagenesCrear() throws NoSeEncontraronImagenesDelTipoCrearException;
}
