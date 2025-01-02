document.onreadystatechange = () => {
    if (document.readyState === 'complete') {
        const loader = document.getElementById('loader');
        const loaderStylesheet = document.querySelector('link[href="CSS/loader.css"]');
        
        // Appliquer l'animation
        loader.style.animation = "shrink-circle 0.5s ease forwards";

        // Supprimer le loader et la feuille de style une fois l'animation terminée
        loader.addEventListener('animationend', () => {
            loader.remove(); // Retirer le loader du DOM
            if (loaderStylesheet) {
                loaderStylesheet.remove(); // Retirer la feuille de style du DOM pour libérer la mémoire
            }
        });
    }
};

document.getElementById('backButton').addEventListener('click', function(e) {
    const ripple = this.querySelector('.ripple');
    
    // Réinitialise l'animation de l'onde (pour qu'elle puisse être relancée)
    ripple.style.animation = 'none';
    
    // Forcer la réinitialisation de l'animation
    void ripple.offsetWidth;
    
    // Lance l'animation d'onde
    ripple.style.animation = 'rippleEffect 0.3s ease-out';
});

window.addEventListener('scroll', () => {
    const backButton = document.getElementById('backButton');
    const ripple = backButton.querySelector('.ripple'); // Sélectionne l'élément ripple
    const header = document.querySelector('header'); // Sélectionner le header

    // Logique pour afficher ou masquer le bouton de retour avec effet fade-in
    if (window.scrollY > 100) {
        backButton.style.display = 'flex';
        header.classList.add('scrolled');
        setTimeout(() => {
            backButton.style.opacity = '1';
            // Lancer l'animation de l'onde de choc
            ripple.style.animation = 'rippleEffect 0.3s linear';
        }, 50);
    } else {
        backButton.style.opacity = '0';
        header.classList.remove('scrolled');
        setTimeout(() => {
            if (window.scrollY <= 100) {
                backButton.style.display = 'none';
                
            }
        }, 500);
    }
});


// Sélectionner l'élément scrollable
const scrollableContent = document.querySelector('.scrollable-content');

// Variable pour limiter le fade-out
let scrollTimeout;

// Fonction pour afficher ou cacher la scrollbar avec une animation de fade-in/fade-out
function handleScroll() {
    // Lorsque l'utilisateur défile, afficher la scrollbar
    scrollableContent.classList.add('scroll-active');

    // Si un timeout est déjà actif, on le réinitialise
    if (scrollTimeout) {
        clearTimeout(scrollTimeout);
    }

    // Démarrer un nouveau timeout pour cacher la scrollbar après 1 seconde sans défilement
    scrollTimeout = setTimeout(() => {
        scrollableContent.classList.remove('scroll-active');
    }, 1000); // 1 seconde d'attente
}

// Écouter l'événement de défilement
scrollableContent.addEventListener('scroll', handleScroll);


