// Sélectionner les éléments de l'orbe et du cercle
const orb = document.querySelector('.cursor-orb');
const circle = document.querySelector('.cursor-circle');
// Variables pour la position de l'orbe et du cercle
let orbX = 0, orbY = 0; 
let circleX = 0, circleY = 0;
let mouseX = 0, mouseY = 0;
const speedOrb = 0.2;  // Vitesse de l'orbe (plus lent)
const speedCircle = 1.9;  // Vitesse du cercle (plus lent)
let lastUpdate = Date.now();



// Suivre la position du curseur
document.addEventListener('mousemove', updateMousePosition);

function updateMousePosition(e) {
    mouseX = e.pageX;
    mouseY = e.pageY;
}
// Déplacer l'orbe et le cercle avec un retard
function updateCursorPositions() {
    const now = Date.now();
    const deltaTime = now - lastUpdate;

    if (deltaTime > 11) {  // Limiter à environ 60 FPS
        // L'orbe suit la souris de manière fluide mais constante
        orbX += (mouseX - orbX) * speedOrb;
        orbY += (mouseY - orbY) * speedOrb;

        // Le cercle suit l'orbe de manière fluide mais constante
        circleX += (orbX - circleX) * speedCircle;
        circleY += (orbY - circleY) * speedCircle;

        // Mettre à jour la position de l'orbe
        orb.style.left = `${orbX - orb.offsetWidth / 2}px`;
        orb.style.top = `${orbY - orb.offsetHeight / 2}px`;

        // Mettre à jour la position du cercle
        circle.style.left = `${circleX - circle.offsetWidth / 2}px`;
        circle.style.top = `${circleY - circle.offsetHeight / 2}px`;

        lastUpdate = now;  // Mise à jour de la dernière frame
    }

    // Appeler la fonction updateCursorPositions à chaque image
    requestAnimationFrame(updateCursorPositions);
}



// Lancer l'animation
updateCursorPositions();

const buttons = document.querySelectorAll('.interactive');
const parts = document.querySelectorAll('section');

buttons.forEach(button => {
    button.addEventListener('mouseenter', () => {
        orb.style.transform = 'scale(3.5)';
        orb.style.opacity = '0.5';
    });

    button.addEventListener('mouseleave', () => {
        orb.style.transform = 'scale(1)'; 
        orb.style.opacity = '1';
    });
});

parts.forEach(part => {
    // Ajouter un effet sur le survol
    part.addEventListener('mouseenter', () => {
        orb.style.transform = 'scale(2.5)'; 
        orb.style.opacity = '0.8';
        orb.style.borderRadius = '0';
        orb.style.clipPath = `polygon(44% 0,  56% 0,  56% 44%, 100% 44%, 100% 56%, 56% 56%, 56% 100%, 44% 100%, 44% 56%, 0 56%,  0 44%, 44% 44%)`;
    });

    // Réinitialiser l'effet en quittant la section
    part.addEventListener('mouseleave', () => {
        orb.style.transform = 'scale(1)'; // Taille initiale
        orb.style.opacity = '1'; // Opacité initiale
        orb.style.borderRadius = '50%'; // Retour au cercle
        orb.style.clipPath = 'circle(50% at 50% 50%)'; // Annuler la croix
    });
});