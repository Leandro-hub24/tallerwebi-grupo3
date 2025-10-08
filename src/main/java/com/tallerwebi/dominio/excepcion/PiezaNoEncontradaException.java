package com.tallerwebi.dominio.excepcion;

public class PiezaNoEncontradaException extends RuntimeException {
    public PiezaNoEncontradaException(String message) {
        super(message);
    }
}
