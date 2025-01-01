document.getElementById('backButton').addEventListener('click', function(e) {
    const ripple = this.querySelector('.ripple');
    
    // Réinitialise l'animation de l'onde (pour qu'elle puisse être relancée)
    ripple.style.animation = 'none';
    
    // Forcer la réinitialisation de l'animation
    void ripple.offsetWidth;
    
    // Lance l'animation d'onde
    ripple.style.animation = 'rippleEffect 0.6s ease-out';
});