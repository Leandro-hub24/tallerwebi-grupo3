package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FaltaSeleccionarEstiloParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.FaltaSeleccionarImagenParaCrearBrainrotException;
import com.tallerwebi.dominio.excepcion.NoSePuedeCrearUnBrainrotConMasDe4ImagenesException;

import java.util.List;

public interface ServicioCrear {

    byte[] crearBrainrot(String estilo, List<Integer> imagenes) throws FaltaSeleccionarImagenParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarEstiloParaCrearBrainrotException;

  }
