const brainrots = [
    { alias: "tun tun tun sahur", nombre: "tung tung tung sahur" },
    { alias: "tun tun tun ", nombre: "tung tung tung sahur" },
    { alias: "tung tun tung sajur", nombre: "tung tung tung sahur" },
    { alias: "tuntuntusahur", nombre: "tung tung tung sahur" },

    { alias: "Tralalelo tralala", nombre: "Tralalelo tralala" },
    { alias: "Trararero trarara", nombre: "Tralalelo tralala" },
    { alias: "Tratratelo tarala", nombre: "Tralalelo tralala" },

    { alias: "chimpazini bananini", nombre: "chimpazini bananini" },
    { alias: "chimazini baranini", nombre: "chimpazini bananini" },
    { alias: "chimpani banini", nombre: "chimpazini bananini" },

    { alias: "Trippi troppi", nombre: "Trippi troppi" },
    { alias: "turipi turopi", nombre: "Trippi troppi" },
    { alias: "tipi topi", nombre: "Trippi troppi" },
];
const fuse = new Fuse(brainrots, {
    keys: ["alias"],       // buscá por alias
    includeScore: true,
    threshold: 0.9          // ajustá sensibilidad
});
function iniciarReconocimiento() {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SpeechRecognition) {
        alert("Tu navegador no soporta reconocimiento de voz.");
        return;
    }
    alert("🧠 Brainrot detectado: ");
    const recognition = new SpeechRecognition();
    recognition.lang = 'es-ES'; // o 'it-IT'
    recognition.interimResults = false;

    recognition.start();

    recognition.onresult = function(event) {
        const texto = event.results[0][0].transcript.toLowerCase().trim();
        document.getElementById("textoReconocido").textContent = texto;

        const resultado = fuse.search(texto);

        if (resultado.length > 0 && resultado[0].score < 0.9) {
            const nombreDetectado = resultado[0].item.nombre;
            alert("🧠 Brainrot detectado: " + nombreDetectado);
        } else {
            alert("❌ No se detectó ningún brainrot italiano");
        }
    };

    recognition.onerror = function(event) {
        console.error("Error en reconocimiento:", event.error);
        alert("Error: " + event.error);
    };
}