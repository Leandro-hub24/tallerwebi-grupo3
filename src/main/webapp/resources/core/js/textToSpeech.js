const brainrots = [


    {alias: "tuntuntunsahur", nombre: "Tuntuntun Sahur"},
    {alias: "tuntuntun", nombre: "Tuntuntun Sahur"},


    {alias: "Tralalelo tralala", nombre: "Tralalelo Tralala"},
    {alias: "Trararerotrarara", nombre: "Tralalelo Tralala"},
    {alias: "Tratratelotarala", nombre: "Tralalelo Tralala"},

    {alias: "chimpazinibananini", nombre: "Chimpancini Bananini"},
    {alias: "chimazinibaranini", nombre: "Chimpancini Bananini"},
    {alias: "chimpanibanini", nombre: "Chimpancini Bananini"},

    {alias: "Trippi troppi", nombre: "Trippi troppi"},
    {alias: "turipi turopi", nombre: "Trippi troppi"},
    {alias: "tipi topi", nombre: "Trippi troppi"},


    { alias: "balerina capuchina", nombre: "Ballerina Capuccina" },
    { alias: "bailarina capuchina", nombre: "Ballerina Capuccina" },
    { alias: "balanina capuchina", nombre: "Ballerina Capuccina" },

    { alias: "bombardiro crocodilo", nombre: "Bombardiro Cocodrilo" },
    { alias: "bombaldilo cocodilo", nombre: "Bombardiro Cocodrilo" },
    { alias: "bombadilo crocodilo", nombre: "Bombardiro Cocodrilo" },

    { alias: "bu bu patapi", nombre: "Brr Brr Patapi" },
    { alias: "br br patapi", nombre: "Brr Brr Patapi" },
    { alias: "bru bru patapi", nombre: "Brr Brr Patapi" },

    { alias: "capiballero cocosini", nombre: "Capiballero Cocosini" },
    { alias: "capibashero cocosini", nombre: "Capiballero Cocosini" },
    { alias: "cabipashero cocosini", nombre: "Capiballero Cocosini" },

    { alias: "los tralaleritos", nombre: "Los Tralaleritos" },
    { alias: "los lalaritos", nombre: "Los Tralaleritos" },
    { alias: "los tralalelitos", nombre: "Los Tralaleritos" },

    { alias: "lirili larila", nombre: "LiririLarila" },
    { alias: "ririri rarira", nombre: "LiririLarila" },
    { alias: "lilili lalila", nombre: "LiririLarila" },

    { alias: "saturno saturnita", nombre: "Saturno Saturnita" },
    { alias: "satuno satunita", nombre: "Saturno Saturnita" },
    { alias: "saltulno satulnita", nombre: "Saturno Saturnita" },


];
//Fuse.js para búsqueda de palabras difusa
const fuse = new Fuse(brainrots, {
    keys: ["alias"],
    includeScore: true,
    threshold: 0.9,
    ignoreLocation: true,
});

// esto modifica el mensaje en la vista verificar
function mostrarMensaje(tipo, texto) {
    const resultado = document.getElementById("resultado");
    resultado.textContent = texto;
    resultado.className = tipo;
    resultado.style.display = "block";
}


function iniciarReconocimiento() {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    iniciarTemporizador();

    if (!SpeechRecognition) {
        mostrarMensaje("no-detectado", "❌ Tu navegador no soporta reconocimiento de voz.");
        return;
    }

    const recognition = new SpeechRecognition();
    recognition.lang = "es-ES";
    recognition.interimResults = false;
    recognition.continuous = false;
    let boton = document.getElementById("btn-voz");

    // Muestra que se activo la escucha de voz
    mostrarMensaje("escuchando", "🎙️ Escuchando... hablá ahora");
    boton.classList.add("escuchando");
    recognition.start();


    recognition.onresult = function (event) {
        let contador = 0;
        let textoOriginal = event.results[0][0].transcript;
        let texto = textoOriginal.toLowerCase().trim();
        let resultado = fuse.search(texto);
        let inputTranscripcion = document.getElementById("inputTranscripcion");
        let form = document.getElementById("formVoz");
        let inputAliasDetectado = document.getElementById("inputAliasDetectado");
        let inputCantidadIntentos = document.getElementById("inputCantidadIntentos");


        let nombreDetectado;

        if (resultado.length > 0) {
            // si se reconoce una palabra se guardan los valores al div para enviarlos despues con el form
            nombreDetectado = resultado[0].item.nombre;
            let aliasDetectado = resultado[0].item.alias;
            inputTranscripcion.value = nombreDetectado;
            inputAliasDetectado.value = aliasDetectado;
            inputCantidadIntentos.value = contador;


            mostrarMensaje("detectado", `✅ Se detectó: ${nombreDetectado}`);
        } else {
            contador++;
            inputTranscripcion.value = textoOriginal;
            mostrarMensaje("no-detectado", `❌ No detectado. Se usará: ${textoOriginal}`);
        }
        boton.classList.remove("escuchando");

        document.getElementById("textoReconocido").textContent = `🗣️ Dijiste: "${textoOriginal}"`;
        form.submit();
    };
}


