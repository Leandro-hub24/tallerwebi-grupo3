package com.tallerwebi.config;

import com.tallerwebi.dominio.BrainRotPregunta;
import com.tallerwebi.dominio.RepositorioBrainRotPregunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class BrainRotDataInitializer {

    @Autowired
    private RepositorioBrainRotPregunta repositorioPregunta;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void inicializarDatos() {
        // Solo inicializar si no hay preguntas en la base de datos
        if (repositorioPregunta.contarPreguntas() == 0) {
            crearPreguntasBrainRot();
        }
    }

    private void crearPreguntasBrainRot() {
        List<BrainRotPregunta> preguntas = new ArrayList<>();

        // Pregunta 1: bellerina-cappuccina.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/bellerina-cappuccina.png",
            "Bellerina Cappuccina", 
            "Bailarina Cafecito", 
            "Princesa Café", 
            "Danzarina Mocha",
            "A", // Respuesta correcta
            "MEME",
            "FACIL",
            "Imagen de personaje bailarina con temática de café"
        ));

        // Pregunta 2: bombardino-crocodilo.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/bombardino-crocodilo.png",
            "Cocodrilo Bombardino", 
            "Lagarto Explosivo", 
            "Bombardino Crocodilo", 
            "Reptil Bomba",
            "C", // Respuesta correcta
            "VIRAL",
            "MEDIO",
            "Imagen de cocodrilo con temática explosiva"
        ));

        // Pregunta 3: brrbrrr-patapi.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/brrbrrr-patapi.png",
            "Patapi Frío", 
            "Brrbrrr Patapi", 
            "Pato Congelado", 
            "Patito Helado",
            "B", // Respuesta correcta
            "TIKTOK",
            "FACIL",
            "Imagen de pato con sonido de frío brrbrrr"
        ));

        // Pregunta 4: capiballero-cocosini.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/capiballero-cocosini.png",
            "Cocosini Caballero", 
            "Capiballero Cocosini", 
            "Coco Guerrero", 
            "Caballero Coco",
            "B", // Respuesta correcta
            "MEME",
            "MEDIO",
            "Imagen de caballero con temática de coco"
        ));

        // Pregunta 5: capuccino-assasno.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/capuccino-assasno.png",
            "Asesino Café", 
            "Cappuccino Ninja", 
            "Capuccino Assasino",
            "Café Mortal",
            "C", // Respuesta correcta
            "VIRAL",
            "DIFICIL",
            "Imagen de asesino con temática de cappuccino"
        ));

        // Pregunta 6: chimpancini-bananini.jpg
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/chimpancini-bananini.jpg",
            "Mono Banana", 
            "Chimpancini Bananini", 
            "Simio Amarillo", 
            "Chimpancé Frutal",
            "B", // Respuesta correcta
            "YOUTUBE",
            "FACIL",
            "Imagen de chimpancé con bananas"
        ));

        // Pregunta 7: liriririlarila.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/liriririlarila.png",
            "Lalalala Riri", 
            "Liriririlarila", 
            "Riri Lala", 
            "Larila Liri",
            "B", // Respuesta correcta
            "TIKTOK",
            "MEDIO",
            "Imagen con sonidos repetitivos liriririlarila"
        ));

        // Pregunta 8: los-tralaleritos.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/los-tralaleritos.png",
            "Tralalá Pequeños", 
            "Los Tralaleritos", 
            "Cantantes Tra", 
            "Coro Lalala",
            "B", // Respuesta correcta
            "MEME",
            "FACIL",
            "Imagen de pequeños personajes cantando tralalá"
        ));

        // Pregunta 9: saturno-saturnita.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/saturno-saturnita.png",
            "Planeta Anillado", 
            "Saturno Saturnita", 
            "Saturnita Espacial", 
            "Anillo Cósmico",
            "B", // Respuesta correcta
            "VIRAL",
            "MEDIO",
            "Imagen de Saturno con temática cute"
        ));

        // Pregunta 10: tralalero.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/tralalero.png",
            "Cantante Tra", 
            "Tralalero", 
            "Músico Lala", 
            "Artista Tralala",
            "B", // Respuesta correcta
            "YOUTUBE",
            "FACIL",
            "Imagen de personaje cantando tralalero"
        ));

        // Pregunta 11: tuntuntunsahur.png
        preguntas.add(new BrainRotPregunta(
            "/resources/img/imgversus/tuntuntunsahur.png",
            "Sahur Tuntun", 
            "Tuntuntunsahur", 
            "Tambor Sahur", 
            "Ritmo Tuntun",
            "B", // Respuesta correcta
            "TIKTOK",
            "DIFICIL",
            "Imagen con ritmo tuntuntunsahur"
        ));

        // Guardar todas las preguntas en la base de datos
        for (BrainRotPregunta pregunta : preguntas) {
            repositorioPregunta.guardar(pregunta);
        }

        System.out.println("✅ Se inicializaron " + preguntas.size() + " preguntas de Brain Rot");
    }
}
