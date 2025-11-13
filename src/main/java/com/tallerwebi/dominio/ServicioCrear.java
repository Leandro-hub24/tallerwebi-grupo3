package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioCrear {

    BrainrotCreado crearBrainrot(String estilo, List<Integer> imagenes, String fondo) throws FaltaSeleccionarImagenParaCrearBrainrotException, NoSePuedeCrearUnBrainrotConMasDe4ImagenesException, FaltaSeleccionarEstiloParaCrearBrainrotException, NoSePudoCrearBrainrotException, FaltaSeleccionarFondoParaCrearBrainrotException;

  }
