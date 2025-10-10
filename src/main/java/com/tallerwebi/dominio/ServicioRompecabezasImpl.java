package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelesNoEncontradosException;
import com.tallerwebi.dominio.excepcion.PiezaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.RompecabezaNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("servicioRompecabezas")
@Transactional
public class ServicioRompecabezasImpl implements ServicioRompecabezas {

    private RepositorioRompecabeza repositorioRompecabeza;

    private static final String imagenBlanco = "data:image/png;base64,iVBORw0KGgoAAAANS" +
            "UhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAwQYFqEAAAAASUVORK5CYII=";

    @Autowired
    public ServicioRompecabezasImpl (RepositorioRompecabeza repositorioRompecabeza) {this.repositorioRompecabeza = repositorioRompecabeza;};


    @Override
    public List<Rompecabeza> consultarRompecabezasDelUsuario(Integer rompecabezaNivel) {
        List<Rompecabeza> rompecabezas = repositorioRompecabeza.buscarRompecabezas(rompecabezaNivel);
        System.out.println(rompecabezas);
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

    @Override
    public List<List<List<String>>> moverPieza(List<List<List<String>>> matrizActual, String idPiezaAMover) {

        String datoABuscar = idPiezaAMover;
        int filas = matrizActual.size();
        int columnas = matrizActual.get(0).size();

        int filaEncontrada = -1;
        int columnaEncontrada = -1;

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {

                if (Objects.equals(matrizActual.get(f).get(c).get(0), datoABuscar)) {
                    filaEncontrada = f;
                    columnaEncontrada = c;
                    f = filas;
                    break;
                }
            }
        }

        if(filaEncontrada == -1) {
            throw new PiezaNoEncontradaException("Pieza no encontrada");
        }

        int y = filaEncontrada;
        int x = columnaEncontrada;

        List<String> piezaClickeada = matrizActual.get(y).get(x);
        List<String> auxiliar;



        // A. Mover Arriba (y-1)
        if (y > 0 && Objects.equals(matrizActual.get(y - 1).get(x).get(1), imagenBlanco)) {
            auxiliar = matrizActual.get(y-1).get(x);
            matrizActual.get(y-1).set(x, piezaClickeada);
            matrizActual.get(y).set(x, auxiliar);
        }

        // B. Mover Abajo (y+1)
        if (y < filas - 1 && Objects.equals(matrizActual.get(y + 1).get(x).get(1), imagenBlanco)) {
            auxiliar = matrizActual.get(y+1).get(x);
            matrizActual.get(y+1).set(x, piezaClickeada);
            matrizActual.get(y).set(x, auxiliar);
        }

        // C. Mover Izquierda (x-1)
        if (x > 0 && Objects.equals(matrizActual.get(y).get(x - 1).get(1), imagenBlanco)) {
            auxiliar = matrizActual.get(y).get(x-1);
            matrizActual.get(y).set(x-1, piezaClickeada);
            matrizActual.get(y).set(x, auxiliar);
        }

        // D. Mover Derecha (x+1)
        if (x < columnas - 1 && Objects.equals(matrizActual.get(y).get(x + 1).get(1), imagenBlanco)) {
            auxiliar = matrizActual.get(y).get(x+1);
            matrizActual.get(y).set(x+1, piezaClickeada);
            matrizActual.get(y).set(x, auxiliar);
        }
        return matrizActual;

    }

    @Override
    public boolean comprobarVictoria(List<List<List<String>>> matrizActual) {
        Map<String, String> map = new HashMap<>();
        int contador = 0;

        for(int x = 0; x < matrizActual.size(); x++){
            for(int y = 0; y < matrizActual.get(x).size(); y++){
                if(matrizActual.get(x).get(y).get(0).equals("img_" + x + "-" + y)){
                    contador++;
                }
            }
        }

        int totalPiezas = matrizActual.size() * matrizActual.get(0).size();

        if(contador == totalPiezas){
            map.put("mensaje", "Victoria");
            return true;
        }
        map.put("mensaje", "No resuelto");
        return false;
    }

    @Override
    public Long buscarUltimoNivelId() {
        return repositorioRompecabeza.buscarUltimoNivelId();
    }


}
