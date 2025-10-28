let tiempoRestante = /*[[${tiempoRestante}]]*/ 30; // en segundos
const tiempoMaximo = tiempoRestante;
const barra = document.getElementById("barraTiempo");

let temporizadorIniciado = false;
let tiempoInicio;
let rafID;

function iniciarTemporizador() {
    let form = document.getElementById("formVoz");
    if (temporizadorIniciado) return;

    temporizadorIniciado = true;
    tiempoInicio = performance.now();

    function animar(now) {
        const transcurrido = (now - tiempoInicio) / 1000;
        const restante = Math.max(0, tiempoMaximo - transcurrido);
        const porcentaje = (restante / tiempoMaximo) * 100;
        let inputTiempo = document.getElementById("inputTiempo")

        // Actualizar barra
        barra.style.width = `${porcentaje}%`;
        barra.innerText = `${Math.ceil(restante)}s`;

        // Cambiar color según porcentaje restante
        barra.classList.remove("bg-success", "bg-warning", "bg-danger");
        if (porcentaje > 60) {
            barra.classList.add("bg-success");
        } else if (porcentaje > 30) {
            barra.classList.add("bg-warning");
        } else {
            barra.classList.add("bg-danger");
        }
        inputTiempo.value = transcurrido;

        if (restante > 0) {
            rafID = requestAnimationFrame(animar);
        } else {
            barra.innerText = "0s";
            form.submit();
        }
    }

    requestAnimationFrame(animar);
}
