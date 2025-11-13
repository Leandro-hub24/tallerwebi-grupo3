document.addEventListener('DOMContentLoaded', () => {
    const listaDeBrainRots = Array.isArray(window.BRAINROTS)
        ? window.BRAINROTS
        : (typeof window.BRAINROTS === 'string' ? JSON.parse(window.BRAINROTS) : []);

    const TIEMPO_MUESTREO = 700;
    const reproductor = new Audio();
    reproductor.preload = 'auto';
    reproductor.volume = 1.0;

    function reproducirAudio(item) {
        if (!item || !item.audio) return;
        try {
            const rutaAudio = item.audio;
            reproductor.pause();
            reproductor.src = rutaAudio;
            reproductor.currentTime = 0;
            reproductor.play().catch(() => {});
        } catch (_) { }
    }

    if (document.getElementById('iniciar-juego')) {
        const contenedorEmojis = document.getElementById('contenedor-emojis');
        const botonIniciarJuego = document.getElementById('iniciar-juego');
        const mensajeInicial = document.getElementById('mensaje-inicial');

        function mostrarPreguntaBrainRot() {
            if (!listaDeBrainRots.length) {
                mensajeInicial.textContent = 'Sin datos. Reemplazá los assets o actualizá el backend.';
                return;
            }
            const indiceAleatorio = Math.floor(Math.random() * listaDeBrainRots.length);
            const brainRotActual = listaDeBrainRots[indiceAleatorio];

            localStorage.setItem('brainRotActualId', brainRotActual.id);
            contenedorEmojis.textContent = brainRotActual.emojis;
            mensajeInicial.textContent = `¿Qué personaje Brain Rot representan estos emojis?`;
            botonIniciarJuego.style.display = 'block';
        }

        botonIniciarJuego.addEventListener('click', () => {
            window.location.href = '/spring/visor';
        });

        mostrarPreguntaBrainRot();
    } else if (document.getElementById('imagen-aleatoria')) {
        const imagenAleatoria = document.getElementById('imagen-aleatoria');
        const mensajeResultado = document.getElementById('mensaje-resultado');
        const tituloVisor = document.getElementById('titulo-visor');
        const botonReiniciar = document.querySelector('.reiniciar');

        let intervaloVisor = null;
        let estaCorriendo = true;

        const brainRotId = localStorage.getItem('brainRotActualId');
        const brainRotActual = listaDeBrainRots.find(item => String(item.id) === String(brainRotId));

        if (!brainRotActual) {
            window.location.href = '/spring/brainRotEmojis';
            return;
        }

        tituloVisor.textContent = `Pistas: ${brainRotActual.emojis}`;
        mensajeResultado.classList.add('oculto');

        function cambiarImagenAleatoria() {
            if (!estaCorriendo) return;
            const indiceAleatorio = Math.floor(Math.random() * listaDeBrainRots.length);
            imagenAleatoria.src = listaDeBrainRots[indiceAleatorio].imagen;
        }

        function detenerVisor() {
            if (!estaCorriendo) return;
            clearInterval(intervaloVisor);
            estaCorriendo = false;

            const nombreArchivoDetenido = (imagenAleatoria.src || '').split('/').pop();
            const brainRotMostrado = listaDeBrainRots.find(item => (item.imagen || '').includes(nombreArchivoDetenido));

            mensajeResultado.classList.remove('oculto');

            if (brainRotMostrado && brainRotMostrado.nombre === brainRotActual.nombre) {
                mensajeResultado.textContent = `¡Adivinaste! 🎉`;
                mensajeResultado.style.color = 'green';
                reproducirAudio(brainRotActual);
                botonReiniciar.textContent = '¡Jugar de nuevo!';
            } else {
                mensajeResultado.textContent = `¡Has fallado! 😔 Respuesta: ${brainRotActual.nombre}.`;
                mensajeResultado.style.color = 'red';
                botonReiniciar.textContent = 'Volver a intentar...';
            }
            imagenAleatoria.style.cursor = 'default';
        }

        imagenAleatoria.src = listaDeBrainRots.length ? listaDeBrainRots[0].imagen : '';
        intervaloVisor = setInterval(cambiarImagenAleatoria, TIEMPO_MUESTREO);

        imagenAleatoria.addEventListener('click', () => {
            if (estaCorriendo) detenerVisor();
        });

        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && estaCorriendo) {
                detenerVisor();
            }
        });
    }
});