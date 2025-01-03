// Sélectionner tous les éléments qui ont une classe
const allElementsWithClasses = document.querySelectorAll('[class]');

// Fonction pour charger le contenu paresseux avec un délai
const loadLazyElement = (element) => {
  // Simuler un délai avant de charger le contenu (par exemple, 1 seconde)
  setTimeout(() => {
    // Si l'élément a un attribut 'data-src', il s'agit probablement d'une image
    if (element.hasAttribute('data-src') && element.tagName === 'IMG') {
      const src = element.getAttribute('data-src');
      element.src = src;  // Charger l'image
      element.classList.add('loaded');  // Ajouter une classe 'loaded' pour l'animation ou autre
    }
    
    // Si l'élément a un attribut 'data-video-src', il s'agit probablement d'une vidéo
    else if (element.hasAttribute('data-video-src') && element.tagName === 'VIDEO') {
      const src = element.getAttribute('data-video-src');
      element.querySelector('source').src = src;
      element.load();  // Charger la vidéo
      element.classList.add('loaded');  // Ajouter une classe 'loaded' pour l'animation ou autre
    }
    
    // Si l'élément a un attribut 'data-content', il s'agit peut-être d'une section de contenu à charger
    else if (element.hasAttribute('data-content')) {
      const content = element.getAttribute('data-content');
      // Ajouter des actions de chargement pour les sections ici
      element.classList.add('loaded');  // Ajouter une classe 'loaded' pour l'animation ou autre
    }

    // Ne plus observer cet élément une fois qu'il est chargé
    observer.unobserve(element);

  }, 500);  // Ajoute un délai de 1 seconde (1000 ms)
};

// Observer les éléments en utilisant IntersectionObserver
window.addEventListener('scroll', function() {
    const elements = document.querySelectorAll('.lazy-load');
    
    elements.forEach(element => {
      const rect = element.getBoundingClientRect();
      
      // Si l'élément est visible dans la fenêtre de scroll
      if (rect.top >= 0 && rect.bottom <= window.innerHeight) {
        // Charge le contenu ici
        loadLazyElement(element);
      }
    });
  });
  

// Ajouter chaque élément avec une classe à l'observateur
allElementsWithClasses.forEach(element => {
  observer.observe(element);
});
