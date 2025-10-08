// ... (variables iniciales)
const matrizRompecabezaResolucion = [];
let matrizRompecabeza = [];

// 💡 Data URL de 1x1 píxel transparente para la pieza vacía
const dataURL_piezaVacia = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAwQYFqEAAAAASUVORK5CYII=';
const btnComenzar = document.getElementById('btn-comenzar');

document.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById("modal_btn").click()
});

btnComenzar.addEventListener('click', (event) => {
    // Obtener los elementos HTML
    const imagenOriginal = document.getElementById('imagen-original');
    const contenedorPiezas = document.getElementById('contenedor-piezas');

    //imagenOriginal.onload = function () {
        // Dimensiones de la imagen original
        const anchoOriginal = imagenOriginal.width;
        const altoOriginal = imagenOriginal.height;

        // Número de filas y columnas para el recorte
        const filas = 3;
        const columnas = 3;

        // Dimensiones de cada pieza
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


        imagenOriginal.height = altoContenedor;
        imagenOriginal.width = anchoContenedor;


        // Limpiar el contenedor antes de añadir nuevas piezas
        contenedorPiezas.innerHTML = '';
        contenedorPiezas.style.width = `${anchoContenedor}px`
        contenedorPiezas.style.height = `${altoContenedor}px`

        // Iterar sobre las filas y columnas para crear las 16 piezas
        for (let y = 0; y < filas; y++) {
            matrizRompecabezaResolucion[y] = [];
            for (let x = 0; x < columnas; x++) {
                // Crear un nuevo canvas para cada pieza
                const canvas = document.createElement('canvas');
                canvas.width = anchoPieza;
                canvas.height = altoPieza;
                const contexto = canvas.getContext('2d');

                // Calcular la posición de la pieza en la imagen original
                const sx = x * anchoPieza;
                const sy = y * altoPieza;

                // Dibujar la porción de la imagen original en el nuevo canvas
                contexto.drawImage(
                    imagenOriginal,
                    sx, sy, anchoPieza, altoPieza,
                    0, 0, anchoPieza, altoPieza
                );

                // Crear un nuevo elemento img y asignarle la imagen recortada
                // ** CREAR UN NUEVO ELEMENTO IMG EN AMBOS CASOS **
                const piezaImg = document.createElement('img');

                if (y == (filas - 1) && x == (columnas - 1)) {
                    // 💡 Caso: Pieza Vacía. Almacena el Data URL blanco/transparente.
                    matrizRompecabezaResolucion[y][x] = [`${y}-${x}`, dataURL_piezaVacia];
                    piezaImg.src = dataURL_piezaVacia;
                    piezaImg.className = 'piezaImg piezaVacia'; // Añadir clase para estilos CSS
                } else {
                    // 💡 Caso: Pieza con Imagen.
                    matrizRompecabezaResolucion[y][x] = [`${y}-${x}`, canvas.toDataURL('image/png')];
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

        // Crear la tabla
        const tabla = document.createElement('table');
        tabla.className = 'tabla';
        const tbody = document.createElement('tbody');

        // Llenar la tabla con las piezas desordenadas
        let piezaIndex = 0;
        for (let y = 0; y < filas; y++) {
            const fila = document.createElement('tr');
            for (let x = 0; x < columnas; x++) {
                const celda = document.createElement('td');
                celda.id = `td_${y}-${x}`;
                celda.className = 'td_celda';
                const pieza = piezasDesordenadas[piezaIndex];

                // **Verificación añadida aquí**
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
                    // 1. Obtener el ID de la pieza clickeada
                    const idPiezaClickeada = event.target.id;
                    const idRompecabeza = document.getElementsByTagName('article').item(0).id.split('-')[1];
                    // 2. Crear un OBJETO que contenga AMBOS datos (Matriz y ID)
                    const datosParaEnviar = {
                        matriz: matrizRompecabeza, // La matriz actual
                        idRompecabeza: idRompecabeza,
                        idPieza: idPiezaClickeada   // El ID de la pieza que se quiere mover
                    };

                    // 3. Convertir el objeto COMPLETO a JSON para el cuerpo del POST
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
                                    const nivelNuevo = data.nivelNuevo
                                    document.getElementById('imagen-original').style.display = 'block';
                                    document.getElementById('contenedor-piezas').style.display = 'none';
                                    document.getElementById('modal-body').innerHTML = '';
                                    document.getElementById('modal-body').innerHTML = '' +
                                        '<h3 class="text-center">Victoria</h3>';
                                    document.getElementById('modal-footer').innerHTML = '';
                                    document.getElementById('modal-footer').innerHTML = '' +
                                        `<a href='/spring/rompecabezas/${nivelNuevo}'><button type="button" class="btn btn-primary" id="btn-next" data-bs-dismiss="modal">Siguiente nivel</button></a>\n` +
                                        '<button type="button" class="btn btn-primary" id="btn-niveles" >Seleccionar nivel</button>';
                                    document.getElementById('btn-niveles').addEventListener('click', (event) => {
                                        cargarNiveles();
                                    })

                                    document.getElementById("modal_btn").click()


                                }

                            } catch (error) {
                                console.error("Error al parsear la respuesta JSON:", error);
                            }

                        }
                    };

                    // 4. Configurar el Header para indicar que estás enviando JSON
                    xhttp.open("POST", url, true);
                    xhttp.setRequestHeader("Content-Type", "application/json");

                    // 5. Enviar solo el JSON combinado
                    xhttp.send(postData);

                })
        }
    //}

});

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

document.getElementById('btn-niveles').addEventListener('click', (event) => {
    cargarNiveles();
})

function cargarNiveles() {
    const url = '/spring/rompecabezas/niveles';
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            const respuestaJSON = this.responseText;

            try {
                const data = JSON.parse(respuestaJSON);
                const niveles = data.niveles;
                console.log(niveles);
                document.getElementById('modal-title').innerHTML = '';
                document.getElementById('modal-title').innerHTML = 'Niveles';
                document.getElementById('modal-body').style.overflowY = 'scroll';
                document.getElementById('modal-body').innerHTML = '';
                niveles.forEach(nivel => {
                    document.getElementById('modal-body').innerHTML += '' +
                        `<a class="d-flex justify-content-center mb-2" href='/spring/rompecabezas/${nivel.id}'><div style="height: auto; width: 250px" class="card"><div class="card-header"><h4 class="card-title">${nivel.nombre}</h4></div><div class="card-body"><img class="card-img" src='/spring${nivel.urlImg}'></div></div></a>`;

                })

            } catch (error) {
                console.error("Error al parsear la respuesta JSON:", error);
            }

        }

    };

    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");

    xhttp.send();
}



function desordenarArray(array) {
    let currentIndex = array.length, randomIndex;

    // Mientras queden elementos a mezclar...
    while (currentIndex != 0) {
        // Seleccionar un elemento sin mezclar...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex--;

        // E intercambiarlo con el elemento actual.
        [array[currentIndex], array[randomIndex]] = [
            array[randomIndex], array[currentIndex]];
    }

    return array;
}

