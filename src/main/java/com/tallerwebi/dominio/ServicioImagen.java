package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoSeEncontraronImagenesDelTipoEspecificadoException;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronImagenesException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioImagen {
    List<Imagen> getImagenesPorTipo(String tipo) throws NoSeEncontraronImagenesDelTipoEspecificadoException;
    List<Imagen> getImagenesPorId(List<Integer> ids) throws NoSeEncontraronImagenesException;
}
