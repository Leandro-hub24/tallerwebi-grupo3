package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RompecabezasRequest {

    // La matriz 2D. Sus elementos son arrays (List) de elementos de la pieza,
    // y cada elemento de la pieza es un array de [id, urlImg]
    // Esto se mapea a: List< [ List<String> , List<String> , ... ] >
    private List<List<List<String>>> matriz;
    private Integer idRompecabeza;
    private String idPieza;
    // El ID de la pieza que fue clickeada

    // --- Constructor sin argumentos (necesario) ---
    public RompecabezasRequest() {}


}
