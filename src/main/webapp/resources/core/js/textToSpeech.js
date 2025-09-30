const brainrots = [
    { alias: "tun tun tun sahur", nombre: "Tuntuntun Sahur" },
    { alias: "tun tun tun ", nombre: "Tuntuntun Sahur" },
    { alias: "tung tun tung sajur", nombre: "Tuntuntun Sahur" },
    { alias: "tuntuntusahur", nombre: "Tuntuntun Sahur" },

    { alias: "Tralalelo tralala", nombre: "Tralalelo Tralala" },
    { alias: "Trararero trarara", nombre: "Tralalelo Tralala" },
    { alias: "Tratratelo tarala", nombre: "Tralalelo Tralala" },

    { alias: "chimpazini bananini", nombre: "Chimpancini Bananini" },
    { alias: "chimazini baranini", nombre: "Chimpancini Bananini" },
    { alias: "chimpani banini", nombre: "Chimpancini Bananini" },

    { alias: "Trippi troppi", nombre: "Trippi troppi" },
    { alias: "turipi turopi", nombre: "Trippi troppi" },
    { alias: "tipi topi", nombre: "Trippi troppi" },
];
const fuse = new Fuse(brainrots, {
    keys: ["alias"],       // buscá por alias
    includeScore: true,
    threshold: 0.9          // ajustá sensibilidad
});

function mostrarMensaje(tipo, texto) {
    const resultado = document.getElementById("resultado");
    resultado.textContent = texto;
    resultado.className = tipo === "detectado" ? "detectado" : "no-detectado";
    resultado.style.display = "block";
}

// Iniciar reconocimiento
function iniciarReconocimiento() {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SpeechRecognition) {
        mostrarMensaje("no-detectado", "❌ Tu navegador no soporta reconocimiento de voz.");
        return;
    }

    const recognition = new SpeechRecognition();
    recognition.lang = "es-ES";
    recognition.interimResults = false;

    // Mostrar que se está escuchando
    mostrarMensaje("escuchando", "🎙️ Escuchando... hablá ahora");

    recognition.start();

    recognition.onresult = function(event) {
        const texto = event.results[0][0].transcript.toLowerCase().trim();
        document.getElementById("textoReconocido").textContent = `🗣️ Dijiste: "${texto}"`;

        const resultado = fuse.search(texto);

        if (resultado.length > 0) {
            const nombre = resultado[0].item.nombre;
            mostrarMensaje("detectado", `${nombre}`);
        } else {
            mostrarMensaje("no-detectado", "❌ No se detectó ningún brainrot conocido.");
        }


        // Mostrar en el div
        document.getElementById("resultado").innerText = texto;

        // Pasar al input oculto
        document.getElementById("inputTranscripcion").value = texto;

        // Enviar el formulario automáticamente
        document.getElementById("formVoz").submit();
    };

    recognition.onerror = function(event) {
        mostrarMensaje("no-detectado", `⚠️ Error: ${event.error}`);
    };
}