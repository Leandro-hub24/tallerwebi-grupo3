var stompClient = null;

document.addEventListener("DOMContentLoaded", function() {
    conectarAlLobby();
});

function conectarAlLobby() {
    var socket = new SockJS(wsConnectUrl);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Conectado al Lobby: ' + frame);

        stompClient.subscribe('/topic/lobby/nueva', function (mensaje) {
            const partida = JSON.parse(mensaje.body);
            agregarPartidaALista(partida);
        });

        stompClient.subscribe('/topic/lobby/removida', function (mensaje) {
            const partida = JSON.parse(mensaje.body);
            removerPartidaDeLista(partida);
        });
    });
}

function agregarPartidaALista(partida) {
    const lista = document.getElementById("listaDePartidas");
    const item = document.createElement("li");
    item.id = "partida-" + partida.id;

    item.innerHTML = `
                <span>${partida.nombre} (Creada por: ${partida.jugador1Nombre})</span>
                <a class="btn btn-info text-white" href="${partidaBaseUrl}${partida.id}">Unirse</a>
            `;
    lista.appendChild(item);
}

function removerPartidaDeLista(partida) {
    const item = document.getElementById("partida-" + partida.id);
    if (item) {
        item.remove();
    }
}