package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class RompecabezasRequest {

    private List<List<List<String>>> matriz;
    private Integer idRompecabeza;
    private String idPieza;
    private Instant inicioTimer;
    private Instant finTimer;

    public RompecabezasRequest() {}

}
