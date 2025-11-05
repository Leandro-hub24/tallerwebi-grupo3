
document.addEventListener("DOMContentLoaded", function() {

    console.log("Estado inicial:", estadoActual);

    actualizarUIInicial(estadoActual);

    conectarAPartida();
});

const divEsperando = document.getElementById('esperando-oponente');
const divTablero = document.getElementById('tablero-de-juego');
const spanNombreJ2 = document.getElementById('nombre-j2');

function actualizarUIInicial(estado) {

    if (estado === "ESPERANDO_OPONENTE") {
        divEsperando.style.display = 'flex';
        divTablero.classList.remove('d-flex');
        divTablero.style.display = 'none';
        divTablero.classList.add('deshabilitado');
    } else if (estado === "EN_CURSO") {
        divEsperando.style.display = 'none';
        divTablero.style.display = 'block';
        divTablero.classList.add('d-flex');
        divTablero.classList.remove('deshabilitado');
        iniciarPartida()

    }
}

var stompClient = null;

function conectarAPartida() {

    var socket = new SockJS(wsConnectUrl);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Conectado a la partida: ' + frame);
        const destino = '/topic/partida/' + idPartida;

        stompClient.subscribe(destino, function (mensaje) {
            const datos = JSON.parse(mensaje.body);

            if (datos.estado) {

                console.log("¡Actualización de estado!", datos.estado);
                estadoActual = datos.estado;
                actualizarUI(estadoActual, datos);

                if(datos.jugador2Id) {
                    spanNombreJ2.innerText = datos.jugador2Nombre;
                }
            }
        });
    });
}

function actualizarUI(estado, datos) {
    if (estado === "EN_CURSO") {
        divEsperando.style.display = 'none';
        divTablero.style.display = 'block';
        divTablero.classList.add('d-flex');
        divTablero.classList.remove('deshabilitado');
        timer = datos.tiempo;
        iniciarPartida()

    } else if (estado === "TERMINADA") {
        detenerTimer();
        document.getElementById('imagen-original').style.display = 'block';
        document.getElementById('contenedor-piezas').style.display = 'none';
        document.getElementById('modal-body').innerHTML = '';
        if (datos.ganador == datos.jugador1Id) {
            document.getElementById('modal-body').innerHTML = '' +
                `<h3 class="text-center">Victoria de ${datos.jugador1Nombre}</h3>`;
        }

        if (datos.ganador == datos.jugador2Id) {
            document.getElementById('modal-body').innerHTML = '' +
                `<h3 class="text-center">Victoria de ${datos.jugador2Nombre}</h3>`;
        }

        document.getElementById('modal-footer').innerHTML = '';
        document.getElementById('modal-footer').innerHTML = '' +
            `<a href='/spring/rompecabezas/lobby'><button type="button" class="btn btn-primary" id="btn-next" data-bs-dismiss="modal">Volver</button></a>\n`;
        document.getElementById("modal_btn").click()
    }
}
