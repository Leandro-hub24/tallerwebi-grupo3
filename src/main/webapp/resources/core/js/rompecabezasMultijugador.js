
let matrizRompecabeza = [];

const dataURL_piezaVacia = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAwQYFqEAAAAASUVORK5CYII=';

let inicioTimer;
let finTimer;

let temporizador;



function timerVista(){
    console.log(timer);
    let tiempoInicio;
    if(timer == null){
        tiempoInicio = 0;
    } else  {
        tiempoInicio = new Date(timer).getTime()
    }

    let tiempo = Math.floor((Date.now() - tiempoInicio) / 1000 );
    const horas = document.getElementById('horas');
    const minutos = document.getElementById('minutos');
    const segundos = document.getElementById('segundos');
    //let tiempo = 0;

    temporizador = setInterval(() => {
        tiempo ++;

        const hor = Math.floor(tiempo / 3600);
        const min = Math.floor((tiempo % 3600) / 60);
        const seg = tiempo % 60;

        horas.innerHTML = String(hor).padStart(2, '0');
        minutos.innerHTML = String(min).padStart(2, '0');
        segundos.innerHTML = String(seg).padStart(2, '0');

        }, 1000);

}

function detenerTimer() {
    clearInterval(temporizador);
}

function iniciarPartida(){
    timerVista()

    inicioTimer = new Date().toISOString();
    console.log(inicioTimer);

    const imagenOriginal = document.getElementById('imagen-original');
    const contenedorPiezas = document.getElementById('contenedor-piezas');


        const anchoOriginal = imagenOriginal.width;
        const altoOriginal = imagenOriginal.height;

        const filas = 3;
        const columnas = 3;

        const anchoPieza = anchoOriginal / columnas;
        const altoPieza = altoOriginal / filas;

        const piezasArray = [];

        let anchoContenedor = "";
        let altoContenedor = "";

        if (altoOriginal >= anchoOriginal) {
            if(altoOriginal >= 1600){
                anchoContenedor = anchoOriginal * 0.4;
                altoContenedor = altoOriginal * 0.4;
            }

            if(altoOriginal > 1200 && altoOriginal < 1600){
                anchoContenedor = anchoOriginal * 0.5;
                altoContenedor = altoOriginal * 0.5;
            }

            if (altoOriginal > 800 && altoOriginal <= 1200){
                anchoContenedor = anchoOriginal * 0.6;
                altoContenedor = altoOriginal * 0.6;
            }

            if (altoOriginal > 600  && altoOriginal <= 800){
                anchoContenedor = anchoOriginal * 0.8;
                altoContenedor = altoOriginal * 0.8;
            }
        }

        if (anchoOriginal > altoOriginal) {
            if(anchoOriginal >= 1600){
                anchoContenedor = anchoOriginal * 0.5;
                altoContenedor = altoOriginal * 0.5;
            }

            if (anchoOriginal < 1600 && anchoOriginal > 1200){
                anchoContenedor = anchoOriginal * 0.6;
                altoContenedor = altoOriginal * 0.6;
            }

            if (anchoOriginal <= 1200 && anchoOriginal > 800){
                anchoContenedor = anchoOriginal * 0.7;
                altoContenedor = altoOriginal * 0.7;
            }

            if(anchoOriginal <= 800){
                anchoContenedor = anchoOriginal * 0.8;
                altoContenedor = altoOriginal * 0.8;
            }
        }



        imagenOriginal.style.height = `${altoContenedor}px`
        imagenOriginal.style.width = `${anchoContenedor}px`

        contenedorPiezas.innerHTML = '';
        contenedorPiezas.style.width = `${anchoContenedor}px`
        contenedorPiezas.style.height = `${altoContenedor}px`


        for (let y = 0; y < filas; y++) {
            for (let x = 0; x < columnas; x++) {

                const canvas = document.createElement('canvas');
                canvas.width = anchoPieza;
                canvas.height = altoPieza;
                const contexto = canvas.getContext('2d');

                const sx = x * anchoPieza;
                const sy = y * altoPieza;

                contexto.drawImage(
                    imagenOriginal,
                    sx, sy, anchoPieza, altoPieza,
                    0, 0, anchoPieza, altoPieza
                );

                const piezaImg = document.createElement('img');

                if (y == (filas - 1) && x == (columnas - 1)) {
                    piezaImg.src = dataURL_piezaVacia;
                    piezaImg.className = 'piezaImg piezaVacia';
                } else {
                    piezaImg.src = canvas.toDataURL('image/png');
                    piezaImg.className = 'piezaImg';
                }

                piezaImg.id = `img_${y}-${x}`;
                piezaImg.alt = `Pieza ${y * columnas + x}`;
                piezaImg.style.width = `calc(calc(${anchoContenedor}px/${filas}) - 3px)`;
                piezaImg.style.height = `calc(calc(${altoContenedor}px/${columnas}) - 3px)`;
                piezaImg.style.border = '1px solid #ccc';
                piezasArray.push(piezaImg);
            }
        }

        const piezasDesordenadas =  piezasArray //desordenarArray(piezasArray);
        let m = 0

        for (let y = 0; y < filas; y++) {
            matrizRompecabeza[y] = [];
            for (let x = 0; x < columnas; x++) {

                matrizRompecabeza[y][x] = [piezasDesordenadas[m].id, piezasDesordenadas[m].src];

                m++
            }
        }

        const tabla = document.createElement('table');
        tabla.className = 'tabla';
        const tbody = document.createElement('tbody');

        let piezaIndex = 0;
        for (let y = 0; y < filas; y++) {
            const fila = document.createElement('tr');
            for (let x = 0; x < columnas; x++) {
                const celda = document.createElement('td');
                celda.id = `td_${y}-${x}`;
                celda.className = 'td_celda';
                const pieza = piezasDesordenadas[piezaIndex];

                if (pieza) {
                    celda.appendChild(pieza);
                }

                fila.appendChild(celda);
                piezaIndex++;
            }
            tbody.appendChild(fila);
        }

        tabla.appendChild(tbody);
        contenedorPiezas.innerHTML = '';
        contenedorPiezas.appendChild(tabla);


        const piezas = document.getElementsByClassName('piezaImg');
        for (const pieza of piezas) {


                pieza.addEventListener('click', (event) => {

                    const idPiezaClickeada = event.target.id;
                    const idRompecabeza = document.getElementsByTagName('article').item(0).id.split('-')[1];
                    finTimer = new Date().toISOString();
                    console.log(finTimer);

                    const datosParaEnviar = {
                        matriz: matrizRompecabeza,
                        idRompecabeza: idRompecabeza,
                        idPieza: idPiezaClickeada,
                        inicioTimer: inicioTimer,
                        finTimer: finTimer,
                    };

                    const postData = JSON.stringify(datosParaEnviar);

                    const url = '/spring/rompecabezas';
                    const xhttp = new XMLHttpRequest();

                    xhttp.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {

                            const respuestaJSON = this.responseText;

                            try {
                                const data = JSON.parse(respuestaJSON);

                                const nuevaMatriz = data.matrizConCambios;
                                const mensaje = data.mensaje;

                                console.log("Nueva matriz recibida:", nuevaMatriz);

                                matrizRompecabeza = nuevaMatriz;

                                actualizarVistaRompecabezas(nuevaMatriz);
                                console.log("Mensaje recibido:", mensaje);
                                if (data.error){
                                    console.log(data.error);
                                }

                                if (mensaje === "Victoria"){

                                    finalizarPartida();

                                }

                            } catch (error) {
                                console.error("Error al parsear la respuesta JSON:", error);
                            }

                        }
                    };

                    xhttp.open("POST", url, true);
                    xhttp.setRequestHeader("Content-Type", "application/json");

                    xhttp.send(postData);

                })
        }
    //}

}

function finalizarPartida() {

    const url = '/spring/partida/finalizar';
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

        }
    };

    xhttp.open("POST", url, true);
    xhttp.send();


}

function actualizarVistaRompecabezas(nuevaMatriz) {

    let imgs = document.getElementsByClassName('piezaImg');
    let j = 0;
    for (let y = 0; y < nuevaMatriz.length; y++) {
        for (let x = 0; x < nuevaMatriz[y].length; x++) {

            const  nuevoId = nuevaMatriz[y][x][0];
            const nuevoSrc = nuevaMatriz[y][x][1];

            imgs[j].id = nuevoId;
            imgs[j].src = nuevoSrc;

            if (nuevoSrc === dataURL_piezaVacia) {
                imgs[j].classList.add('piezaVacia');
            } else {
                imgs[j].classList.remove('piezaVacia');
            }
            j++
        }
    }

}



function desordenarArray(array) {
    let currentIndex = array.length, randomIndex;

    while (currentIndex != 0) {

        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex--;

        [array[currentIndex], array[randomIndex]] = [
            array[randomIndex], array[currentIndex]];
    }

    return array;
}

