const brainrots = [
    { alias: "tuntuntunsahur", nombre: "Tuntuntun Sahur" },
    { alias: "tuntuntun", nombre: "Tuntuntun Sahur" },
    { alias: "tun tun tun", nombre: "Tuntuntun Sahur" },
    { alias: "tungtuntungsajur", nombre: "Tuntuntun Sahur" },
    { alias: "tuntuntusahur", nombre: "Tuntuntun Sahur" },

    { alias: "Tralalelotralala", nombre: "Tralalelo Tralala" },
    { alias: "Trararerotrarara", nombre: "Tralalelo Tralala" },
    { alias: "Tratratelotarala", nombre: "Tralalelo Tralala" },

    { alias: "chimpazinibananini", nombre: "Chimpancini Bananini" },
    { alias: "chimazinibaranini", nombre: "Chimpancini Bananini" },
    { alias: "chimpanibanini", nombre: "Chimpancini Bananini" },

    { alias: "Trippi troppi", nombre: "Trippi troppi" },
    { alias: "turipi turopi", nombre: "Trippi troppi" },
    { alias: "tipi topi", nombre: "Trippi troppi" },
];
// Configurar Fuse.js para búsqueda difusa
const fuse = new Fuse(brainrots, {
    keys: ["alias"],
    includeScore: true,
    threshold: 0.9,
    ignoreLocation: true,
});

function mostrarMensaje(tipo, texto) {
    const resultado = document.getElementById("resultado");
    resultado.textContent = texto;
    resultado.className = tipo;
    resultado.style.display = "block";
}

function iniciarReconocimiento() {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

    if (!SpeechRecognition) {
        mostrarMensaje("no-detectado", "❌ Tu navegador no soporta reconocimiento de voz.");
        return;
    }

    const recognition = new SpeechRecognition();
    recognition.lang = "it-IT";
    recognition.interimResults = false;
    recognition.continuous = false;

    // Mostrar que estamos escuchando
    mostrarMensaje("escuchando", "🎙️ Escuchando... hablá ahora");

    recognition.start();


    recognition.onresult = function (event) {
        let textoOriginal = event.results[0][0].transcript;
        let texto = textoOriginal.toLowerCase().trim();

        let resultado = fuse.search(texto);
        let inputTranscripcion = document.getElementById("inputTranscripcion");
        let form = document.getElementById("formVoz");
        let inputAliasDetectado = document.getElementById("inputAliasDetectado");
        inputAliasDetectado.value = "no asignado"
         inputTranscripcion.value = "no asignado transcripcion"

        console.log("🗣️ Texto reconocido:", texto);
        console.log("🔍 Resultados de Fuse:", resultado);

        let nombreDetectado;

        if (resultado.length > 0) {
            nombreDetectado = resultado[0].item.nombre;
            let aliasDetectado = resultado[0].item.alias;

            inputTranscripcion.value = nombreDetectado;
            inputAliasDetectado.value = aliasDetectado;

            mostrarMensaje("detectado", `✅ Se detectó: ${nombreDetectado}`);
            console.log("✅ Coincidencia encontrada:", nombreDetectado);
            console.log("🎯 Alias detectado:", aliasDetectado);
        } else {
            nombreDetectado = textoOriginal;
            inputTranscripcion.value = textoOriginal;
            mostrarMensaje("no-detectado", `❌ No detectado. Se usará: ${textoOriginal}`);
        }

        document.getElementById("textoReconocido").textContent = `🗣️ Dijiste: "${textoOriginal}"`;
        form.submit();
    };
}


