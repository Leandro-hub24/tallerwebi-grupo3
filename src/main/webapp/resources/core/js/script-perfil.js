// Simulación de datos del jugador
const playerProfile = {
    username: "El Rey del Rizz",
    profilePic: "https://via.placeholder.com/100/32cd32/00bfff?text=Rizz",
    totalPoints: "12,345,678",
    upgradesUnlocked: 25,
    totalUpgrades: 50,
    playTime: "5h 42min",
    unlockedTrophies: [
        { id: 1, name: "¡Nivel Skibidi!", icon: "https://via.placeholder.com/80/00bfff/fff?text=🚽" },
        { id: 2, name: "¡Nivel Maestro del Tung Tung!", icon: "https://via.placeholder.com/80/ff69b4/fff?text=🥁" },
        { id: 3, name: "¡Nivel Rizzlord!", icon: "https://via.placeholder.com/80/ffd700/fff?text=👑" },
        { id: 4, name: "¡Nivel Brain Rot Supremo!", icon: "https://via.placeholder.com/80/000/fff?text=🧠" },
    ],
    unlockedBackgrounds: [
        "https://via.placeholder.com/600x400/ff69b4/fff?text=Fondo+Meme+1",
        "https://via.placeholder.com/600x400/00bfff/fff?text=Fondo+Meme+2",
        "https://via.placeholder.com/600x400/32cd32/fff?text=Fondo+Meme+3"
    ]
};

// Referencias a los elementos HTML
const userNameElement = document.getElementById('user-name');
const profilePicElement = document.getElementById('profile-pic');
const totalPointsElement = document.getElementById('total-points');
const upgradesUnlockedElement = document.getElementById('upgrades-unlocked');
const playTimeElement = document.getElementById('play-time');
const trophyGridElement = document.getElementById('trophy-grid');
const backgroundGridElement = document.getElementById('background-grid');
const profileContainer = document.querySelector('.profile-container');

let selectedBackground = playerProfile.unlockedBackgrounds[0];

// Función para actualizar la UI con los datos del jugador
function updateProfileView() {
    userNameElement.textContent = playerProfile.username;
    profilePicElement.src = playerProfile.profilePic;
    totalPointsElement.textContent = playerProfile.totalPoints;
    upgradesUnlockedElement.textContent = `${playerProfile.upgradesUnlocked}/${playerProfile.totalUpgrades}`;
    playTimeElement.textContent = playerProfile.playTime;

    // Cargar logros
    playerProfile.unlockedTrophies.forEach(trophy => {
        const trophyItem = document.createElement('div');
        trophyItem.className = 'trophy-item';
        trophyItem.title = trophy.name;
        trophyItem.innerHTML = `<img src="${trophy.icon}" alt="${trophy.name}">`;
        trophyGridElement.appendChild(trophyItem);
    });

    // Cargar fondos de perfil
    playerProfile.unlockedBackgrounds.forEach(bgUrl => {
        const backgroundItem = document.createElement('div');
        backgroundItem.className = 'background-item';
        backgroundItem.style.backgroundImage = `url('${bgUrl}')`;
        backgroundItem.onclick = () => selectBackground(bgUrl);
        backgroundGridElement.appendChild(backgroundItem);
    });
}

// Función para seleccionar un nuevo fondo
function selectBackground(bgUrl) {
    selectedBackground = bgUrl;
    alert(`Fondo seleccionado: ${bgUrl}`);
    // Opcional: añadir un borde para indicar el fondo seleccionado
    const allBackgrounds = document.querySelectorAll('.background-item');
    allBackgrounds.forEach(item => item.style.border = '2px solid #ffd700');
    event.currentTarget.style.border = '2px solid #ff69b4';
}

// Función para aplicar el fondo seleccionado
function changeBackground() {
    profileContainer.style.backgroundImage = `url('${selectedBackground}')`;
    profileContainer.style.backgroundSize = 'cover';
    profileContainer.style.backgroundPosition = 'center';
    alert('¡Fondo de perfil cambiado!');
}

// Inicializar la vista
document.addEventListener('DOMContentLoaded', updateProfileView);