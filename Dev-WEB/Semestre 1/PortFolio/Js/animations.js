const backButton = document.getElementById('backButton');
const header = document.querySelector('header');
const mousey = document.querySelector('.mousey');
const ripple = backButton.querySelector('.ripple');
const accueil = document.querySelector('.accueil');
let initialMarginTop = parseInt(window.getComputedStyle(accueil).marginTop, 10) || 0;


document.onreadystatechange = () => {
    if (document.readyState === 'complete') {
        const loader = document.getElementById('loader');
        const loaderStylesheet = document.querySelector('link[href="CSS/loader.css"]');
        const descElement = document.querySelector('.description');

        mousey.style.opacity = '0';
        loader.style.animation = "shrink-circle 0.5s ease forwards";

        loader.addEventListener('animationend', () => {
            loader.remove();
            descElement.classList.add('show');
            if (loaderStylesheet) loaderStylesheet.remove();
            setTimeout(() => {
            mousey.style.opacity = '1';
            mousey.classList.add('show')
            }, "1000");
        });
    }
};

document.getElementById('backButton').addEventListener('click', function() {
    ripple.style.animation = 'none';
    ripple.offsetWidth;
    ripple.style.animation = 'rippleEffect 0.4s ease-out';
});

window.addEventListener('scroll', () => {
    const headerHeight = header.offsetHeight;
    let scrollY = window.scrollY;
    let overlay = document.querySelector(".elementor-background-overlay");

    if (overlay) {
        let moveY = (scrollY * 0.1) + "px";
        overlay.style.transform = `translateY(${moveY})`;
    }

    if (window.scrollY > 50) {
        backButton.style.display = 'flex';
        header.classList.add('scrolled');
        accueil.style.marginTop = `${initialMarginTop + headerHeight}px`;
        mousey.style.opacity = '0';
        setTimeout(() => {
            backButton.style.opacity = '1';
            ripple.style.animation = 'rippleEffect 0.4s linear';
        }, 50);
    } else {
        accueil.style.marginTop = `${initialMarginTop}px`;
        mousey.style.opacity = '1';
        backButton.style.opacity = '0';
        header.classList.remove('scrolled');
        setTimeout(() => {
            if (window.scrollY < 50) {
                backButton.style.opacity = '0';
            }
        }, 500);
    }
});

const accordions = document.getElementsByClassName("accordion");
for (const accordion of accordions) {
    accordion.addEventListener("click", () => {
        const panel = accordion.nextElementSibling;
        const isActive = panel.classList.contains("show");
        if (isActive) {
            panel.classList.remove("show");
            accordion.classList.remove("active");
        } else {
            const panels = document.getElementsByClassName("panel");
            const allAccordions = document.getElementsByClassName("accordion");

            Array.from(panels).forEach(p => p.classList.remove("show"));
            Array.from(allAccordions).forEach(acc => acc.classList.remove("active"));
            
            panel.classList.add("show");
            accordion.classList.add("active");
        }
    });
}
