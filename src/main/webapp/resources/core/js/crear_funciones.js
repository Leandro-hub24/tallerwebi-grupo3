
const seleccionadas = [];

function toggleSeleccion(imgElem) {
    const id = imgElem.getAttribute("data-id");
    const index = seleccionadas.indexOf(id);

    if (index === -1) {
        seleccionadas.push(id);
        imgElem.classList.add("imagen-seleccionada");
    } else {
        seleccionadas.splice(index, 1);
        imgElem.classList.remove("imagen-seleccionada");
    }

    document.getElementById("imagenesSeleccionadas").value = seleccionadas.join(",");
}
